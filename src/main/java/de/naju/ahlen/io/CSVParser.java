package de.naju.ahlen.io;

import au.com.bytecode.opencsv.CSVReader;
import de.naju.ahlen.model.Area;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Steffen on 08.03.2017.
 */
public class CSVParser implements Parser {

    private CSVReader reader;

    public Area parse(String name, File file) {
        try {
            int maxOperations;
            List<Date> listOperationDates = new ArrayList<Date>();
            Area area = new Area();
            area.setName(name);

            reader = new CSVReader(new FileReader(file),'\t');
            String [] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line
                // System.out.println(nextLine[0] + nextLine[1] + "etc...");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
