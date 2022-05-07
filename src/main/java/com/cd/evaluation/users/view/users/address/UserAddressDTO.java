package com.cd.evaluation.users.view.users.address;

import com.cd.evaluation.users.commons.validation.user.address.country.ValidCountry;
import com.cd.evaluation.users.commons.validation.user.address.state.ValidBrazilState;
import com.cd.evaluation.users.model.enums.address.BrazilStateEnum;
import com.cd.evaluation.users.model.enums.address.CountriesEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * DTO users address class
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddressDTO {

    @NotBlank(message = "user.address.number.must.not.be.null.or.empty")
    private int number;

    @NotBlank(message = "user.address.street.must.not.be.null.or.empty")
    private String street;

    @NotBlank(message = "user.address.neighborhood.must.not.be.null.or.empty")
    private String neighborhood;

    @NotBlank(message = "user.address.zip.code.must.not.be.null.or.empty")
    private String zipCode;

    //In the future, refactor the countries and states enums to a collection in the database or use some API to handle the data integrity
    @NotBlank(message = "user.address.country.must.not.be.null.or.empty")
    @ValidCountry
    private CountriesEnum country;

    @NotBlank(message = "user.address.state.must.not.be.null.or.empty")
    @ValidBrazilState(message = "user.address.brazil.state.invalid")
    private BrazilStateEnum state;
}
