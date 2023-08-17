package com.ethereal.closeai.pojo.close;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 消费金额
 * @author Jie Jie
 * date 2023-08-17
 */
@Data
public class BillingCreditGrantsResponse {
    String object;
    /**
     * 无实际意义，可能某些前端会用到，不要依赖这个字段
     */
    @JSONField(name = "total_granted")
    Double totalGranted;
    /**
     * 本月消费
     */
    @JSONField(name = "total_used")
    Double totalUsed;
    /**
     * 剩余余额
     */
    @JSONField(name = "total_available")
    Double totalAvailable;
}
