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
    private File fileOperationOverview;
    private File fileOperation;

    private Area area;

    public IOEngine(GUIController guiController){
        this.guiController = guiController;
        name = "";
        hours = null;
        addresses = null;
        outputFolder = "";
        fileCashPayment = null;
        fileDonation = null;
        fileOperation = null;
        fileOperationOverview = null;
    }

    public void readAndWriteData(){
        Area area = readData();
        writeData(area);
    }

    private Area readData(){
        parser = new CSVParser();
        area = parser.parse(name, hours, addresses);
        System.out.println(area.toString());
        return area;
    }

    private void writeData(Area area){
        writer = new ODTWriter(outputFolder, fileCashPayment, fileDonation, fileOperationOverview, fileOperation);
        //area = new Area();
        writer.write(area);
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

    public File getFileOperationOverview() {
        return fileOperationOverview;
    }

    public void setFileOperationOverview(File fileOperationOverview) {
        this.fileOperationOverview = fileOperationOverview;
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
