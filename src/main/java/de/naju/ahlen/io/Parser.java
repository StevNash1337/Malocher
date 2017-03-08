package de.naju.ahlen.io;

import de.naju.ahlen.model.Area;

import java.io.File;

/**
 * Read in .xls file and parse into model
 * Created by Steffen on 08.03.2017.
 */
public interface Parser {

    Area parse(String name, File fileHours, File fileAddresses);
}
