package de.naju.ahlen.io;

import de.naju.ahlen.model.Area;
import org.odftoolkit.odfdom.doc.OdfTextDocument;
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

    private final String VARIABLE_NODE_NAME = "text:variable-set";
    private final String VARIABLE_NODE_CONTENT = "text:name";

    private String outputFolder;

    private TextDocument docCashPayment;
    private TextDocument docDonation;
    private TextDocument docOperationOverview;
    private TextDocument docOperation;

    /**
     * @param outputFolder Folder for output files
     * @param fileCashPayment odt template for cash payments
     * @param fileDonation odt template for donations
     * @param fileOperationOverview odt template for overview of operations
     * @param fileOperation odt template for operations
     */

    public ODTWriter(String outputFolder, File fileCashPayment, File fileDonation, File fileOperationOverview, File fileOperation) {
        this.outputFolder = outputFolder;
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
        //writeCashPayment(docCashPayment, area);
        //writeDonation(docDonation, area);
        writeOperationOverview(docOperationOverview, area);
        //writeOperation(docOperation, area);
    }

    private void writeCashPayment(TextDocument doc, Area area) {

    }

    private void writeDonation(TextDocument doc, Area area) {

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

        int startRow = 1;
        for (Date date : dates) {

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

            if (startRow == 0) {
                cellHours.setValueType("float");
                cellHours.setDoubleValue(hours_double);
                cellHours.setFormatString("0.#");
            } else {
                //cellHours.setValueType("string");
                cellHours.setStringValue(format.format(hours_double));
                cellHours.setFont(font_hand);
                cellHours.setHorizontalAlignment(StyleTypeDefinitions.HorizontalAlignmentType.CENTER);
            }
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
