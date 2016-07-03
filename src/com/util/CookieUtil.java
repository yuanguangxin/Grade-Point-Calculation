package com.util;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class CookieUtil {

    public Content content;
    public List<String> lsit;
    public Map<String, String> resmap;
    public final static String CONTENT_TYPE = "Content-Type";

    //得到页面Cookie,并返回一个Map
    public Map<String, String> getCookie(List<String> lsit) {
        Map<String, String> resmap = new HashMap<>();
        if (lsit != null) {
            StringBuffer sb = new StringBuffer();
            boolean isLast = false;
            int i = 0;
            for (String val : lsit) {
                i++;
                if (i == lsit.size()) {
                    isLast = true;
                }
                int pos = val.indexOf("=");
                if (pos != -1) {
                    String cookieName = val.substring(0, pos);
                    String cookieVal = val.substring(pos + 1);
                    cookieVal = cookieVal.split(";")[0];
                    if (isLast) {
                        sb.append(cookieName + "=" + cookieVal);
                    } else {
                        sb.append(cookieName + "=" + cookieVal + ";");
                    }
                }
            }
            resmap.put("Cookie", sb.toString());
        }
        return resmap;
    }

    public void getCode(){
        content = getRandom("GET", "http://ssfw1.hlju.edu.cn/ssfw/jwcaptcha.do", null, null, false, "img");
        InputStreamCopy isc=new InputStreamCopy("img/code.bmp","/Users/yuanguangxin/Desktop/Grade Point Calculation/out/artifacts/Grade_Point_Calculation_war_exploded/img/");
        if(content==null){
            getCode();
        }
        lsit = content.getHeaders().get("Set-Cookie");
        resmap = getCookie(lsit);
    }

    public String getBody(String userCode,String password,String a) {
        String loginUrl = "http://ssfw2.hlju.edu.cn/ssfw/j_spring_ids_security_check";
        String rateReviewUrl = "http://ssfw3.hlju.edu.cn/ssfw/zhcx/cjxx.do";
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("j_username", userCode);
        paramMap.put("j_password", password);
        paramMap.put("validateCode", a + "");
        content = curl("POST", loginUrl, paramMap, resmap, false, "");
        //得到登录后的Cookie,用作通行证
        resmap = getCookie(lsit);
        paramMap = new HashMap<>();
        content = curl("POST", rateReviewUrl, paramMap, resmap, false, "");
        inFile(content.getBody(), "/Users/yuanguangxin/Desktop/sss.txt");
        return content.getBody();
    }

    public Content curl(String method, //方法类型
                               String sUrl,//要解析的URL
                               Map<String, String> paramMap, //存放用户名和密码的map
                               Map<String, String> requestHeaderMap,//存放COOKIE的map
                               boolean isOnlyReturnHeader,
                               String path) {//存放文件路径
        Content content = null;
        HttpURLConnection httpUrlConnection = null;
        InputStream in = null;
        try {
            URL url = new URL(sUrl);
            boolean isPost = "POST".equals(method);

            if (method == null || (!"GET".equalsIgnoreCase(method) && !"POST".equalsIgnoreCase(method))) {
                method = "POST";
            }

            URL resolvedURL = url;
            URLConnection urlConnection = resolvedURL.openConnection();
            httpUrlConnection = (HttpURLConnection) urlConnection;
            httpUrlConnection.setRequestMethod(method);
            httpUrlConnection.setRequestProperty("Accept-Language", "zh-cn,zh;q=0.5");

            httpUrlConnection.setInstanceFollowRedirects(false);
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);
            urlConnection.setUseCaches(false);
            urlConnection.setDefaultUseCaches(false);
            if (requestHeaderMap != null) {
                for (Map.Entry<String, String> entry : requestHeaderMap.entrySet()) {
                    String key = entry.getKey();
                    String val = entry.getValue();
                    if (key != null && val != null) {
                        urlConnection.setRequestProperty(key, val);
                    }
                }
            }
            if (isPost) {
                urlConnection.setDoOutput(true);
                ByteArrayOutputStream bufOut = new ByteArrayOutputStream();
                boolean firstParam = true;
                for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                    String encName = URLEncoder.encode(entry.getKey(), "UTF-8");
                    if (firstParam) {
                        firstParam = false;
                    } else {
                        bufOut.write((byte) '&');
                    }
                    String encValue = URLEncoder.encode(entry.getValue(), "UTF-8");
                    bufOut.write(encName.getBytes("UTF-8"));
                    bufOut.write((byte) '=');
                    bufOut.write(encValue.getBytes("UTF-8"));
                }
                byte[] postContent = bufOut.toByteArray();
                if (urlConnection instanceof HttpURLConnection) {
                    ((HttpURLConnection) urlConnection).setFixedLengthStreamingMode(postContent.length);
                }
                OutputStream postOut = urlConnection.getOutputStream();
                postOut.write(postContent);
                postOut.flush();
                postOut.close();
            }
            httpUrlConnection.connect();
            int responseCode = httpUrlConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_MOVED_PERM || responseCode == HttpURLConnection.HTTP_MOVED_TEMP) {
                String location = httpUrlConnection.getHeaderField("Location");
                URL newAction = new URL(url, location);
                StringBuffer newUrlSb = new StringBuffer(newAction.getProtocol() + "://" + newAction.getHost());
                if (newAction.getPort() != -1) {
                    newUrlSb.append(":" + newAction.getPort());
                }
                if (newAction.getPath() != null) {
                    newUrlSb.append(newAction.getPath());
                }
                if (newAction.getQuery() != null) {
                    newUrlSb.append("?" + newAction.getQuery());
                }
                if (newAction.getRef() != null) {
                    newUrlSb.append("#" + newAction.getRef());
                }

                return curl("POST", newUrlSb.toString(), paramMap, requestHeaderMap, isOnlyReturnHeader, path);
            } else if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                byte[] bytes = new byte[0];
                if (!isOnlyReturnHeader) {
                    if (isPost) {
                        in = httpUrlConnection.getInputStream();
                        ByteArrayOutputStream bout = new ByteArrayOutputStream();
                        byte[] buf = new byte[1024];
                        while (true) {
                            int rc = in.read(buf);
                            if (rc <= 0) {
                                break;
                            } else {
                                bout.write(buf, 0, rc);
                            }
                        }
                        bytes = bout.toByteArray();
                        in.close();
                    }
                }
                String encoding = null;
                if (encoding == null) {
                    encoding = getEncodingFromContentType(httpUrlConnection.getHeaderField(CONTENT_TYPE));
                }
                content = new Content(sUrl, new String(bytes, encoding), httpUrlConnection.getHeaderFields());
            }
        } catch (Exception e) {
            return null;
        } finally {
            if (httpUrlConnection != null) {
                httpUrlConnection.disconnect();
            }
        }
        return content;
    }


    public Content getRandom(String method,
                                    String sUrl,//要解析的url
                                    Map<String, String> paramMap, //存放用户名和密码的map
                                    Map<String, String> requestHeaderMap,//存放COOKIE的map
                                    boolean isOnlyReturnHeader,
                                    String path) {

        Content content = null;
        HttpURLConnection httpUrlConnection = null;
        InputStream in = null;
        try {
            URL url = new URL(sUrl);
            boolean isPost = "POST".equals(method);
            if (method == null || (!"GET".equalsIgnoreCase(method) && !"POST".equalsIgnoreCase(method))) {
                method = "POST";
            }
            URL resolvedURL = url;
            URLConnection urlConnection = resolvedURL.openConnection();
            httpUrlConnection = (HttpURLConnection) urlConnection;
            httpUrlConnection.setRequestMethod(method);
            httpUrlConnection.setRequestProperty("Accept-Language", "zh-cn,zh;q=0.5");
            httpUrlConnection.setInstanceFollowRedirects(false);
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setDoInput(true);
            httpUrlConnection.setConnectTimeout(5000);
            httpUrlConnection.setReadTimeout(5000);
            httpUrlConnection.setUseCaches(false);
            httpUrlConnection.setDefaultUseCaches(false);
            httpUrlConnection.connect();

            int responseCode = httpUrlConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                byte[] bytes = new byte[0];
                if (!isOnlyReturnHeader) {
                    DataInputStream ins = new DataInputStream(httpUrlConnection.getInputStream());
                    //验证码的位置
                    DataOutputStream out = new DataOutputStream(new FileOutputStream(path + "/code.bmp"));
                    byte[] buffer = new byte[4096];
                    int count = 0;
                    while ((count = ins.read(buffer)) > 0) {
                        out.write(buffer, 0, count);
                    }
                    out.close();
                    ins.close();
                }
                String encoding = null;
                if (encoding == null) {
                    encoding = getEncodingFromContentType(httpUrlConnection.getHeaderField(CONTENT_TYPE));
                }
                content = new Content(sUrl, new String(bytes, encoding), httpUrlConnection.getHeaderFields());
            }
        } catch (Exception e) {
            return null;
        } finally {
            if (httpUrlConnection != null) {
                httpUrlConnection.disconnect();
            }
        }
        return content;
    }

    public static String getEncodingFromContentType(String contentType) {
        String encoding = null;
        if (contentType == null) {
            return null;
        }
        StringTokenizer tok = new StringTokenizer(contentType, ";");
        if (tok.hasMoreTokens()) {
            tok.nextToken();
            while (tok.hasMoreTokens()) {
                String assignment = tok.nextToken().trim();
                int eqIdx = assignment.indexOf('=');
                if (eqIdx != -1) {
                    String varName = assignment.substring(0, eqIdx).trim();
                    if ("charset".equalsIgnoreCase(varName)) {
                        String varValue = assignment.substring(eqIdx + 1).trim();
                        if (varValue.startsWith("\"") && varValue.endsWith("\"")) {
                            varValue = varValue.substring(1, varValue.length() - 1);
                        }
                        if (Charset.isSupported(varValue)) {
                            encoding = varValue;
                        }
                    }
                }
            }
        }
        if (encoding == null) {
            return "UTF-8";
        }
        return encoding;
    }


    public static boolean inFile(String content, String path) {
        PrintWriter out = null;
        File file = new File(path);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            out = new PrintWriter(new FileWriter(file));

            out.write(content);
            out.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
        return false;
    }
}



