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
    }
}
