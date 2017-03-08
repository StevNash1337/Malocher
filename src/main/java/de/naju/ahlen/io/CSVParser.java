package de.naju.ahlen.io;

import au.com.bytecode.opencsv.CSVReader;
import de.naju.ahlen.model.Area;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by Steffen on 08.03.2017.
 */
public class CSVParser implements Parser {

    private CSVReader reader;

    public Area parse(File file) {
        try {
            reader = new CSVReader(new FileReader(file));
            String [] nextLine;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
