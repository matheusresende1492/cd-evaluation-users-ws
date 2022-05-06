package com.cd.evaluation.users.model.enums.address;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CountriesEnum {
    //At this moment this API only supports Brazil
    BR("Brasil");

    private final String name;
}
