package com.pshi.consentmanager.link.discovery;

import com.pshi.consentmanager.link.ClientRegistry;
import com.pshi.consentmanager.link.discovery.model.Address;
import com.pshi.consentmanager.link.discovery.model.Telecom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

import static com.pshi.consentmanager.link.discovery.TestBuilders.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class DiscoveryTest {

    private Discovery discovery;

    @Mock
    ClientRegistry clientRegistry;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void providersOfCalledWithMax() {
        discovery = new Discovery(clientRegistry);
        Address address = address().use("work").build();
        Telecom telecom = telecom().use("work").build();
        var provider = provider()
                .addresses(List.of(address))
                .telecoms(List.of(telecom))
                .name("max")
                .build();
        when(clientRegistry.providersOf(eq("Max"))).thenReturn(Flux.just(provider));

        StepVerifier.create(discovery.providersFrom("Max"))
                .expectNext(Transformer.to(provider))
                .verifyComplete();
    }
}
