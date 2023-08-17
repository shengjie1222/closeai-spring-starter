package com.ethereal.closeai.test.factory;

import com.alibaba.fastjson.JSON;
import com.ethereal.closeai.openai.CloseAiClient;
import com.ethereal.closeai.openai.CloseAiClientFactory;
import com.ethereal.closeai.pojo.close.BillingCreditGrantsResponse;
import com.ethereal.closeai.pojo.close.BillingUsageResponse;
import com.ethereal.closeai.pojo.close.LinkResponse;
import com.ethereal.closeai.test.config.CloseAIProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Jie Jie
 * date 2023-08-17
 */
@Configuration
public class ClientFactory {

    @Autowired
    private CloseAIProperties properties;

    private Object locker = new Object();

    private CloseAiClient instance;

    public CloseAiClient getClient(){
        if(instance == null){
            synchronized (locker){
                if(instance == null){
                    instance =  CloseAiClientFactory.createClient(properties.getToken());
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) throws IOException {
        CloseAiClient client = CloseAiClientFactory.createClient("sk-SxG8HKyTPC5Tn6ROyX9uKoO5sQfh2DDng055pUHjwy8uvJDU@12891");
        List<LinkResponse> body = client.links().execute().body();
        System.out.println(JSON.toJSONString(body));
    }
}
