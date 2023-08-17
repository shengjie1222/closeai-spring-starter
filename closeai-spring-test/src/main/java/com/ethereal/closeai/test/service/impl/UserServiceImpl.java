package com.ethereal.closeai.test.service.impl;

import com.ethereal.closeai.openai.CloseAiClient;
import com.ethereal.closeai.pojo.close.BillingCreditGrantsResponse;
import com.ethereal.closeai.pojo.close.BillingUsageResponse;
import com.ethereal.closeai.test.factory.ClientFactory;
import com.ethereal.closeai.test.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author Jie Jie
 * date 2023-08-17
 */
@Component
public class UserServiceImpl implements IUserService {

    @Autowired
    private ClientFactory clientFactory;

    @Override
    public BillingUsageResponse getBillingUsage(LocalDate startDate, LocalDate endDate) {
        CloseAiClient service =  clientFactory.getCloseClient();
        try {
            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            BillingUsageResponse response = service.getBillingUsage(startDate.format(timeFormat),endDate.format(timeFormat)).execute().body();
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BillingCreditGrantsResponse getBillingCreditGrants() {
        CloseAiClient service =  clientFactory.getCloseClient();
        try {
            BillingCreditGrantsResponse response = service.getBillingCreditGrants().execute().body();
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
