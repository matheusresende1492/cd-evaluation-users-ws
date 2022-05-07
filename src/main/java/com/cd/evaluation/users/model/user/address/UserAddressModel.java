package com.cd.evaluation.users.model.user.address;

import com.cd.evaluation.users.model.enums.address.BrazilStateEnum;
import com.cd.evaluation.users.model.enums.address.CountriesEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddressModel {

    private int number;

    private String street;

    private String neighborhood;

    private String zipCode;

    //In the future, refactor the countries and states enums to a collection in the database or use some API to handle the data integrity
    private CountriesEnum country;

    private BrazilStateEnum state;
}
