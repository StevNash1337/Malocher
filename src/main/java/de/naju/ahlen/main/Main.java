package de.naju.ahlen.main;

import de.naju.ahlen.gui.GUIController;
import de.naju.ahlen.io.IOEngine;
import org.apache.commons.io.FilenameUtils;

/**
 * Startet die GUI.
 * Created by Steffen on 09.03.2017.
 */
public class Main {

    public static void main(String[] args){
        GUIController guiController = new GUIController();
        IOEngine ioEngine = new IOEngine(guiController);
        guiController.setIoEngine(ioEngine);


        // Check File extension
        if(args.length != 7){
            System.out.println("Falsche Eingabe! Bitte folgendes Schema beachten:");
            System.out.println("Name des Gebiets");
            System.out.println("Absoluter Pfad der .csv Datei mit den Stunden");
            System.out.println("Absoluter Pfad der .csv Datei mit den Adressen");
            System.out.println("Absoluter Pfad der .odt Datei mit den Barauszahlungen");
            System.out.println("Absoluter Pfad der .odt Datei mit den Barspenden");
            System.out.println("Absoluter Pfad der .odt Datei mit den Übersicht zu Stundenachweisen");
            System.out.println("Absoluter Pfad der .odt Datei mit den Stundenachweisen");
            return;
        }
        // Check if File has the right extension
        if(!FilenameUtils.getExtension(args[1]).equals("csv")){
            System.out.println("Die Datei mit den Stunden ist nicht im .csv Format.");
            return;
        }
        if(!FilenameUtils.getExtension(args[2]).equals("csv")){
            System.out.println("Die Datei mit den Adressen ist nicht im .csv Format.");
            return;
        }



        System.out.println("Name des Gebiets: " + args[0]);
        System.out.println("Stunden: " + args[1]);
        System.out.println("Adressen: " + args[2]);

        System.out.println("Vorlage Barauszahlung: " + args[3]);
        System.out.println("Vorlage Barspende: " + args[4]);
        System.out.println("Vorlage Übersicht Arbeitsnachweise: " + args[5]);
        System.out.println("Vorlage Arbeitsnachweise: " + args[6]);


    }
}
