package com.ethereal.confidant.ai.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jie Jie
 * date 2023-08-17
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "closeai")
public class CloseAIProperties {

    private String token;
}
