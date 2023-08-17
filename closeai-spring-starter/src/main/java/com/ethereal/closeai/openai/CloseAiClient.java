package com.ethereal.closeai.openai;

import cn.gjsm.api.openai.OpenAiClient;
import com.ethereal.closeai.pojo.close.BillingCreditGrantsResponse;
import com.ethereal.closeai.pojo.close.BillingUsageResponse;
import com.ethereal.closeai.pojo.close.LinkResponse;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

/**
 * 定义CloseAi的接口
 *
 * @author JieJie jsheng.ethereal@gmail.com
 */
public interface CloseAiClient extends OpenAiClient {

    /**
     * 查询指定时间段的消费金额
     * @return
     */
    @GET("/dashboard/billing/usage")
    Call<BillingUsageResponse> getBillingUsage(@Query(value="start_date", encoded=true)String startDate,
                                               @Query(value="end_date", encoded=true)String endDate);

    /**
     * 查询当前余额
     * @return
     */
    @GET("/dashboard/billing/credit_grants")
    Call<BillingCreditGrantsResponse> getBillingCreditGrants();

    /**
     * 动态获取API地址
     * priority提示了该API的推荐优先级，数字越大，越推荐作为优先选择。优先级低仅作为紧急时刻备用选择，替换频率较高。
     * @return
     */
    @GET("/api/v1/links")
    Call<List<LinkResponse>> links();

    /**
     * 查询API健康状态
     * 通过动态API查询接口返回的API地址，可以通过以下接口查询API健康状态，能正常访问时，说明域名处于健康状态。可以正常提供服务。接口有限流，请合理设置检查频率。不要频繁检查可用性，建议5分钟检测一次即可。
     * @return
     */
    @GET("/api/v1/healthy")
    Call<String> healthy();
}
