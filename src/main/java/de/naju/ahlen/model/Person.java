package de.naju.ahlen.model;

/**
 * Created by Steffen on 08.03.2017.
 */

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public @Data class Person {

    private String firstName;
    private String lastName;
    private String city;
    private int postCode;
    private String street;
    private String houseNumber;
    private List<Operation> operations = new ArrayList<Operation>();
}
