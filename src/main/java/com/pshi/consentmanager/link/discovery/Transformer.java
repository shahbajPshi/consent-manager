package com.pshi.consentmanager.link.discovery;

import com.pshi.consentmanager.link.discovery.model.Address;
import com.pshi.consentmanager.link.discovery.model.Provider;
import com.pshi.consentmanager.link.discovery.model.Telecom;

public class Transformer {
    public static ProviderRepresentation to(Provider provider) {
        Address address = new Address("", "");
        Telecom telecom = new Telecom("", "");
        if (provider.getAddresses().size() > 0) {
            address = provider.getAddresses()
                    .stream()
                    .filter(add -> add.getUse().equalsIgnoreCase("work"))
                    .findFirst()
                    .orElse(provider.getAddresses().get(0));
        }

        if (provider.getTelecoms().size() > 0) {
            telecom = provider.getTelecoms()
                    .stream()
                    .filter(tel -> tel.getUse().equalsIgnoreCase("work"))
                    .findFirst()
                    .orElse(provider.getTelecoms().get(0));
        }

        return new ProviderRepresentation(
                provider.getName(),
                address.getCity(),
                telecom.getValue(),
                provider.getTypes().get(0).getCoding().get(0).getCode()
                );
    }
}
