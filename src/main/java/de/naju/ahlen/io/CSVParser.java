package de.naju.ahlen.io;

import au.com.bytecode.opencsv.CSVReader;
import de.naju.ahlen.model.Area;
import de.naju.ahlen.model.Operation;
import de.naju.ahlen.model.Person;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Steffen on 08.03.2017.
 */
public class CSVParser implements Parser {

    private CSVReader reader;

    /**
     * Read addresses and than the matrix with working hours per person and the dates of every operation
     *
     * @param name Name des NSG
     * @param fileHours .csv Datei mit den Stunden der Person pro Einsatz und dem Datum jedes Einsatzes
     * @param fileAddresses .csv Datei mit den Namen und Adressen aller Personen die dort mitgearbeitet haben
     * @return Area Objekt mit allen Daten
     */
    public Area parse(String name, File fileHours, File fileAddresses) {
        // Read addresses
        List<Person> persons = new ArrayList<>();
        try {
            reader = new CSVReader(new FileReader(fileAddresses), '\t');
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                Person person = new Person();
                if (!(nextLine[0].isEmpty())) {
                    person.setFirstName(nextLine[0]);
                }
                if (!(nextLine[1].isEmpty())) {
                    person.setLastName(nextLine[1]);
                }
                if (!(nextLine[2].isEmpty())) {
                    person.setAddress(nextLine[2]);
                }
                if (!(nextLine[3].isEmpty())) {
                    person.setPostCode(Integer.parseInt(nextLine[3]));
                }
                if (!(nextLine[4].isEmpty())) {
                    person.setCity(nextLine[4]);
                }
                persons.add(person);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Read hour matrix
        Area area = new Area();
        try {
            int maxOperations;
            List<Date> listOperationDates = new ArrayList<>();
            area.setName(name);

            reader = new CSVReader(new FileReader(fileHours), '\t');

            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine[0].isEmpty()) {
                    break;
                }
                // nextLine[] is an array of values from the line
                // System.out.println(nextLine[0] + nextLine[1] + "etc...");

                // 1. Zeile Datum parsen
                if (nextLine[0].equals("Name")) {
                    for (int i = 1; i < nextLine.length; i++) {
                        DateFormat format = new SimpleDateFormat("dd.MM.yy");
                        Date date = new Date();
                        try {
                            date = format.parse(nextLine[i]);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        listOperationDates.add(date);
                    }
                }

                // Jede weitere Zeile, welche auch nicht leer ist
                if (!(nextLine[0].equals("Name")) && !(nextLine[0].equals(""))) {
                    Person person = new Person();
                    // TODO: genauere Abfrage, Personen könnten gleichen Vornamen haben
                    for (Person p : persons) {
                        if (p.getFirstName().equals(nextLine[0])) {
                            person = p;
                        }
                    }
                    for (int i = 1; i < nextLine.length; i++) {
                        if (nextLine[i].isEmpty()) {
                            continue;
                        }
                        Operation op = new Operation();
                        // Das erste Element der Datumsliste ist in der zweiten Spalte von nextLine, daher i-1
                        op.setDate(listOperationDates.get(i-1));
                        op.setDuration(Integer.parseInt(nextLine[i]));
                        op.setComment("");
                        person.getOperations().add(op);
                    }
                    area.getPersons().add(person);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return area;
    }
}
