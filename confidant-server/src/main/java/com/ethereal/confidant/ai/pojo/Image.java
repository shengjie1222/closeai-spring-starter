package com.ethereal.confidant.ai.pojo;

import lombok.Data;

/**
 * @author Jie Jie
 * date 2023-08-16
 */
@Data
public class Image {
    /**
     * 必填
     * A text description of the desired image(s). The maximum length is 1000 characters.
     * 所需图像的文本描述。最大长度为 1000 个字符。
     */
    private String prompt;
    /**
     * 整数可选默认值为 1
     * A text description of the desired image(s). The maximum length is 1000 characters.
     * 要生成的图像数。必须介于 1 和 10 之间。
     */
    private int n = 1;
    /**
     * 可选 默认值为  1024x1024
     * The size of the generated images. Must be one of 256x256, 512x512, or 1024x1024.
     * 生成的图像的大小。必须是以下之一256×256,512×512或1024×1024.
     */
    private String size = "1024x1024";
    /**
     * 默认 地址
     * The format in which the generated images are returned. Must be one of url or b64_json.
     * 返回生成的图像的格式。必须是以下之一: url/b64_json
     */
    private String responseFormat = "url";
    /**
     * 字符串可选
     * A unique identifier representing your end-user, which can help OpenAI to monitor and detect abuse.
     * 代表最终用户的唯一标识符，可帮助 OpenAI 监控和检测滥用行为。
     */
    private String user;

}
