package tools;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.xml.sax.InputSource;

import exeptions.FileOpenNoSuccessException;
import geometrie.Ebene;
import geometrie.GeoObject;
import geometrie.Gerade;
import geometrie.LAVector;

/**
 * Stellt Methoden zur Verfügung, xml-Dateien zu erzeugen
 * @author reiner
 *
 */
public class XML {
	
	/**
	 * Erzeugt ein xml-ELement aus einem String
	 * @param name der Name des Elements
	 * @param content der String, der unter diesem Namen gespeichert wird
	 * @return ein xml-ELement mit Namen "name" und String-Inhalt "content"
	 */
	public static Element myElement(String name, String content){
		Element out = new Element(name);
		out.addContent(content);
		return out;
	}
	
	/**
	 * Erzeugt ein xml-Element aus einem Integer. Der Integerwert wird dabei in einen String umgewandelt
	 * @param name der Name des Elements
	 * @param content der Integerwert, der unter diesem Namen gespeichert wird
	 * @return ein xml-ELement mit Namen "name" und Inhalt "content"
	 */
	public static Element myElement(String name, int content){
		Element out = new Element(name);
		out.addContent(content+"");
		return out;
	}

	/**
	 * Erzeugt ein xml-Element aus einem Double. Der Doublewert wird dabei in einen String umgewandelt
	 * @param name der Name des Elements
	 * @param content derDoublewert, der unter diesem Namen gespeichert wird
	 * @return ein xml-ELement mit Namen "name" und Inhalt "content"
	 */
	public static Element myElement(String name, double content){
		Element out = new Element(name);
		out.addContent(content+"");
		return out;
	}
	
	/**
	 * Erzeugt ein xml-Element aus einem Bruch. Der Bruch wird dabei in einen String umgewandelt
	 * @param name der Name des Elements
	 * @param content derBruch, der unter diesem Namen gespeichert wird
	 * @return ein xml-ELement mit Namen "name" und Inhalt "content"
	 */
	public static Element myElement(String name, Bruch content){
		Element out = new Element(name);
		out.addContent(content.toString());
		return out;
	}
	
	/**
	 * Erzeugt ein xml-Element aus einem LAVector. Der LAVector wird dabei in einen String umgewandelt
	 * @param name der Name des Elements
	 * @param content derLAVector, der unter diesem Namen gespeichert wird
	 * @return ein xml-ELement mit Namen "name" und Inhalt "content"
	 */
	public static Element myElement(String name, LAVector content){
		Element out = new Element(name);
		out.addContent(content.toPointString(true));
		return out;
	}
	
	/**
	 * Speichert einen xml-Baum
	 * 
	 * @param filename
	 *            der Dateiname
	 * @param root
	 *            das root-Element des Baums
	 * @throws IOException
	 *             wirft eine IOException wenn etwas schif geht
	 */
	public static void save(String filename, Element root) throws IOException {
		String savename = filename;
		Document doc = new Document();
		doc.setRootElement(root);
		XMLOutputter out = new XMLOutputter(Format.getPrettyFormat().setEncoding("ISO-8859-15"));
		java.io.FileWriter writer = new FileWriter(savename);
		out.output(doc, writer);
		writer.flush();
		writer.close();
	}

	/**
	 * Läd einen XML-Baum in den Speicher
	 * 
	 * @param filename
	 *            der Dateiname
	 * @return das Wurzelelement des XML-Baums
	 * @throws JDOMException
	 *             Exception, wenn die XML-Struktur nicht stimmt
	 * @throws FileOpenNoSuccessException
	 *             Exception, wenn die Datei nicht geöffnet werden kann
	 */
	public static Element load(String filename) throws JDOMException, FileOpenNoSuccessException {
		Document doc = new Document();
		int cycle = 0;
		String[] formats = { "UTF-8", "iso-8859-15" };
		while (cycle < 2) {
			try {
				InputSource is = new InputSource(filename);
				// JOptionPane.showMessageDialog(null, "Öffne: "+filename);
				is.setEncoding(formats[cycle]); // Warum nur????
				doc = new SAXBuilder().build(is);
				break;
			} catch (IOException ioe) {
				System.out.println("IO-Exception beim Laden von " + filename + " mit Encoding " + formats[cycle]);
				if (cycle == 0)
					System.out.println("Versuche Encoding " + formats[cycle + 1]);
				else {
					throw new FileOpenNoSuccessException();
				}
				cycle++;
			}
		}
		return doc.getRootElement();
	}
	
	/**
	 * Liest aus einem xml-Element ein GeoObject aus und erzeugt es
	 * @param root die Wurzel des xml-Baums
	 * @return ein GeoObject, das in dem xml-Baum gespeichert war
	 */
	@SuppressWarnings("unchecked")
	public static Vector<GeoObject> parseXML(Element root){
		Vector<GeoObject> out = new Vector<GeoObject>();
		List<Element> list = root.getChildren("Ebene");
		for (Element e:list){
			out.add(new Ebene(e));
		}
		list = root.getChildren("Gerade");
		for (Element e:list){
			out.add(new Gerade(e));
		}
		list = root.getChildren("Vektor");
		for (Element e:list){
			out.add(new LAVector(e));
		}
		return out;
	}
	
	
	
}
