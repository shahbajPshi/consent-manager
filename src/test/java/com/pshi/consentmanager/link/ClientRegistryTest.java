package com.pshi.consentmanager.link;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pshi.consentmanager.link.discovery.model.Provider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ClientRegistryTest {
//    any ClientRequest instance passed as an argument to a method during
//    the test can be captured and inspected using the captor variable.
    @Captor
    ArgumentCaptor<ClientRequest> captor;

    @Mock
    private ExchangeFunction exchangeFunction;

    ClientRegistry clientRegistry;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        WebClient.Builder webClientBuilder = WebClient.builder()
                .exchangeFunction(exchangeFunction);
        ClientRegistryProperties clientRegistryProperties = new ClientRegistryProperties("localhost:8000", "", "");
        clientRegistry = new ClientRegistry(webClientBuilder, clientRegistryProperties);
    }

    @Test
    void getProvidersByGivenName() throws IOException {
        var source = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(
                ClassLoader.getSystemClassLoader().getResource("provider.json"),
                new TypeReference<List<Provider>>() {}
        );
        var jsonNode = new ObjectMapper().readValue(
                ClassLoader.getSystemClassLoader().getResource("provider.json"),
                new TypeReference<List<JsonNode>>() {}
        );
        when(exchangeFunction.exchange(captor.capture())).thenReturn(
                Mono.just(ClientResponse.create(HttpStatus.OK)
                        .header("Content-Type", "application/json")
                        .body(jsonNode.toString()).build()
        ));

        StepVerifier.create(clientRegistry.providersOf("Max"))
                .assertNext(provider -> {
                    assertThat(provider.getName()).isEqualTo(source.get(0).getName());
                    assertThat(provider.getAddresses().get(0).getCity()).isEqualTo(source.get(0).getAddresses().get(0).getCity());
                    assertThat(provider.getTelecoms().get(0).getValue()).isEqualTo(source.get(0).getTelecoms().get(0).getValue());
                    assertThat(provider.getTypes().get(0).getCoding().get(0).getCode()).isEqualTo(source.get(0).getTypes().get(0).getCoding().get(0).getCode());
                })
                .verifyComplete();

        assertThat(captor.getValue().url().toString()).isEqualTo("localhost:8000/providers?name=Max");
    }
}
