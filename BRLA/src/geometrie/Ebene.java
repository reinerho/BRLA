package geometrie;

import java.awt.Point;
import java.util.concurrent.ThreadLocalRandom;

import org.jdom.Element;

import exeptions.RVKollinearException;
import tools.Bruch;
import tools.Etc;
import tools.MyMath;
import tools.Wurzel;

public class Ebene extends GeoObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LAVector ortsVektor;
	private LAVector richtungsVektor1;
	private LAVector richtungsVektor2;
	public final String XMLROOT = "Ebene";

	/**
	 * Legt eine Ebene aus drei Vektoren an. richtungsZahl gibt an, wieviele der
	 * angegebenen Vektoren als Richtungsvektoren anzusehen sind (0-2)
	 * 
	 * @param name
	 *            der Name der Ebene
	 * @param ov
	 *            der Ortsvektor
	 * @param rv1
	 *            Orts-/oder Richtungsvektor
	 * @param rv2
	 *            Orts-/oder Richtungsvektor
	 * @param richtungsZahl
	 *            Anzahl der Vektoren die als Richtungsvektoren übergegeben
	 *            werden (in der Reihenfolge der Übergabe)
	 * @throws RVKollinearException
	 *             wirft eine Exception, wenn die Richtungsvektoren kollinear
	 *             sind (und damit keine Ebene erzeugt werden kann)
	 */
	public Ebene(String name, LAVector ov, LAVector rv1, LAVector rv2, int richtungsZahl) throws RVKollinearException {
		// this.level = GeoObject.EBENE;
		this.setOrtsVektor(ov);
		this.setiD(Etc.timestamp());
		this.setName(name);
		switch (richtungsZahl) {
		case 2:
			this.setRichtungsVektor1(rv1);
			this.setRichtungsVektor2(rv2);
			break;
		case 1:
			this.setRichtungsVektor2(rv2);
			this.setRichtungsVektor1(ov.sub(rv1));
			break;
		default:
			this.setRichtungsVektor1(ov.sub(rv1));
			this.setRichtungsVektor2(ov.sub(rv2));
		}
		if (this.getRichtungsVektor1().isKollinear(this.getRichtungsVektor2()))
			throw new RVKollinearException();
	}
	
	
	/**
	 * Legt ein Duplikat einer Ebene an 
	 * @param eo die Originalebene
	 * @throws RVKollinearException
	 */
	public Ebene(Ebene eo) throws RVKollinearException {
		this(eo.getName()+"_1",eo.getOrtsVektor(),eo.getRichtungsVektor1(),eo.getRichtungsVektor2(),2);
	}

	/**
	 * Legt eine Ebene aus den Informationen einer xml-Datei an
	 * 
	 * @param root
	 *            das root-Element der xml-Datei
	 */
	public Ebene(Element root) {
		// this.level = GeoObject.EBENE;
		this.name = root.getChildText(XMLName);
		this.setiD(Etc.timestamp());
		this.setOrtsVektor(new LAVector(root.getChild("Ortsvektor").getChild("Vektor")));
		this.setRichtungsVektor1(new LAVector(root.getChild("Richtungsvektor1").getChild("Vektor")));
		this.setRichtungsVektor2(new LAVector(root.getChild("Richtungsvektor2").getChild("Vektor")));
		this.setPos(new Point(MyMath.toInt(root.getChild("Position").getChildText("x")),
				MyMath.toInt(root.getChild("Position").getChildText("y"))));
	}

	/**
	 * Legt eine Ebene aus vier Bruchzahlen an. Die ersten drei stehen für die
	 * Koorniaten des Normalenvektors, die vierte ist die Konstante in der
	 * Normalenform
	 * 
	 * @param name
	 *            Name der Ebene
	 * @param x
	 *            x-Koordinate des Normalenvektors
	 * @param y
	 *            y--Koordinate des Normalenvektors
	 * @param z
	 *            z-Koordinate des Normalenvektors
	 * @param k
	 *            die Konstante der Normalenform
	 */
	public Ebene(String name, Bruch x, Bruch y, Bruch z, Bruch k) {
		this.setName(name);
		this.setiD(Etc.timestamp());
		// this.level = GeoObject.EBENE;
		LAVector normale = new LAVector(x, y, z);
		new Ebene(name, normale, k);
	}

	/**
	 * Legt eine Ebene aus einer Geraden und einem Vektor an.
	 * 
	 * @param name
	 *            Name der Ebene
	 * @param g
	 *            die Gerade
	 * @param v
	 *            der Vektor
	 * @param isRichtungsvektor
	 *            legt fest, ob der Vektor ein Richtungsvektor der Ebene ist
	 *            (true) oder auf einen Punkt der Ebene zeigt (false)
	 */
	public Ebene(String name, Gerade g, LAVector v, boolean isRichtungsvektor) {
		// this.level = GeoObject.EBENE;
		this.setOrtsVektor(g.getOrtsVektor());
		this.setName(name);
		this.setiD(Etc.timestamp());
		this.setRichtungsVektor1(g.getRichtungsVektor());
		if (isRichtungsvektor)
			this.setRichtungsVektor2(v);
		else
			this.setRichtungsVektor2(this.getOrtsVektor().sub(v));
	}

	/**
	 * Legt eine Ebene aus einem (Normalen-)Vektor und einer Konstanten an
	 * 
	 * @param name
	 *            Name der Ebene
	 * @param normale
	 *            der Normalenvektor
	 * @param b
	 *            die Konstante
	 */
	public Ebene(String name, LAVector normale, Bruch b) {
		// this.level = GeoObject.EBENE;
		this.setName(name);
		this.setiD(Etc.timestamp());
		this.generateRichtung(normale);
		this.ortsVektor = getBestOrtsVektor(normale, b);
		/*
		 * if (normale.get(0).gt(0)) this.ortsVektor.set(0,
		 * b.div(normale.get(0))); else { if (normale.get(1).gt(0))
		 * this.ortsVektor.set(1, b.div(normale.get(1))); else {
		 * this.ortsVektor.set(2, b.div(normale.get(2))); } }
		 */
	}

	/**
	 * Ermittelt den besten Ortsvektor, der ohne Aufwand zu finden ist.
	 * 
	 * @param normale
	 *            Der Normalenvektor
	 * @param b
	 *            Die Konstante in der Normalenform
	 * @return der Ortsvektor
	 */
	private LAVector getBestOrtsVektor(LAVector normale, Bruch b) {
		if (b.isZero())
			return LAVector.nullVektor(3);
		LAVector out = LAVector.nullVektor(3);
		Bruch[] bruch = new Bruch[3];
		for (int i = 0; i < 3; i++) {
			if (!normale.get(i).isZero()) {
				bruch[i] = normale.get(i).div(b);
				if (bruch[i].isGanzzahl()) {
					out.set(i, bruch[i]);
					return out;
				}
			}
		}
		int[] zahl = new int[3];
		for (int i = 0; i < 3; i++) {
			if (bruch[i]!=null)
			zahl[i] = bruch[i].getZaehler() + bruch[i].getNenner();else zahl[i]=9999999;
		}
		int i = 0;
		if (zahl[1] < zahl[i])
			i = 1;
		if (zahl[2] < zahl[i])
			i = 2;
		out.set(i, bruch[i]);
		return out;
	}

	/**
	 * Erzeugt zwei Richtungsvektoren aus einem Normalenvektor
	 * 
	 * @param normalenvektor
	 */
	private void generateRichtung(LAVector normalenvektor) {
		if (normalenvektor.size() != 3)
			return;
		this.richtungsVektor1 = new LAVector(normalenvektor.size());
		this.richtungsVektor2 = new LAVector(normalenvektor.size());
		int zero = normalenvektor.containsZero();
		if (zero > -1) {
			this.richtungsVektor1.set(zero, new Bruch(1));
			switch (zero) {
			case 0:
				richtungsVektor2.set(1, normalenvektor.get(2).mult(-1));
				richtungsVektor2.set(2, normalenvektor.get(1));
				break;
			case 1:
				richtungsVektor2.set(0, normalenvektor.get(2).mult(-1));
				richtungsVektor2.set(2, normalenvektor.get(0));
				break;
			default:
				richtungsVektor2.set(0, normalenvektor.get(1).mult(-1));
				richtungsVektor2.set(1, normalenvektor.get(0));
			}
			return;
		} else {
			this.richtungsVektor1.set(0, normalenvektor.get(1).mult(-1));
			this.richtungsVektor1.set(1, normalenvektor.get(0));
			this.richtungsVektor1.set(2, 0);
			this.richtungsVektor2.set(1, normalenvektor.get(2).mult(-1));
			this.richtungsVektor2.set(2, normalenvektor.get(1));
			this.richtungsVektor2.set(0, 0);
		}
		this.richtungsVektor1 = this.richtungsVektor1.toGanzzahl();
		this.richtungsVektor2 = this.richtungsVektor2.toGanzzahl();
	}

	/**
	 * Bestimmt den kleinsten ganzzahligen Normalenvektor zur gegebenen Ebene
	 * 
	 * @return der Normalenvektor
	 */
	public LAVector getNormalenVektor() {
		LAVector out = this.getRichtungsVektor1().kreuzProdukt(this.getRichtungsVektor2());
		out = out.kuerze();
		out = out.toGanzzahl();
		return out;
	}

	/**
	 * bestimmt die Konstante zur Normalenform als Bruch
	 * 
	 * @return die Konstante
	 */
	public Bruch getNormalformKonst() {
		return this.getOrtsVektor().skalarProdukt(this.getNormalenVektor());
	}

	/**
	 * Bestimmt den Schnittpunkt der Ebene mit einer Geraden g
	 * 
	 * @param g
	 *            die Gerade
	 * @return der Schnittpunkt;
	 */
	public LAVector getSchnittPunkt(Gerade g) {
		Bruch s = this.getNormalformKonst().sub(this.getNormalenVektor().skalarProdukt(g.getOrtsVektor()));
		s = s.div(this.getNormalenVektor().skalarProdukt(g.getRichtungsVektor())).kuerze();
		return g.getOrtsVektor().add(g.getRichtungsVektor().sMult(s));
	}

	/**
	 * Gibt den Schnittpunkt mit einer Achse zurück. Dabei steht achse = 0 für
	 * die 1. Achse
	 * 
	 * @param achse
	 *            die Nummer der Achse mit der der Schnittpunkt gefunden werden
	 *            soll
	 * @return Schnittpunkt
	 */
	public LAVector getAchsenSchnittpunkt(int achse) {
		LAVector out = LAVector.nullVektor(3);
		if (this.getNormalenVektor().get(achse).equals(0))
			return LAVector.noVector;
		Bruch b = this.getNormalformKonst().div(this.getNormalenVektor().get(achse));
		out.set(achse, b);
		return out;
	}

	/**
	 * Bestimmt den Lotfußpunkt zu einem Punkt
	 * 
	 * @param v
	 *            der Ortsvektor des Punktes
	 * @return der Ortsvektor des Lotfußpunktes
	 */
	public LAVector getLotFussPunkt(LAVector v) {
		Gerade g = new Gerade("g", v, this.getNormalenVektor(), true);
		return this.getSchnittPunkt(g);
	}

	/**
	 * Bestimmt den Spiegelpunkt zu einem Punkt
	 * 
	 * @param v
	 *            der Ortsvektor des Punktes
	 * @return der Ortsvektor des Spiegelpunktes
	 */
	public LAVector getSpiegelpunkt(LAVector v) {
		return v.add(this.getLotFussPunkt(v).sub(v).sMult(2));
	}

	/**
	 * Bestimmt den Winkel zwischen einer Geraden und der Ebene
	 * 
	 * @param g
	 *            die Gerade
	 * @return der Winkel in Grad
	 */
	public double getWinkel(Gerade g) {
		double out = Math.abs(this.getNormalenVektor().winkel(g.getRichtungsVektor(), LAVector.GRAD));
		out = Math.abs(Math.PI /2 -out);
		out = MyMath.toGrad(out);
		return out;
	}

	/**
	 * Bestimmt den Winkel mir der Ebene e
	 * 
	 * @param e
	 *            die Ebene
	 * @return der Schnittwinkel in Grad
	 */
	public double getWinkel(Ebene e) {
		return this.getNormalenVektor().winkel(e.getNormalenVektor(), LAVector.GRAD);
		// return
		// Math.abs(this.getNormalenVektor().winkel(e.getNormalenVektor(),
		// LAVector.GRAD));
	}

	/**
	 * Bestimmt die Schnittgerade mit der Ebene e
	 * 
	 * @param e
	 *            die andere Ebene
	 * @return die Schnittgerade
	 */
	public Gerade getSchnittGerade(Ebene e) {
		// if (this.isParallel(e))return Gerade.leerGerade();
		// System.out.println(e.printKForm());
		LAVector[] v = new LAVector[2];
		v[0] = e.getNormalenVektor();
		v[1] = this.getNormalenVektor();
		Matrix m = new Matrix(v).transponiere();
		LAVector ev = new LAVector(e.getOrtsVektor().skalarProdukt(e.getNormalenVektor()),
				this.getOrtsVektor().skalarProdukt(this.getNormalenVektor()));
		// System.out.println(ev);
		m = m.erweitere(ev, true);
		// System.out.println(m);
		m = m.toDiagonal();
		// System.out.println(m);
		LAVector r = LAVector.nullVektor(3);
		LAVector o = LAVector.nullVektor(3);
		for (int i = 0; i < 3; i++) {
			if (m.getVector(i, Matrix.MATRIX_SPALTE).equals(LAVector.nullVektor(2))) {
				r.set(i, 1);
				switch (i) {
				case 0:
					o.set(1, m.getElement(0, 3));
					o.set(2, m.getElement(1, 3));
					o.kuerze();
					break;
				case 1:
					o.set(0, m.getElement(0, 3));
					o.set(2, m.getElement(1, 3));
					o.kuerze();
					break;
				case 2:
					o.set(0, m.getElement(0, 3));
					o.set(1, m.getElement(1, 3));
					o.kuerze();
				}
				return new Gerade("gs", o, r, true);
			}
		}
		if (m.getElement(1, 1).equals(0)) {
			o = new LAVector(m.getElement(0, 3), new Bruch(0), m.getElement(1, 3)).kuerze();
			r = new LAVector(m.getElement(0, 1).mult(-1), new Bruch(1), new Bruch(0)).toGanzzahl();
			// return new Gerade("Gs", o, r, true);
		} else {
			o = new LAVector(m.getElement(0, 3), m.getElement(1, 3), new Bruch(0)).kuerze();
			r = new LAVector(m.getElement(0, 2), m.getElement(1, 2), new Bruch(-1)).toGanzzahl();
		}
		//Gerade gr = new Gerade("gs", o, r, true);
		//System.out.println("Ebene: "+gr);
		return new Gerade("gs", o, r, true);
	}

	/**
	 * Bestimmt die Schnittgerade mit der Ebene e und benennt diese
	 * 
	 * @param e
	 *            die andere Ebene
	 * @param s
	 *            ein String mit dem Namen der Schnittgeraden
	 * @return die Schnittgerade
	 */
	public Gerade getSchnittGerade(Ebene e, String s) {
		Gerade g = this.getSchnittGerade(e);
		g.setName(s);
		return g;
	}

	/**
	 * * Gibt die Spurgerade in der Ebene zurück, in der die Koordinatenrichtung
	 * richtung Null wird
	 * 
	 * @param richtung
	 *            die Koordinatenrichtung, die Null wird (z.B. 0 für die
	 *            x2-x3-Ebene)
	 * @return die Spurgerade
	 */
	public Gerade getSpurgerade(int richtung) {

		Ebene e = Ebene.koordinatenEbene(richtung + 1);
		if (this.isParallel(e))
			return Gerade.leerGerade();
		return this.getSchnittGerade(e, "gs" + (richtung + 1));
	}

	/**
	 * Vergleicht mit der Ebene e
	 * 
	 * @param e
	 *            die andere Ebene
	 * @return true, wenn die Ebenen identisch sind
	 */
	public boolean equals(Ebene e) {
		return this.getRichtungsVektor1().isKomplanar(e.getRichtungsVektor1(), e.getRichtungsVektor2())
				&& this.getRichtungsVektor2().isKomplanar(e.getRichtungsVektor1(), e.getRichtungsVektor2())
				&& (this.getOrtsVektor().sub(e.getOrtsVektor())).isKomplanar(e.getRichtungsVektor1(),
						e.getRichtungsVektor2());
	}

	/**
	 * Überprüft ob die Ebene e parallel ist
	 * 
	 * @param e
	 *            die andere Ebene
	 * @return true, wenn parallel
	 */
	public boolean isParallel(Ebene e) {
		return (this.getNormalenVektor().isKollinear(e.getNormalenVektor()));
	}

	/**
	 * Überprüft ob die Gerade g parallel ist
	 * 
	 * @param g
	 *            die Gerade
	 * @return true, wenn parallel
	 */
	public boolean isParallel(Gerade g) {
		return (g.getRichtungsVektor().skalarProdukt(this.getNormalenVektor()).equals(new Bruch(0)));
	}

	/**
	 * Bestimmt den Abstand des Punkts P von der Ebene
	 * 
	 * @param p
	 *            der Ortsvektor des Punkts
	 * @return der Abstand
	 */
	public Wurzel getAbstand(LAVector p) {
		Wurzel ln = getNormalenVektor().length();
		Bruch d = this.getOrtsVektor().skalarProdukt(this.getNormalenVektor())
				.sub(p.skalarProdukt(this.getNormalenVektor())).abs();
		return ln.invers().mult(d);
	}

	/**
	 * Bestimmt den Abstand der Ebene e von der Ebene
	 * 
	 * @param e
	 *            die Referenzebene
	 * @return der Abstand
	 */
	public Wurzel getAbstand(Ebene e) {
		if (!isParallel(e))
			return new Wurzel(0);
		LAVector p = e.getOrtsVektor();
		Wurzel ln = getNormalenVektor().length();
		Bruch d = this.getOrtsVektor().skalarProdukt(this.getNormalenVektor())
				.sub(p.skalarProdukt(this.getNormalenVektor())).abs();
		return ln.invers().mult(d);
	}

	/**
	 * Bestimmt den Abstand der Gerade g von der Ebene
	 * 
	 * @param g
	 *            die Gerade
	 * @return der Abstand
	 */
	public Wurzel getAbstand(Gerade g) {
		if (!isParallel(g))
			return new Wurzel(0);
		LAVector p = g.getOrtsVektor();
		Wurzel ln = getNormalenVektor().length();
		Bruch d = this.getOrtsVektor().skalarProdukt(this.getNormalenVektor())
				.sub(p.skalarProdukt(this.getNormalenVektor())).abs();
		return ln.invers().mult(d);
	}

	/**
	 * Gibt den Ortsvektor zurück
	 * 
	 * @return der Ortsvektor
	 */
	public LAVector getOrtsVektor() {
		return ortsVektor;
	}

	/*
	 * public String getName() { return name; }
	 * 
	 * public void setName(String name) { this.name = name; }
	 */

	public void setOrtsVektor(LAVector ortsVektor) {
		this.ortsVektor = ortsVektor;
	}

	public LAVector getRichtungsVektor1() {
		return richtungsVektor1;
	}

	public void setRichtungsVektor1(LAVector richtungsVektor1) {
		this.richtungsVektor1 = richtungsVektor1;
	}

	public LAVector getRichtungsVektor2() {
		return richtungsVektor2;
	}

	public void setRichtungsVektor2(LAVector richtungsVektor2) {
		this.richtungsVektor2 = richtungsVektor2;
	}

	/**
	 * Ermittelt den Ortsvektor zu einem zufälligen Punkt auf der Ebene
	 * 
	 * @return der Ortsvektor zum Zufallspunkt
	 */
	public LAVector getRandomPoint() {
		int r = ThreadLocalRandom.current().nextInt(0, 6);
		int s = ThreadLocalRandom.current().nextInt(0, 6);
		return this.ortsVektor.add(this.richtungsVektor1.sMult(r).add(this.richtungsVektor2.sMult(s)));
	}

	/**
	 * Gibt einen String zurück, der den Bruch in der kürzest möglichen Form als
	 * Faktor darstellt: Wenn der Bruck Null ist, entfällt die Darstellung
	 * völlig, ist er 1 oder -1, wird nur das Vorzeichen dargestellt ansonsten
	 * wird der Bruch mit Vorzeichen (+ bzw. - ) dargestellt
	 * 
	 * @param b
	 *            der Bruch
	 * @return die aufbereitete Stringdarstellung des Bruchs
	 */
	private String getSummand(Bruch b) {
		if (b.equals(0))
			return "";
		if (b.equals(1))
			return "+";
		if (b.equals(-1))
			return "-";
		return MyMath.praedikat(b);
	}

	/**
	 * gibt eine Koordinatenebene zurück
	 * 
	 * @param k
	 *            die gewünschte Ebene z.B. in der Form 1 oder 32 oder 23 für
	 *            die x2-x3-Ebene
	 * @return die Koordinatenebene
	 */
	public static Ebene koordinatenEbene(int k) {
		try {
			switch (k) {
			case 1:
			case 32:
			case 23:
				return new Ebene("x2-x3", new LAVector(0, 0, 0), new LAVector(0, 1, 0), new LAVector(0, 0, 1), 2);
			case 2:
			case 13:
			case 31:
				return new Ebene("x1-x3", new LAVector(0, 0, 0), new LAVector(1, 0, 0), new LAVector(0, 0, 1), 2);
			default:
				return new Ebene("x1-x2", new LAVector(0, 0, 0), new LAVector(0, 1, 0), new LAVector(1, 0, 0), 2);
			}
		} catch (RVKollinearException e) {
			return null;
		}
	}

	/**
	 * gibt die Ebene als String in der Koordinatenform aus
	 * 
	 * @return die Ebene in Koordinatenform ax+by+cz=k
	 */
	public String toKoordinatenFormString() {
		String[] koor = { "x", "y", "z" };
		LAVector n = this.getNormalenVektor();
		Bruch k = n.skalarProdukt(this.ortsVektor);
		String out = "";
		for (int i = 0; i < 3; i++) {
			if (!getSummand(n.get(i)).isEmpty()) {
				out = out + getSummand(n.get(i)) + koor[i];
			}
		}
		out = out + " = " + k;
		if (out.substring(0, 1).equals("+"))
			out = out.substring(1);
		out = name + ": " + out;
		return out;
		// return n.get(0) + "x" + MyMath.praedikat(n.get(1)) + "y" +
		// MyMath.praedikat(n.get(2)) + "z = " + k + "\n";
	}

	public String[] toStringArray() {
		String[] out = new String[5];
		String[] ov = this.getOrtsVektor().toStringArray();
		String[] rv1 = this.getRichtungsVektor1().toStringArray();
		String[] rv2 = this.getRichtungsVektor2().toStringArray();
		String[] g = textArray("E: ", this.getOrtsVektor().size() + 2);
		String[] k = textArray(" + k", this.getOrtsVektor().size() + 2);
		String[] l = textArray(" + l", this.getOrtsVektor().size() + 2);
		String[] x = LAVector.vSymbol('x');
		String[] gleich = textArray(" = ", this.getOrtsVektor().size() + 2);
		for (int i = 0; i < this.getOrtsVektor().size() + 2; i++) {
			out[i] = g[i] + x[i] + gleich[i] + ov[i] + k[i] + rv1[i] + l[i] + rv2[i];
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
	public GeoObject cut(GeoObject gObj) {
		return null;
	}

	@Override
	public GeoObject join(GeoObject gObj) {
		return null;
	}

	// Eine Ebene kann nur Teil von sich selbst sein
	@Override
	public boolean isPartOf(GeoObject g) {
		if (g instanceof Ebene) {
			Ebene e = (Ebene) g;
			if (this.equals(e))
				return true;
		}
		return false;
	}

	@Override
	public Element xml() {
		Element root = super.xml(XMLROOT);
		root.addContent(this.vektorElement("Ortsvektor", this.getOrtsVektor()));
		root.addContent(this.vektorElement("Richtungsvektor1", this.getRichtungsVektor1()));
		root.addContent(this.vektorElement("Richtungsvektor2", this.getRichtungsVektor2()));
		return root;
	}

	@Override
	public Ebene clone() {
		try {
			Ebene out = new Ebene(this.getName(), this.getOrtsVektor(), this.getRichtungsVektor1(),
					this.getRichtungsVektor2(), 2);
			return out;
		} catch (RVKollinearException e) {
		}
		return null;
	}

	public int getLevel() {
		return EBENE;
	}
}
