package com.ethereal.confidant.ai.controller;

import com.ethereal.confidant.ai.pojo.ChatCompletions;
import com.ethereal.confidant.ai.pojo.ResponseVo;
import com.ethereal.confidant.ai.service.IChatCompletionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Jie Jie
 * date 2023-08-17
 */
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private IChatCompletionsService chatCompletionsService;


    @GetMapping("/say")
    public ResponseVo<List<String>> answer(String message){
        ChatCompletions chatCompletions = new ChatCompletions();
        chatCompletions.appendMessage("user",message);
        List<String> results = chatCompletionsService.answer(chatCompletions);
        return ResponseVo.ok(results);
    }
}
