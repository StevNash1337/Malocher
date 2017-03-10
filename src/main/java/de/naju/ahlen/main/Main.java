package de.naju.ahlen.main;

import de.naju.ahlen.io.CSVParser;
import de.naju.ahlen.io.ODTWriter;
import de.naju.ahlen.io.Parser;
import de.naju.ahlen.io.Writer;
import de.naju.ahlen.model.Area;
import org.apache.commons.io.FilenameUtils;

import java.io.File;

/**
 * Test reader and writer implementations
 * Created by Steffen on 09.03.2017.
 */
public class Main {

    public static void main(String[] args){
        // Check File extension
        if(args.length != 6){
            System.out.println("Falsche Eingabe! Bitte folgendes Schema beachten:");
            System.out.println("Name des Gebiets");
            System.out.println("Absoluter Pfad der .csv Datei mit den Stunden");
            System.out.println("Absoluter Pfad der .csv Datei mit den Adressen");
            System.out.println("Absoluter Pfad der .odt Datei mit den Barauszahlungen");
            System.out.println("Absoluter Pfad der .odt Datei mit den Barspenden");
            System.out.println("Absoluter Pfad der .odt Datei mit den Stundenachweisen");

            return;
        }
        if(!FilenameUtils.getExtension(args[1]).equals("csv")){
            System.out.println("Die Datei mit den Stunden ist nicht im .csv Format.");
            return;
        }
        if(!FilenameUtils.getExtension(args[2]).equals("csv")){
            System.out.println("Die Datei mit den Adressen ist nicht im .csv Format.");
            return;
        }


        Parser parser = new CSVParser();
        System.out.println("Name des Gebiets: " + args[0]);
        System.out.println("Stunden: " + args[1]);
        System.out.println("Adressen: " + args[2]);

        System.out.println("Vorlage Barauszahlung: " + args[3]);
        System.out.println("Vorlage Barspende: " + args[4]);
        System.out.println("Vorlage Arbeitsnachweise: " + args[5]);

        // Name des Gebiets; Datei mit den Stunden; Datei mit den Adressen
        //Area area = parser.parse(args[0], new File(args[1]), new File(args[2]));
        //area.toString();

        Writer writer = new ODTWriter(new File(args[3]), new File(args[4]), new File(args[5]));
        writer.write(new Area());
    }
}
