import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import details.Constraint;
import details.Edge;
import details.Point;
import details.Rule;


public class TerrainIntelligentAllocation {
	
	public static void main(String [ ] args) throws SAXException, IOException, ParserConfigurationException, TransformerException {
		Map m = new Map();
		List<String> existentBuildings = new ArrayList<String>();
		File terrains = new File("src/config/terrains.xml");
		XML xmlTerrains = new XML(terrains);
		File constraints = new File("src/config/config.xml");
		XML xmlConstraints = new XML(constraints);
		
		//User inserts the size of the map
		int sizeOfTerrain,fertile;
		String fertileString = "";
		double leaning, width, height, price;
		Locale.setDefault(Locale.US);
		Scanner s = new Scanner(System.in);
		System.out.println("TERRAIN INTELLIGENT ALLOCATION\n");
		System.out.println("Which is the size of the map ? (number of terrains)");
		sizeOfTerrain = s.nextInt();
		m.setNumberTerrains(sizeOfTerrain);
		
		System.out.println("\n");
		System.out.println("Terrains Information.\n");
		System.out.println("Define the characteristics of each terrain.\n");
		for(int i = 1; i <= m.getNumberTerrains(); i++) {
			System.out.println("Terrain " + i + ": ");
			System.out.println("Type ? (0 for NOT FERTILE, 1 FOR FERTILE)");
			fertile = s.nextInt();
			System.out.println("Leaning: ( >= 0.0)");
			leaning = s.nextDouble();
			System.out.println("Width: (> 0)");
			width = s.nextDouble();
			System.out.println("Height: (> 0)");
			height = s.nextDouble();
			System.out.println("Price: (> 0)");
			price = s.nextDouble();
			
			if(fertile == 0) fertileString = "NOT FERTILE";
			else if(fertile == 1) fertileString = "FERTILE";
			else fertileString = "";
			Terrain t = new Terrain(fertileString, leaning, width, height, price);
			if(t.validationNumber == 1)
				m.addTerrain(t);
			
			//Prints inserted terrain info
			t.print();
		}
		
		//Adds inserted terrains to the terrains.xml file
		for(int b = 0; b < m.getListTerrains().size(); b++) {
			xmlTerrains.addTerrainToFile(m.getListTerrains().get(b));
		}
		
		//The user inputs how types of buildings he wants to allocate.
		int numberBuildings, constraint, rule, field, exists = 0;
		double value;
		String buildingName, measurementString="", constraintString="", fieldString="";
		
		System.out.println("BUILDING RESTRICTIONS");
		System.out.println("How many types of buildings do you want to allocate ? (>0)");
		numberBuildings = s.nextInt();
		
		if(!(numberBuildings > 0)) {
			System.out.println("Can't be equal or less than 0 buildings.");
			return;
		}
		
		for(int a = 1; a <= numberBuildings; a++) {
			System.out.println("Building " +a+ ": ");
			System.out.println("Name of the building: ");
			System.out.println();
			buildingName = s.next();
			
			/*
			 * validation if the same buildingName exists;
			 * if it does, add the constraint anyway to this building and decrement the a variable
			 * if it doesn't, add it to the existantBuildings arraylist and keep going.
			 */
			for(int e = 0; e < existentBuildings.size(); e++) {
				if(existentBuildings.get(e).equals(buildingName)) {
					System.out.println("The building name you inserted already exists. ");
					System.out.println("The system WILL NOT create a new building.");
					System.out.println("All the constraint information you add will be added to the same building previously inserted.");
					a--;
					exists = 1;
				}
				else exists = 0;
			}
			if(exists == 0) existentBuildings.add(buildingName);
			
			System.out.println("Restriction type: (1 - MUST HAVE, 0 - MUST NOT HAVE)");
			constraint = s.nextInt();
			
			//constraint validation and conversion
			switch(constraint){
				case 0:
					constraintString = "MUST NOT HAVE";
					break;
				case 1:
					constraintString = "MUST HAVE";
					break;
				default:
					System.out.println("The constraint type you inserted does not correspond to any of the specified above.");
					break;
			}
			
			System.out.println("Rule measurement: (1 - MORE THAN, 2 - MORE OR THE SAME AS, 3 - LESS THAN, 4 - LESS OR THE SAME AS, 5 - EXACTLY )");
			rule = s.nextInt();
			
			//rule input validation and conversion
			switch(rule) {
				case 1:
					measurementString = "MORE THAN";
					break;
				case 2:
					measurementString = "MORE OR THE SAME AS";
					break;
				case 3:
					measurementString = "LESS THAN";
					break;
				case 4:
					measurementString = "LESS OR THE SAME AS";
					break;
				case 5:
					measurementString  = "EXACTLY";
					break;
				default:
					System.out.println("The number you inserted does not correspond to any kind of the above specified rules.");
					break;
			}
			
			System.out.println("Value: (>0)");
			value = s.nextDouble();
			System.out.println("Field: (1 - type, 2 - leaning, 3 - width, 4 - height, 5 - price)");
			field = s.nextInt();
			
			//field validation and conversion
			switch(field) {
				case 1:
					fieldString = "type";
					break;
				case 2:
					fieldString = "leaning";
					break;
				case 3: 
					fieldString = "width";
					break;
				case 4:
					fieldString = "height";
					break;
				case 5:
					fieldString = "price";
					break;
				default:
					System.out.println("The field number you inserted does not correspond to any field specified above.");
					break;
			}
			Rule r = new Rule("", measurementString, value, fieldString);
			Constraint c = new Constraint(buildingName, constraintString, r);
			m.addConstraint(c);
			
			//Prints the current map info.
			m.print();
			
			//Adds inserted constraints to the config.xml file
			for(int d = 0; d < m.getListConstraints().size(); d++) {
				xmlConstraints.addRestrictionsToTerrain(m.getNumberTerrains(), m.getListConstraints().get(d));
			}
		}
	}
}
