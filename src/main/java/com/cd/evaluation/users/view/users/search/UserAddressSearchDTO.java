package com.cd.evaluation.users.view.users.search;

import lombok.Data;

@Data
public class UserAddressSearchDTO {
    private Long number;

    private String street;

    private String neighborhood;

    private String zipCode;

    private String country;

    private String state;
}
