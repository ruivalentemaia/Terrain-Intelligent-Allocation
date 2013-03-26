package details;

public class Constraint {
	String terrainType;
	String connector;
	Rule rule;
	
	public Constraint(String terraintype1, String connector1, Rule r) {	
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
		
		rule = r;
		
		/*
		 * Validations for the rule structure.

		// - if the field is "type" the measurement has to be "EXACTLY"
		if( (r.getField() == "type") && (r.getMeasurement() != "EXACTLY") ) {
			System.out.println("Field: " + r.getField());
			System.out.println("Measurement: " + r.getMeasurement());
			System.out.println("Error: Invalid rule. \"Type\" field can only be measured with \"EXACTLY\".");
			return;
		}
		else {
			r = new Rule("", "EXACTLY", 0, "type");
		}*/
	}
	
	/* Gets */
	public String getTerrainType() {
		return terrainType;
	}
	
	public String getConnector() {
		return connector;
	}
	
	public Rule getRule() {
		return rule;
	}
	
	/* Sets */
	public void setTerrainType(String tT) {
		terrainType = tT;
	}
	
	public void setConnector(String c) {
		connector = c;
	}
	
	public void setRule(Rule r) {
		rule = r;
	}
}
