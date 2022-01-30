package Server.SchemeCardsHandler;

import Shared.Color;
import Shared.Model.Schemes.Scheme;
import Shared.Model.Schemes.SchemeCard;
import Shared.Model.Schemes.SchemeCell;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class SchemeCardHandler {

    private final static String PATH = "src/main/resources/CustomCards/";
    private Document doc;

    public SchemeCardHandler(){
        this.doc = null;
    }

    /**
     * builds new XML file with proper tags and attributes
     * @param frontName first card's name to be saved
     * @param frontFavors first card's favors' value to be saved
     * @param frontCellsNumber first ordered list of numbers of cells to be saved
     * @param frontCellsColor first ordered list of colors of cells to be saved
     * @param rearName second card's name to be saved
     * @param rearFavors second card's favors' value to be saved
     * @param rearCellsNumber second ordered list of numbers of cells to be saved
     * @param rearCellsColor second ordered list of colors of cells to be saved
     * @author Fabrizio Siciliano
     * */
    public void buildCustomCard(String frontName, String frontFavors, String[] frontCellsNumber, String[] frontCellsColor, String rearName, String rearFavors, String[] rearCellsNumber, String[] rearCellsColor){
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            this.doc = dBuilder.newDocument();

            //root element card
            Element root = doc.createElement("card");
            doc.appendChild(root);

            //root.front
            Element front = doc.createElement("scheme");
            root.appendChild(front);

            //front attributes
            Attr frontNameAttr = doc.createAttribute("name");
            frontNameAttr.setValue(frontName);
            front.setAttributeNode(frontNameAttr);

            Attr frontFavorsAttr = doc.createAttribute("favors");
            frontFavorsAttr.setValue(frontFavors);
            front.setAttributeNode(frontFavorsAttr);

            //root.front.cell
            addXMLCells(front, frontCellsNumber, frontCellsColor);

            //root.rear
            Element rear = doc.createElement("scheme");
            root.appendChild(rear);

            //rear attributes
            Attr rearNameAttr = doc.createAttribute("name");
            rearNameAttr.setValue(rearName);
            rear.setAttributeNode(rearNameAttr);

            Attr rearFavorsAttr = doc.createAttribute("favors");
            rearFavorsAttr.setValue(rearFavors);
            rear.setAttributeNode(rearFavorsAttr);

            //root.rear.cell
            addXMLCells(rear, rearCellsNumber, rearCellsColor);

            //write content to XML file
            String fileName = frontName + "-" + rearName + ".xml";

            //initializes transformer
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            //creates DOM tree for the XML file
            DOMSource source = new DOMSource(doc);

            //creates File
            File txtFile = new File(PATH + fileName);
            while(!txtFile.createNewFile()) {
                //TODO: check this while!!!
                txtFile = new File(PATH + fileName);
            }
            //creates result
            StreamResult result = new StreamResult(PATH + fileName);
            //transforms XML to File
            transformer.transform(source, result);

            //uncomment this lines for output to console for testing and checking
            /*
            * StreamResult consoleResult = new StreamResult(System.out);
            * transformer.transform(source, consoleResult);
            */
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * XML cells adder
     * @param scheme adding children to this element
     * @param cellsNumber children to be added
     * @param cellsColor children to be added
     * @author Fabrizio Siciliano
     * */
    //support function for buildCustomCards. Adds cells to XML file passed through doc element scheme
    private void addXMLCells(Element scheme, String[] cellsNumber, String[] cellsColor){

        Element cell;
        Attr cellNumAttr;
        Attr cellColorAttr;

        for(int i=0; i<cellsNumber.length; i++){
            cell = doc.createElement("cell");

            cellNumAttr = doc.createAttribute("number");
            cellNumAttr.setValue(cellsNumber[i]);
            cell.setAttributeNode(cellNumAttr);

            cellColorAttr = doc.createAttribute("color");
            cellColorAttr.setValue(cellsColor[i]);
            cell.setAttributeNode(cellColorAttr);

            cell.appendChild(doc.createTextNode("Cell" +i));
            scheme.appendChild(cell);
        }
    }

    /**
     * reads and returns card with given name
     * @param cardName name of the card to read
     * @return card read
     * @exception NullPointerException if file could not be found
     * @exception IOException if IO error occurs
     * @author Fabrizio Siciliano
     * */
    public SchemeCard readCustomCard(String cardName) throws NullPointerException, IOException{
        try {
            File cardFile = new File(PATH + cardName + ".xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            this.doc = dBuilder.parse(cardFile);
        }
        catch(ParserConfigurationException | SAXException e){
            e.printStackTrace();
        }

        doc.getDocumentElement().normalize();

        NodeList schemes = doc.getElementsByTagName("scheme");  //should be 2
        NodeList cells = doc.getElementsByTagName("cell");      //should be 40

        //saves completeSchemeFront
        Node scheme = schemes.item(0);
        Element element = (Element) scheme;
        String frontName = new String(element.getAttribute("name"));
        int frontFavors =Integer.parseInt(element.getAttribute("favors"));

        SchemeCell[][] completeSchemeFront = createScheme(cells, false);

        //saves completeSchemeRear
        scheme = schemes.item(1);
        element = (Element) scheme;
        String rearName = new String(element.getAttribute("name"));
        int rearFavors = Integer.parseInt(element.getAttribute("favors"));

        SchemeCell[][] completeSchemeRear = createScheme(cells, true);

        //creates and returns card
        Scheme front = new Scheme(frontName,frontFavors,completeSchemeFront);
        Scheme rear = new Scheme(rearName,rearFavors,completeSchemeRear);

        return new SchemeCard(front, rear);
    }

    /**
     * creates scheme from file read
     * @param cells elements to be added to the scheme
     * @param rear is second scheme of the card
     * @return scheme read from file
     * @author Fabrizio Siciliano
     * */
    private SchemeCell[][] createScheme(NodeList cells, boolean rear){
        int cellNode, i = 0, j = 0;
        int span;
        Color color = Color.WHITE;
        SchemeCell[][] completeScheme = new SchemeCell[4][5];

        if(!rear)
            span = 0;
        else
            span = cells.getLength()/2;

        for ( cellNode = 0; cellNode < cells.getLength() / 2; cellNode++) {
            Node cell = cells.item(cellNode + span);
            Element cellElement = (Element) cell;
            if(j==5){
                j=0;
                i++;
            }
            if (cellElement.getAttribute("color").equals("W")&& cellElement.getAttribute("number").equals("0"))   {
                completeScheme[i][j]=new SchemeCell();
            }else{
                if(!cellElement.getAttribute("color").equals("W")){
                    completeScheme[i][j]=new SchemeCell(color.stringToColor(cellElement.getAttribute("color")));
                }else{
                    if (cellElement.getAttribute("color").equals("W") && !cellElement.getAttribute("number").equals("0")){
                        completeScheme[i][j]=new SchemeCell(Integer.parseInt(cellElement.getAttribute("number")));
                    }
                }
            }
            j++;
        }
        return completeScheme;
    }

    /**
     * removes card from package CustomCards
     * @param cardName name of the file to be removed
     * @return value of exact removal of the file
     * @author Fabrizio Siciliano
     * */
    public boolean removeCustomCard(String cardName){
        File fileTo = new File(PATH + cardName + ".xml");

        if(fileTo.exists()){
            if(fileTo.delete()) {
                System.out.println("Card " + cardName + " deleted");
                return true;
            }
            else{
                System.err.println("Could not delete " + cardName);
                return false;
            }
        }

        return false;
    }
}
