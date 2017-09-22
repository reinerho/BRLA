package geometrie;

import java.awt.Point;

import org.jdom.Element;

import exeptions.RVKollinearException;
import tools.*;
import geometrie.Ebene;

/**
 * Stellt eine Gerade in Parameterform dar, bestehend aus Ortsvektor und
 * Richtungsvektor. Die Klasse stellt Methoden zur Verfuegung, die Gerade aus
 * zwei Vektoren zu erzeugen, die entweder zwei Ortsvektoren oder Orts- und
 * Richtungsvektor sein koennen. <br>
 * Zusaetzlich werden Methoden bereitgestellt, die Informationen in einen
 * xml-Baum zu schreiben und aus dem entsprechenden Zweig eines xml-Baums die
 * Gerade wieder auszulesen. <br>
 * Dazu kommen Methoden, die die Lagebeziehungen zu anderen Geometrieobjekten
 * (GeoObject) zu bestimmen: zu anderen Geraden, zu Ebenen und zu Punkten
 * 
 * @author reiner
 *
 */
public class Gerade extends GeoObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LAVector ortsVektor;
	private LAVector richtungsVektor;

	public static final int X1_ACHSE = 0;
	public static final int X2_ACHSE = 1;
	public static final int X3_ACHSE = 2;

	public final String XMLROOT = "Gerade";

	/**
	 * Erzeugt eine Gerade aus zwei LAVektoren. Wenn der Parameter
	 * isRichtungsvektor true ist, wird der zweite LAVektor als Richtungsvektor
	 * benutzt
	 * 
	 * @param name
	 *            der Name der Gerade
	 * @param a
	 *            der LAVektor Ortsvektor
	 * @param b
	 *            ein zweiter Ortsvektor oder ein Richtungsvektor
	 * @param isRichtungsVektor
	 *            legt fest, ob der zweite LAVektor Orts- oder Richtungsvektor
	 *            ist (true wenn Richtungsvektor)
	 */
	public Gerade(String name, LAVector a, LAVector b, boolean isRichtungsVektor) {
		this.setName(name);
		// this.level = GeoObject.GERADE;
		this.setiD(Etc.timestamp());
		this.setOrtsVektor(a);
		if (isRichtungsVektor)
			this.setRichtungsVektor(b.toGanzzahl());
		else
			this.setRichtungsVektor(a.sub(b).toGanzzahl());
	}
	
	/**
	 * Dupliziert die Gerade g
	 * @param g die Originalgerade
	 */
	public Gerade (Gerade g) {
		this(g.getName()+"_1",g.getOrtsVektor(),g.getRichtungsVektor(),true);
	}

	/**
	 * Liest eine Gerade aus einem xml-Baum ein
	 * 
	 * @param root
	 *            das root-Element des xml-Baums mit den Geradeninformationen
	 */
	public Gerade(Element root) {
		// this.level = GeoObject.GERADE;
		this.name = root.getChildText(XMLName);
		this.setiD(Etc.timestamp());
		this.setOrtsVektor(new LAVector(root.getChild("Ortsvektor").getChild("Vektor")));
		this.setRichtungsVektor(new LAVector(root.getChild("Richtungsvektor").getChild("Vektor")));
		this.setPos(new Point(MyMath.toInt(root.getChild("Position").getChildText("x")),
				MyMath.toInt(root.getChild("Position").getChildText("y"))));
	}

	/**
	 * Erzeugt eine "leere Gerade", die nur aus Nullvektoren besteht
	 * 
	 * @return eine leere Gerade aus Nullvektoren
	 */
	public static Gerade leerGerade() {
		return new Gerade("Null", new LAVector(3), new LAVector(3), true);
	}

	/**
	 * Erzeugt eine Gerade, die auf einer der Achsen liegt (0 fuer die x1-Achse,
	 * ...)
	 * 
	 * @param i
	 *            die Nummer der Achse (Start bei 0)
	 * @return die Gerade, die auf der Achse liegt
	 */
	public static Gerade getAchse(int i) {
		String name = "x" + (i + 1);
		LAVector r = LAVector.nullVektor(3);
		r.set(i, 1);
		return new Gerade(name, LAVector.nullVektor(3), r, true);
	}

	/**
	 * Ist wahr, wenn die uebergebene Gerade auf einer Koordinatenachse liegt
	 * 
	 * @param g
	 *            die zu untersuchende Gerade
	 * @return wahr, wenn es sich um eine Achse ahndelt
	 */
	public static boolean isAchse(Gerade g) {
		for (int i = 0; i < 3; i++) {
			if (g.equals(getAchse(i)))
				return true;
		}
		return false;
	}

	/**
	 * Vergleicht mit einer anderen Gerade auf Parallelitaet
	 * 
	 * @param g
	 *            die Referenzgerade
	 * @return true, wenn beide Geraden parallel sind
	 */
	public boolean isParallel(Gerade g) {
		LAVector v1 = this.getRichtungsVektor();
		LAVector v2 = g.getRichtungsVektor();
		return v1.isKollinear(v2);
	}

	/**
	 * Vergleicht mit einer anderen Gerade auf Identitaet
	 * 
	 * @param g
	 *            die Referenzgerade
	 * @return true, wenn beide Geraden identisch sind
	 */
	public boolean isIdent(Gerade g) {
		if (!isParallel(g))
			return false;
		if (this.getOrtsVektor().sub(g.getOrtsVektor()).isKollinear(this.getRichtungsVektor()))
			return true;
		return false;
	}
	
	public boolean equals (Gerade g) {
		return this.isIdent(g);
	}

	public boolean isLeerGerade() {
		return this.getRichtungsVektor().isNullVector();
	}
	
	/**
	 * Vergleicht mit einer anderen Gerade ob sie windschief sind
	 * 
	 * @param g
	 *            die Referenzgerade
	 * @return true, wenn die Geraden windschief sind
	 */
	public boolean isWindschief(Gerade g) {
		return this.getSchnittPunkt(g) == null;
	}

	/**
	 * Bestimmt den Schnittpunkt mit einer anderen Geraden
	 * 
	 * @param g
	 *            die Referenzgerade
	 * @return ein LAVektor zum Schnittpunkt, null, wenn es keinen Schnittpunkt
	 *         gibt
	 */
	public LAVector getSchnittPunkt(Gerade g) {
		LAVector[] v = new LAVector[3];
		v[0] = this.getRichtungsVektor();
		v[1] = g.getRichtungsVektor().getGegenVektor();
		v[2] = g.getOrtsVektor().sub(this.getOrtsVektor());
		Matrix m = new Matrix(v);
		m = m.toDiagonal();
		if (!m.getElement(2, 2).isZero())
			return null;
		Bruch faktor = m.getElement(0, 2);
		return this.getOrtsVektor().add(this.getRichtungsVektor().sMult(faktor));
	}

	/**
	 * Bestimmt den Schnittpunkt der Gerade mit einer Ebene e
	 * 
	 * @param e
	 *            die Ebene
	 * @return ein LAVector zum Schnittpunkt;
	 */
	public LAVector getSchnittPunkt(Ebene e) {
		return e.getSchnittPunkt(this);
	}

	/**
	 * Bestimmt den Schnittpunkt mit einer Koordinatenebene
	 * 
	 * @param k
	 *            die Nummer der Achse, die in dieser Ebene 0 ist (0 fuer
	 *            x1-Achse)
	 * @return der LAVektor zum Spurpunkt in der entsprechenden Ebene,ein leerer
	 *         LAVector, wenn es keinen Spurpunkt gibt
	 */
	public LAVector getSpurpunkt(int k) {
		LAVector out = new LAVector();
		out.setName("S" + (k + 1));
		if (k > 2 || k < 0)
			return out;
		if (this.getRichtungsVektor().get(k).equals(0)) {
			if (this.getOrtsVektor().get(k).equals(0) && LAVector.nullVektor(3).isPartOf(this)) {
				out = LAVector.nullVektor(3);
				out.setName("S" + (k + 1));
				return out;
			} else {
				out = LAVector.noVector;
				out.setName("S" + (k + 1));
				return out;
			}
		}
		Bruch faktor = this.getOrtsVektor().get(k).mult(-1).div(this.getRichtungsVektor().get(k));
		out = this.getOrtsVektor().add(this.getRichtungsVektor().sMult(faktor));
		out.setName("S" + (k + 1));
		return out;
	}

	/**
	 * Bestimmt den Winkel zwischen den Richtungsvektoren der gegebenen Gerade
	 * und einer Referenzgerade
	 * 
	 * @param g
	 *            die Referenzgerade
	 * @param digits
	 *            die Anzahl der Dezimalen, auf die der Winkel gerundet werden
	 *            soll
	 * @param grad
	 *            true, wenn der Winkel in Grad bestimmt werden soll, alternativ
	 *            wird er in Radiant bestimmt
	 * @return der Winkel im gewuenschten Masssystem uaf die gewuenschte
	 *         Dezimalenzahl gerundet
	 */
	public double Winkel(Gerade g, int digits, boolean grad) {
		double ang = this.getRichtungsVektor().winkel(g.getRichtungsVektor(), LAVector.RAD);
		if (grad)
			ang = MyMath.toGrad(ang);
		ang = MyMath.round(ang, digits);
		return ang;
	}

	/**
	 * Gibt den Abstand eines Punktes von der Geraden zurueck
	 * 
	 * @param punkt
	 *            der LAVektor zum Punkt
	 * @return der Abstand als Wurzel
	 */
	public Wurzel getAbstand(LAVector punkt) {
		Bruch dist1 = punkt.sub(this.getOrtsVektor()).length().toQuadrat();
		Bruch dist2 = punkt.sub(this.getOrtsVektor()).skalarProdukt(this.getRichtungsVektor()).pow(2)
				.div(this.getRichtungsVektor().length().toQuadrat());
		return new Wurzel(dist1.sub(dist2));
	}

	/**
	 * Gibt den Lotfusspunkt des Lots von einem gegebenen Punkt auf die Gerade
	 * zurueck
	 * 
	 * @param v
	 *            der LAVektor zum Punkt
	 * @return der LAVektor zum Lotfusspunkt
	 */
	public LAVector getLotFussPunkt(LAVector v) {
		Ebene e = new Ebene(Init.TMP, this.getRichtungsVektor(), this.getRichtungsVektor().skalarProdukt(v));
		return e.getSchnittPunkt(this);
	}

	/**
	 * Gibt den Spiegelpunkt eines gegebenen Punktes bei der Spiegelung an der
	 * Geraden zurueck
	 * 
	 * @param v
	 *            der LAVektor zum Punkt
	 * @return der LAVektor zum Spiegelpunkt
	 */
	public LAVector getSpiegelpunkt(LAVector v) {
		return v.sub(this.getLotFussPunkt(v)).sMult(2);
	}

	/**
	 * Gibt den Abstand der Geraden zum Ursprung zurueck
	 * 
	 * @return der Abstand der Geraden zum Ursprung als Wurzel
	 */
	public Wurzel getAbstandUrsprung() {
		return getAbstand(LAVector.nullVektor(3));
	}

	/**
	 * Gibt den Abstand zu einer anderen Geraden zurueck
	 * 
	 * @param g
	 *            die Referenzgerade
	 * @return der Abstand zur Referenzgerade als Wurzel
	 */
	public Wurzel getAbstand(Gerade g) {
		try {
			if (!isParallel(g)) {
				Ebene e = new Ebene("tmp", this.getOrtsVektor(), this.getRichtungsVektor(), g.getRichtungsVektor(), 2);
				return e.getAbstand(g);
			} else {
				Ebene e1 = new Ebene("tmp1", this.getOrtsVektor(), g.getOrtsVektor(), this.getRichtungsVektor(), 1);
				Ebene e2 = new Ebene("tmp2", this.getOrtsVektor(), this.getRichtungsVektor(), e1.getNormalenVektor(),
						2);
				return e2.getAbstand(g);
			}
		} catch (RVKollinearException e) {
			return new Wurzel(0);
		}
	}

	/**
	 * Gibt den Ortsvektor der Geraden zurueck
	 * 
	 * @return der Ortsvektor der Geraden
	 */
	public LAVector getOrtsVektor() {
		return ortsVektor;
	}

	/**
	 * Setzt den uebergegebenen LAVektor als Ortsvektor
	 * 
	 * @param ortsVektor
	 *            der neue Ortsvektor
	 */
	public void setOrtsVektor(LAVector ortsVektor) {
		this.ortsVektor = ortsVektor;
	}

	/**
	 * Gibt denRichtungssvektor der Geraden zurueck
	 * 
	 * @return der Richtungsvektor der Geraden
	 */
	public LAVector getRichtungsVektor() {
		return richtungsVektor;
	}

	/**
	 * Setzt den uebergegebenen LAVektor als Richtungsvektor
	 * 
	 * @param richtungsVektor
	 *            der neue Richtungsvektor
	 */
	public void setRichtungsVektor(LAVector richtungsVektor) {
		this.richtungsVektor = richtungsVektor;
	}

	/**
	 * Gibt einen Array of String zurueck, u.a der von der toString-Methode
	 * benoetigt wird
	 */
	public String[] toStringArray() {
		String[] out = new String[5];
		String[] ov = this.getOrtsVektor().toStringArray();
		String[] rv = this.getRichtungsVektor().toStringArray();
		String[] g = textArray(this.getName() + ": ", this.getOrtsVektor().size() + 2);
		String[] k = textArray(" + k", this.getOrtsVektor().size() + 2);
		String[] x = LAVector.vSymbol('x');
		String[] gleich = textArray(" = ", this.getOrtsVektor().size() + 2);
		for (int i = 0; i < this.getOrtsVektor().size() + 2; i++) {
			out[i] = g[i] + x[i] + gleich[i] + ov[i] + k[i] + rv[i];
		}
		return out;
	}

	public String toString() {
		String out = "";
		String[] comp = this.toStringArray();
		for (int i = 0; i < 5; i++) {
			out = out + comp[i] + "\n";
		}
		return out;
	}

	@Override
	public GeoObject cut(GeoObject g) {
		if (g instanceof LAVector)
			return ((LAVector) g).cut(this);
		if (g instanceof Gerade) {
			Gerade gr = (Gerade) g;
			if (this.equals(gr))
				return this;
			if (this.isParallel(gr) || this.isWindschief(gr))
				return LAVector.noVector;
			return this.getSchnittPunkt(gr);
		}
		return LAVector.noVector;
	}

	@Override
	public GeoObject join(GeoObject go) throws RVKollinearException {
		if (go instanceof LAVector)
			return ((LAVector) go).join(this);
		if (go instanceof Gerade) {
			Gerade g = (Gerade) go;
			if (this.isWindschief(g) || this.equals(g))
				return this;
			if (this.isParallel(g))
				return new Ebene("e", this, this.ortsVektor.sub(g.getOrtsVektor()), true);
			return new Ebene("e", this.ortsVektor, this.richtungsVektor, g.getRichtungsVektor(), 2);
		}
		return this;
	}

	@Override
	public boolean isPartOf(GeoObject g) {
		if (g instanceof Gerade) { // Geraden muessen identisch sein
			Gerade gr = (Gerade) g;
			if (this.equals(gr))
				return true;
		}
		if (g instanceof Ebene) {
			Ebene e = (Ebene) g;
			if (e.isParallel(this) && this.getOrtsVektor().isPartOf(e))
				return true;
		}
		return false;
	}

	@Override
	public Element xml() {
		Element root = super.xml(XMLROOT);
		root.addContent(this.vektorElement("Ortsvektor", this.getOrtsVektor()));
		root.addContent(this.vektorElement("Richtungsvektor", this.getRichtungsVektor()));
		return root;
	}

	@Override
	public Gerade clone() {
		return new Gerade(this.getName(), this.getOrtsVektor(), this.getRichtungsVektor(), true);
	}

	public int getLevel() {
		return GERADE;
	}

}
