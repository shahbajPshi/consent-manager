package com.pshi.consentmanager.link.discovery.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Identifier {
    private String system;
    private String type;
}
