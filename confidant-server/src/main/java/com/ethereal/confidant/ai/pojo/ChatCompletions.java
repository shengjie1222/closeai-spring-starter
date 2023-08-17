package com.ethereal.confidant.ai.pojo;

import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jie Jie
 * date 2023-08-16
 */
@Data
public class ChatCompletions {
    /**
     * 模型字符串 必需
     * 要使用的模型的 ID。可以使用列表模型 API 查看所有可用模型，或参阅模型概述了解它们的描述。
     */
    private String model = "gpt-3.5-turbo";
    /**
     * 数组必需
     * 要为其生成聊天完成的消息，采用聊天格式。
     */
    private List<Messages> messages;
    /**
     * 使用什么采样温度，介于 0 和 2 之间。较高的值（如 0.8）将使输出更加随机，而较低的值（如 0.2）将使其更加集中和确定。
     * 可选默认值为 1
     * 我们通常建议更改此设置或top_p但不能两者兼而有之。
     */
    private double temperature = 1;
    /**
     * 个数字可选默认值为 1
     * 使用温度采样的替代方法称为核心采样，其中模型考虑具有top_p概率质量的令牌的结果。因此，0.1 意味着只考虑包含前 10% 概率质量的代币。
     *
     * 我们通常建议更改此设置或温度但不能两者兼而有之。
     */
    private double topP = 1;
    /**
     * 整数可选默认值为 1
     * 为每个提示生成的完成次数。
     */
    private int n = 1;
    /**
     * 布尔值可选默认值为 false
     * 是否流式传输回部分进度。如果设置，令牌将在可用时作为纯数据服务器发送的事件发送，流由数据：[完成]消息。
     */
    private boolean stream;
    /**
     * 停止
     * 字符串或数组可选默认值为 null
     * 最多 4 个序列，其中 API 将停止生成更多令牌。返回的文本将不包含停止序列。
     */
    private String stop;
    /**
     *  完成时要生成的最大令牌数。
     *  提示的令牌计数加上max_tokens不能超过模型的上下文长度。大多数模型的上下文长度为 2048 个令牌（最新模型除外，它支持 4096）。
     */
    private int maxTokens = 2048;
    /**
     * 数字
     * 可选 默认值为 0
     * 介于 -2.0 和 2.0 之间的数字。正值会根据新标记到目前为止是否出现在文本中来惩罚它们，从而增加模型讨论新主题的可能性。
     */
    private double presencePenalty;
    /**
     * 数字
     * 可选 默认值为 0
     * 介于 -2.0 和 2.0 之间的数字。正值会根据新标记到目前为止在文本中的现有频率来惩罚新标记，从而降低模型逐字重复同一行的可能性。
     */
    private double frequencyPenalty;
    /**
     * 映射可选默认值为空
     * 修改完成中出现指定令牌的可能性。
     *
     * 接受将令牌（由其在 GPT 标记器中的令牌 ID 指定）映射到 -100 到 100 之间的关联偏差值的 json 对象。您可以使用此分词器工具（适用于 GPT-2 和 GPT-3）将文本转换为令牌 ID。在数学上，偏差在采样之前被添加到模型生成的对数中。确切的效果因模型而异，但介于 -1 和 1 之间的值应降低或增加选择的可能性;像 -100 或 100 这样的值应该会导致禁止或排他性选择相关令牌。
     *
     * 例如，您可以通过{"50256": -100}以防止生成<|结尾文本|>令牌。
     */
    private String logitBias;
    /**
     * 字符串可选
     * 代表最终用户的唯一标识符，可帮助 OpenAI 监控和检测滥用行为
     */
    private String user;

    public void appendMessage(String role, String content){
        if(this.messages == null) this.messages = new ArrayList<>();
        this.messages.add(new Messages(role,content));
    }

    @Getter
    public class Messages{
        String role;
        String content;
        public Messages(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }

}
