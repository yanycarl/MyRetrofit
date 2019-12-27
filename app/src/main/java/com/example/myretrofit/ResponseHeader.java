package com.example.myretrofit;

import java.util.HashMap;
import java.util.Map;

public class ResponseHeader {

    enum HeaderType{
        Server, Date, Content_Type, Transfer_Encoding,Connection,
        Vary, X_Source, X_Real_Ip, Accept_Ranges, Expires,
        Cache_Control, Age, X_Request_Id, Via
    }

    public Map<String, String> mHeader = new HashMap<>();

    public void parseHeader(String line){
        for(HeaderType headerType: HeaderType.values()){
            String type = headerType.toString().replace('_','-');
            if(line.startsWith(type)){
                mHeader.put(type, line.substring(type.length()+2));
                break;
            }
        }
    }
}
