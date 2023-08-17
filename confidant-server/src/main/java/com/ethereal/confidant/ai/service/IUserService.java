package com.ethereal.confidant.ai.service;

import com.ethereal.closeai.pojo.close.BillingCreditGrantsResponse;
import com.ethereal.closeai.pojo.close.BillingUsageResponse;

import java.time.LocalDate;

/**
 * @author Jie Jie
 * date 2023-08-17
 */
public interface IUserService {

    BillingUsageResponse getBillingUsage(LocalDate startDate, LocalDate endDate);

    /**
     * 查看剩余金额
     * @return
     */
    BillingCreditGrantsResponse getBillingCreditGrants();
}
