package com.util;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Content {
    private String url;
    private String body;
    private Map<String, List<String>> m_mHeaders = new HashMap<String, List<String>>();

    public Content(String url, String body, Map<String, List<String>> headers) {
        this.url = url;
        this.body = body;
        this.m_mHeaders = headers;
    }

    public String getUrl() {
        return url;
    }

    public String getBody() {
        return body;
    }

    public Map<String, List<String>> getHeaders() {
        return m_mHeaders;
    }

}

