package com.ethereal.closeai.pojo.close;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 消费金额
 * @author Jie Jie
 * date 2023-08-17
 */
@Data
public class BillingUsageResponse {
    String object;
    /**
     * 当日消费
     */
    @JSONField(name = "daily_costs")
    Double dailyCosts;
    /**
     * 总消费
     */
    @JSONField(name = "total_usage")
    Double totalUsage;
}
