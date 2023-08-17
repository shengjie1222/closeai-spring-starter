package com.ethereal.confidant.ai.factory;

import cn.gjsm.api.openai.OpenAiClient;
import cn.gjsm.api.openai.OpenAiClientFactory;
import com.ethereal.closeai.openai.CloseAiClient;
import com.ethereal.closeai.openai.CloseAiClientFactory;
import com.ethereal.confidant.ai.config.CloseAIProperties;
import com.ethereal.confidant.ai.config.OpenAIProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jie Jie
 * date 2023-08-17
 */
@Configuration
public class ClientFactory {

    @Autowired
    private OpenAIProperties openAIProperties;

    @Autowired
    private CloseAIProperties closeAIProperties;

    private Object openLocker = new Object();
    private Object closeLocker = new Object();

    private OpenAiClient openAiClient;
    private CloseAiClient closeAiClient;

    public OpenAiClient getOpenClient(){
        if(openAiClient == null){
            synchronized (openLocker){
                if(openAiClient == null){
                    openAiClient = OpenAiClientFactory.createClient(openAIProperties.getToken());
                }
            }
        }
        return openAiClient;
    }

    public CloseAiClient getCloseClient(){
        if(closeAiClient == null){
            synchronized (closeLocker){
                if(closeAiClient == null){
                    closeAiClient =  CloseAiClientFactory.createClient(closeAIProperties.getToken());
                }
            }
        }
        return closeAiClient;
    }
}
