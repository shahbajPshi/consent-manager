package com.pshi.consentmanager.link;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "consentmanager.clientregistry")
@Getter
@Setter
@NoArgsConstructor
public class ClientRegistryProperties {
    private String url;
    private String XAuthToken;
    private String clientId;
}
