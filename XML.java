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
	
	public XML(File f) throws IOException {
		if(!f.exists()) {
			f.createNewFile();
			file = f;
		}
		else file = f;
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
	public List<Terrain> readFile() throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(file);
		int counterTerrains = 0;
		List<Terrain> terrains = new ArrayList<Terrain>();
		      
		for(int a = 0; a < document.getElementsByTagName("terrain").getLength()-1; a++) {
		    
			int id = Integer.parseInt(document.getElementsByTagName("id").item(a).getTextContent());
			
			String type = document.getElementsByTagName("type").item(a).getTextContent();
		        
			String leaningx = document.getElementsByTagName("leaning").item(a).getTextContent();
		    double leaning = Double.parseDouble(leaningx);
		        
		    String widthx = document.getElementsByTagName("width").item(a).getTextContent();
		    double width = Double.parseDouble(widthx);
		    
		    String heightx = document.getElementsByTagName("height").item(a).getTextContent();
		    double height = Double.parseDouble(heightx);
		        
		    String pricex = document.getElementsByTagName("price").item(a).getTextContent();
		    double price = Double.parseDouble(pricex);
		            
		    Terrain t = new Terrain(type, leaning, width, height, price);
		    t.id = id;
		    t.print();
		    counterTerrains++;
		    terrains.add(t);
		}
		      
		System.out.println("Number of terrains: " + counterTerrains);
		return terrains;
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
			 * 	<id>t.id</id>
			 * 	<type>t.getType()</type>
			 * 	<leaning>t.getLeaning()</leaning>
			 * 	<width>t.getWidth()</width>
			 * 	<height>t.getHeight()</height>
			 *	<price>t.getPrice()</price>
			 * </terrain>
			 */
			
			Element terrain = doc.createElement("terrain");
			
			Text id = doc.createTextNode(Integer.toString(t.id));
			Element idElement = doc.createElement("id");
			idElement.appendChild(id);
			
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
			
			terrain.appendChild(idElement);
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
	public List<Constraint> readConfigurationFile() throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(file);
		
		//add to map attributes...
		List<Constraint> constraints = new ArrayList<Constraint>();
	
		for(int i = 0; i < document.getElementsByTagName("building").getLength()-1;i++) {
			String bName = document.getElementsByTagName("name").item(i).getTextContent();
			for(int a = 0; a < document.getElementsByTagName("restriction").getLength(); a++){
				String rType = document.getElementsByTagName("type").item(a).getTextContent();
				String measurement = document.getElementsByTagName("measurement").item(a).getTextContent();
				String val = document.getElementsByTagName("value").item(a).getTextContent();
				double v = Double.parseDouble(val);
				String field = document.getElementsByTagName("field").item(a).getTextContent();
				
				Rule r = new Rule(measurement, v, field);
				Constraint c = new Constraint(bName, rType, r);
				
				constraints.add(c);
				break;
			}
			constraints.get(i).print();
		}
		return constraints;
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
			
			Text rMeasurement = doc.createTextNode(c.getRule().getMeasurement());
			Element measurement = doc.createElement("measurement");
			measurement.appendChild(rMeasurement);
			
			Text rValue = doc.createTextNode(Double.toString(c.getRule().getNumber()));
			Element value = doc.createElement("value");
			value.appendChild(rValue);
			
			Text rField = doc.createTextNode(c.getRule().getField());
			Element field = doc.createElement("field");
			field.appendChild(rField);
			
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
