import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;


import details.Edge;
import details.Point;

public class Terrain {
	private String type;
	private double leaning;
	private double width;
	private double height;
	private double price;
	//arraylist of edges
	private static List<Edge> edges = new ArrayList<Edge>();
	
	//number that says if a terrain is valid or not
	public int validationNumber;
	
	public Terrain(String typeT, double leaningT, double widthT, double heightT, double priceT, List<Edge> edgesT) {
		
		validationNumber = 0;
		
		if( (typeT != "") || (typeT != null)) {
			if(typeT.contentEquals("FERTILE")) {
				type = typeT;
			}
			else if (typeT.contains("NOT FERTILE")) {
				type = typeT;
			}
			else {
				System.out.println(typeT);
				System.out.println("Type not well specified. Try \"FERTILE\" or \"NOT FERTILE\".");
				return;
			}
		}
		else {
			System.out.println("Type not well specified. Try \"FERTILE\" or \"NOT FERTILE\".");
			return;
		}
		
		if(leaningT >= 0) {
			leaning = leaningT;
		}
		else {
			System.out.println(leaningT);
			System.out.println("Leaning has to be 0 or higher.");
			return;
		}
		
		if(widthT > 0) {
			width = widthT;
		}
		else {
			System.out.println("Width has to be higher than 0.");
			return;
		}
		
		if(heightT > 0) {
			height = heightT;
		}
		else{
			System.out.println("Height has to be higher than 0.");
			return;
		}
		
		if(priceT > 0){
			price = priceT;
		}
		else{
			System.out.println("Price has to higher than 0.");
			return;
		}
		
		if(edgesT.size() > 2) {
			edges.equals(edgesT);
		}
		else{
			System.out.println(edgesT.size());
			System.out.println("The number of edges of the terrain has to be at least 3.");
			return;
		}
		
		validationNumber = 1;
		
	}

	/* Gets */
	
	public String getType() {
		return type;
	}
	
	public double getLeaning() {
		return leaning;
	}
	
	public double getWidth() {
		return width;
	}
	
	public double getHeight() {
		return height;
	}
	
	public double getPrice() {
		return price;
	}
	
	public List<Edge> getEdges() {
		return edges;
	}
	
	/* Sets */
	
	public void setType(String typeS) {
		type = typeS;
	}
	
	public void setLeaning(double leaningS) {
		leaning = leaningS;
	}
	
	public void getWidth(double widthS) {
		width = widthS;
	}
	
	public void setHeight(double heightS) {
		height = heightS;
	}
	
	public void setPrice(double priceS) {
		price = priceS;
	}
	
	public void setEdges(List<Edge> edgesS[]) {
		edges.equals(edgesS);
	}

	/* Print Terrain Information */
	public void print() {
		if(validationNumber == 1) {
			System.out.println("Type: " + this.getType());
			System.out.println("Leaning: " + this.getLeaning());
			System.out.println("Width: " + this.getWidth() + " " + "Height: " + this.getHeight());
			System.out.println("Area: " + this.getWidth() * this.getHeight());
			System.out.println("Price: " + this.getPrice());
			System.out.println("Price per sq. meter: " + this.getPrice()/(this.getWidth() + this.getHeight()));
			for(int i = 0; i < this.edges.size(); i++) {
				System.out.println("Edge " + i);
				System.out.println("P1: " + this.edges.get(i).getP1().getX1() + "," + this.edges.get(i).getP1().getY1());
				System.out.println("P2: " + this.edges.get(i).getP2().getX1() + "," + this.edges.get(i).getP2().getY1());
			}
			System.out.print("\n");
		}
		else {
			System.out.println("\n");
		}
	}
	
	public static void main(String [ ] args) throws SAXException, IOException, ParserConfigurationException
	{
		/*
		 * Reads data from the terrains.xml file and constructs each Terrain object
		 * specified in there.
		 */
	
		File file = new File("config/terrains.xml");
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
			edges.clear();
			counterTerrains++;
		}
		
		System.out.println("Number of terrains: " + counterTerrains);
		
	}
	
}