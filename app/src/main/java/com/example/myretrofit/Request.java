package com.example.myretrofit;

public class Request {
    private Url url;
    private String method;
    private Headers headers;
    private RequestBody requestBody;
    private String ip;

    private Request(Builder builder){
        this.url = builder.url;
        this.method = builder.method;
        this.headers = builder.headers;
        this.requestBody = builder.requestBody;
    }

    public static class Builder{

        private Url url;
        private String method;
        private Headers headers;
        private RequestBody requestBody;

        public Builder setUrl(Url url) {
            this.url = url;
            return this;
        }

        public Builder setMethod(String method) {
            this.method = method;
            return this;
        }

        public Request build(){
            return new Request(this);
        }
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Url getUrl(){
        return this.url;
    }

    public String getPackage(){
        StringBuffer fragment = new StringBuffer();
        fragment.append(this.method + " ");
        fragment.append(this.url.getPathWithQuery() + " HTTP/1.1\r\n");
        fragment.append("Host: " + this.url.getHost() + ":80\r\n");
        return fragment.toString();
    }
}
