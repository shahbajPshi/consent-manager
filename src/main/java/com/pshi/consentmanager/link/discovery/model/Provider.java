package com.pshi.consentmanager.link.discovery.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@AllArgsConstructor
@Builder
public class Provider {
    private String resourceType;
    @JsonAlias("identifier")
    private List<Identifier> identifiers;
    @JsonAlias("type")
    private List<Type> types;
    @JsonAlias("telecom")
    private List<Telecom> telecoms;
    @JsonAlias("address")
    private List<Address> addresses;
    private String name;
}
