package utils;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import mapEditor.ImageForObject;
import mapEditor.MapEditor;
import mapEditor.PanelForObject;
import mapEditor.PolygonObject;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ConverterMapToXml {

	private DocumentBuilderFactory documentBuilderFactory;
	private DocumentBuilder documentBuilder;
	private Document doc;
	private Element rootElement;
	
	public ConverterMapToXml() throws ParserConfigurationException {

		documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilder = documentBuilderFactory.newDocumentBuilder();
		
		//root element
		doc = documentBuilder.newDocument();
		rootElement = doc.createElement("world");
		doc.appendChild(rootElement);
	}
	
	public void convertToXml(MapEditor mapEditor) throws TransformerException
	{
		//type element
		Element typeWorld  = doc.createElement("type");
		typeWorld.appendChild(doc.createTextNode(((PanelForObject) mapEditor.getPanelForObject()).getTypeWorld()));
		rootElement.appendChild(typeWorld);
	
		//dimension elements
	    Element dimension = doc.createElement("dimension");
	    dimension.setAttribute("height", ((PanelForObject) mapEditor.getPanelForObject()).getHeightToInsert());
	    dimension.setAttribute("width", ((PanelForObject) mapEditor.getPanelForObject()).getWidthToInsert());
	    rootElement.appendChild(dimension);
	
	    //objectInMap elements
	    for (PolygonObject object : mapEditor.getObjectInMap()) {
	    	if (object.getNameObject().equals("linearGround"))
	    	{
		    	Element elementGround = doc.createElement("linearGround");
		    	rootElement.appendChild(elementGround);
		    	
		    	Element position = doc.createElement("position");
//		    	position.setAttribute("posX", Double.toString(object.getUpperLeftPosition().getX()));
//		    	position.setAttribute("posY", Double.toString(object.getUpperLeftPosition().getY()));
		    	elementGround.appendChild(position);
		    	
		    	Element size = doc.createElement("size");
		    	size.setAttribute("height", Double.toString(object.getHeight()));
			    size.setAttribute("width", Double.toString(object.getWidth()));
			    elementGround.appendChild(size);
	    	}
	    	else if (object.getNameObject().equals("inclinedGround"))
	    	{
		    	Element elementGround = doc.createElement("inclinedGround");
		    	rootElement.appendChild(elementGround);
		    	
		    	Element position = doc.createElement("position");
//		    	position.setAttribute("posX", Double.toString(object.getUpperLeftPosition().getX()));
//		    	position.setAttribute("posY", Double.toString(object.getUpperLeftPosition().getY()));
		    	elementGround.appendChild(position);
		    	
		    	Element size = doc.createElement("size");
		    	size.setAttribute("height", Double.toString(object.getHeight()));
			    size.setAttribute("width", Double.toString(object.getWidth()));
			    elementGround.appendChild(size);
			    
			    Element angleRotation = doc.createElement("angleRotation");
			    angleRotation.setAttribute("degree", object.getAngleRotation());
			    elementGround.appendChild(angleRotation);
	    	}
			
		}
	
	    //write the content into xml file
	    TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    Transformer transformer = transformerFactory.newTransformer();
	    DOMSource source = new DOMSource(doc);
		StreamResult result =  new StreamResult(new File("worldXML/mapUser.xml"));
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount","4");
		transformer.transform(source, result);
	}
}
