package com.ethereal.closeai.factory;

import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;
import org.jsoup.Jsoup;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.*;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseCloseProxySelector extends ProxySelector {
    @Override
    public List<Proxy> select(URI uri) {
        autoConfigURL();
        String proxyHost = System.getProperty("http.proxyHost");
        String proxyPort = System.getProperty("http.proxyPort");
        if(!StringUtil.isBlank(proxyHost)&&!StringUtil.isBlank(proxyPort)){
            System.out.println("=====使用代理："+proxyHost+":"+proxyPort);
            return Collections.singletonList(new Proxy(Proxy.Type.HTTP,new InetSocketAddress(proxyHost,Integer.valueOf(proxyPort))));
        }else{
            return Collections.singletonList(Proxy.NO_PROXY);
        }
    }

    @Override
    public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {

    }

    /**
     * 自动配置代理脚本
     */
    public void autoConfigURL(){
        try {
            System.clearProperty("http.proxyHost");
            System.clearProperty("http.proxyPort");
            System.clearProperty("https.proxyHost");
            System.clearProperty("https.proxyPort");
            // 优先手动设置的IP
            String proxyHost = System.getProperty("openai.proxyHost");
            String proxyPort = System.getProperty("openai.proxyPort");
            if(!StringUtil.isBlank(proxyHost)&&!StringUtil.isBlank(proxyPort)){
                setProxy(proxyHost,proxyPort);
                return;
            }
            String autoConfigURL = null;
            try {
                autoConfigURL = Advapi32Util.registryGetStringValue(
                        WinReg.HKEY_CURRENT_USER, "Software\\Microsoft\\Windows\\CurrentVersion\\Internet Settings", "AutoConfigURL");

            }catch (Exception e){
                return;
            }
            if (autoConfigURL == null || "".equals(autoConfigURL)) {
                autoConfigSystemProxies();
                return;
            }
            Document document = Jsoup.connect(autoConfigURL)
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .get();
            Pattern pattern = Pattern.compile("PROXY (.*?):(\\d*)");
            Matcher matcher = pattern.matcher(document.text());
            while (matcher.find()){
                proxyHost = matcher.group(1);
                proxyPort = matcher.group(2);
                setProxy(proxyHost,proxyPort);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void autoConfigSystemProxies(){
        System.setProperty("java.net.useSystemProxies", "true");
        try {
            Proxy proxy = (Proxy) ProxySelector.getDefault().select(new URI(
                            "http://www.baidu.com/")).iterator().
                    next();
            InetSocketAddress address = (InetSocketAddress) proxy.address();
            if(address!=null){
                setProxy(address.getAddress().getHostName(),""+address.getPort());
            }
        } catch (URISyntaxException e) {
        }
    }

    private void setProxy(String proxyHost,String proxyPort){
        System.setProperty("http.proxyHost",proxyHost);
        System.setProperty("http.proxyPort",proxyPort);
        System.setProperty("https.proxyHost",proxyHost);
        System.setProperty("https.proxyPort",proxyPort);
    }
}
