package com.pshi.consentmanager.link;

import com.pshi.consentmanager.link.discovery.model.Provider;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class ClientRegistry implements IClientRegistry {

    private final WebClient.Builder webClientBuilder;
    private final ClientRegistryProperties clientRegistryProperties;

    public ClientRegistry(
            WebClient.Builder webClientBuilder,
            ClientRegistryProperties clientRegistryProperties
    ) {
        this.webClientBuilder = webClientBuilder;
        this.clientRegistryProperties = clientRegistryProperties;
    }

    @Override
    public Flux<Provider> providersOf(String name) {
        return webClientBuilder.build()
                .get()
                .uri(String.format("%s/providers?name=%s", clientRegistryProperties.getUrl(), name))
                .header("client_id", clientRegistryProperties.getClientId())
                .header("X-Auth-Token", clientRegistryProperties.getXAuthToken())
                .retrieve()
                .bodyToFlux(Provider.class);
    }
}
