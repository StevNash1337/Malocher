package de.naju.ahlen.gui;

import de.naju.ahlen.io.IOEngine;

import javax.swing.*;
import java.io.File;

/**
 * Created by Steffen on 11.03.2017.
 */
public class GUIController {

    private IOEngine ioEngine;

    private Window window;
    private MenuPanel menuPanel;

    public GUIController(){
        try {
            // Set cross-platform Java L&F (also called "Metal")
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException|ClassNotFoundException|InstantiationException|IllegalAccessException e) {
            // handle exception
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                window = new Window();
            }
        }); // Erzeugt einen neuen Thread, der eine Instanz von TestJFrame erzeugt
        //this.window = new Window();
        this.menuPanel = new MenuPanel(this);
        window.add(menuPanel);

        window.revalidate();
        window.repaint();
    }

    public void nameNSG(String name){
        ioEngine.setName(name);
    }

    public void hoursButtonPressed(File file){
        ioEngine.setHours(file);
    }

    public void addressesButtonPressed(File file){
        ioEngine.setAddresses(file);
    }

    public void outputFolderButtonPressed(String path){
        ioEngine.setOutputFolder(path);
    }

    public void fileCashPaymentButtonPressed(File file){
        ioEngine.setFileCashPayment(file);
    }

    public void fileDonationButtonPressed(File file){
        ioEngine.setFileDonation(file);
    }

    public void fileOperationButtonPressed(File file){
        ioEngine.setFileOperation(file);
    }

    public void fileOperationOverviewButtonPressed(File file){
        ioEngine.setFileOperationOverview(file);
    }

    public Window getWindow() {
        return window;
    }

    public void setWindow(Window window) {
        this.window = window;
    }

    public MenuPanel getMenuPanel() {
        return menuPanel;
    }

    public void setMenuPanel(MenuPanel menuPanel) {
        this.menuPanel = menuPanel;
    }

    public void setIoEngine(IOEngine ioEngine) {
        this.ioEngine = ioEngine;
    }

    public void processButtonPressed() {
        ioEngine.readAndWriteData();
    }

    private void setProcessButtonEnablbeIfAllValid(Boolean valid){
        menuPanel.getbProcess().setEnabled(valid);
    }

    public boolean validateAllData(){
        boolean valid = !(ioEngine.getName().equals("")
                || ioEngine.getAddresses() == null
                || ioEngine.getHours() == null
                || ioEngine.getOutputFolder().equals("")
                || ioEngine.getFileCashPayment() == null
                || ioEngine.getFileDonation() == null
                || ioEngine.getFileOperation() == null
                || ioEngine.getFileOperationOverview() == null);

        setProcessButtonEnablbeIfAllValid(valid);
        return valid;
    }

    public void createErrorMessege(){

    }
}
