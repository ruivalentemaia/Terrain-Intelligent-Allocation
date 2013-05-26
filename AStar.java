import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class AStar {
	List<String> solutions = new ArrayList<String>();
	List<String> landuses = new ArrayList<String>();
	List<Terrain> unassignedTerrains = new ArrayList<Terrain>();
	
	public AStar(List<String> s, List<String> l, List<Terrain> uT) {
		solutions = s;
		landuses = l;
		unassignedTerrains = uT;
	}
	
	public  double updateHeuristicValue(List<String> l, List<Terrain> uT) {
		double hn = 0.0, minCost;
		List<Double> pricesOrdered = new ArrayList<Double>();
		
		for(int i = 0; i < uT.size(); i++) {
			pricesOrdered.add(uT.get(i).getPrice());
		}
		
		Collections.sort(pricesOrdered);
		
		for(int a = 0; a < l.size(); a++) { 
			hn += pricesOrdered.get(a);
		}
		
		System.out.println("Value of heuristic: " + hn);
		return hn;
	}
	
	public void print() {
		System.out.println();
		int i = 0;
		for(i = 0; i < solutions.size(); i++)
			System.out.println("Solution " + i + ": " + solutions.get(i));
		for(i = 0; i < landuses.size(); i++)
			System.out.println("Landuse " + i + ": " + landuses.get(i));
		for(i = 0; i < unassignedTerrains.size(); i++)
			System.out.println("Unassigned Terrain " + i + ": " + unassignedTerrains.get(i).id);
	}
}
