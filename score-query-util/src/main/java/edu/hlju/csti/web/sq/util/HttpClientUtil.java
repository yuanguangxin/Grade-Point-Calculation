package edu.hlju.csti.web.sq.util;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 开发者:李嘉鼎
 * 开发时间:2016/7/7
 * 描述:
 */
public class HttpClientUtil {
    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
    private CloseableHttpClient client = HttpClientBuilder.create().build();

    public HttpResponse executeGet(String url) {
        return executeGet(url, null, null, null);
    }

    public HttpResponse executrGet(String url, Map<String, String> params) {
        return executeGet(url, params, null, null);
    }

    public HttpResponse executeGet(String url, Map<String, String> params, Map<String, String> headers, String cookieStr) {
        try {
            URI uri = new URI(url);
            if (params != null) {
                URIBuilder uriBuilder = new URIBuilder(uri);
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    uriBuilder.addParameter(entry.getKey(), entry.getValue());
                }
                uri = uriBuilder.build();
            }
            HttpGet get = new HttpGet(uri);
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    get.setHeader(entry.getKey(), entry.getValue());
                }
            }
            if (cookieStr != null) {
                get.setHeader("Cookie", cookieStr);
            }
            return client.execute(get);
        } catch (URISyntaxException e) {
            logger.error("Wrong URL:{}", e);
        } catch (IOException e) {
            logger.error("Can not execute Method:{}", e);
        }
        return null;
    }

    public HttpResponse executePost(String url, Object param, Map<String, String> headers, String cookieStr) {
        try {
            URI uri = new URI(url);
            HttpPost post = new HttpPost(uri);
            if (param != null) {
                if (param instanceof Map) {
                    Map<String, Object> paraMap = (Map<String, Object>) param;
                    List<NameValuePair> pairs = new ArrayList<>();
                    for (Map.Entry<String, Object> entry : paraMap.entrySet()) {
                        try {
                            pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
                        } catch (Exception ignored) {
                        }
                    }
                    post.setEntity(new UrlEncodedFormEntity(pairs));
                } else if (param instanceof byte[]) {
                    post.setEntity(new ByteArrayEntity((byte[]) param));
                }
            }
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    post.setHeader(entry.getKey(), entry.getValue());
                }
            }
            if (cookieStr != null) {
                post.setHeader("Cookie", cookieStr);
            }
            return client.execute(post);
        } catch (URISyntaxException e) {
            logger.error("Wrong URL:{}", e);
        } catch (IOException e) {
            logger.error("Can not execute Method:{}", e);
        }
        return null;
    }


//    public enum HttpMethods {
//        GET("GET"),
//        POST("POST"),
//        PUT("PUT"),
//        DELETE("DELETE"),
//        OPTIONS("OPTIONS"),
//        PATCH("PATCH"),
//        COPY("COPY"),
//        HEAD("HEAD");
//        private String methodName;
//
//        HttpMethods(String get) {
//            this.methodName = get;
//        }
//
//        public String getMethodName() {
//            return methodName;
//        }
//    }
}
