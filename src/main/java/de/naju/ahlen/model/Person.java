package de.naju.ahlen.model;



import java.util.*;

/**
 * Created by Steffen on 08.03.2017.
 */
public class Person {

    private String firstName;
    private String lastName;
    private String city;
    private int postCode;
    private String address;
    private List<Operation> operations;

    public Person() {
        this.firstName = "Unknown";
        this.lastName = "Unknown";
        this.city = "Unknown";
        this.postCode = -1;
        this.address = "Unknown";
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

    public String getFullName() {
        return getFirstName() + " " +getLastName();
    }

    public String getFullAddress() {
        return address + ", " + postCode + " " + city;
    }

    public float getHours() {
        float sum = 0.0f;

        for (Operation op : operations) {
            sum += op.getDuration();
        }

        return sum;
    }

    public Map<Date, Float> getHoursByDate() {
        Map<Date, Float> result = new HashMap<>();

        for (Operation o : operations) {
            Date date = o.getDate();
            float duration = o.getDuration();
            if (result.containsKey(date)) {
                System.out.println("Person " + getFirstName() + " worked multiple times on " + date);
            } else {
                result.put(date, duration);
            }
        }
        return result;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Vorname: ")
                .append(firstName)
                .append("; Nachname: ")
                .append(lastName)
                .append("; Adresse: ")
                .append(address)
                .append("; PLZ: ")
                .append(postCode)
                .append("; Stadt: ")
                .append(city)
                .append("\nEinsätze: ");
        for(Operation o : operations){
            sb.append("\n\t").append(o.toString());
        }
        return sb.toString();
    }
}
