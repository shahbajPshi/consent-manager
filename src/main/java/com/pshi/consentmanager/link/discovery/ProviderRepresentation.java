package com.pshi.consentmanager.link.discovery;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor
@Getter
@Value
public class ProviderRepresentation {
    String name;
    String city;
    String telephone;
    String type;
}
