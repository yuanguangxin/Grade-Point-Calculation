package party.csti.gpa.util.http;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;

@Component
public class HttpUtil {

    private Content content;
    private List<String> lsit;
    private Map<String, String> resmap;
    private final static String CONTENT_TYPE = "Content-Type";

    @Value("#{config['heida.captcha.url']}")
    private String captchaUrl;
    @Value("#{config['heida.login.url']}")
    private String loginUrl;
    @Value("#{config['heida.rate.view.url']}")
    private String rateReviewUrl;
    @Value("#{config['heida.user.info.url']}")
    private String userInfoUrl;

    private Map<String, String> getCookie(List<String> lsit) {
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

    public String getCode() {
        String code = null;
        for (int i = 3; i >= 1; i--) {
            content = getRandom("GET", captchaUrl.replaceAll("\\[n\\]", String.valueOf(i)), false);
            if (content != null) break;
        }
        if (content == null) {
            return null;
        }
        code = content.getBase64Code();
        lsit = content.getHeaders().get("Set-Cookie");
        resmap = getCookie(lsit);
        return code;
    }

    public Map<String, String> getBody(String userCode, String password, String code) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("j_username", userCode);
        paramMap.put("j_password", password);
        paramMap.put("validateCode", code);
        Map<String, String> map = new HashMap<>();
        for (int i = 3; i >= 1; i--) {
            content = curl("POST", loginUrl.replaceAll("\\[n\\]", String.valueOf(i)), paramMap, resmap, false);
            if (content != null) break;
        }
        resmap = getCookie(lsit);
        paramMap = new HashMap<>();
        for (int i = 3; i >= 1; i--) {
            content = curl("POST", rateReviewUrl.replaceAll("\\[n\\]", String.valueOf(i)), paramMap, resmap, false);
            if (content != null) break;
        }
	if(content!=null){
            map.put("message", content.getBody());
        }
        for (int i = 3; i >= 1; i--) {
            content = curl("POST", userInfoUrl.replaceAll("\\[n\\]", String.valueOf(i)), paramMap, resmap, false);
            if (content != null) break;
        }
        if (content!=null){
            map.put("userInfo", content.getBody());
        }
        return map;
    }

    private Content curl(String method,
                        String sUrl,
                        Map<String, String> paramMap,
                        Map<String, String> requestHeaderMap,
                        boolean isOnlyReturnHeader) {
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

                return curl("POST", newUrlSb.toString(), paramMap, requestHeaderMap, isOnlyReturnHeader);
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
                content = new Content(sUrl, new String(bytes, encoding), null, httpUrlConnection.getHeaderFields());
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


    private Content getRandom(String method, String sUrl, boolean isOnlyReturnHeader) {
        Content content = null;
        HttpURLConnection httpUrlConnection = null;
        String base64Code = null;
        try {
            URL url = new URL(sUrl);
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
                    byte[] btImg = readInputStream(httpUrlConnection.getInputStream());
                    base64Code = Base64.encode(btImg);
                }
                String encoding = null;
                if (encoding == null) {
                    encoding = getEncodingFromContentType(httpUrlConnection.getHeaderField(CONTENT_TYPE));
                }
                content = new Content(sUrl, new String(bytes, encoding), base64Code, httpUrlConnection.getHeaderFields());
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

    private static String getEncodingFromContentType(String contentType) {
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

    private static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }
}



