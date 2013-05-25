import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import details.Constraint;
import details.Point;
import details.Rule;


public class XML {
	
	private File file;
	
	public XML(File f) {
		file = f;
	}
	
	public File getFile() {
		return file;
	}
	
	public void setFile(File file1) {
		file = file1;
	}
	
	/*
	 * Reads the terrains from the terrains.xml configuration file and
	 * writes its' information and the number of terrains that exist
	 * in the file.
	 */
	public void readFile(List<details.Edge> edges) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(file);
		int counterTerrains = 0;
		      
		for(int a = 0; a < document.getElementsByTagName("terrain").getLength(); a++) {
		    
			String type = document.getElementsByTagName("type").item(a).getTextContent();
		        
			String leaningx = document.getElementsByTagName("leaning").item(a).getTextContent();
		    double leaning = Double.parseDouble(leaningx);
		        
		    String widthx = document.getElementsByTagName("width").item(a).getTextContent();
		    double width = Double.parseDouble(widthx);
		    
		    String heightx = document.getElementsByTagName("height").item(a).getTextContent();
		    double height = Double.parseDouble(heightx);
		        
		    String pricex = document.getElementsByTagName("price").item(a).getTextContent();
		    double price = Double.parseDouble(pricex);
		        
		    for(int i = 0; i < 4; i++) {
		    	String x1x = document.getElementsByTagName("x1").item((counterTerrains*4)+i).getTextContent();
		        double x1 = Double.parseDouble(x1x);
		          
		        String y1x = document.getElementsByTagName("y1").item((counterTerrains*4)+i).getTextContent();
		        double y1 = Double.parseDouble(y1x);
		       
		        details.Point P1 = new Point(x1,y1);
		         
		        String x2x = document.getElementsByTagName("x2").item((counterTerrains*4)+i).getTextContent();
		        double x2 = Double.parseDouble(x2x);
		         
		        String y2x = document.getElementsByTagName("y2").item((counterTerrains*4)+i).getTextContent();
		        double y2 = Double.parseDouble(y2x);
		         
		        details.Point P2 = new Point(x2, y2);
		      
		        details.Edge edge = new details.Edge(P1,P2);
		        edges.add(edge);
		    }
		        
		    Terrain t = new Terrain(type, leaning, width, height, price);
		    t.print();
		    counterTerrains++;
		}
		      
		System.out.println("Number of terrains: " + counterTerrains);
	}
	
	/*
	 * Adds a terrain to the terrains.xml configuration file
	 */
public void addTerrainToFile(Terrain t) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		
		if(t.validationNumber == 1) {
			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance(); 
			domFactory.setIgnoringComments(true);
			DocumentBuilder builder = domFactory.newDocumentBuilder(); 
			Document doc = builder.parse(file);
			
			NodeList nodes = doc.getElementsByTagName("terrain");
			
			/*
			 * Structure of the "terrain" element
			 * <terrain>
			 * 	<type>t.getType()</type>
			 * 	<leaning>t.getLeaning()</leaning>
			 * 	<width>t.getWidth()</width>
			 * 	<height>t.getHeight()</height>
			 *	<price>t.getPrice()</price>
			 *	<edges>
			 *		<edge>
			 *			<x1>t.getEdges().get(i).getP1().getX1()</x1>
			 *			<y1>t.getEdges().get(i).getP1().getY1()</y1>
			 *			<x2>t.getEdges().get(i).getP2().getX1()</x2>
			 *			<y2>t.getEdges().get(i).getP2().getY1()</y2>
			 *		</edge>
			 *		<edge>
			 *			<x1>t.getEdges().get(i+1).getP1().getX1()</x1>
			 *			<y1>t.getEdges().get(i+1).getP1().getY1()</y1>
			 *			<x2>t.getEdges().get(i+1).getP2().getX1()</x2>
			 *			<y2>t.getEdges().get(i+1).getP2().getY1()</y2>
			 *		</edge>
			 *		<edge>
			 *			<x1>t.getEdges().get(i+2).getP1().getX1()</x1>
			 *			<y1>t.getEdges().get(i+2).getP1().getY1()</y1>
			 *			<x2>t.getEdges().get(i+2).getP2().getX1()</x2>
			 *			<y2>t.getEdges().get(i+2).getP2().getY1()</y2>
			 *		</edge>
			 *		<edge>
			 *			<x1>t.getEdges().get(i+3).getP1().getX1()</x1>
			 *			<y1>t.getEdges().get(i+3).getP1().getY1()</y1>
			 *			<x2>t.getEdges().get(i+3).getP2().getX1()</x2>
			 *			<y2>t.getEdges().get(i+3).getP2().getY1()</y2>
			 *		</edge>
			 *	</edges>
			 * </terrain>
			 */
			
			Element terrain = doc.createElement("terrain");
			
			Text ty = doc.createTextNode(t.getType()); 
			Element p = doc.createElement("type"); 
			p.appendChild(ty);
			
			Text l = doc.createTextNode(Double.toString(t.getLeaning()));
			Element leaning = doc.createElement("leaning");
			leaning.appendChild(l);
			
			Text w = doc.createTextNode(Double.toString(t.getWidth()));
			Element width = doc.createElement("width");
			width.appendChild(w);
			
			Text h = doc.createTextNode(Double.toString(t.getHeight()));
			Element height = doc.createElement("height");
			height.appendChild(h);
			
			Text pri = doc.createTextNode(Double.toString(t.getPrice()));
			Element price = doc.createElement("price");
			price.appendChild(pri);

			terrain.appendChild(p);
			terrain.appendChild(leaning);
			terrain.appendChild(width);
			terrain.appendChild(height);
			terrain.appendChild(price);
			
			nodes.item(0).getParentNode().insertBefore(terrain, nodes.item(0));
			
			TransformerFactory tf = TransformerFactory.newInstance();
			tf.setAttribute("indent-number", new Integer(2));
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			StreamResult result = new StreamResult(new StringWriter());
			DOMSource source = new DOMSource(doc);
			transformer.transform(source, result);

			String xmlOutput = result.getWriter().toString();
			
			//Writing to the file
			FileOutputStream fop = null;
	        try {
	        	fop = new FileOutputStream(file);
	            if (!file.exists()) {
	            	file.createNewFile();
	            }
	            byte[] contentInBytes = xmlOutput.getBytes();
	            fop.write(contentInBytes);
	            fop.flush();
	            fop.close();

	        } catch (IOException e) {
	        	e.printStackTrace();
	        } finally {
	        	try {
	        		if (fop != null) {
	        			fop.close();
	                }
	            } catch (IOException e) {
	            	e.printStackTrace();
	            }
	        }
		}
		else {
			System.out.println("Error: it's not possible to write to the terrains.xml file because of the error specified above.");
		}
		
	}
	
	/*
	 * Reads data from config.xml and creates the Map object
	 */
	public Map readConfigurationFile() throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(file);
		
		//add to map attributes...
		Map m = new Map();
	
		for(int i = 0; i < document.getElementsByTagName("building").getLength();i++) {
			String bName = document.getElementsByTagName("name").item(i).getTextContent();
			for(int a = 0; a < document.getElementsByTagName("restriction").getLength(); a++){
				String rType = document.getElementsByTagName("type").item(a).getTextContent();
				String nTerrain = document.getElementsByTagName("nearTerrain").item(a).getTextContent();
				String measurement = document.getElementsByTagName("measurement").item(a).getTextContent();
				String val = document.getElementsByTagName("value").item(a).getTextContent();
				double v = Double.parseDouble(val);
				String field = document.getElementsByTagName("field").item(a).getTextContent();
				
				Rule r = new Rule(nTerrain, measurement, v, field);
				Constraint c = new Constraint(bName, rType, r);
				//add to map list of constraints...
				m.addConstraint(c);
			}
		}
		return m;
	}
	
	/*
	 * Writes data to file config.xml
	 */
	public void addRestrictionsToTerrain(int nT, Constraint c) throws ParserConfigurationException, SAXException, IOException, TransformerException {
			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance(); 
			domFactory.setIgnoringComments(true);
			DocumentBuilder builder = domFactory.newDocumentBuilder(); 
			Document doc = builder.parse(file);
				
			NodeList nodes = doc.getElementsByTagName("building");
			
			/*
			 * Structure of the "config" element
			 * 
			 * <config>
			 * 	<configuration>
			 * 	<numberTerrains>nT</numberTerrains>
			 * 	<maxArea>mA</maxArea>
			 * 	<buildingTypes>
			 * 		<building>
			 * 			<name>c.getTerrainType()</name>
			 * 			<restrictions>
			 * 				<restriction>
			 * 					<type>c.getConnector()</type>
			 * 					<rule>
			 * 						<nearTerrain>c.getRule().getNearTerrain()</nearTerrain>
			 * 						<measurement>c.getRule().getMeasurement</measurement>
			 * 						<value>c.getRule.getValue()</value>
			 * 						<field>c.getRule.getField()</field>
			 * 					</rule>
			 * 				</restriction>
			 * 				<restriction>
			 * 					<type>c.getConnector()</type>
			 * 					<rule>
			 * 						<nearTerrain>c.getRule().getNearTerrain()</nearTerrain>
			 * 						<measurement>c.getRule().getMeasurement()</measurement>
			 * 						<value>c.getRule().getValue()</value>
			 * 						<field>c.getRule().getField()</field>
			 * 					</rule>
			 * 				</restriction>
			 * 			</restrictions>
			 * 		</building>
			 * 	</buildingTypes>
			 * </configuration>
			 * </config>
			 * 
			 */
			
			Element building = doc.createElement("building");
			
			Text bName = doc.createTextNode(c.getTerrainType());
			Element name = doc.createElement("name");
			name.appendChild(bName);
			
			Element restrictions = doc.createElement("restrictions");
			Element restriction = doc.createElement("restriction");
			
			Text bType = doc.createTextNode(c.getConnector());
			Element type = doc.createElement("type");
			type.appendChild(bType);
			
			Element rule = doc.createElement("rule");
			
			Text rNT = doc.createTextNode(c.getRule().getNearTerrain());
			Element nearTerrain = doc.createElement("nearTerrain");
			nearTerrain.appendChild(rNT);
			
			Text rMeasurement = doc.createTextNode(c.getRule().getMeasurement());
			Element measurement = doc.createElement("measurement");
			measurement.appendChild(rMeasurement);
			
			Text rValue = doc.createTextNode(Double.toString(c.getRule().getNumber()));
			Element value = doc.createElement("value");
			value.appendChild(rValue);
			
			Text rField = doc.createTextNode(c.getRule().getField());
			Element field = doc.createElement("field");
			field.appendChild(rField);
			
			rule.appendChild(nearTerrain);
			rule.appendChild(measurement);
			rule.appendChild(value);
			rule.appendChild(field);
			
			restriction.appendChild(type);
			restriction.appendChild(rule);
			
			restrictions.appendChild(restriction);
			
			building.appendChild(name);
			building.appendChild(restrictions);
			
			nodes.item(0).getParentNode().insertBefore(building, nodes.item(0));
			
			TransformerFactory tf = TransformerFactory.newInstance();
			tf.setAttribute("indent-number", new Integer(2));
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			StreamResult result = new StreamResult(new StringWriter());
			DOMSource source = new DOMSource(doc);
			transformer.transform(source, result);

			String xmlOutput = result.getWriter().toString();
			
			//Writing to the file
			FileOutputStream fop = null;
	        try {
	        	fop = new FileOutputStream(file);
	            if (!file.exists()) {
	            	file.createNewFile();
	            }
	            byte[] contentInBytes = xmlOutput.getBytes();
	            fop.write(contentInBytes);
	            fop.flush();
	            fop.close();

	        } catch (IOException e) {
	        	e.printStackTrace();
	        } finally {
	        	try {
	        		if (fop != null) {
	        			fop.close();
	                }
	            } catch (IOException e) {
	            	e.printStackTrace();
	            }
	        }	
	}
}
