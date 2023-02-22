package com.pshi.consentmanager.link;

import com.pshi.consentmanager.link.discovery.model.Provider;
import reactor.core.publisher.Flux;

public interface IClientRegistry {
    Flux<Provider> providersOf(String name);
}
