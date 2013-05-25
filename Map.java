import java.util.ArrayList;
import java.util.List;

import details.Constraint;

public class Map {
	int numberTerrains;
	List<Terrain> terrains = new ArrayList<Terrain>();
	List<Constraint> constraints = new ArrayList<Constraint>();
	
	public Map(int nT) {
		numberTerrains = nT;
		terrains.clear();
		constraints.clear();
	}
	
	public Map() {
		numberTerrains = 0;
		terrains.clear();
		constraints.clear();
	}
	
	//Gets e sets...
	public int getNumberTerrains() {
		return numberTerrains;
	}
	
	public List<Terrain> getListTerrains() {
		return terrains;
	}
	
	public List<Constraint> getListConstraints() {
		return constraints;
	}
	
	public void setNumberTerrains(int nT){
		numberTerrains = nT;
	}
	
	public void setTerrainsList(List<Terrain> l){
		terrains = l;
	}
	
	public void setConstraintsList(List<Constraint> cs){
		constraints = cs;
	}
	
	//Print information
	public void print() {
		System.out.println("Number of Terrains: " + numberTerrains);
		for(int i = 0; i < terrains.size(); i++) {
			terrains.get(i).print();
		}
		for(int i = 0; i < constraints.size(); i++) {
			System.out.println("Terrain type: " + constraints.get(i).getTerrainType());
			System.out.println("Connector: " + constraints.get(i).getConnector());
			System.out.println("Near Terrain: " + constraints.get(i).getRule().getNearTerrain());
			System.out.println("Measurement: " + constraints.get(i).getRule().getMeasurement());
			System.out.println("Number: " + constraints.get(i).getRule().getNumber());
			System.out.println("Field: " + constraints.get(i).getRule().getField());
		}
		System.out.println();
	}
	
	//Add, update, remove constraint to 'constraints'
	public void addConstraint(Constraint c){
		constraints.add(c);
	}
	
	public void updateConstraint(Constraint cOld, Constraint cNew) {
		cOld = cNew;
		constraints.remove(cOld);
		constraints.add(cNew);
	}
	
	public void removeConstraint(Constraint c) {
		constraints.remove(c);
	}
	
	//Add, update and remove terrain to 'terrains'
	public void addTerrain(Terrain t){
		terrains.add(t);
	}
	
	public void updateTerrain(Terrain tOld, Terrain tNew) {
		tOld = tNew;
		terrains.remove(tOld);
		terrains.add(tNew);
	}
	
	public void removeTerrain(Terrain t){
		terrains.remove(t);
	}
}
