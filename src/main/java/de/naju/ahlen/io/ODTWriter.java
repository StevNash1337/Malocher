package de.naju.ahlen.io;

import de.naju.ahlen.model.Area;
import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.odftoolkit.odfdom.dom.element.office.OfficeTextElement;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.table.Table;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.*;

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
        writeVariables(doc);
        Table table = getTable(doc, 2);
        Map<Date, Float> hoursByDate = area.getHoursByDate();
        SortedSet<Date> dates = new TreeSet(hoursByDate.keySet());

        int maxRows = table.getRowCount()-5;
        if (dates.size() > maxRows) {
            System.out.println("To many entries");
            throw new IndexOutOfBoundsException("To many rows needed in Overview Template (got " + maxRows + " need " + dates.size() + ").");
        }

        int startRow = 2;
        for (Date date : dates) {
            Float hours = hoursByDate.get(date);

            table.getCellByPosition(1, startRow).setDisplayText(date.toString());
            table.getCellByPosition(2, startRow).setDisplayText(hours.toString());
            startRow++;
        }

        try {
            doc.save("replacementTest");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeOperation(TextDocument doc, Area area) {

    }

    private void writeVariables(TextDocument doc) {
        List<Node> variables = collectVariables(doc);

        for (Node node : variables) {
            System.out.println("variable_name: " + node.getAttributes().getNamedItem("text:name").getNodeValue());
            System.out.println("variable_value: " + node.getChildNodes().item(0).getNodeValue());
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
