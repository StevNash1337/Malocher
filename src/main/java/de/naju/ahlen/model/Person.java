package de.naju.ahlen.model;

/**
 * Created by Steffen on 08.03.2017.
 */

import java.util.ArrayList;
import java.util.List;

public class Person {

    private String firstName;
    private String lastName;
    private String city;
    private int postCode;
    private String address;
    private List<Operation> operations = new ArrayList<>();

    public Person() {
        this.firstName = "";
        this.lastName = "";
        this.city = "";
        this.postCode = -1;
        this.address = "";
        this.operations = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPostCode() {
        return postCode;
    }

    public void setPostCode(int postCode) {
        this.postCode = postCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }
}
