package de.naju.ahlen.gui;

import de.naju.ahlen.io.IOEngine;

import javax.swing.*;

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

        this.window = new Window();
        this.menuPanel = new MenuPanel(this);
        window.add(menuPanel);
    }

    public void hoursButtonPressed(){

    }

    public void addressesButtonPressed(){

    }

    public void outputFolderButtonPressed(){

    }

    public void fileCashPaymentButtonPressed(){

    }

    public void fileDonationButtonPressed(){

    }

    public void fileOperationButtonPressed(){

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
    }
}
