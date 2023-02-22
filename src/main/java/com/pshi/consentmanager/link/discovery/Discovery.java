package com.pshi.consentmanager.link.discovery;

import com.pshi.consentmanager.link.ClientRegistry;
import reactor.core.publisher.Flux;

public class Discovery {

    private final ClientRegistry client;

    public Discovery(ClientRegistry client) {
        this.client = client;
    }

    public Flux<ProviderRepresentation> providersFrom(String name) {
        return client.providersOf(name)
                .map(Transformer::to);
    }
}
