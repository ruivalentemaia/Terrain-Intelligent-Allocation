import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;


import org.xml.sax.SAXException;

import details.Edge;

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
			for(int i = 0; i < edges.size(); i++) {
				System.out.println("Edge " + i);
				System.out.println("P1: " + edges.get(i).getP1().getX1() + "," + edges.get(i).getP1().getY1());
				System.out.println("P2: " + edges.get(i).getP2().getX1() + "," + edges.get(i).getP2().getY1());
			}
			System.out.print("\n");
		}
		else {
			System.out.println("\n");
		}
	}
	
	public static void main(String [ ] args) throws SAXException, IOException, ParserConfigurationException
	{
		XML xml = new XML();
		xml.readFile(edges);
		
		//Example data for testing
		/*List<Edge> edges1 = new ArrayList<Edge>();
		Point p1 = new Point(1.0,1.1);
		Point p2 = new Point(1.2,1.3);
		Point p3 = new Point(1.4,1.5);
		Point p4 = new Point(1.6,1.7);
		Edge e1 = new Edge(p1,p2);
		Edge e2 = new Edge(p2,p3);
		Edge e3 = new Edge(p3, p4);
		edges1.add(e1);
		edges1.add(e2);
		edges1.add(e3);*/
	}
	
}