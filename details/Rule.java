package details;

public class Rule {
	String nearTerrain;
	String measurement;
	double number;
	String field;
	
	public Rule(String nT, String m, double n, String f) {
		/*
		 * Rule of the kind "Lake nearby", not mandatory
		 */
		if(nT == "LAKE") {
			nearTerrain = nT;
		}
		else {
			nearTerrain = "";
		}
		
		/*
		 * Rule of the kind "Lake MORE THAN 2 distance"
		 * or
		 * "MORE OR THE SAME AS 5.5 leaning", i.e.
		 */
		if(m == "MORE THAN") {
			measurement = m;
		}
		else if( m == "MORE OR THE SAME AS") {
			measurement = m;
		}
		else if(m == "LESS THAN") {
			measurement = m;
		}
		else if(m == "LESS OR THE SAME AS") {
			measurement = m;
		}
		else if(m == "EXACTLY") {
			measurement = m;
		}
		else {
			System.out.println("Measurement typed: " + m);
			System.out.println("Error: Measurement typed not valid. Try \"MORE THAN\", \"MORE OR THE SAME AS \", \"LESS THAN\", \"LESS OR THE SAME AS\" or \"EXACTLY\".");
			return;
		}
		
		if(n >= 0) {
			number = n;
		}
		else {
			System.out.println("Number introduced: " + n);
			System.out.println("Error: Number not valid. Number has to be bigger or equal to 0.");
			return;
		}
		
		/*
		 * Fields to be compared with can be
		 * type, leaning, width, height or price.
		 * I.e., "LESS THAN 2.1 leaning" or
		 * 		 "MORE THAN 400.5 price" or
		 * 		 "EXACTLY "NOT FERTILE" type.
		 */
		if(f == "type") {
			field = f;
		}
		else if(f == "leaning") {
			field = f;
		}
		else if(f == "width") {
			field = f;
		}
		else if(f == "height") {
			field = f;
		}
		else if(f == "price") {
			field = f;
		}
		else {
			System.out.println("Field typed: " + f);
			System.out.println("Field typed is not valid. Try \"type\", \"leaning\", \"width\", \"height\" or \"price\".");
			return;
		}
	}
	
	/* Gets */
	public String getNearTerrain() {
		return nearTerrain;
	}
	
	public String getMeasurement() {
		return measurement;
	}
	
	public double getNumber() {
		return number;
	}
	
	public String getField() {
		return field;
	}
	
	/* Sets */
	public void setNearTerrain(String n) {
		nearTerrain = n;
	}
	
	public void setMeasurement(String m) {
		measurement = m;
	}
	
	public void setNumber(double nr) {
		number = nr;
	}
	
	public void setField(String f) {
		field = f;
	}
}
