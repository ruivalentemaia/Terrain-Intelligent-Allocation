import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import details.Constraint;
import details.Edge;
import details.Point;
import details.Rule;


public class TerrainIntelligentAllocation {
	
	public static void main(String [ ] args) throws SAXException, IOException, ParserConfigurationException, TransformerException {
		XML xml = new XML();
		
		//Example data for testing
		List<Edge> edges1 = new ArrayList<Edge>();
		Point p1 = new Point(1.0,1.1);
		Point p2 = new Point(1.2,1.3);
		Point p3 = new Point(1.4,1.5);
		Point p4 = new Point(1.6,1.7);
		Edge e1 = new Edge(p1,p2);
		Edge e2 = new Edge(p2,p3);
		Edge e3 = new Edge(p3, p4);
		Edge e4 = new Edge(p1,p4);
		edges1.add(e1);
		edges1.add(e2);
		edges1.add(e3);
		edges1.add(e4);
		
		Rule r = new Rule("LAKE", "", 0, "");
		Constraint c = new Constraint("SCHOOL", "MUST HAVE", r);
		Terrain t = new Terrain("NOT FERTILE", 1.1, 2.2, 3.3, 4.4, edges1);
		xml.readFile(t.getEdges());
	}
}
