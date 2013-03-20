import details.Point;

public class Terrain {
	private String type;
	private double leaning;
	private double width;
	private double height;
	private double price;
	//array of edges
	private details.Edge[] edges;
	
	public Terrain(String typeT, double leaningT, double widthT, double heightT, double priceT, details.Edge edgesT[]) {
		
		if( (typeT != "") || (typeT != null)) {
			if(typeT == "FERTILE") {
				type = typeT;
			}
			else if (typeT == "NOT FERTILE") {
				type = typeT;
			}
			else {
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
		else return;
		
		if(widthT > 0) {
			width = widthT;
		}
		else return;
		
		if(heightT > 0) {
			height = heightT;
		}
		else return;
		
		if(priceT > 0){
			price = priceT;
		}
		else return;
		
		if(edgesT.length > 0) {
			edges = edgesT;
		}
		else return;
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
	
	public details.Edge[] getEdges() {
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
	
	public void setEdges(details.Edge edgesS[]) {
		edges = edgesS;
	}

	/* Print Terrain Information */
	public void print() {
		System.out.println("Type: " + this.getType());
		System.out.println("Leaning: " + this.getLeaning());
		System.out.println("Width: " + this.getWidth() + " " + "Height: " + this.getHeight());
		System.out.println("Area: " + this.getWidth() * this.getHeight());
		System.out.println("Price: " + this.getPrice());
		System.out.println("Price per sq. meter: " + this.getPrice()/(this.getWidth() + this.getHeight()));
		for(int i = 0; i < this.edges.length; i++) {
			System.out.println("Edge " + i);
			System.out.println("P1: " + this.edges[i].getP1().getX1() + "," + this.edges[i].getP1().getY1());
			System.out.println("P2: " + this.edges[i].getP2().getX1() + "," + this.edges[i].getP2().getY1());
		}
	}
	
	public static void main(String [ ] args)
	{
		  details.Point PointOne = new Point(1.1, 2.5);
		  details.Point PointTwo = new Point(2.0, 2.5);
		  details.Point PointThree = new Point(1.1, 4.3);
		  details.Point PointFour = new Point(2.0, 4.3);
		  
		  details.Edge edges1 = new details.Edge(PointOne, PointTwo);
		  details.Edge edges2 = new details.Edge(PointOne, PointThree);
		  details.Edge edges3 = new details.Edge(PointThree, PointFour);
		  details.Edge edges4 = new details.Edge(PointFour, PointTwo);
		  
		  details.Edge[] edges = {edges1, edges2, edges3, edges4};
	      
		  Terrain t = new Terrain(null,1.5,140.1,38.1,12800.1,edges);
	      t.print();
	}
	
}