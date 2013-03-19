public class Terrain {
	String type;
	float leaning;
	float width;
	float height;
	float price;
	//array of edges
	details.Edge edges[];
	
	public Terrain(String typeT, float leaningT, float widthT, float heightT, float priceT, details.Edge edgesT[]) {
		
		if(typeT != "") {
			if(typeT == "FERTILE") {
				type = typeT;
			}
			else if (typeT == "NOT FERTILE") {
				type = typeT;
			}
			else return;
		}
		else return;
		
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
}