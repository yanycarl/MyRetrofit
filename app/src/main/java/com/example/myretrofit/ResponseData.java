package com.example.myretrofit;

import androidx.annotation.NonNull;

public class ResponseData{


    private String html;
    private ResponseHeader mResponseHeader;

    public String getJson() {
        return json;
    }

    public void setHtml(String html){
        this.html = html;
    }

    private String json;

    public ResponseHeader getmResponseHeader() {
        return mResponseHeader;
    }

    public String getProtocol() {
        return protocol;
    }

    private String protocol = "";

    public String getStatusCode() {
        return statusCode;
    }

    private String statusCode = "";

    public void parseFirstLine(String firstLine){
        if(firstLine == null){
            return;
        }
        protocol = firstLine.substring(0, 8);
        statusCode = firstLine.substring(9, 12);
    }

    @NonNull
    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("\n" + "Protocol:"+protocol + "\n");
        stringBuffer.append("Status:"+statusCode+"\n");
        if(mResponseHeader != null){
            stringBuffer.append("Header:"+mResponseHeader.mHeader.toString()+"\n");
            stringBuffer.append("json:"+json);
        }
        if(html != null){
            stringBuffer.append(html);
        }
        return stringBuffer.toString();
    }

    public void setmResponseHeader(ResponseHeader header){
        this.mResponseHeader = header;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
