package de.naju.ahlen.io;

import de.naju.ahlen.model.Area;
import de.naju.ahlen.model.Person;
import org.odftoolkit.odfdom.dom.element.office.OfficeTextElement;
import org.odftoolkit.odfdom.type.Color;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.style.Font;
import org.odftoolkit.simple.style.StyleTypeDefinitions;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Table;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Writer implementation for ODT templates
 * Created by simon on 10.03.17.
 */
public class ODTWriter implements Writer{

    // TODO disable info logging from odfdom
    // TODO create subfolders automatically

    private final String VARIABLE_NODE_NAME = "text:variable-set";
    private final String VARIABLE_NODE_CONTENT = "text:name";

    private String outputFolder;

    private TextDocument docCashPayment;
    private TextDocument docDonation;
    private TextDocument docOperationOverview;
    private TextDocument docOperation;

    private Date auszahlungsDatum;

    /**
     * @param outputFolder Folder for output files
     * @param fileCashPayment odt template for cash payments
     * @param fileDonation odt template for donations
     * @param fileOperationOverview odt template for overview of operations
     * @param fileOperation odt template for operations
     */

    public ODTWriter(String outputFolder, File fileCashPayment, File fileDonation, File fileOperationOverview, File fileOperation) {
        this.outputFolder = outputFolder;
        auszahlungsDatum = new Date();
        try {
            docCashPayment = TextDocument.loadDocument(fileCashPayment);
            docDonation = TextDocument.loadDocument(fileDonation);
            docOperation = TextDocument.loadDocument(fileOperation);
            docOperationOverview = TextDocument.loadDocument(fileOperationOverview);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(Area area) {
        // TODO create mapping from variable_name to content
        // TODO fill out tables
        writeCashPayment(docCashPayment, area);
        writeDonation(docDonation, area);
        writeOperationOverview(docOperationOverview, area);
        writeOperation(docOperation, area);
    }

    private void writeCashPayment(TextDocument doc, Area area) {
        DecimalFormat format = new DecimalFormat("0.#");
        format.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.GERMAN));

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");

        for (Person person : area.getPersons()) {
            Map<String, String> mapping = new HashMap<>();

            String hourString = format.format(person.getHours());
            String betrag = format.format(person.getHours()*10);

            mapping.put("Person", person.getFullName());
            mapping.put("NSG", area.getName());
            mapping.put("Summe", hourString);
            mapping.put("Betrag", betrag);
            mapping.put("Datum", dateFormat.format(auszahlungsDatum));

            writeVariables(doc, mapping);

            Table table = getTable(doc, 0);

            Map<Date, Float> hoursByDate = person.getHoursByDate();
            SortedSet<Date> dates = new TreeSet(hoursByDate.keySet());

            int maxRows = table.getRowCount()-2;
            if (dates.size() > maxRows) {
                System.out.println("To many entries");
                throw new IndexOutOfBoundsException("To many rows needed in Cash Payment Template (got " + maxRows + " need " + dates.size() + ").");
            }

            String style = table.getCellByPosition(0,0).getCellStyleName();

            int startRow = 1;

            // lösche zweite bis vorletzte Zeile
            for (int i = 0; i < maxRows; i++) {
                int currentRow = startRow + i;

                table.getCellByPosition(0, currentRow).setStringValue("");
                table.getCellByPosition(1, currentRow).setStringValue("");
            }

            for (Date date : dates) {

                Float hours = hoursByDate.get(date);
                Double hours_double = hours.doubleValue();

                Font font_hand = new Font("Arial", StyleTypeDefinitions.FontStyle.REGULAR, 10, Color.BLACK);

                Cell cellDate = table.getCellByPosition(0, startRow);
                //cellDate.setCellStyleName(style);
                cellDate.setStringValue(dateFormat.format(date));
                cellDate.setFont(font_hand);
                cellDate.setHorizontalAlignment(StyleTypeDefinitions.HorizontalAlignmentType.LEFT);

                Cell cellHours = table.getCellByPosition(1, startRow);
                //String styleBeforeCell = cellHours.getCellStyleName();
                //String styleBefore = cellHours.getStyleName();
                //cellHours.setCellStyleName(style);
                //cellHours.setCellStyleName("");

                // can't remove trailing decimal point
                //cellHours.setValueType("float");
                //cellHours.setDoubleValue(hours_double);
                //cellHours.setFormatString("0.#");

                // TODO find out why the format gets messed up
                //cellHours.setValueType("string");
                cellHours.setStringValue(format.format(hours_double));
                cellHours.setFont(font_hand);
                cellHours.setHorizontalAlignment(StyleTypeDefinitions.HorizontalAlignmentType.CENTER);

                startRow++;
            }

            String fileName = "Barauszahlung " + person.getFullName() + ".odt";
            try {
                Path path = Paths.get(outputFolder);
                path = path.resolve("Barauszahlung");
                path = path.resolve(fileName);
                doc.save(path.toFile());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }



    }

    private void writeDonation(TextDocument doc, Area area) {
        for (Person person : area.getPersons()) {
            DecimalFormat format = new DecimalFormat("#.##");

            Map <String, String> mapping = new HashMap<>();

            mapping.put("Person", person.getFullName());
            mapping.put("Spendenempfaenger", "Lucas Camphausen");
            mapping.put("Betrag", format.format(person.getHours()*10));

            writeVariables(doc, mapping);

            String fileName = "Barspende " + person.getFullName() + ".odt";
            try {
                Path path = Paths.get(outputFolder);
                path = path.resolve("Barspende");
                path = path.resolve(fileName);
                doc.save(path.toFile());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Table getTable(TextDocument doc, int index) {
        List<Table> tables = doc.getTableList();
        return tables.get(index);
    }

    private void writeOperationOverview(TextDocument doc, Area area) {
        Map<String, String> mapping = new HashMap<>();

        DecimalFormat format = new DecimalFormat("0.#");
        format.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.GERMAN));
        String hours_total_string = format.format(area.getHours());

        Date start = area.getStartDate();
        Date end = area.getEndDate();

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy", Locale.GERMAN);

        String zeitraumString = dateFormat.format(start) + " - " + dateFormat.format(end);

        mapping.put("gebiet", area.getName());
        mapping.put("zeitraum", zeitraumString);
        mapping.put("stunden", hours_total_string);


        writeVariables(doc, mapping);
        Table table = getTable(doc, 1);
        Map<Date, Float> hoursByDate = area.getHoursByDate();
        SortedSet<Date> dates = new TreeSet(hoursByDate.keySet());

        int maxRows = table.getRowCount()-5;
        if (dates.size() > maxRows) {
            System.out.println("To many entries");
            throw new IndexOutOfBoundsException("To many rows needed in Overview Template (got " + maxRows + " need " + dates.size() + ").");
        }

        String style = table.getCellByPosition(0,0).getCellStyleName();

        Map<Date, String> operationDescriptions = area.getOpeartionDescriptionByDate();

        int startRow = 1;
        for (Date date : dates) {
            // TODO benutze echte Beschreibung, noch nicht im m Odel vorhanden
            String arbeitBeschreibung = operationDescriptions.get(date);

            Float hours = hoursByDate.get(date);
            Double hours_double = hours.doubleValue();

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            Cell cellDate = table.getCellByPosition(0, startRow);
            Font font_hand = new Font("Helvetica", StyleTypeDefinitions.FontStyle.REGULAR, 12, Color.BLACK);
            //cellDate.setCellStyleName(style);
            cellDate.setValueType("date");
            cellDate.setDateValue(cal);
            cellDate.setFormatString("dd.MM.yy");

            Cell cellHours = table.getCellByPosition(1, startRow);
            //String styleBeforeCell = cellHours.getCellStyleName();
            //String styleBefore = cellHours.getStyleName();
            //cellHours.setCellStyleName(style);
            //cellHours.setCellStyleName("");

            // can't remove trailing decimal point
            //cellHours.setValueType("float");
            //cellHours.setDoubleValue(hours_double);
            //cellHours.setFormatString("0.#");

            // TODO find out why the format gets messed up
            //cellHours.setValueType("string");
            cellHours.setStringValue(format.format(hours_double));
            cellHours.setFont(font_hand);
            cellHours.setHorizontalAlignment(StyleTypeDefinitions.HorizontalAlignmentType.CENTER);

            Cell cellArbeit = table.getCellByPosition(2, startRow);
            cellArbeit.setStringValue(arbeitBeschreibung);
            cellArbeit.setFont(font_hand);
            cellArbeit.setHorizontalAlignment(StyleTypeDefinitions.HorizontalAlignmentType.LEFT);

            startRow++;
        }

        try {
            Path path = Paths.get(outputFolder);
            path = path.resolve("Stundenachweis.odt");
            doc.save(path.toFile());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeOperation(TextDocument doc, Area area) {
        DecimalFormat format = new DecimalFormat("0.#");
        format.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.GERMAN));

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");


        Map<Date, List<Person>> personsByDate = area.getPersonsByDate();
        SortedSet<Date> dates = new TreeSet(personsByDate.keySet());

        Map<Date, String> operationDescriptions = area.getOpeartionDescriptionByDate();
        for (Date date : dates) {
            Map<String, String> mapping = new HashMap<>();

            Float dateSum = area.getHoursByDate().get(date);

            mapping.put("Datum", dateFormat.format(date));
            // TODO Benutze echte beschreibung, noch nicht im model vorhanden
            mapping.put("Taetigkeit", operationDescriptions.get(date));



            Table table = getTable(doc, 0);
            List<Person> persons = personsByDate.get(date);
            int maxRows = table.getRowCount()-3;
            if (persons.size() > maxRows) {
                System.out.println("To many entries");
                throw new IndexOutOfBoundsException("To many rows needed in Cash Payment Template (got " + maxRows + " need " + dates.size() + ").");
            }

            int startRow = 1;

            // lösche zweite bis vorletzte Zeile
            for (int i = 0; i < maxRows; i++) {
                int currentRow = startRow + i;

                table.getCellByPosition(0, currentRow).setStringValue("");
                table.getCellByPosition(1, currentRow).setStringValue("");
                table.getCellByPosition(2, currentRow).setStringValue("");
                table.getCellByPosition(3, currentRow).setStringValue("");
            }

            Float hoursTotal = 0.0f;
            for (Person p : persons) {
                Float hours = p.getHoursByDate().get(date);
                hoursTotal += hours;
                Double hours_double = hours.doubleValue();

                Font font_hand = new Font("Times New Roman", StyleTypeDefinitions.FontStyle.REGULAR, 10, Color.BLACK);

                Cell cellHours = table.getCellByPosition(0, startRow);
                // TODO find out why the format gets messed up
                //cellHours.setValueType("string");
                cellHours.setStringValue(format.format(hours_double));
                cellHours.setFont(font_hand);
                cellHours.setHorizontalAlignment(StyleTypeDefinitions.HorizontalAlignmentType.CENTER);

                //String styleBeforeCell = cellHours.getCellStyleName();
                //String styleBefore = cellHours.getStyleName();
                //cellHours.setCellStyleName(style);
                //cellHours.setCellStyleName("");

                // can't remove trailing decimal point
                //cellHours.setValueType("float");
                //cellHours.setDoubleValue(hours_double);
                //cellHours.setFormatString("0.#");

                Cell cellName = table.getCellByPosition(1, startRow);
                cellName.setStringValue(p.getFullName());
                cellName.setFont(font_hand);
                cellName.setHorizontalAlignment(StyleTypeDefinitions.HorizontalAlignmentType.LEFT);

                Cell cellAnschrift = table.getCellByPosition(2, startRow);
                cellAnschrift.setStringValue(p.getFullAddress());
                cellAnschrift.setFont(font_hand);
                cellAnschrift.setHorizontalAlignment(StyleTypeDefinitions.HorizontalAlignmentType.LEFT);

                startRow++;
            }

            mapping.put("Summe", format.format(hoursTotal));

            writeVariables(doc, mapping);


            String fileName = "Tagesnachweis " + dateFormat.format(date) + ".odt";
            try {
                Path path = Paths.get(outputFolder);
                path = path.resolve("Tagesnachweis");
                path = path.resolve(fileName);
                doc.save(path.toFile());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private void writeVariables(TextDocument doc, Map<String, String> mapping) {
        List<Node> variables = collectVariables(doc);

        for (Node node : variables) {
            String variableName = node.getAttributes().getNamedItem("text:name").getNodeValue();
            String variableValue = node.getChildNodes().item(0).getNodeValue();
            System.out.println("variable_name: " + variableName);
            System.out.println("variable_value: " + variableValue);

            if (mapping.containsKey(variableName)) {
                String render = mapping.get(variableName);
                node.getChildNodes().item(0).setNodeValue(render);
            }

            variableValue = node.getChildNodes().item(0).getNodeValue();
            System.out.println("variable_name: " + variableName);
            System.out.println("variable_value: " + variableValue);
        }
    }

    private List<Node> collectVariables(TextDocument doc) {
        List<Node> vars = new ArrayList<>();

        try {
            OfficeTextElement contentRoot = doc.getContentRoot();
            NodeList childNodes = contentRoot.getChildNodes();
            vars = collectNodeVariables(childNodes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vars;
    }

    private List<Node> collectNodeVariables(NodeList childNodes) {
        List<Node> vars = new ArrayList<>();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);

            if (item instanceof NodeList) {
                List<Node> innerVars = collectNodeVariables(item.getChildNodes());
                vars.addAll(innerVars);
            }
            if (item.getNodeName().equals(VARIABLE_NODE_NAME)) {
                //String nodeValue = item.getAttributes().getNamedItem("text:name").getNodeValue();
                //item.getChildNodes().item(0).setNodeValue("REPLACEMENT"+i);
                vars.add(item);
            }
        }

        return vars;
    }
}
