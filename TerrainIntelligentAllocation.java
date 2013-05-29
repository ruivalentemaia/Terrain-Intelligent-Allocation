import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import details.Constraint;
import details.Rule;


public class TerrainIntelligentAllocation {
	
	public static void main(String [ ] args) throws SAXException, IOException, ParserConfigurationException, TransformerException {
		Map m = new Map();
		List<String> existentBuildings = new ArrayList<String>();
		File terrains = new File("src/config/terrains.xml");
		XML xmlTerrains = new XML(terrains);
		File constraints = new File("src/config/config.xml");
		XML xmlConstraints = new XML(constraints);
		int readData = 0;
		
		//Needed for the A* algorithm
		List<String> solutions = new ArrayList<String>();
		
		//Selection of file to read data from.
		System.out.println("Do you want to read data from an existent file? (Y/N)");
		Scanner s = new Scanner(System.in);
		Locale.setDefault(Locale.US);
		String readFile = s.next();
		if(readFile.equals("Y") || readFile.equals("y")) {
			System.out.println("Insert the name of the terrains data file you want to read from the src/config folder: ");
			String fileTerrains = s.next();
			terrains = new File("src/config/" + fileTerrains + ".xml");
			xmlTerrains = new XML(terrains);
			List<Terrain> ts = xmlTerrains.readFile();
			m.setTerrainsList(ts);
			
			System.out.println("Insert the name of the config file you want to read from the src/config folder: ");
			String fileConfig = s.next();
			constraints = new File("src/config/" + fileConfig + ".xml");
			xmlConstraints = new XML(constraints);
			List<Constraint> cs = xmlConstraints.readConfigurationFile();
			m.setConstraintsList(cs);
			for(int t = 0; t < cs.size(); t++) {
				existentBuildings.add(cs.get(t).getTerrainType());
			}
			readData = 1;
		}
		else if(readFile.equals("N") || readFile.equals("n")) readData = 0;
		else System.out.println("WARNING: You didn't input any of the possible answers (Y or N). The system will assume you don't want to read data from a file.");
		
		//User inserts the size of the map
		int sizeOfTerrain,fertile;
		String fertileString = "";
		double leaning, width, height, price;
		if(readData == 0) {
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
				t.id = i;
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
				Rule r = new Rule(measurementString, value, fieldString);
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
		
		
		/*
		 * A* algorithm
		 */
		AStar aStar = new AStar(solutions, existentBuildings, m.getListTerrains());
		List<String> landuses = new ArrayList<String>();
		landuses = aStar.getLandusesList();
		List<Terrain> unassignedTerrains = new ArrayList<Terrain>();
		unassignedTerrains = aStar.getUnassignedTerrainsList();
		Terrain current;
		aStar.print();
		double fn = 0.0;
		double totalPrice = 0.0;
		double accumulatedCost = 0.0;
		int i = 0,inc = 0;
		List<Terrain> visited = new ArrayList<Terrain>();
			
		//orders nodes by crescent order of price
		List<Double> pricesOrdered = new ArrayList<Double>();
		List<Terrain> orderedUnassignedTerrains = new ArrayList<Terrain>();
		for(int o = 0; o < unassignedTerrains.size(); o++) {
			pricesOrdered.add(unassignedTerrains.get(o).getPrice());
		}
		
		Collections.sort(pricesOrdered);	
		for(int j = 0; j < pricesOrdered.size(); j++) {
			for(int u = 0; u < unassignedTerrains.size(); u++) {
				if(pricesOrdered.get(j) == unassignedTerrains.get(u).getPrice()) {
					orderedUnassignedTerrains.add(unassignedTerrains.get(u));
				}
			}
		}
	
		//calculates the fn = gn + hn for the first terrain and adds to the visited terrains
		fn = accumulatedCost + aStar.updateHeuristicValue(landuses, orderedUnassignedTerrains);
		System.out.println("Heuristic Value: " + fn);
		visited.add(orderedUnassignedTerrains.get(0));
		
		while(landuses.size() > 0) {
			current = visited.get(inc);
			if(landuses.size() > 0) {
				//check if terrain goes with the constraints defined
				for(int b = 0; b < m.getListConstraints().size(); b++) {
					Constraint c = m.getListConstraints().get(b);
					if(current.applyUserSelectedConstraint(c)) { 
						solutions.add(current.id + "-" + m.getListConstraints().get(b).getTerrainType());
						totalPrice += current.getPrice();
						landuses.remove(m.getListConstraints().get(b).getTerrainType());
						m.getListConstraints().remove(b);
						orderedUnassignedTerrains.remove(b);
					}
				}
			}
			inc++;
			for(; i < orderedUnassignedTerrains.size(); i++) {
				visited.add(orderedUnassignedTerrains.get(i));
				fn = totalPrice + aStar.updateHeuristicValue(landuses, orderedUnassignedTerrains);
				for(int aux = 0; aux < visited.size(); aux++) {
					if((orderedUnassignedTerrains.get(i).equals(visited.get(aux))) && (fn >= orderedUnassignedTerrains.get(i).getPrice())) {
						// ....
						continue;
					}
					if( !(orderedUnassignedTerrains.contains(orderedUnassignedTerrains.get(i))) || (fn < orderedUnassignedTerrains.get(i).getPrice()) ) {
						current = orderedUnassignedTerrains.get(i);
						accumulatedCost = fn;
						fn = accumulatedCost + aStar.updateHeuristicValue(landuses, orderedUnassignedTerrains);
						if(!(orderedUnassignedTerrains.contains(orderedUnassignedTerrains.get(i)))) 
							visited.add(orderedUnassignedTerrains.get(i));
					}
				}
			}

		}
		System.out.println("Allocation complete. Allocation (Terrain-Building):");
		System.out.println("Solution: " + solutions);
		System.out.println("Total price: " + totalPrice);
	}
}

