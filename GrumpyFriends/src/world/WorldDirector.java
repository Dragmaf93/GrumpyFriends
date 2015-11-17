package world;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import physic.PhysicalObjectManager;
import utils.Point;
import utils.Vector;

public class WorldDirector {

	private WorldBuilder builder;

	public WorldDirector(WorldBuilder builder) {
		this.builder = builder;
		PhysicalObjectManager.getInstance();
	}

	public void createWorld(String pathXMLFile) {

		try {
			Document document = loadDocument(pathXMLFile);
			Element documentElement = document.getDocumentElement();

			if (documentElement.hasChildNodes()) {
				NodeList childNodes = documentElement.getChildNodes();
				for (int i = 0; i < childNodes.getLength(); i++) {
					Node item = childNodes.item(i);
					switch (item.getNodeName()) {
					case "type":

						builder.initializes(item.getTextContent());
						break;
					case "dimension": {
						NamedNodeMap attributes = item.getAttributes();
						float height = Float.parseFloat(attributes.getNamedItem("height").getNodeValue());
						float width = Float.parseFloat(attributes.getNamedItem("width").getNodeValue());
						builder.setWorldDimension(width, height);
						break;
					}
					case "linearGround": {
						double x = 0, y = 0;
						NodeList child = item.getChildNodes();
						List<Point> points = new ArrayList();
						for (int j = 0; j < child.getLength(); j++) {
							if (child.item(j).getNodeName().equals("point")) {
								NamedNodeMap positon = child.item(j).getAttributes();
								x = Double.parseDouble(positon.getNamedItem("x").getNodeValue());
								y = Double.parseDouble(positon.getNamedItem("y").getNodeValue());
								Point point = new Point(x, y);
								System.out.println(point);
								points.add(point);
							}

						}
						builder.addLinearGround(points);
						break;
					}
					case "inclinedGround": {
						double x = 0, y = 0;
						NodeList child = item.getChildNodes();
						List<Point> points = new ArrayList();
						for (int j = 0; j < child.getLength(); j++) {
							if (child.item(j).getNodeName().equals("point")) {
								NamedNodeMap positon = child.item(j).getAttributes();
								x = Double.parseDouble(positon.getNamedItem("x").getNodeValue());
								y = Double.parseDouble(positon.getNamedItem("y").getNodeValue());
								Point point = new Point(x, y);
								System.out.println(point);
								points.add(point);
							}

						}
						builder.addInclinedGround(points);
						break;
					}
					case "genericGround": {
						double x = 0, y = 0;
						NodeList child = item.getChildNodes();
						List<Point> points = new ArrayList();
						for (int j = 0; j < child.getLength(); j++) {
							if (child.item(j).getNodeName().equals("point")) {
								NamedNodeMap positon = child.item(j).getAttributes();
								x = (float) Double.parseDouble(positon.getNamedItem("x").getNodeValue());
								y = (float) Double.parseDouble(positon.getNamedItem("y").getNodeValue());
								Point point = new Point(x, y);
								System.out.println(point);
								points.add(point);
							}

						}
						builder.addGenericGround(points);
						break;
					}
					
					case "character": {
						float x = 0, y = 0;
						String name = "";
						NodeList child = item.getChildNodes();
						for (int j = 0; j < child.getLength(); j++)
							if (child.item(j).getNodeName().equals("position")) {
								NamedNodeMap positon = child.item(j).getAttributes();
								x = Integer.parseInt(positon.getNamedItem("x").getNodeValue());
								y = Integer.parseInt(positon.getNamedItem("y").getNodeValue());

							} else if (child.item(j).getTextContent().equals("name")) {
								name = child.item(j).getTextContent();
							}
						builder.addCharacter(name, x, y);
						break;
					}
					default:
						break;
					}
				}
			}
			builder.lastSettings();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

	}

	private static Document loadDocument(String pathFile)
			throws SAXException, IOException, ParserConfigurationException {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(pathFile);
		return document;

	}
}
