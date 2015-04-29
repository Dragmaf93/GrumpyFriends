package world;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jbox2d.dynamics.World;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.sun.org.apache.xerces.internal.dom.ChildNode;





public class WorldDirector {
	
	 private WorldBuilder builder;
	
	 
	 public WorldDirector(WorldBuilder builder){
		 this.builder = builder; 
	 }
	 
	 public void createWorld(String pathXMLFile){
		 
		 try {
			Document document = loadDocument(pathXMLFile);
			Element documentElement = document.getDocumentElement();
			
			if(documentElement.hasChildNodes()){
				NodeList childNodes = documentElement.getChildNodes();
				for(int i = 0; i < childNodes.getLength();i++){
					Node item = childNodes.item(i);
					switch (item.getNodeName()) {
					case "type":
					
						builder.initializes(item.getTextContent());
						break;
					case "dimension":
						NamedNodeMap attributes = item.getAttributes();
						int height = Integer.parseInt(attributes.getNamedItem("height").getNodeValue());
						int width =  Integer.parseInt(attributes.getNamedItem("width").getNodeValue());
						builder.setWorldDimension(height, width);
						break;
					case "wall":
						NodeList child = item.getChildNodes();
						NamedNodeMap fromAtt = child.item(1).getAttributes();
						NamedNodeMap toAtt = child.item(3).getAttributes();
						int toX = Integer.parseInt(toAtt.getNamedItem("posX").getNodeValue());
						int toY = Integer.parseInt(toAtt.getNamedItem("posY").getNodeValue());
						int fromX = Integer.parseInt(fromAtt.getNamedItem("posX").getNodeValue());  
						int fromY = Integer.parseInt(fromAtt.getNamedItem("posY").getNodeValue());
						builder.addWalls(fromX,fromY,toX,toY);
						break;
					default:
						break;
					}
				}
			}
			
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		 
		 
	 }
	 
	 private static Document loadDocument(String pathFile) throws SAXException, IOException, ParserConfigurationException{
		 
		 DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		 DocumentBuilder builder = factory.newDocumentBuilder();
		 Document document = builder.parse(pathFile);
		 return document;

	 }
	 public static void main(String[] args) {
		WorldBuilder builder = new WorldBuilder();
		WorldDirector worldDirector = new WorldDirector(builder);
		worldDirector.createWorld("worldXML/world.xml");
		AbstractWorld w = (AbstractWorld) builder.getWorld();
		w.print();
	}
}
