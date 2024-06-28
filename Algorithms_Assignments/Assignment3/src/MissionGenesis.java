import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

// Class representing the mission of Genesis
public class MissionGenesis {

    // Private fields
    private MolecularData molecularDataHuman; // Molecular data for humans
    private MolecularData molecularDataVitales; // Molecular data for Vitales

    // Getter for human molecular data
    public MolecularData getMolecularDataHuman() {
        return molecularDataHuman;
    }

    // Getter for Vitales molecular data
    public MolecularData getMolecularDataVitales() {
        return molecularDataVitales;
    }

    // Method to read XML data from the specified filename
    public void readXML(String filename) {
        try {
            File xmlFile = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            Element humanMolecularDataElement = (Element) doc.getElementsByTagName("HumanMolecularData").item(0);
            Element vitalesMolecularDataElement = (Element) doc.getElementsByTagName("VitalesMolecularData").item(0);

            List<Molecule> humanMolecules = parseMolecules(humanMolecularDataElement);
            List<Molecule> vitalesMolecules = parseMolecules(vitalesMolecularDataElement);

            this.molecularDataHuman = new MolecularData(humanMolecules);
            this.molecularDataVitales = new MolecularData(vitalesMolecules);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Molecule> parseMolecules(Element molecularDataElement) {
        List<Molecule> molecules = new ArrayList<>();
        NodeList moleculeList = molecularDataElement.getElementsByTagName("Molecule");

        for (int i = 0; i < moleculeList.getLength(); i++) {
            Element moleculeElement = (Element) moleculeList.item(i);
            String id = moleculeElement.getElementsByTagName("ID").item(0).getTextContent();
            int bondStrength = Integer.parseInt(moleculeElement.getElementsByTagName("BondStrength").item(0).getTextContent());
            NodeList bondsList = moleculeElement.getElementsByTagName("MoleculeID");
            List<String> bonds = new ArrayList<>();
            for (int j = 0; j < bondsList.getLength(); j++) {
                Element bondElement = (Element) bondsList.item(j);
                bonds.add(bondElement.getTextContent());
            }
            molecules.add(new Molecule(id, bondStrength, bonds));
        }

        return molecules;
    }
}
