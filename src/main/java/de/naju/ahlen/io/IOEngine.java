package de.naju.ahlen.io;

import de.naju.ahlen.gui.GUIController;
import de.naju.ahlen.model.Area;

import java.io.File;

/**
 * Created by Steffen on 11.03.2017.
 */
public class IOEngine {

    private GUIController guiController;

    private Parser parser;
    private Writer writer;

    private String name;
    private File hours;
    private File addresses;

    private String outputFolder;
    private File fileCashPayment;
    private File fileDonation;
    private File fileOperation;

    private Area area;

    public IOEngine(GUIController guiController){
        this.guiController = guiController;
    }

    public void readData(){
        parser = new CSVParser();
        area = parser.parse(name, hours, addresses);
        System.out.println(area.toString());
    }

    public void writeData(){
        writer = new ODTWriter(outputFolder, fileCashPayment, fileDonation, fileOperation);
        writer.write(new Area());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getHours() {
        return hours;
    }

    public void setHours(File hours) {
        this.hours = hours;
    }

    public File getAddresses() {
        return addresses;
    }

    public void setAddresses(File addresses) {
        this.addresses = addresses;
    }

    public File getFileCashPayment() {
        return fileCashPayment;
    }

    public void setFileCashPayment(File fileCashPayment) {
        this.fileCashPayment = fileCashPayment;
    }

    public File getFileDonation() {
        return fileDonation;
    }

    public void setFileDonation(File fileDonation) {
        this.fileDonation = fileDonation;
    }

    public File getFileOperation() {
        return fileOperation;
    }

    public void setFileOperation(File fileOperation) {
        this.fileOperation = fileOperation;
    }

    public String getOutputFolder() {
        return outputFolder;
    }

    public void setOutputFolder(String outputFolder) {
        this.outputFolder = outputFolder;
    }
}
