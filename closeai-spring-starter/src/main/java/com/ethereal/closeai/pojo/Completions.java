package com.ethereal.closeai.pojo;

import lombok.Builder;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Jie Jie
 * date 2023-08-16
 */
@Builder
public class Completions {
    /**
     * 模型字符串 必需
     * 要使用的模型的 ID。可以使用列表模型 API 查看所有可用模型，或参阅模型概述了解它们的描述。
     */
    private String model;
    /**
     *
     */
    private String messages;
    /**
     *提示字符串或数组可选默认值为 <|结尾文本|>
     * 用于生成完成、编码为字符串、字符串数组、标记数组或标记数组数组的提示。
     *
     * 请注意，<|endoftext|> 是模型在训练期间看到的文档分隔符，因此如果未指定提示，模型将生成，就像从新文档的开头一样。
     */
    private String prompt;
    /**
     * 后缀字符串可选默认值为 null
     *  完成插入文本后的后缀。
     */
    private String suffix;
    /**
     * 完成时要生成的最大令牌数。
     * 提示的令牌计数加上max_tokens不能超过模型的上下文长度。大多数模型的上下文长度为 2048 个令牌（最新模型除外，它支持 4096）。
     */
    private Integer maxTokens;
    /**
     * 使用什么采样温度，介于 0 和 2 之间。较高的值（如 0.8）将使输出更加随机，而较低的值（如 0.2）将使其更加集中和确定。
     * 可选默认值为 1
     * 我们通常建议更改此设置或top_p但不能两者兼而有之。
     */
    private Integer temperature;
    /**
     * 个数字可选默认值为 1
     * 使用温度采样的替代方法称为核心采样，其中模型考虑具有top_p概率质量的令牌的结果。因此，0.1 意味着只考虑包含前 10% 概率质量的代币。
     *
     * 我们通常建议更改此设置或温度但不能两者兼而有之。
     */
    private String topP;
    /**
     * 整数可选默认值为 1
     * 为每个提示生成的完成次数。
     */
    private Integer n;
    /**
     * 布尔值可选默认值为 false
     * 是否流式传输回部分进度。如果设置，令牌将在可用时作为纯数据服务器发送的事件发送，流由数据：[完成]消息。
     */
    private Boolean stream;
    /**
     * 整数可选默认值为 null
     * 将日志概率包含在对数最有可能的代币，以及所选的代币。例如，如果对数为 5，API 将返回 5 个最可能的令牌的列表。API 将始终返回对普罗布的采样令牌，因此最多可能有对数+1元素。
     *
     * 的最大值对数为 5。如果您需要更多，请通过我们的帮助中心与我们联系并描述您的使用案例。
     */
    private Integer logprobs;
    /**
     * 回显
     * 布尔值可选默认值为 false
     * 除了完成之外，还回显提示
     */
    private Boolean echo;
    /**
     * 停止
     * 字符串或数组可选默认值为 null
     * 最多 4 个序列，其中 API 将停止生成更多令牌。返回的文本将不包含停止序列。
     */
    private String stop;
    /**
     * 数字
     * 可选 默认值为 0
     * 介于 -2.0 和 2.0 之间的数字。正值会根据新标记到目前为止是否出现在文本中来惩罚它们，从而增加模型讨论新主题的可能性。
     */
    private Integer presencePenalty;
    /**
     * 数字
     * 可选 默认值为 0
     * 介于 -2.0 和 2.0 之间的数字。正值会根据新标记到目前为止在文本中的现有频率来惩罚新标记，从而降低模型逐字重复同一行的可能性。
     */
    private Integer frequencyPenalty;
    /**
     * 整数
     * 可选 默认值为 1
     * 生成best_of完成服务器端并返回“最佳”（每个令牌的日志概率最高的那个）。无法流式传输结果。
     *
     * 当与n,best_of控制候选人完成的数量和n指定要返回的数量 –best_of必须大于n.
     *
     * 注意：由于此参数会生成许多完成，因此它会快速消耗令牌配额。谨慎使用并确保您有合理的设置max_tokens和停.
     */
    private Integer bestOf;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("{");
        if(StringUtils.isNotBlank(model)) sb.append("\"model\":").append("\"").append(model).append("\"");
        if(StringUtils.isNotBlank(prompt)) sb.append(", \"prompt\":").append("\"").append(prompt).append("\"");
        if(maxTokens != null) sb.append(", \"max_tokens\":").append("\"").append(maxTokens).append("\"");
        if(temperature != null) sb.append(", \"temperature\":").append("\"").append(temperature).append("\"");
        if(StringUtils.isNotBlank(topP)) sb.append(", \"top_p\":").append("\"").append(topP).append("\"");
        if(n != null) sb.append(", \"n\":").append("\"").append(n).append("\"");
        if(stream != null) sb.append(", \"stream\":").append("\"").append(stream).append("\"");
        if(logprobs != null) sb.append(", \"logprobs\":").append("\"").append(logprobs).append("\"");
        if(echo != null) sb.append(", \"echo\":").append("\"").append(echo).append("\"");
        if(StringUtils.isNotBlank(stop)) sb.append(", \"stop\":").append("\"").append(stop).append("\"");
        if(presencePenalty != null) sb.append(", \"presence_penalty\":").append("\"").append(presencePenalty).append("\"");
        if(frequencyPenalty != null) sb.append(", \"frequency_penalty\":").append("\"").append(frequencyPenalty).append("\"");
        if(bestOf != null) sb.append(", \"best_of\":").append("\"").append(bestOf).append("\"");
        if(StringUtils.isNotBlank(logitBias)) sb.append(", \"logit_bias\":").append("\"").append(logitBias).append("\"");
        if(StringUtils.isNotBlank(user)) sb.append(", \"user\":").append("\"").append(user).append("\"");
        sb.append("}");
        return sb.toString();
    }
}
