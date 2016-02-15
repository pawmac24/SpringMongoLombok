package com.pm.mongodb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by pmackiewicz on 2016-02-11.
 */
@Data
@AllArgsConstructor
public class Address {

    private String postcode;

    private String city;

    private String street;

    private String localNumber;
}
