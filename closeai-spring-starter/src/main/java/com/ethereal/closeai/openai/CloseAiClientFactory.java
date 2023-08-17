package com.ethereal.closeai.openai;

import com.ethereal.closeai.factory.BaseCloseClientFactory;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;

import java.io.IOException;

/**
 * 定义OpenAi客户端创建工程
 *
 * @author JieJie jsheng.ethereal@gmail.com
 */
@Data
@NoArgsConstructor
@SuperBuilder
public class CloseAiClientFactory extends BaseCloseClientFactory{

    static CloseAiClientFactory INSTANCE = new CloseAiClientFactory();

    String baseUrl = "https://api.closeai-proxy.xyz/";

    public static CloseAiClient createClient(String token){
        System.setProperty("OPENAPI_TOKEN",token);
        return INSTANCE.createClient();
    }

    public static CloseAiClient defaultClient(){
        return INSTANCE.createClient();
    }

    public static CloseAiClientFactory getInstance(){
        return INSTANCE;
    }

    public CloseAiClient createHttpProxyClient(String token,String host,int port){
        System.setProperty("OPENAPI_TOKEN",token);
        return INSTANCE.createHttpProxyClient(host,port);
    }

    public CloseAiClient createSocketProxyClient(String token,String host,int port){
        System.setProperty("OPENAPI_TOKEN",token);
        return INSTANCE.createSocketProxyClient(host,port);
    }

    public static void refreshToken(String token){
        System.setProperty("OPENAPI_TOKEN",token);
    }


    public Retrofit.Builder defualtRetrofitBuilder(){
        Retrofit.Builder builder = super.defualtRetrofitBuilder();
        builder.baseUrl(baseUrl);
        return builder;
    }

    public OkHttpClient.Builder defaultUnsafeOkHttpClient() {
        OkHttpClient.Builder builder = super.defaultUnsafeOkHttpClient();
        builder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                String token = System.getProperty("OPENAPI_TOKEN");
                if(token!=null) {
                    Request request = chain.request().newBuilder()
                            .addHeader("Connection","close")
                            .header("Authorization", "Bearer " + token).build();
                    return chain.proceed(request);
                }else{
                    return chain.proceed(chain.request());
                }
            }
        });
        return builder;
    }

}
