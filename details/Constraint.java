package details;

public class Constraint {
	String terrainType;
	String connector;
	Rule rule;
	
	public Constraint(String terraintype1, String connector1) {	
		/*
		 * Validation for the terrain types available
		 * (SCHOOL, HOSPITAL, HOUSE, AIRPORT, STADIUM)
		 */
		if(terraintype1 == "SCHOOL") {
			terrainType = terraintype1;
		}
		else if(terraintype1 == "HOSPITAL") {
			terrainType = terraintype1;
		}
		else if(terraintype1 == "HOUSE") {
			terrainType = terraintype1;
		}
		else if(terraintype1 == "AIRPORT") {
			terrainType = terraintype1;
		}
		else if(terraintype1 == "STADIUM"){
			terrainType = terraintype1;
		}
		else {
			System.out.println("Terrain type typed: " + terraintype1);
			System.out.println("Error: Terrain type not valid. Please try \"SCHOOL\", \"HOSPITAL\", \"HOUSE\", \"AIRPORT\" or \"STADIUM\"");
			return;
		}
		
		/*
		 * Validation for the connectors available
		 * (MUST HAVE, MUST NOT HAVE, CAN HAVE)
		 */
		if(connector1 == "MUST HAVE") {
			connector = connector1;
		}
		else if(connector1 == "MUST NOT HAVE") {
			connector = connector1;
		}
		else if(connector1 == "CAN HAVE") {
			connector = connector1;
		}
		else {
			System.out.println("Connector typed: " + connector1);
			System.out.println("Error: Connector typed not available. Try \"MUST HAVE\", \"MUST NOT HAVE\" or \"CAN HAVE\".");
			return;
		}
		
	}
}
