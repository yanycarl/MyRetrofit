package com.example.myretrofit;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class DnsIntercepter implements IInterceptor {

    @Override
    public ResponseData intercept(RealInterceptorChain chain) {
        String host = chain.request.getUrl().getHost();
        try {
            String hostAddress = InetAddress.getByName(host).getHostAddress();
            chain.request.setIp(hostAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return chain.proceed(chain.request, null);
    }
}
