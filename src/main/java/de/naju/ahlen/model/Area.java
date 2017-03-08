package de.naju.ahlen.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steffen on 08.03.2017.
 */
public @Data class Area {

    private String name;
    private float hours;
    private List<Person> persons = new ArrayList<Person>();
}
