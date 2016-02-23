package menu.worldMenu;

import gui.Popup;
import gui.drawer.PolygonGroundDrawer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;




import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import menu.MenuManager;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import utils.Point;
import utils.Utils;

public class PreviewLoaderMap {

	private final static String PATH_FILE = "worldXML/";

	private double widthPreview, heightPreview;

	public PreviewLoaderMap(double width, double height) {
		this.widthPreview = width;
		this.heightPreview = height;
		
	}

	public List<List<Point>> getPolygonPoints(String map) throws ParserConfigurationException, SAXException, IOException {
		List<List<Point>> polygons = null;
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(PATH_FILE+map+".xml");
			Element documentElement = document.getDocumentElement();
			polygons = new ArrayList<List<Point>>();
			double width = 0,height=0;
			int polygonCount = 0;

			if (documentElement.hasChildNodes()) {
				NodeList childNodes = documentElement.getChildNodes();
				for (int i = 0; i < childNodes.getLength(); i++) {
					Node item = childNodes.item(i);
					switch (item.getNodeName()) {
					case "dimension": {
						NamedNodeMap attributes = item.getAttributes();
						float h = Float.parseFloat(attributes
								.getNamedItem("height").getNodeValue());
						float w = Float.parseFloat(attributes.getNamedItem(
								"width").getNodeValue());
						Utils.setPhysicsSize(w, h);
						width = (float) Utils.widthFromJbox2dToJavaFx(w);
						height = (float) Utils.heightFromJbox2dToJavaFx(h);
						break;
					}
					case "linearGround":
					case "inclinedGround":
					case "genericGround": {

						double x = 0, y = 0;
						NodeList child = item.getChildNodes();
						polygons.add(new ArrayList());
						for (int j = 0; j < child.getLength(); j++) {
							if (child.item(j).getNodeName().equals("point")) {
								NamedNodeMap positon = child.item(j)
										.getAttributes();
								x = Utils.xFromJbox2dToJavaFx(Float
										.parseFloat(positon.getNamedItem("x")
												.getNodeValue()));
								y = Utils.yFromJbox2dToJavaFx(Float
										.parseFloat(positon.getNamedItem("y")
												.getNodeValue()));
								Point point = new Point(x*widthPreview/width, y*heightPreview/height);
								polygons.get(polygonCount).add(point);
							}
						}
						polygonCount++;
						break;
					}
					}
				}
			}


		return polygons;
	}
}
