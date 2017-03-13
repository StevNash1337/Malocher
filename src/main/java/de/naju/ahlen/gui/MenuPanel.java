package de.naju.ahlen.gui;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by Steffen on 11.03.2017.
 */
public class MenuPanel extends JPanel {

    private final GUIController guiController;

    private int textFieldWidth;

    private final FileFilter CSVfilter;
    private final FileFilter ODTfilter;

    private JFileChooser fileChooser;

    private JLabel lName;
    private JLabel lHours;
    private JLabel lAddresses;
    private JLabel lOutputFolder;
    private JLabel lFileCashPayment;
    private JLabel lFileDonation;
    private JLabel lFileOperationOverview;
    private JLabel lFileOperation;

    private JTextField tName;
    private JTextField tHours;
    private JTextField tAddresses;
    private JTextField tOutputFolder;
    private JTextField tFileCashPayment;
    private JTextField tFileDonation;
    private JTextField tFileOperationOverview;
    private JTextField tFileOperation;

    private JButton bHours;
    private JButton bAddresses;
    private JButton bOutputFolder;
    private JButton bFileCashPayment;
    private JButton bFileDonation;
    private JButton bFileOperationOverview;
    private JButton bFileOperation;
    private JButton bProcess;

    public MenuPanel(GUIController guiController){
        this.guiController = guiController;
        textFieldWidth = 450;
        MigLayout layout = new MigLayout();
        setLayout(layout);

        CSVfilter = new FileNameExtensionFilter("CSV","csv");
        ODTfilter = new FileNameExtensionFilter("OpenDocumentFormat","odt");

        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

        // Labels
        lName = new JLabel("Name des NSG");
        lHours = new JLabel("Datei mit den Stunden");
        lAddresses = new JLabel("Datei mit den Adressen");
        lOutputFolder = new JLabel("Pfad für den Output");
        lFileCashPayment = new JLabel("Vorlage für die Barauszahlung");
        lFileDonation = new JLabel("Vorlage für die Barspende");
        lFileOperationOverview = new JLabel("Vorlage für den Tagesnachweis");
        lFileOperation = new JLabel("Vorlage für den Tagesnachweis");

        // Textfelder
        tName = new JTextField();
        tHours = new JTextField();
        tHours.setEditable(false);
        tAddresses = new JTextField();
        tAddresses.setEditable(false);
        tOutputFolder = new JTextField();
        tOutputFolder.setEditable(false);
        tFileCashPayment = new JTextField();
        tFileCashPayment.setEditable(false);
        tFileDonation = new JTextField();
        tFileDonation.setEditable(false);
        tFileOperationOverview = new JTextField();
        tFileOperationOverview.setEditable(false);
        tFileOperation = new JTextField();
        tFileOperation.setEditable(false);

        // Buttons für den FileChooser
        bHours = new JButton("Öffnen");
        bHours.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.setFileFilter(CSVfilter);
                int returnValue = fileChooser.showOpenDialog(MenuPanel.this);
                if (returnValue == JFileChooser.FILES_ONLY) {
                    File selectedFile = fileChooser.getSelectedFile();
                    tHours.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        bAddresses = new JButton("Öffnen");
        bAddresses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.setFileFilter(CSVfilter);
                int returnValue = fileChooser.showOpenDialog(MenuPanel.this);
                if (returnValue == JFileChooser.FILES_ONLY) {
                    File selectedFile = fileChooser.getSelectedFile();
                    tAddresses.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        bOutputFolder = new JButton("Öffnen");
        bOutputFolder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.setFileFilter(null);
                int returnValue = fileChooser.showOpenDialog(MenuPanel.this);
                if (returnValue == JFileChooser.DIRECTORIES_ONLY) {
                    tOutputFolder.setText(fileChooser.getCurrentDirectory().toString());
                }
            }
        });

        bFileCashPayment = new JButton("Öffnen");
        bFileCashPayment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.setFileFilter(ODTfilter);
                int returnValue = fileChooser.showOpenDialog(MenuPanel.this);
                if (returnValue == JFileChooser.FILES_ONLY) {
                    File selectedFile = fileChooser.getSelectedFile();
                    tFileCashPayment.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        bFileDonation = new JButton("Öffnen");
        bFileDonation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.setFileFilter(ODTfilter);
                int returnValue = fileChooser.showOpenDialog(MenuPanel.this);
                if (returnValue == JFileChooser.FILES_ONLY) {
                    File selectedFile = fileChooser.getSelectedFile();
                    tFileDonation.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        bFileOperationOverview = new JButton("Öffnen");
        bFileOperationOverview.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.setFileFilter(ODTfilter);
                int returnValue = fileChooser.showOpenDialog(MenuPanel.this);
                if (returnValue == JFileChooser.FILES_ONLY) {
                    File selectedFile = fileChooser.getSelectedFile();
                    tFileOperationOverview.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        bFileOperation = new JButton("Öffnen");
        bFileOperation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.setFileFilter(ODTfilter);
                int returnValue = fileChooser.showOpenDialog(MenuPanel.this);
                if (returnValue == JFileChooser.FILES_ONLY) {
                    File selectedFile = fileChooser.getSelectedFile();
                    tFileOperation.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        bProcess = new JButton("Verarbeiten");
        bFileOperation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiController.processButtonPressed();
            }
        });

        addComponents();
        repaint();
    }

    public void addComponents(){
        this.add(lName);
        this.add(tName, "wrap, w 200!");

        this.add(lHours);
        this.add(tHours, "w " + textFieldWidth + "!");
        this.add(bHours, "wrap");

        this.add(lAddresses);
        this.add(tAddresses, "w " + textFieldWidth + "!");
        this.add(bAddresses, "wrap");

        this.add(lOutputFolder);
        this.add(tOutputFolder, "w " + textFieldWidth + "!");
        this.add(bOutputFolder, "wrap");

        this.add(lFileCashPayment);
        this.add(tFileCashPayment, "w " + textFieldWidth + "!");
        this.add(bFileCashPayment, "wrap");

        this.add(lFileDonation);
        this.add(tFileDonation, "w " + textFieldWidth + "!");
        this.add(bFileDonation, "wrap");

        this.add(lFileOperationOverview);
        this.add(tFileOperationOverview, "w " + textFieldWidth + "!");
        this.add(bFileOperationOverview, "wrap");

        this.add(lFileOperation);
        this.add(tFileOperation, "w " + textFieldWidth + "!");
        this.add(bFileOperation, "wrap");

        this.add(bProcess, "wrap");
    }
}
