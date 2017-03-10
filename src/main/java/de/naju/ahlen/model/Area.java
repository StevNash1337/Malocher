package de.naju.ahlen.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steffen on 08.03.2017.
 */
public class Area {

    private String name;
    private float hours;
    private List<Person> persons;

    public Area() {
        this.name = "";
        this.hours = 0;
        this.persons = new ArrayList<>();
    }

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

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Name des Gebiets: ")
                .append(name)
                .append("; geleistete Stunden: ")
                .append(hours)
                .append("\nBeteiligte Personen: ");
        for(Person p : persons){
            sb.append("\n\t").append(p.toString());
        }
        return sb.toString();
    }
}
