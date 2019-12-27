package com.example.myretrofit;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Url {

    private Builder builder;

    public Url(Builder pBuilder){
        builder = pBuilder;
    }

    public String getUrl(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(builder.scheme);
        stringBuffer.append("://");
        stringBuffer.append(builder.host);
        stringBuffer.append("/");
        stringBuffer.append(getPathWithQuery());
        return stringBuffer.toString();
    }

    public String getPath() {
        return builder.path;
    }

    public String getPathWithQuery() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(builder.path);
        if(builder.queryParameters.size() != 0){
            stringBuffer.append("?");
        }
        Iterator<Map.Entry<String, String>> iterator = builder.queryParameters.entrySet().iterator();
        if(iterator.hasNext()){
            Map.Entry<String, String> entry = iterator.next();
            stringBuffer.append(entry.getKey()+"="+entry.getValue());
        }
        while (iterator.hasNext()){
            stringBuffer.append("&");
            Map.Entry<String, String> entry = iterator.next();
            stringBuffer.append(entry.getKey()+"="+entry.getValue());
        }
        return stringBuffer.toString();
    }

    public String getHost(){
        return builder.host;
    }

    public static class Builder{

        private String scheme;
        private String host;

        private String path;
        private Map<String, String> queryParameters;

        public Builder(){
            queryParameters = new HashMap<>();
        }

        public Builder setScheme(String scheme){
            this.scheme = scheme;
            return this;
        }

        public Builder setHost(String host){
            this.host = host;
            return this;
        }

        public Builder setPath(String path){
            this.path = path;
            return this;
        }


        public Builder addQueryParam(String key, String value){
            queryParameters.put(key, value);
            return this;
        }

        public Url build(){
            return new Url(this);
        }

    }
}
