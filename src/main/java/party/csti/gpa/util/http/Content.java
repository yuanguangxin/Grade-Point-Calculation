package party.csti.gpa.util.http;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Content {
    private String url;
    private String body;
    private String base64Code;
    private Map<String, List<String>> m_mHeaders = new HashMap<String, List<String>>();

    public Content(String url, String body, String base64Code,Map<String, List<String>> headers) {
        this.url = url;
        this.body = body;
        this.base64Code = base64Code;
        this.m_mHeaders = headers;
    }

    public String getBase64Code() {
        return base64Code;
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

