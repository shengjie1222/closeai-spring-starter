package com.ethereal.confidant.ai.service.impl;

import cn.gjsm.api.openai.OpenAiClient;
import cn.gjsm.api.pojo.chat.ChatCompletionChoice;
import cn.gjsm.api.pojo.chat.ChatCompletionRequest;
import cn.gjsm.api.pojo.chat.ChatMessage;
import com.ethereal.confidant.ai.pojo.ChatCompletions;
import com.ethereal.confidant.ai.factory.ClientFactory;
import com.ethereal.confidant.ai.service.IChatCompletionsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jie Jie
 * date 2023-08-17
 */
@Component
public class ChatCompletionsServiceImpl implements IChatCompletionsService {

    @Autowired
    private ClientFactory clientFactory;

    @Override
    public List<String> answer(ChatCompletions completions) {
        OpenAiClient service =  clientFactory.getOpenClient();
        List<ChatMessage> messages = new ArrayList<>();
        for (ChatCompletions.Messages item : completions.getMessages()) {
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setRole(item.getRole());
            chatMessage.setContent(item.getContent());
            messages.add(chatMessage);
        }
        ChatCompletionRequest.ChatCompletionRequestBuilder builder = ChatCompletionRequest.builder()
                .model(completions.getModel())
                .messages(messages)
                .temperature(completions.getTemperature())
                .maxTokens(completions.getMaxTokens())
                .topP(completions.getTopP())
                .frequencyPenalty(completions.getFrequencyPenalty())
                .presencePenalty(completions.getPresencePenalty());
        if (!StringUtils.isEmpty(completions.getStop())) {
            builder.stop(Arrays.asList(completions.getStop().split(",")));
        }
        ChatCompletionRequest completionRequest = builder.build();
        try {
            List<ChatCompletionChoice> choices = service.callChatCompletion(completionRequest).execute().body().getChoices();
            if(!CollectionUtils.isEmpty(choices)){
                List<String> resultList = choices.stream().map(ChatCompletionChoice::getMessage).map(ChatMessage::getContent).collect(Collectors.toList());
                return resultList;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Collections.emptyList();
    }
}
