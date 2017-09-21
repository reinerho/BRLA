package geometrie;

import java.awt.Point;
import java.util.Vector;

import org.jdom.Element;

import exeptions.RVKollinearException;
import tools.Bruch;

public abstract class GeoObject extends Vector<Bruch> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int VEKTOR = 0;
	public static final int GERADE = 1;
	public static final int EBENE = 2;

	public final String XMLROOT = "GeoObject";
	public static final String XMLName = "NAME";

	// protected int level;
	protected String name;
	protected long iD;
	protected Point pos;

	/**
	 * Bildet die Schnittmenge mit dem GeoObject gObj
	 * 
	 * @param g
	 *            das Objekt, mit dem die Schnittmenge gebildet wird
	 * @return die Schnittmenge
	 */
	public abstract GeoObject cut(GeoObject g);

	/**
	 * Bildet ein Geoobjekt aus der Vereinigung mit dem übergebenen Geoobjekt g:
	 * zwei Vektoren zu Gerade Vektor und Gerade oder zwei Geraden zu Ebene
	 * 
	 * @param g
	 *            das übergebene GeoObjekt
	 * @return das vereinigte GeoObjekt
	 * @throws RVKollinearException
	 *             wirft eine Exception, wenn keine Ebene gebildet werden kann
	 */
	public abstract GeoObject join(GeoObject g) throws RVKollinearException;

	public abstract boolean isPartOf(GeoObject g);

	/**
	 * Erzeugt ein Array of String, aus dem eine Darstellung der Ebene in
	 * Parameterform zusammengesetzt werden kann
	 * 
	 * @return das Stringarray
	 */
	protected abstract String[] toStringArray();

	public abstract GeoObject clone();

	public abstract String toString();

	public Vector<String> print() {
		Vector<String> out = new Vector<String>();
		if (this.name != null)
			out.add(this.name);
		else
			out.add("kein Name");
		out.add(" ");
		String[] data = toStringArray();
		for (String s : data) {
			out.add(s);
		}
		out.add(" ");
		return out;
	}

	/**
	 * Erzeugt ein xml-Element mit den Daten des GeoObjects.
	 * 
	 * @return Das root-xml-Element mit den Daten des GeoObjects
	 */
	public abstract Element xml();

	/**
	 * Erzeugt ein xml-Element nit dem Namen "xmlName", das den Namen des
	 * GeoObjects speichert
	 * 
	 * @param xmlName
	 *            der xml-Name
	 * @return das xml-Element mit dem Namen "xml-Name" und dem Inhalt
	 *         "Name des GeoObjects"
	 */
	public Element xml(String xmlName) {
		Element root = new Element(xmlName);
		Element name = new Element("NAME");
		name.addContent(this.getName());
		root.addContent(name);
		return root;
	}

	/**
	 * Gibt die Laenge des laengsten String im StringArray des GeoObjects zurück
	 * 
	 * @return die Laenge des laengsten Strings im StringArray
	 */
	public int width() {
		String[] comp = this.toStringArray();
		int out = 0;
		for (int i = 0; i < 5; i++) {
			if (comp.length > out)
				out = comp.length;
		}
		return out;
	}

	/**
	 * Speicher einen LAVektor des GeoObjects in einem xml-Element
	 * 
	 * @param xmlName
	 *            der Name des LAVektors
	 * @param vektor
	 *            der LAVector
	 * @return das xml-Element in dem der LAVector gespeichert ist
	 */
	protected Element vektorElement(String xmlName, LAVector vektor) {
		Element ve = new Element(xmlName);
		ve.addContent(vektor.xml());
		return ve;
	}

	public long getiD() {
		return iD;
	}

	public void setiD(long iD) {
		this.iD = iD;
	}

	public int getLevel() {
		return -1;
	}

	/**
	 * Gibt den Namen des GeoObjekts zurück
	 * 
	 * @return der Name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setzt den Namen des GeoObjekts
	 * 
	 * @param name
	 *            der Name
	 */
	public void setName(String name) {
		this.name = name;
	}

	public Point getPos() {
		return pos;
	}

	public void setPos(Point pos) {
		this.pos = pos;
	}

	/**
	 * Erzeugt aus einem String ein StringArray indem oben und unten mit leeren
	 * Strings aufgefüllt wird. Auf diese Weise werden einzelne Zeilen zum
	 * Aarray aus LAVektoren hinzugefügt.
	 * 
	 * @param text
	 *            der Test
	 * @param size
	 *            die Größe des Arrays
	 * @return ein String-Array, dass in der Mitte den String "text" enthält
	 */
	protected String[] textArray(String text, int size) {
		String out[] = new String[size];
		int mid = size / 2;
		String empty = "";
		for (int j = 0; j < text.length(); j++)
			empty = empty + " ";
		for (int i = 0; i < size; i++) {
			out[i] = empty;
		}
		out[mid] = text;
		return out;
	}

}
