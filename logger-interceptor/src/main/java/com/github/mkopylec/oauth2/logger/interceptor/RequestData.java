package com.github.mkopylec.oauth2.logger.interceptor;

public class RequestData {

    private String time;
    private String application;
    private String method;
    private String url;
    private String authorizationHeader;
    private String body;
    private int responseStatus;

    public String getTime() {
        return time;
    }

    public RequestData setTime(String time) {
        this.time = time;
        return this;
    }

    public String getApplication() {
        return application;
    }

    public RequestData setApplication(String application) {
        this.application = application;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public RequestData setMethod(String method) {
        this.method = method;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public RequestData setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getAuthorizationHeader() {
        return authorizationHeader;
    }

    public RequestData setAuthorizationHeader(String authorizationHeader) {
        this.authorizationHeader = authorizationHeader;
        return this;
    }

    public String getBody() {
        return body;
    }

    public RequestData setBody(String body) {
        this.body = body;
        return this;
    }

    public int getResponseStatus() {
        return responseStatus;
    }

    public RequestData setResponseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                "time='" + time + '\'' +
                ", application='" + application + '\'' +
                ", method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", authorizationHeader='" + authorizationHeader + '\'' +
                ", body='" + body + '\'' +
                ", responseStatus='" + responseStatus + '\'' +
                '}';
    }
}
