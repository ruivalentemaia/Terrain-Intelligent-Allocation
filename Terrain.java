import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;


import org.xml.sax.SAXException;

import details.Constraint;
import details.Edge;
import details.Point;
import details.Rule;

public class Terrain {
	private String type;
	private double leaning;
	private double width;
	private double height;
	private double price;
	//arraylist of edges
	private static List<Edge> edges = new ArrayList<Edge>();
	private boolean penalty;
	
	//number that says if a terrain is valid or not
	public int validationNumber;
	
	public Terrain(String typeT, double leaningT, double widthT, double heightT, double priceT, List<Edge> edgesT) {
		
		penalty = false;
		validationNumber = 0;
		
		if( (typeT != "") || (typeT != null)) {
			if(typeT.contentEquals("FERTILE")) {
				type = typeT;
			}
			else if (typeT.contains("NOT FERTILE")) {
				type = typeT;
			}
			else {
				System.out.println("Type inserted: " + typeT);
				System.out.println("Error: Type not well specified. Try \"FERTILE\" or \"NOT FERTILE\".");
				return;
			}
		}
		else {
			System.out.println("Error: Type not well specified. Try \"FERTILE\" or \"NOT FERTILE\".");
			return;
		}
		
		if(leaningT >= 0) {
			leaning = leaningT;
		}
		else {
			System.out.println("Leaning inserted: " + leaningT);
			System.out.println("Error: Leaning has to be 0 or higher.");
			return;
		}
		
		if(widthT > 0) {
			width = widthT;
		}
		else {
			System.out.println("Error: Width has to be higher than 0.");
			return;
		}
		
		if(heightT > 0) {
			height = heightT;
		}
		else{
			System.out.println("Error: Height has to be higher than 0.");
			return;
		}
		
		if(priceT > 0){
			price = priceT;
		}
		else{
			System.out.println("Error: Price has to higher than 0.");
			return;
		}
		
		if(edgesT.size() > 2) {
			edges = edgesT;
		}
		else{
			System.out.println("Size of the edges list: " + edgesT.size());
			System.out.println("Error: The number of edges of the terrain has to be at least 3.");
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
	
	public int getEdgesSize() {
		return edges.size();
	}
	
	public boolean getPenalty() {
		return penalty;
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
	
	public void setEdges(List<Edge> edgesS) {
		edges = edgesS;
	}
	
	public void setPenalty(boolean pen) {
		penalty = pen;
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
	
	public boolean applyUserSelectedConstraint(Constraint c) {
		Rule r;
		if(c.getConnector() == "MUST HAVE") {
			r = c.getRule();
			/*
			 * Rule for the "MORE THAN" (operator >) and comparison between each field of the terrain
			 * and the number specified in the rule.
			 */
			if(r.getMeasurement() == "MORE THAN") {

				if(r.getField() == "leaning") {
					if(this.getLeaning() > r.getNumber()) {
						return true;
					}
					else return false;
				}
				else if(r.getField() == "width") {
					if(this.getWidth() > r.getNumber()) {
						return true;
					}
					else return false;
				}
				else if(r.getField() == "height") {
					if(this.getHeight() > r.getNumber()) {
						return true;
					}
					else return false;
				}
				else if(r.getField() == "price") {
					if(this.getPrice() > r.getNumber()) {
						return true;
					}
					else return false;
				}
				else {
					System.out.println("Measurement: " + r.getMeasurement());
					System.out.println("Field: " + r.getField());
					System.out.println("Error: Invalid comparison.");
					return false;
				}
			}

			/*
			 * Same thing as previous comparison, but now with the operator >=
			 */
			else if(r.getMeasurement() == "MORE OR THE SAME AS") {
				if(r.getField() == "leaning") {
					if(this.getLeaning() >= r.getNumber()) {
						return true;
					}
					else return false;
				}
				else if(r.getField() == "width") {
					if(this.getWidth() >= r.getNumber()) {
						return true;
					}
					else return false;
				}
				else if(r.getField() == "height") {
					if(this.getHeight() >= r.getNumber()) {
						return true;
					}
					else return false;
				}
				else if(r.getField() == "price") {
					if(this.getPrice() >= r.getNumber()) {
						return true;
					}
					else return false;
				}
				else {
					System.out.println("Measurement: " + r.getMeasurement());
					System.out.println("Field: " + r.getField());
					System.out.println("Error: Invalid comparison.");
					return false;
				}
			}

			/*
			 * Same thing as previous comparison, but now with the operator <
			 */
			else if(r.getMeasurement() == "LESS THAN") {
				if(r.getField() == "leaning") {
					if(this.getLeaning() < r.getNumber()) {
						return true;
					}
					else return false;
				}
				else if(r.getField() == "width") {
					if(this.getWidth() < r.getNumber()) {
						return true;
					}
					else return false;
				}
				else if(r.getField() == "height") {
					if(this.getHeight() < r.getNumber()) {
						return true;
					}
					else return false;
				}
				else if(r.getField() == "price") {
					if(this.getPrice() < r.getNumber()) {
						return true;
					}
					else return false;
				}
				else {
					System.out.println("Measurement: " + r.getMeasurement());
					System.out.println("Field: " + r.getField());
					System.out.println("Error: Invalid comparison.");
					return false;
				}
			}

			/*
			 * Same thing as previous comparison, but now with the operator <=
			 */
			else if(r.getMeasurement() == "LESS OR THE SAME AS") {
				if(r.getField() == "leaning") {
					if(this.getLeaning() <= r.getNumber()) {
						return true;
					}
					else return false;
				}
				else if(r.getField() == "width") {
					if(this.getWidth() <= r.getNumber()) {
						return true;
					}
					else return false;
				}
				else if(r.getField() == "height") {
					if(this.getHeight() <= r.getNumber()) {
						return true;
					}
					else return false;
				}
				else if(r.getField() == "price") {
					if(this.getPrice() <= r.getNumber()) {
						return true;
					}
					else return false;
				}
				else {
					System.out.println("Measurement: " + r.getMeasurement());
					System.out.println("Field: " + r.getField());
					System.out.println("Error: Invalid comparison.");
					return false;
				}
			}

			/*
			 * Same thing as previous comparison, but now with the operator ==
			 */
			else if(r.getMeasurement() == "EXACTLY") {
				if(r.getField() == "leaning") {
					if(this.getLeaning() == r.getNumber()) {
						return true;
					}
					else return false;
				}
				else if(r.getField() == "width") {
					if(this.getWidth() == r.getNumber()) {
						return true;
					}
					else return false;
				}
				else if(r.getField() == "height") {
					if(this.getHeight() == r.getNumber()) {
						return true;
					}
					else return false;
				}
				else if(r.getField() == "price") {
					if(this.getPrice() == r.getNumber()) {
						return true;
					}
					else return false;
				}
				else if(r.getField() == "type") {
					if(this.getType() == "FERTILE") {
						r.setNumber(1);
						return true;
					}
					else if(this.getType() == "NOT FERTILE") {
						r.setNumber(0);
						return true;
					}
					else {
						System.out.println("Type: " + this.getType());
						System.out.println("Error: Invalid type.");
						return false;
					}
				}
				else {
					System.out.println("Measurement: " + r.getMeasurement());
					System.out.println("Field: " + r.getField());
					System.out.println("Error: Invalid comparison.");
					return false;
				}
			}

		}
		
		else if(c.getConnector() == "MUST NOT HAVE") {
			r = c.getRule();
			/*
			 * Rule for the "MORE THAN" (operator >) and negative comparison between each field of the terrain
			 * and the number specified in the rule.
			 */
			if(r.getMeasurement() == "MORE THAN") {

				if(r.getField() == "leaning") {
					if(!(this.getLeaning() > r.getNumber())) {
						return true;
					}
					else return false;
				}
				else if(r.getField() == "width") {
					if(!(this.getWidth() > r.getNumber())) {
						return true;
					}
					else return false;
				}
				else if(r.getField() == "height") {
					if(!(this.getHeight() > r.getNumber())) {
						return true;
					}
					else return false;
				}
				else if(r.getField() == "price") {
					if(!(this.getPrice() > r.getNumber())) {
						return true;
					}
					else return false;
				}
				else {
					System.out.println("Measurement: " + r.getMeasurement());
					System.out.println("Field: " + r.getField());
					System.out.println("Error: Invalid comparison.");
					return false;
				}
			}

			/*
			 * Same thing as previous comparison, but now with the operator >=
			 */
			else if(r.getMeasurement() == "MORE OR THE SAME AS") {
				if(r.getField() == "leaning") {
					if(!(this.getLeaning() >= r.getNumber())) {
						return true;
					}
					else return false;
				}
				else if(r.getField() == "width") {
					if(!(this.getWidth() >= r.getNumber())) {
						return true;
					}
					else return false;
				}
				else if(r.getField() == "height") {
					if(!(this.getHeight() >= r.getNumber())) {
						return true;
					}
					else return false;
				}
				else if(r.getField() == "price") {
					if(!(this.getPrice() >= r.getNumber())) {
						return true;
					}
					else return false;
				}
				else {
					System.out.println("Measurement: " + r.getMeasurement());
					System.out.println("Field: " + r.getField());
					System.out.println("Error: Invalid comparison.");
					return false;
				}
			}

			/*
			 * Same thing as previous comparison, but now with the operator <
			 */
			else if(r.getMeasurement() == "LESS THAN") {
				if(r.getField() == "leaning") {
					if(!(this.getLeaning() < r.getNumber())) {
						return true;
					}
					else return false;
				}
				else if(r.getField() == "width") {
					if(!(this.getWidth() < r.getNumber())) {
						return true;
					}
					else return false;
				}
				else if(r.getField() == "height") {
					if(!(this.getHeight() < r.getNumber())) {
						return true;
					}
					else return false;
				}
				else if(r.getField() == "price") {
					if(!(this.getPrice() < r.getNumber())) {
						return true;
					}
					else return false;
				}
				else {
					System.out.println("Measurement: " + r.getMeasurement());
					System.out.println("Field: " + r.getField());
					System.out.println("Error: Invalid comparison.");
					return false;
				}
			}

			/*
			 * Same thing as previous comparison, but now with the operator <=
			 */
			else if(r.getMeasurement() == "LESS OR THE SAME AS") {
				if(r.getField() == "leaning") {
					if(!(this.getLeaning() <= r.getNumber())) {
						return true;
					}
					else return false;
				}
				else if(r.getField() == "width") {
					if(!(this.getWidth() <= r.getNumber())) {
						return true;
					}
					else return false;
				}
				else if(r.getField() == "height") {
					if(!(this.getHeight() <= r.getNumber())) {
						return true;
					}
					else return false;
				}
				else if(r.getField() == "price") {
					if(!(this.getPrice() <= r.getNumber())) {
						return true;
					}
					else return false;
				}
				else {
					System.out.println("Measurement: " + r.getMeasurement());
					System.out.println("Field: " + r.getField());
					System.out.println("Error: Invalid comparison.");
					return false;
				}
			}

			/*
			 * Same thing as previous comparison, but now with the operator ==
			 */
			else if(r.getMeasurement() == "EXACTLY") {
				if(r.getField() == "leaning") {
					if(!(this.getLeaning() == r.getNumber())) {
						return true;
					}
					else return false;
				}
				else if(r.getField() == "width") {
					if(!(this.getWidth() == r.getNumber())) {
						return true;
					}
					else return false;
				}
				else if(r.getField() == "height") {
					if(!(this.getHeight() == r.getNumber())) {
						return true;
					}
					else return false;
				}
				else if(r.getField() == "price") {
					if(!(this.getPrice() == r.getNumber())) {
						return true;
					}
					else return false;
				}
				else if(r.getField() == "type") {
					if(!(this.getType() == "FERTILE")) {
						r.setNumber(0);
						return true;
					}
					else if(!(this.getType() == "NOT FERTILE")) {
						r.setNumber(1);
						return true;
					}
					else {
						System.out.println("Type: " + this.getType());
						System.out.println("Error: Invalid type.");
						return false;
					}
				}
				else {
					System.out.println("Measurement: " + r.getMeasurement());
					System.out.println("Field: " + r.getField());
					System.out.println("Error: Invalid comparison.");
					return false;
				}
			}
		}
		
		else if(c.getConnector() == "CAN HAVE") {
			r = c.getRule();
			/*
			 * Rule for the "MORE THAN" (operator >) and comparison between each field of the terrain
			 * and the number specified in the rule.
			 */
			if(r.getMeasurement() == "MORE THAN") {

				if(r.getField() == "leaning") {
					if(this.getLeaning() > r.getNumber()) {
						this.penalty = false;
					}
					else this.penalty = true;
				}
				else if(r.getField() == "width") {
					if(this.getWidth() > r.getNumber()) {
						this.penalty = false;
					}
					else this.penalty = true;
				}
				else if(r.getField() == "height") {
					if(this.getHeight() > r.getNumber()) {
						this.penalty = false;
					}
					else this.penalty = true;
				}
				else if(r.getField() == "price") {
					if(this.getPrice() > r.getNumber()) {
						this.penalty = false;
					}
					else this.penalty = true;
				}
				else {
					System.out.println("Measurement: " + r.getMeasurement());
					System.out.println("Field: " + r.getField());
					System.out.println("Error: Invalid comparison.");
					this.penalty = true;
				}
			}

			/*
			 * Same thing as previous comparison, but now with the operator >=
			 */
			else if(r.getMeasurement() == "MORE OR THE SAME AS") {
				if(r.getField() == "leaning") {
					if(this.getLeaning() >= r.getNumber()) {
						this.penalty = false;
					}
					else this.penalty = true;
				}
				else if(r.getField() == "width") {
					if(this.getWidth() >= r.getNumber()) {
						this.penalty = false;
					}
					else this.penalty = true;
				}
				else if(r.getField() == "height") {
					if(this.getHeight() >= r.getNumber()) {
						this.penalty = false;
					}
					else this.penalty = true;
				}
				else if(r.getField() == "price") {
					if(this.getPrice() >= r.getNumber()) {
						this.penalty = false;
					}
					else this.penalty = true;
				}
				else {
					System.out.println("Measurement: " + r.getMeasurement());
					System.out.println("Field: " + r.getField());
					System.out.println("Error: Invalid comparison.");
					this.penalty = true;
				}
			}

			/*
			 * Same thing as previous comparison, but now with the operator <
			 */
			else if(r.getMeasurement() == "LESS THAN") {
				if(r.getField() == "leaning") {
					if(this.getLeaning() < r.getNumber()) {
						this.penalty = false;
					}
					else this.penalty = true;
				}
				else if(r.getField() == "width") {
					if(this.getWidth() < r.getNumber()) {
						this.penalty = false;
					}
					else this.penalty = true;
				}
				else if(r.getField() == "height") {
					if(this.getHeight() < r.getNumber()) {
						this.penalty = false;
					}
					else this.penalty = true;
				}
				else if(r.getField() == "price") {
					if(this.getPrice() < r.getNumber()) {
						this.penalty = false;
					}
					else this.penalty = true;
				}
				else {
					System.out.println("Measurement: " + r.getMeasurement());
					System.out.println("Field: " + r.getField());
					System.out.println("Error: Invalid comparison.");
					this.penalty = true;
				}
			}

			/*
			 * Same thing as previous comparison, but now with the operator <=
			 */
			else if(r.getMeasurement() == "LESS OR THE SAME AS") {
				if(r.getField() == "leaning") {
					if(this.getLeaning() <= r.getNumber()) {
						this.penalty = false;
					}
					else this.penalty = true;
				}
				else if(r.getField() == "width") {
					if(this.getWidth() <= r.getNumber()) {
						this.penalty = false;
					}
					else this.penalty = true;
				}
				else if(r.getField() == "height") {
					if(this.getHeight() <= r.getNumber()) {
						this.penalty = false;
					}
					else this.penalty = true;
				}
				else if(r.getField() == "price") {
					if(this.getPrice() <= r.getNumber()) {
						this.penalty = false;
					}
					else this.penalty = true;
				}
				else {
					System.out.println("Measurement: " + r.getMeasurement());
					System.out.println("Field: " + r.getField());
					System.out.println("Error: Invalid comparison.");
					this.penalty = true;
				}
			}

			/*
			 * Same thing as previous comparison, but now with the operator ==
			 */
			else if(r.getMeasurement() == "EXACTLY") {
				if(r.getField() == "leaning") {
					if(this.getLeaning() == r.getNumber()) {
						this.penalty = false;
					}
					else this.penalty = true;
				}
				else if(r.getField() == "width") {
					if(this.getWidth() == r.getNumber()) {
						this.penalty = false;
					}
					else this.penalty = true;
				}
				else if(r.getField() == "height") {
					if(this.getHeight() == r.getNumber()) {
						this.penalty = false;
					}
					else this.penalty = true;
				}
				else if(r.getField() == "price") {
					if(this.getPrice() == r.getNumber()) {
						this.penalty = false;
					}
					else this.penalty = true;
				}
				else if(r.getField() == "type") {
					if(this.getType() == "FERTILE") {
						r.setNumber(1);
						this.penalty = false;
					}
					else if(this.getType() == "NOT FERTILE") {
						r.setNumber(0);
						this.penalty = false;
					}
					else {
						System.out.println("Type: " + this.getType());
						System.out.println("Error: Invalid type.");
						this.penalty = true;
					}
				}

				else {
					System.out.println("Measurement: " + r.getMeasurement());
					System.out.println("Field: " + r.getField());
					System.out.println("Error: Invalid comparison.");
					return false;
				}
			}
		}
		return false;
	}
}