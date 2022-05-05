package com.cd.evaluation.users.model.user.address;

import lombok.Data;

@Data
public class UserAddress {

    private Long number;

    private String street;

    private String neighborhood;

    private String zipCode;

    private String country;

    private String state;
}
