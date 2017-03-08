package de.naju.ahlen.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steffen on 08.03.2017.
 */
public class Area {

    private String name;
    private float hours;
    private List<Person> persons = new ArrayList<Person>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getHours() {
        return hours;
    }

    public void setHours(float hours) {
        this.hours = hours;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }
}
