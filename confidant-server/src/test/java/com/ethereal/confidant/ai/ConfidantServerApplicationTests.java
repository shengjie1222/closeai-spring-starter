package com.ethereal.confidant.ai;

import cn.gjsm.api.pojo.image.Base64Image;
import com.alibaba.fastjson.JSON;
import com.ethereal.confidant.ai.pojo.ChatCompletions;
import com.ethereal.confidant.ai.pojo.Image;
import com.ethereal.confidant.ai.service.IChatCompletionsService;
import com.ethereal.confidant.ai.service.IImageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConfidantServerApplicationTests {

    @Autowired
    private IChatCompletionsService chatCompletionsService;

    @Autowired
    private IImageService imageService;

    @Test
    public void chatCompletions() {
        ChatCompletions completions = new ChatCompletions();
        completions.appendMessage("user","阳光普照的照片地址");
        List<String> answer = chatCompletionsService.answer(completions);
        for (String s : answer) System.out.println(s);
    }

    @Test
    public void image() {
        Image image = new Image();
        image.setPrompt("A cute baby sea otter");
        List<Base64Image> answer = imageService.answer(image);
        for (Base64Image s : answer) System.out.println(JSON.toJSONString(s));
    }

}
