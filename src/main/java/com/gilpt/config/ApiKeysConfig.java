package com.gilpt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "api.keys")
public record ApiKeysConfig(String odsay, String gpt) {
}
