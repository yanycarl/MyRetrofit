package com.example.myretrofit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class CodeC {
    private Socket socket;
    private InetSocketAddress mAddress;
    boolean isInited = false;

    public CodeC(String ip){
        if(ip == null){
            throw new IllegalArgumentException("DNS Error");
        }
        createSocket();
        buildConnection(ip);
    }

    private void createSocket(){
        Socket client = new Socket();
        socket = client;
    }

    private void buildConnection(String ip){
        mAddress = new InetSocketAddress(ip, 80);
        try {
            socket.connect(mAddress, 1000);
            isInited = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeContent(String request){
        if( mAddress == null){
            throw new IllegalArgumentException("Empty Address");
        }
        PrintWriter pWriter;
        try {
            pWriter = new PrintWriter(socket.getOutputStream(),true);
            pWriter.println(request);
            pWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ResponseData readContent(){
        boolean htmlStarted = false;
        ResponseData responseData = new ResponseData();
        try {
            String lineContent;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
            responseData.parseFirstLine(bufferedReader.readLine());

            if(!responseData.getStatusCode().equals("200")){
                return responseData;
            }

            ResponseHeader header = new ResponseHeader();
            StringBuffer stringBuffer = new StringBuffer();

            lineContent = bufferedReader.readLine();
            while(lineContent != null) {
                if(lineContent.startsWith("{")){
                    responseData.setJson(lineContent);
                }
                else if(htmlStarted || lineContent.startsWith("<")){
                    htmlStarted = true;
                    stringBuffer.append(lineContent);
                }
                else{
                    header.parseHeader(lineContent);
                }
                lineContent = bufferedReader.readLine();
            }
            responseData.setHtml(stringBuffer.toString());
            responseData.setmResponseHeader(header);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseData;
    }
}
