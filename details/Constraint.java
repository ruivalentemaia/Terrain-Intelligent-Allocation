package details;

public class Constraint {
	String terrainType;
	String connector;
	Rule rule;
	
	public Constraint(String terraintype1, String connector1, Rule r) {	
	
		if(terraintype1.equals("")) {
			System.out.println("Terrain type typed: " + terraintype1);
			System.out.println("Error: Terrain type not valid.");
			return;
		}
		
		else terrainType = terraintype1;
	
		/*
		 * Validation for the connectors available
		 * (MUST HAVE, MUST NOT HAVE, CAN HAVE)
		 */
		if(connector1.equals("MUST HAVE")) {
			connector = connector1;
		}
		else if(connector1.equals("MUST NOT HAVE")) {
			connector = connector1;
		}
		else if(connector1.equals("CAN HAVE")) {
			connector = connector1;
		}
		else {
			System.out.println("Connector typed: " + connector1);
			System.out.println("Error: Connector typed not available. Try \"MUST HAVE\", \"MUST NOT HAVE\" or \"CAN HAVE\".");
			return;
		}
		
		rule = r;
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
