package utils;

import java.io.File;
import java.math.BigDecimal;
import java.util.Optional;

import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Shape;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import mapEditor.Curve;
import mapEditor.MapEditor;
import mapEditor.PanelForObject;
import mapEditor.PolygonObject;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

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
	
	public void convertToXml(MapEditor mapEditor, String nameFile) throws TransformerException
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
		    	
		    	insertPoints(object, elementGround);
	    	}
	    	else if (object.getNameObject().equals("inclinedGround"))
	    	{
		    	Element elementGround = doc.createElement("inclinedGround");
		    	rootElement.appendChild(elementGround);

		    	insertPoints(object, elementGround);
	    	}
	    	else if (object.getNameObject().equals("genericGround"))
	    	{
		    	Element elementGround = doc.createElement("genericGround");
		    	rootElement.appendChild(elementGround);

		    	insertPoints(object, elementGround);
	    	}
		}
	
	    for (Curve object : mapEditor.getCurveInMap()) {
	    	Element elementGround = doc.createElement("curveGround");
	    	rootElement.appendChild(elementGround);

	    	insertPoints(object, elementGround);
	    }
	    
	    //write the content into xml file
	    TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    Transformer transformer = transformerFactory.newTransformer();
	    DOMSource source = new DOMSource(doc);
	    File dir = new File("worldXML/");
	    boolean exist = false;
	    String[] children = dir.list();
	    if (children != null)
	    {
	        for (int i=0; i < children.length; i++) {
	            String filename = children[i];
	            if (filename.equals(nameFile+".xml"))
	            {
//	            	Alert alert = new Alert(AlertType.WARNING);
//	        		alert.setTitle("Information Submit");
//	        		alert.setHeaderText(null);
//	        		alert.setContentText("Existing name");
//	        		alert.showAndWait();
//	        		mapEditor.getPanelForObject().setAlert();
	            	Alert alert = new Alert(AlertType.CONFIRMATION);
	            	alert.setTitle("Confirmation Dialog");
	            	alert.setHeaderText("Existing name");
	            	alert.setContentText("Do you want save?");

	            	Optional<ButtonType> result = alert.showAndWait();
	            	if (result.get() == ButtonType.OK){
	            	    exist = false;
	            	} else {
	            		exist = true;
	            		mapEditor.getPanelForObject().setAlert();
	            	}
	            }
	        }
	    }
	    if (!exist)
	    {
			StreamResult result =  new StreamResult(new File("worldXML/"+nameFile+".xml"));
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount","4");
			transformer.transform(source, result);
			Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("Information Submit");
    		alert.setHeaderText(null);
    		alert.setContentText("Submit saved with success");
    		alert.showAndWait();
	    }
	}
	
	private void insertPoints(Shape object, Node elementGround) {
		if (object instanceof PolygonObject)
		{
			for (Point2D point : ((PolygonObject) object).getPointsVertex()) {
	    		Element elementPoint = doc.createElement("point");
		    	elementPoint.setAttribute("x", Double.toString(new BigDecimal(Utils.xFromJavaFxToJbox2d(point.getX())).setScale(2 , BigDecimal.ROUND_UP).doubleValue()));
			    elementPoint.setAttribute("y", Double.toString(new BigDecimal(Utils.yFromJavaFxToJbox2d(point.getY())).setScale(2 , BigDecimal.ROUND_UP).doubleValue()));
			    elementGround.appendChild(elementPoint);
			}
		}
		else
		{
    		Element elementPointStart = doc.createElement("start");
    		elementPointStart.setAttribute("x", Double.toString(new BigDecimal(Utils.xFromJavaFxToJbox2d(((Curve) object).getRealPoints().get(0).getX())).setScale(2 , BigDecimal.ROUND_UP).doubleValue()));
    		elementPointStart.setAttribute("y", Double.toString(new BigDecimal(Utils.yFromJavaFxToJbox2d(((Curve) object).getRealPoints().get(0).getY())).setScale(2 , BigDecimal.ROUND_UP).doubleValue()));
		    elementGround.appendChild(elementPointStart);
		    
		    Element elementPointEnd = doc.createElement("end");
		    elementPointEnd.setAttribute("x", Double.toString(new BigDecimal(Utils.xFromJavaFxToJbox2d(((Curve) object).getRealPoints().get(1).getX())).setScale(2 , BigDecimal.ROUND_UP).doubleValue()));
		    elementPointEnd.setAttribute("y", Double.toString(new BigDecimal(Utils.yFromJavaFxToJbox2d(((Curve) object).getRealPoints().get(1).getY())).setScale(2 , BigDecimal.ROUND_UP).doubleValue()));
		    elementGround.appendChild(elementPointEnd);
		    
		    Element elementPointControl = doc.createElement("control");
		    elementPointControl.setAttribute("x", Double.toString(new BigDecimal(Utils.xFromJavaFxToJbox2d(((Curve) object).getRealPoints().get(2).getX())).setScale(2 , BigDecimal.ROUND_UP).doubleValue()));
		    elementPointControl.setAttribute("y", Double.toString(new BigDecimal(Utils.yFromJavaFxToJbox2d(((Curve) object).getRealPoints().get(2).getY())).setScale(2 , BigDecimal.ROUND_UP).doubleValue()));
		    elementGround.appendChild(elementPointControl);
		}
	}
	
}
