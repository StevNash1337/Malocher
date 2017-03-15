package de.naju.ahlen.gui;

import javax.swing.*;

/**
 * Created by Steffen on 11.03.2017.
 */
public class Window extends JFrame {

    public Window(){
        this.setSize(720, 300);
        this.setTitle("Stundenabrechnung - Malocher v0.3");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
