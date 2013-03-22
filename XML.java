import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.sun.org.apache.xerces.internal.dom.DocumentImpl;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

import details.Edge;
import details.Point;


public class XML {
	private File file;
	
	public XML() {
		file = new File("src/config/terrains.xml");
	}
	
	public File getFile() {
		return file;
	}
	
	public void setFile(File file2) {
		file = file2;
	}
	
	public void writeToXML(String type, double leaning, double width, double height, double price, List<Edge> edges) throws IOException {
		Element tt,t,l,w,h,p,es,e,x1,y1,x2,y2 = null;
		Node n = null;
		Document xmldoc= new DocumentImpl();
		
		Element root = xmldoc.createElement("terrains");
		
		//creates element "terrain" and appends as son of "terrains"
		tt = xmldoc.createElement("terrain");
		root.appendChild(tt);
		
		//creates element "type" and appends as son of "terrain"
		t = xmldoc.createElement("type");
		t.setTextContent(type);
		tt.appendChild(t);
		System.out.println("File to write: " + this.file);
		System.out.println("Type: " + t.getTextContent());
		
		//creates element "leaning" and appends as son of "terrain"
		l = xmldoc.createElement("leaning");
		l.setTextContent(String.valueOf(leaning));
		tt.appendChild(l);
		System.out.println("Leaning: " + l.getTextContent());
		
		//creates element "width" and appends as son of "terrain"
		w = xmldoc.createElement("width");
		w.setTextContent(String.valueOf(width));
		tt.appendChild(w);
		System.out.println("Width: " + w.getTextContent());
		
		//creates element "height" and appends as son of "terrain"
		h = xmldoc.createElement("height");
		h.setTextContent(String.valueOf(height));
		tt.appendChild(h);
		System.out.println("Height: " + h.getTextContent());
		
		//creates element "price" and appends as son of "terrain"
		p = xmldoc.createElement("price");
		p.setTextContent(String.valueOf(price));
		tt.appendChild(p);
		System.out.println("Price: " + p.getTextContent());
		
		//creates element "edges" and appends as son of "terrain"
		es = xmldoc.createElement("edges");
		tt.appendChild(es);
		
		for(int i = 0; i < edges.size(); i++) {
			e = xmldoc.createElement("edge");
			es.appendChild(e);
			
			x1 = xmldoc.createElement("x1");
			x1.setTextContent(String.valueOf(edges.get(i).getP1().getX1()));
			e.appendChild(x1);
			System.out.println("X1: " + x1.getTextContent());
			
			y1 = xmldoc.createElement("y1");
			y1.setTextContent(String.valueOf(edges.get(i).getP1().getY1()));
			e.appendChild(y1);
			System.out.println("Y1: " + y1.getTextContent());
			
			x2 = xmldoc.createElement("x2");
			x2.setTextContent(String.valueOf(edges.get(i).getP2().getX1()));
			e.appendChild(x2);
			System.out.println("X2: " + x2.getTextContent());
			
			y2 = xmldoc.createElement("y2");
			y2.setTextContent(String.valueOf(edges.get(i).getP2().getY1()));
			e.appendChild(y2);
			System.out.println("Y2: " + y2.getTextContent());
		}
		
		xmldoc.appendChild(root);
		FileOutputStream fos = new FileOutputStream(file);
		
		// XERCES 1 or 2 additional classes.
		OutputFormat of = new OutputFormat("XML","ISO-8859-1",true);
		of.setIndent(1);
		of.setIndenting(true);
		of.setDoctype(null,"src/config/terrains.dtd");
		XMLSerializer serializer = new XMLSerializer(fos,of);
		
		// As a DOM Serializer
		serializer.asDOMSerializer();
		serializer.serialize( xmldoc.getDocumentElement() );
		fos.close();
	}
	
	
	/*
	 * Reads data from the terrains.xml file and constructs each Terrain object
	 * specified in there.
	 */
	
	public void readFile(List<Edge> edges) throws ParserConfigurationException, SAXException, IOException {
		
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(file);
		int counterTerrains = 0;
		
		for(int a = 0; a < document.getElementsByTagName("terrain").getLength(); a++) {
	
			String type = document.getElementsByTagName("type").item(a).getTextContent();
			
			String leaningx = document.getElementsByTagName("leaning").item(a).getTextContent();
			double leaning = Double.parseDouble(leaningx);
			
			String widthx = document.getElementsByTagName("width").item(a).getTextContent();
			double width = Double.parseDouble(widthx);
			
			String heightx = document.getElementsByTagName("height").item(a).getTextContent();
			double height = Double.parseDouble(heightx);
			
			String pricex = document.getElementsByTagName("price").item(a).getTextContent();
			double price = Double.parseDouble(pricex);
			
			for(int i = 0; i < 4; i++) {
				
				String x1x = document.getElementsByTagName("x1").item((counterTerrains*4)+i).getTextContent();
				double x1 = Double.parseDouble(x1x);
				
				String y1x = document.getElementsByTagName("y1").item((counterTerrains*4)+i).getTextContent();
				double y1 = Double.parseDouble(y1x);
				
				details.Point P1 = new Point(x1,y1);
				
				String x2x = document.getElementsByTagName("x2").item((counterTerrains*4)+i).getTextContent();
				double x2 = Double.parseDouble(x2x);
				
				String y2x = document.getElementsByTagName("y2").item((counterTerrains*4)+i).getTextContent();
				double y2 = Double.parseDouble(y2x);
				
				details.Point P2 = new Point(x2, y2);
				
				details.Edge edge = new details.Edge(P1,P2);
				edges.add(edge);
			}
			
			Terrain t = new Terrain(type, leaning, width, height, price, edges);
			t.print();
			t.getEdges().clear();
			counterTerrains++;
		}
		
		System.out.println("Number of terrains: " + counterTerrains);
	}

}
