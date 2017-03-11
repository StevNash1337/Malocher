package de.naju.ahlen.io;

import de.naju.ahlen.model.Area;
import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.odftoolkit.odfdom.dom.element.office.OfficeTextElement;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;
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

    private OdfTextDocument docCashPayment;
    private OdfTextDocument docDonation;
    private OdfTextDocument docOperation;

    /**
     * @param outputFolder Folder for output files
     * @param fileCashPayment odt template for cash payments
     * @param fileDonation odt template for donations
     * @param fileOperation odt template for operations
     */

    public ODTWriter(String outputFolder, File fileCashPayment, File fileDonation, File fileOperation) {
        this.outputFolder = outputFolder;
        try {
            docCashPayment = OdfTextDocument.loadDocument(fileCashPayment);
            docDonation = OdfTextDocument.loadDocument(fileDonation);
            docOperation = OdfTextDocument.loadDocument(fileOperation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(Area area) {
        // TODO create mapping from variable_name to content
        // TODO fill out tables
        writeVariables(docCashPayment);
        writeVariables(docDonation);
        writeVariables(docOperation);
    }

    private void writeVariables(OdfTextDocument doc) {
        List<Node> variables = collectVariables(doc);

        for (Node node : variables) {
            System.out.println("variable_name: " + node.getAttributes().getNamedItem("text:name").getNodeValue());
            System.out.println("variable_value: " + node.getChildNodes().item(0).getNodeValue());
        }
    }

    private List<Node> collectVariables(OdfTextDocument doc) {
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
