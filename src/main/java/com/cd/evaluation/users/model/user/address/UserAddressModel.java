package com.cd.evaluation.users.model.user.address;

import com.cd.evaluation.users.model.enums.address.BrazilStateEnum;
import com.cd.evaluation.users.model.enums.address.CountriesEnum;
import lombok.Data;

@Data
public class UserAddressModel {

    private Long number;

    private String street;

    private String neighborhood;

    private String zipCode;

    //In the future, refactor the countries and states enums to a collection in the database or use some API to handle the data integrity
    private CountriesEnum country;

    private BrazilStateEnum state;
}
