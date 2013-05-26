

public class Node {
	Terrain current;
	Terrain right;
	Terrain left;
	double fn;
	
	public Node(Terrain c, Terrain r, Terrain l, double h) {
		current = c;
		right = r;
		left = l;
		fn = h;
	}
	
	public Terrain getCurrent() {
		return current;
	}
	
	public Terrain getRight() {
		return right;
	}
	
	public Terrain getLeft() {
		return left;
	}
	
	public double getHeuristic() {
		return fn;
	}
	
	public void setCurrent(Terrain c){
		current = c;
	}
	
	public void setRight(Terrain r) {
		right = r;
	}
	
	public void setLeft(Terrain l) {
		left = l;
	}
	
	public void setHeuristic(double h) {
		fn = h;
	}
	
	public double calculateHeuristic(double g, double h){
		return fn = g + h;
	}
}
