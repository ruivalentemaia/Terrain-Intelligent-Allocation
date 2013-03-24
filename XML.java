import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
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

import details.Point;


public class XML {
	
	private File file;
	
	public XML() {
		file = new File("src/config/terrains.xml");
	}
	
	public File getFile() {
		return file;
	}
	
	public void setFile(File file1) {
		file = file1;
	}
	
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
		        
		    Terrain t = new Terrain(type, leaning, width, height, price, edges);
		    t.print();
		    t.getEdges().clear();
		    counterTerrains++;
		}
		      
		System.out.println("Number of terrains: " + counterTerrains);
	}
	
	public void addTerrainToFile(Terrain t) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance(); 
		domFactory.setIgnoringComments(true);
		DocumentBuilder builder = domFactory.newDocumentBuilder(); 
		Document doc = builder.parse(file);
		
		NodeList nodes = doc.getElementsByTagName("terrain");
		
		//estrutura do elemento "terrain" criado
		
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
		
		Element edges = doc.createElement("edges");
		
		int edgesSize = t.getEdgesSize();
		
		for(int i = 0; i < edgesSize; i++) {
			Element edge = doc.createElement("edge");
			
			Text x1t = doc.createTextNode(Double.toString(t.getEdges().get(i).getP1().getX1()));
			Element x1 = doc.createElement("x1");
			x1.appendChild(x1t);
			
			Text y1t = doc.createTextNode(Double.toString(t.getEdges().get(i).getP1().getY1()));
			Element y1 = doc.createElement("y1");
			y1.appendChild(y1t);
			
			Text x2t = doc.createTextNode(Double.toString(t.getEdges().get(i).getP2().getX1()));
			Element x2 = doc.createElement("x2");
			x2.appendChild(x2t);
			
			Text y2t = doc.createTextNode(Double.toString(t.getEdges().get(i).getP2().getY1()));
			Element y2 = doc.createElement("y2");
			y2.appendChild(y2t);
			
			edge.appendChild(x1);
			edge.appendChild(y1);
			edge.appendChild(x2);
			edge.appendChild(y2);
			
			edges.appendChild(edge);
		}
			
		terrain.appendChild(edges);
		
		nodes.item(0).getParentNode().insertBefore(terrain, nodes.item(0));
		
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");

		StreamResult result = new StreamResult(new StringWriter());
		DOMSource source = new DOMSource(doc);
		transformer.transform(source, result);

		String xmlOutput = result.getWriter().toString();
		//System.out.println(xmlOutput);
		
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
