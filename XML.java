import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import details.Point;


public class XML {
	
	private File file;
	
	public XML() {
		file = new File("src/config/terrains.xml");
	}
	
	public File getFile() {
		return file;
	}
	
	public void setFile(File file1) {
		file = file1;
	}
	
	public void readFile(List<details.Edge> edges) throws ParserConfigurationException, SAXException, IOException {
		      
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
