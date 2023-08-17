package com.ethereal.confidant.ai.service;


import com.ethereal.confidant.ai.pojo.ChatCompletions;

import java.util.List;

/**
 * @author Jie Jie
 * date 2023-08-17
 */
public interface IChatCompletionsService {

    List<String> answer(ChatCompletions completions);
}
