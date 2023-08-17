package com.ethereal.closeai.factory;

import com.ethereal.closeai.openai.CloseAiClient;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * 抽象客户端创建工厂
 *
 * @author JieJie jsheng.ethereal@gmail.com
 */
@Data
@NoArgsConstructor
@SuperBuilder
public class BaseCloseClientFactory {

    @Builder.Default
    private Duration connectTimeout = Duration.ofMinutes(5);
    @Builder.Default
    private Duration readTimeout = Duration.ofMinutes(5);

    private Converter.Factory converterFactory;

    private OkHttpClient.Builder clientBuilder;
    private Retrofit.Builder retrofitBuilder;

    protected CloseAiClient createClient(){
        if(clientBuilder==null){
            clientBuilder = defaultUnsafeOkHttpClient();
        }
        if(retrofitBuilder==null){
            retrofitBuilder = defualtRetrofitBuilder();
        }
        OkHttpClient client =clientBuilder.build();
        retrofitBuilder.client(client);
        return retrofitBuilder.build().create(CloseAiClient.class);
    }

    public CloseAiClient createHttpProxyClient(String host,int port){
        if(clientBuilder==null){
            clientBuilder = defaultUnsafeOkHttpClient();
            clientBuilder.proxy(new Proxy(Proxy.Type.HTTP,new InetSocketAddress(host,port)));
        }
        return this.createClient();
    }

    public CloseAiClient createSocketProxyClient(String host,int port){
        if(clientBuilder==null){
            clientBuilder = defaultUnsafeOkHttpClient();
            clientBuilder.proxy(new Proxy(Proxy.Type.SOCKS,new InetSocketAddress(host,port)));
        }
        return this.createClient();
    }

    public Retrofit.Builder defualtRetrofitBuilder(){
        if(converterFactory==null){
            converterFactory = defaultConverterFactory();
        }
        return  new Retrofit.Builder()
                .addConverterFactory(converterFactory);
    }

    public Converter.Factory defaultConverterFactory(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        return JacksonConverterFactory.create(mapper);
    }

    /**
     * 默认非安全的请求客户端
     * @return
     */
    public OkHttpClient.Builder defaultUnsafeOkHttpClient() {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.proxySelector(new BaseCloseProxySelector());
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    return chain.proceed(chain.request());
                }
            });
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            builder.connectionPool(new ConnectionPool(5, 1L, TimeUnit.SECONDS))
                    .connectTimeout(connectTimeout)
                    .readTimeout(readTimeout).build();
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
