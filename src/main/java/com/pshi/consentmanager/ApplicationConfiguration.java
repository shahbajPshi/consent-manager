package com.pshi.consentmanager;

import com.pshi.consentmanager.link.ClientRegistry;
import com.pshi.consentmanager.link.ClientRegistryProperties;
import com.pshi.consentmanager.link.discovery.Discovery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public Discovery discovery(WebClient.Builder builder, ClientRegistryProperties clientRegistryProperties) {
        return new Discovery(new ClientRegistry(builder, clientRegistryProperties));
    }
}
