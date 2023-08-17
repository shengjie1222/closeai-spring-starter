package com.ethereal.closeai.test.service;


import com.ethereal.closeai.test.pojo.ChatCompletions;

import java.util.List;

/**
 * @author Jie Jie
 * date 2023-08-17
 */
public interface IChatCompletionsService {

    List<String> answer(ChatCompletions completions);
}
