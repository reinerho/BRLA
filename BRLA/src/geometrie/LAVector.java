package geometrie;

import java.awt.Point;
import java.text.ParseException;

import org.jdom.Element;

import tools.Bruch;
import tools.Etc;
import tools.MyMath;
import tools.Wurzel;

/**
 * Verwaltet Vektoren im Sinne der Linaren Algebra. Stellt Methoden zur
 * Erzeugung zur Verfügung, darüberhinaus Methoden um sie mit anderen Vektoren
 * zu Vergleichen (Kollinearitaet, Komplanaritaet, Winkel, Abstand). Zusaetzlich
 * sind Methoden zur Verknuepfung mit anderen Vektoren vorhanden: Addieren,
 * Subtrahieren, S-Multiplikation, Skalarprodukt, Kreuzprodukt.
 * 
 * @author reiner
 *
 */
public class LAVector extends GeoObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final String eckeLO = "\u250C";
	private final String eckeLU = "\u2514";
	private final String eckeRO = "\u2510";
	private final String eckeRU = "\u2518";
	private final String vLine = "\u2502";
	public final static String PFEIL = "\u0362";
	public final static boolean GRAD = true;
	public final static boolean RAD = false;

	public final String XMLROOT = "Vektor";

	public static LAVector noVector = new LAVector();

	/**
	 * Erzeugt einen Nullvektor mit einer waehlbaren Anzahl von Komponenten
	 * 
	 * @param dim
	 *            die Anzahl der Komponenten des Vektors (Dimension)
	 */
	public LAVector(int dim) {
		this.setiD(Etc.timestamp());
		for (int i = 0; i < dim; i++)
			this.add(i, new Bruch(0));
	}

	/**
	 * Erzeugt einen dreidimensionalen Vektor aus drei Bruchzahlen
	 * 
	 * @param v1
	 *            die erste Komponente
	 * @param v2
	 *            die zweite Komponente
	 * @param v3
	 *            die dritte Komponente
	 */
	public LAVector(Bruch v1, Bruch v2, Bruch v3) {
		this.setiD(Etc.timestamp());
		this.addElement(v1);
		this.addElement(v2);
		this.addElement(v3);
	}

	/**
	 * Erzeugt einen dreidimensionalen Vektor aus drei Bruchzahlen und benennt
	 * ihn
	 * 
	 * @param name
	 *            der Name des Vektors
	 * @param v1
	 *            die erste Komponente
	 * @param v2
	 *            die zweite Komponente
	 * @param v3
	 *            die dritte Komponente
	 */
	public LAVector(String name, Bruch v1, Bruch v2, Bruch v3) {
		this.name = name;
		this.setiD(Etc.timestamp());
		this.addElement(v1);
		this.addElement(v2);
		this.addElement(v3);
	}
	
	public LAVector(LAVector v) {
		this(v.getName()+"_1", v.get(0),v.get(1),v.get(2));
	}

	/**
	 * Erzeugt einen dreidimensionalen leeren Vektor, d.h. alle Komponenten sind
	 * null
	 */
	public LAVector() {
		// this.level = GeoObject.VEKTOR;
		this.setiD(Etc.timestamp());
		for (int i = 0; i < 3; i++)
			this.addElement(null);
	}

	/**
	 * Erzeugt einen dreidimensionalen Vektor aus zwei Bruchzahlen
	 * 
	 * @param v1
	 *            die erste Komponente
	 * @param v2
	 *            die zweite Komponente
	 */
	public LAVector(Bruch v1, Bruch v2) {
		// this.level = GeoObject.VEKTOR;
		this.setiD(Etc.timestamp());
		this.addElement(v1);
		this.addElement(v2);
	}

	/**
	 * Erzeugt einen dreidimensionalen Vektor aus drei Integerzahlen
	 * 
	 * @param v1
	 *            die erste Komponente
	 * @param v2
	 *            die zweite Komponente
	 * @param v3
	 *            die dritte Komponente
	 */
	public LAVector(int v1, int v2, int v3) {
		this.setiD(Etc.timestamp());
		// this.level = GeoObject.VEKTOR;
		this.addElement(new Bruch(v1));
		this.addElement(new Bruch(v2));
		this.addElement(new Bruch(v3));
	}

	/**
	 * Erzeugt einen dreidimensionalen Vektor aus drei Integerzahlen und benennt
	 * ihn
	 * 
	 * @param name
	 *            der Name des Vektors
	 * @param v1
	 *            die erste Komponente
	 * @param v2
	 *            die zweite Komponente
	 * @param v3
	 *            die dritte Komponente
	 */
	public LAVector(String name, int v1, int v2, int v3) {
		this.setName(name);
		this.setiD(Etc.timestamp());
		// this.level = GeoObject.VEKTOR;
		this.addElement(new Bruch(v1));
		this.addElement(new Bruch(v2));
		this.addElement(new Bruch(v3));
	}

	/**
	 * Erzeugt einen dreidimensionalen Vektor aus drei Doublezahlen
	 * 
	 * @param v1
	 *            die erste Komponente
	 * @param v2
	 *            die zweite Komponente
	 * @param v3
	 *            die dritte Komponente
	 */
	public LAVector(double v1, double v2, double v3) {
		// this.level = GeoObject.VEKTOR;
		this.setiD(Etc.timestamp());
		this.addElement(new Bruch(v1));
		this.addElement(new Bruch(v2));
		this.addElement(new Bruch(v3));
	}

	/**
	 * Erzeugt einen dreidimensionalen Vektor aus drei Doublezahlen und benennt
	 * ihn
	 * 
	 * @param name
	 *            der Name des Vektors
	 * @param v1
	 *            die erste Komponente
	 * @param v2
	 *            die zweite Komponente
	 * @param v3
	 *            die dritte Komponente
	 */
	public LAVector(String name, double v1, double v2, double v3) {
		this.setName(name);
		this.setiD(Etc.timestamp());
		// this.level = GeoObject.VEKTOR;
		this.addElement(new Bruch(v1));
		this.addElement(new Bruch(v2));
		this.addElement(new Bruch(v3));
	}

	/**
	 * Erzeugt einen dreidimensionalen Vektor aus drei Strings
	 * 
	 * @param v1
	 *            die erste Komponente
	 * @param v2
	 *            die zweite Komponente
	 * @param v3
	 *            die dritte Komponente
	 * @throws ParseException
	 *             wird geworfen, wenn die Strings keine gültigen Zahlstrings
	 *             darstellen
	 */
	public LAVector(String v1, String v2, String v3) throws ParseException {
		// this.level = GeoObject.VEKTOR;
		this.setiD(Etc.timestamp());
		this.addElement(new Bruch(v1));
		this.addElement(new Bruch(v2));
		this.addElement(new Bruch(v3));
	}
	/**
	 * Erzeugt einen dreidimensionalen Vektor aus drei Strings und benennt ihn
	 * 
	 * @param name der Name des Vektors
	 * @param v1
	 *            die erste Komponente
	 * @param v2
	 *            die zweite Komponente
	 * @param v3
	 *            die dritte Komponente
	 * @throws ParseException
	 *             wird geworfen, wenn die Strings keine gültigen Zahlstrings
	 *             darstellen
	 */
	public LAVector(String name, String v1, String v2, String v3) throws ParseException {
		this.setName(name);
		this.setiD(Etc.timestamp());
		// this.level = GeoObject.VEKTOR;
		this.addElement(new Bruch(v1));
		this.addElement(new Bruch(v2));
		this.addElement(new Bruch(v3));
	}

	/**
	 * Erzeugt einen LAVektor aus einem xml-Baum
	 * @param root das Wurzelelement des xml-Baums
	 */
	public LAVector(Element root) {
		this.setName(root.getChildText(XMLName));
		this.setiD(Etc.timestamp());
		for (int i = 0; i < 3; i++) {
			try {
				this.addElement(new Bruch(root.getChildText("x" + i)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (root.getChild("Position") != null)
			this.setPos(new Point(MyMath.toInt(root.getChild("Position").getChildText("x")),
					MyMath.toInt(root.getChild("Position").getChildText("y"))));
	}

	/**
	 * Gibt den Summenvektor aus der Summe mit dem LAVektor v zurueck
	 * @param v der Vektor, der addiert werden soll
	 * @return der Summenvektor
	 */
	public LAVector add(LAVector v) {
		LAVector out = new LAVector(v.size());
		for (int i = 0; i < v.size(); i++)
			out.set(i, this.get(i).add(v.get(i)));
		return out;
	}
	/**
	 * Gibt den Differenzvektor aus der Differenz mit dem LAVektor v zurueck
	 * @param v der Vektor, der subtrahiert  werden soll
	 * @return der Differenzvektor
	 */
	public LAVector sub(LAVector v) {
		return this.add(v.getGegenVektor());
	}

	/**
	 * Gibt den Abstand des Punktes auf den der Vektor  (this) zeigt zu dem Punkt auf den uebergebene Vektor v zeigt zurueck 
	 * @param v der uebergebene Vektor
	 * @return der Abstand der Punkte, auf die ide Vektoren zeigen
	 */
	public Wurzel getAbstand(LAVector v) {
		return this.sub(v).length();
	}

	/**
	 * Gibt den Gegenvektor zurueck
	 * @return der Gegenvektor
	 */
	public LAVector getGegenVektor() {
		LAVector v = new LAVector(this.size());
		for (int i = 0; i < v.size(); i++)
			v.set(i, this.get(i).mult(-1));
		return v;
	}

	public static LAVector nullVektor(int dim) {
		LAVector out = new LAVector(dim);
		for (int i = 0; i < dim; i++)
			out.set(i, 0);
		out.setName("0");
		return out;
	}

	public LAVector sMult(Bruch faktor) {
		LAVector out = new LAVector(this.size());
		for (int i = 0; i < this.size(); i++) {
			out.set(i, this.get(i).mult(faktor));
		}
		return out;
	}

	public LAVector sMult(int faktor) {
		LAVector out = new LAVector(this.size());
		for (int i = 0; i < this.size(); i++) {
			out.set(i, this.get(i).mult(faktor));
		}
		return out;
	}

	public LAVector einheitsVektor() {
		return this.sMult(1 / this.length().toDouble());
	}

	public LAVector sMult(double faktor) {
		faktor = MyMath.round(faktor, 4);
		LAVector out = new LAVector(this.size());
		for (int i = 0; i < this.size(); i++) {
			out.set(i, this.get(i).mult(new Bruch(faktor)));
		}
		return out;
	}

	public Bruch skalarProdukt(LAVector v) {
		Bruch out = new Bruch(0);
		for (int i = 0; i < this.size(); i++) {
			out = out.add(this.get(i).mult(v.get(i)));
		}
		return out;
	}

	public LAVector kreuzProdukt(LAVector v) {
		LAVector out = new LAVector(3);
		out.set(0, (this.get(1).mult(v.get(2)).sub(this.get(2).mult(v.get(1)))));
		out.set(1, (this.get(2).mult(v.get(0)).sub(this.get(0).mult(v.get(2)))));
		out.set(2, (this.get(0).mult(v.get(1)).sub(this.get(1).mult(v.get(0)))));
		out = out.toGanzzahl();
		return out;
	}

	public int containsZero() {
		for (int i = 0; i < this.size(); i++) {
			if (this.get(i).isZero())
				return i;
		}
		return -1;
	}

	public Wurzel length() {
		Bruch tmp = new Bruch(0);
		for (int i = 0; i < this.size(); i++) {
			tmp = tmp.add(this.get(i).pow(2));
		}
		return new Wurzel(tmp);
	}

	public boolean equals(LAVector v) {
		if (this.size() != v.size())
			return false;
		if (this.isNoVector() && v.isNoVector()) return true;
		if (this.isNoVector() || v.isNoVector()) return false;
		for (int i = 0; i < this.size(); i++) {
			if (!this.get(i).equals(v.get(i)))
				return false;
		}
		return true;
	}
	
	private boolean isNoVector() {
		boolean out = true;
		for (int i=0; i<this.size(); i++) {
			if (this.get(i)!=null) out = false;
		}
		return out;
	}

	public double winkel(LAVector v, boolean grad) {
		double rad = Math
				.acos(Math.abs(this.skalarProdukt(v).toDouble() / (this.length().toDouble() * v.length().toDouble())));
		if (!grad)
			return MyMath.round(rad, 4);
		return MyMath.toGrad(rad, 4);
	}

	private int getFirstNonZero(LAVector v) {
		for (int i = 0; i < this.size(); i++) {
			if (!this.get(i).isZero())
				return i;
		}
		return -1;
	}

	public LAVector kuerze() {
		LAVector out = new LAVector(this.size());
		for (int i = 0; i < this.size(); i++) {
			out.set(i, this.get(i).kuerze());
		}
		return out;
	}

	public boolean isKollinear(LAVector v) {
		Bruch faktor;
		int n1 = this.getFirstNonZero(this);
		int n2 = this.getFirstNonZero(v);
		if (n1 != n2)
			return false;
		if (n1 == -1)
			return false;
		faktor = this.get(n1).div(v.get(n1));
		for (int i = 0; i < 3; i++) {
			if (!this.get(i).equals((v.get(i).mult(faktor))))
				return false;
		}
		return true;
	}

	public boolean isNullVector() {
		for (int i=0;i<this.size(); i++) {
			if(!this.get(i).equals(0)) return false;
		}
		return true;
	}
	
	public boolean isKomplanar(LAVector a, LAVector b) {
		LAVector[] v = new LAVector[3];
		v[0] = this;
		v[1] = a;
		v[2] = b;
		Matrix m = new Matrix(v);
		if (m.toDiagonal().isNullZeile(2))
			return true;
		return false;
	}

	public LAVector toGanzzahl() {
		// System.out.println(this.getClass().getName() + ": \n" + this);
		int faktor = MyMath.kgv(MyMath.kgv(this.get(0).getNenner(), this.get(1).getNenner()), this.get(2).getNenner());
		// System.out.println(this.getClass().getName() + ": " + faktor);
		LAVector out = new LAVector(this.get(0).getZaehler() * faktor / this.get(0).getNenner(),
				this.get(1).getZaehler() * faktor / this.get(1).getNenner(),
				this.get(2).getZaehler() * faktor / this.get(2).getNenner());
		/*
		 * System.out.println(this.getClass().getName()+": \n"+out); faktor =
		 * MyMath.ggt(this.get(0).getZaehler(),
		 * MyMath.ggt(this.get(1).getZaehler(), this.get(2).getZaehler())); if
		 * (faktor != 0) { this.set(0, new Bruch(this.get(0).getZaehler() /
		 * faktor)); this.set(1, new Bruch(this.get(1).getZaehler() / faktor));
		 * this.set(2, new Bruch(this.get(2).getZaehler() / faktor)); }
		 */
		// System.out.println(this.getClass().getName() + ": \n" + out);
		return out.toMinGanzzahl();
	}

	private int countMinus(LAVector v) {
		int out = 0;
		for (int i = 0; i < this.size(); i++) {
			if (v.get(i).lt(0))
				out++;
		}
		return out;
	}

	private LAVector toMinGanzzahl() {
		for (int i = 0; i < this.size(); i++) {
			if (!this.get(i).isGanzzahl()) {
				this.toGanzzahl();
				break;
			}
		}
		int faktor = MyMath.ggt(MyMath.ggt(this.get(0).getZaehler(), this.get(1).getZaehler()),
				this.get(2).getZaehler());
		if (faktor == 0)
			faktor = 1;
		LAVector out = new LAVector(this.get(0).getZaehler() / faktor, this.get(1).getZaehler() / faktor,
				this.get(2).getZaehler() / faktor);
		if (countMinus(out) > 1)
			return out.getGegenVektor();
		return out;
	}

	private int width(boolean bruch) {
		int out = 0;
		if (bruch) {
			for (int i = 0; i < this.size(); i++) {
				String s = "" + this.get(i);
				if (s.length() > out)
					out = s.length();
			}
		} else {
			int breite = 0;
			for (int i = 0; i < this.size(); i++) {
				if (this.get(i).toDouble() < 10) {
					breite = 1;
				} else {
					breite = (int) MyMath.round(Math.log10(this.get(i).toDouble()), 0);
				}
				if (breite > out)
					out = breite + 5;
			}
		}
		return out;
	}

	private String zeile(int i, boolean bruch) {
		String out = "";
		if (bruch) {
			if (i != -1)
				out = "" + this.get(i);
			while (out.length() < width(bruch)) {
				out = " " + out;
			}
		} else {
			if (i == -1) {
				for (int j = 0; j < width(false); j++) {
					out = out + " ";
				}
				return out;
			}
			double zahl = MyMath.round(this.get(i).toDouble(), 4);
			int part[] = new int[2];
			part[0] = (int) zahl;
			part[1] = (int) ((zahl - part[0]) * 10000);
			String parts[] = new String[2];
			parts[0] = part[0] + "";
			parts[1] = part[1] + "";
			while (parts[1].length() < 4)
				parts[1] = parts[1] + "0";
			while (parts[0].length() < width(bruch) - 4) {
				parts[0] = " " + parts[0];
			}
			out = parts[0] + "." + parts[1];
		}
		return out;
	}

	public String[] toStringArray() {
		String[] out = new String[this.size() + 2];
		if (this != noVector) {
			out[0] = eckeLO + zeile(-1, true) + eckeRO;
			for (int i = 0; i < this.size(); i++) {
				out[i + 1] = vLine + zeile(i, true) + vLine;
			}
			out[this.size() + 1] = eckeLU + zeile(-1, true) + eckeRU;
		} else {
			out[0] = eckeLO + eckeRO;
			for (int i = 0; i < this.size(); i++) {
				out[i + 1] = vLine + vLine;
			}
			out[this.size() + 1] = eckeLU + eckeRU;
		}

		return out;
	}

	public String toString() {
		String[] lines = toStringArray();
		String out = "";
		for (int i = 0; i < lines.length - 1; i++)
			out = out + lines[i] + "\n";
		out = out + lines[lines.length - 1];

		return out;
	}

	public String toDoubleString() {
		String out = eckeLO + zeile(-1, false) + " " + eckeRO + "\n";
		for (int i = 0; i < this.size(); i++) {
			out = out + vLine + zeile(i, false) + vLine + "\n";
		}
		out = out + eckeLU + zeile(-1, false) + " " + eckeRU;
		return out;
	}

	public String toPointString(boolean asBruch) {
		String trenner = "|";
		String out = "(";
		if (asBruch) {
			for (int i = 0; i < this.size(); i++) {
				out = out + this.get(i);
				if (i < 2)
					out = out + trenner;
			}
			out = out + ")";
		} else {
			for (int i = 0; i < this.size(); i++) {
				out = out + this.get(i).toDouble();
				if (i < 2)
					out = out + trenner;
			}
			out = out + ")";
		}
		return out;
	}

	public static String[] vSymbol(char c) {
		String out[] = new String[5];
		for (int i = 0; i < out.length; i++) {
			out[i] = " ";
		}
		out[(out.length / 2) - 1] = PFEIL + " ";
		out[out.length / 2] = c + "";
		return out;
	}

	public boolean isPartOf(GeoObject g) {
		if (g instanceof Gerade) {
			Gerade gerade = (Gerade) g;
			//System.out.println(gerade.getAbstand(this));
			if (gerade.getAbstand(this).equals(new Bruch(0)))
				return true;
			else
				return false;
		}
		if (g instanceof Ebene) {
			Ebene e = (Ebene) g;
			if (e.getAbstand(this).equals(new Bruch(0)))
				return true;
		}
		return false;
	}

	public void set(int index, int value) {
		this.set(index, new Bruch(value));
	}

	public void set(int index, String value) throws ParseException {
		this.set(index, new Bruch(value));
	}

	@Override
	public GeoObject cut(GeoObject gObj) {
		if (gObj instanceof LAVector) {
			if (((LAVector) gObj).equals(this))
				return this;
		} else {
			if (this.isPartOf(gObj))
				return this;
		}
		return noVector;
	}

	@Override
	public GeoObject join(GeoObject g) {
		if (g instanceof LAVector)
			return new Gerade("g", this, (LAVector) g, false);
		if (g instanceof Gerade) {
			Gerade gr = (Gerade) g;
			if (this.cut(gr).equals(this))
				return gr;
			else
				return new Ebene("e", gr, this, false);
		}
		return this;
	}

	@Override
	public Element xml() {
		Element root = super.xml(XMLROOT);
		for (int i = 0; i < this.size(); i++) {
			Element e = new Element("x" + i);
			e.addContent(this.get(i).toString());
			root.addContent(e);
		}
		return root;
	}

	@Override
	public GeoObject clone() {
		LAVector out = new LAVector(this.size());
		for (int i = 0; i < this.size(); i++) {
			out.set(i, this.get(i));
		}
		return out;
	}

	public int getLevel() {
		return VEKTOR;
	}

}
