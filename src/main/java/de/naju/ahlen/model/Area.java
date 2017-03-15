package de.naju.ahlen.model;

import java.util.*;

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

    public Map<Date, Float> getHoursByDate() {
        Map<Date, Float> result = new HashMap<>();

        for (Person p : persons) {
            for (Operation o : p.getOperations()) {
                Date date = o.getDate();
                float duration = o.getDuration();
                if (!result.containsKey(date)) {
                    result.put(date, duration);
                } else {
                    result.put(date, result.get(date) + duration);
                }
            }
        }
        return result;
    }

    public Map<Date, List<Person>> getPersonsByDate() {
        Map<Date, List<Person>> result = new HashMap<>();

        for (Person p : persons) {
            for (Operation o : p.getOperations()) {
                Date date = o.getDate();
                if (!result.containsKey(date)) {
                    result.put(date, new ArrayList<>());
                    result.get(date).add(p);
                } else if (!result.get(date).contains(p)) {
                    result.get(date).add(p);
                }
            }
        }
        return result;
    }

    /**
     *
     * @return return the comment of the first Operation found for each date
     */
    public Map<Date, String> getOpeartionDescriptionByDate() {
        Map<Date, String> result = new HashMap<>();

        for (Person p : persons) {
            for (Operation o : p.getOperations()) {
                Date date = o.getDate();
                if (!result.containsKey(date)) {
                    result.put(date, o.getComment());
                }
            }

        }
        return result;
    }

    public Date getStartDate() {
        SortedSet<Date> dates = new TreeSet<>();

        for (Person p : persons) {
            for (Operation o : p.getOperations()) {
                Date date = o.getDate();
                dates.add(date);
            }
        }

        return dates.first();
    }

    public Date getEndDate() {
        SortedSet<Date> dates = new TreeSet<>();

        for (Person p : persons) {
            for (Operation o : p.getOperations()) {
                Date date = o.getDate();
                dates.add(date);
            }
        }

        return dates.last();
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Name des Gebiets: ")
                .append(name)
                .append("; geleistete Stunden: ")
                .append(hours)
                .append("\nBeteiligte Personen: ");
        for(Person p : persons){
            sb.append("\n\n");
            sb.append("------------------------------------------------------------------------");
            sb.append("\n\t").append(p.toString());
            sb.append("\n------------------------------------------------------------------------");
        }
        return sb.toString();
    }
}
