package com.ethereal.closeai.test;

import com.ethereal.closeai.test.service.IChatCompletionsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CloseaiSpringTestApplicationTests {

    @Autowired
    private IChatCompletionsService chatCompletionsService;

    @Test
    public void contextLoads() {
        List<String> answer = chatCompletionsService.answer("你的生日是多久");
        for (String s : answer) System.out.println(s);
    }

}
