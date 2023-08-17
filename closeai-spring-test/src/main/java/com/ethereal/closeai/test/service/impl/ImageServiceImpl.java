package com.ethereal.closeai.test.service.impl;

import cn.gjsm.api.openai.OpenAiClient;
import cn.gjsm.api.pojo.image.Base64Image;
import cn.gjsm.api.pojo.image.ImageRequest;
import cn.gjsm.api.pojo.image.ImageResult;
import com.ethereal.closeai.openai.CloseAiClient;
import com.ethereal.closeai.test.pojo.Image;
import com.ethereal.closeai.test.factory.ClientFactory;
import com.ethereal.closeai.test.service.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import retrofit2.Response;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * @author Jie Jie
 * date 2023-08-17
 */
@Component
public class ImageServiceImpl implements IImageService {

    @Autowired
    private ClientFactory clientFactory;

    @Override
    public List<Base64Image> answer(Image image) {
        OpenAiClient service =  clientFactory.getOpenClient();
        ImageRequest builder = ImageRequest.builder()
                .prompt(image.getPrompt())
                .size(image.getSize())
                .n(image.getN())
                .responseFormat(image.getResponseFormat())
                .user(image.getUser())
                .build();
        try {
            Response<ImageResult> response = service.callImage(builder).execute();
            if(response != null && response.body() != null){
                return response.body().getData();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Collections.emptyList();
    }
}
