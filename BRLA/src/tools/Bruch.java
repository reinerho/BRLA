package tools;

import java.text.ParseException;

/**
 * Verwaltet Brueche. Ein Bruch besteht aus einem (Integer-) Zaehler und einem
 * (Integer-Nenner). Der Nenner kann nur positiv sein!
 * 
 * @author reiner
 *
 */
public class Bruch {

	private int zaehler = 0;
	private int nenner = 1;
	public final static String XML_ZAEHLER = "Zaehler";
	public final static String XML_NENNER = "Nenner";
	public final static String XML_ROOT = "Bruch";

	/**
	 * Erzeigt einen Bruch aus zwei Integer-Zahlen
	 * 
	 * @param z
	 *            der Zaehler
	 * @param n
	 *            der Nenner
	 */
	public Bruch(int z, int n) {
		this.setZaehler(z);
		this.setNenner(n);
		if (nenner < 0) {
			nenner = nenner * (-1);
			zaehler = zaehler * (-1);
		}
	}

	/**
	 * Verwandelt einen Integer in einen Bruch mit Nenner 1
	 * 
	 * @param zahl
	 *            der Zaehler
	 */
	public Bruch(int zahl) {
		this.setZaehler(zahl);
		this.setNenner(1);
	}

	/**
	 * Verwandelt eine Double in einen Bruch
	 * 
	 * @param zahl
	 *            die Double - Zahl
	 */
	public Bruch(double zahl) {
		int nenner = 1;
		while (!MyMath.isGanzzahl(zahl)) {
			zahl = zahl * 10;
			nenner = nenner * 10;
		}
		this.setZaehler((int) zahl);
		this.setNenner(nenner);
		this.kuerze();
	}

	/**
	 * Parsed einen String in einen Bruch
	 * 
	 * @param zahl
	 *            ein Zahlstring
	 * @throws ParseException
	 *             Wirft eine ParseException, wenn der Zahl-String einen nicht
	 *             erlaubten Aufbau hat.
	 */
	public Bruch(String zahl) throws ParseException {
		zahl = zahl.replace(",", ".");
		if (MyMath.isZahlString(zahl)) { // nur erlaubte Zeichen in zahl?
			if (MyMath.isInteger(zahl)) {
				this.zaehler = MyMath.toInteger(zahl);
				this.nenner = 1;
				return;
			}
			if (MyMath.isBruchString(zahl)) {
				String[] split = zahl.split("/");
				this.setZaehler(MyMath.toInt(split[0]));
				this.setNenner(MyMath.toInt(split[1]));
			}
			if (MyMath.isDoubleString(zahl)) {
				double z = MyMath.toDouble(zahl);
				int nenner = 1;
				while (!MyMath.isGanzzahl(z)) {
					z = z * 10;
					nenner = nenner * 10;
				}
				this.setZaehler((int) z);
				this.setNenner(nenner);
				this.kuerze();
			}
		} else
			throw new ParseException(zahl, 0);
	}

	/**
	 * Prueft ob der Bruch positiv ist
	 * 
	 * @return true wenn positiv
	 */
	public boolean isPositiv() {
		return zaehler > 0;
	}

	/**
	 * (less then ... Prueft ob der Bruch kleiner als eine Integerzahl ist
	 * 
	 * @param i
	 *            die Integer-Zahl
	 * @return true wenn kleiner
	 */
	public boolean lt(int i) {
		return (this.toDouble() < i);
	}

	/**
	 * (less then ... Prueft ob der Bruch kleiner als ein anderer ist
	 * 
	 * @param b
	 *            der andere Bruch
	 * @return true wenn kleiner
	 */
	public boolean lt(Bruch b) {
		return (this.toDouble() < b.toDouble());
	}

	/**
	 * (greater then ... Prueft ob der Bruch groesser als eine Integerzahl ist
	 * 
	 * @param i
	 *            die Integer-Zahl
	 * @return true wenn groesser
	 */
	public boolean gt(int i) {
		return (this.toDouble() > i);
	}

	/**
	 * (greater then ... Prueft ob der Bruch groesser als ein anderer ist
	 * 
	 * @param b
	 *            der andere Bruch
	 * @return true wenn groesser
	 */
	public boolean gt(Bruch b) {
		return (this.toDouble() > b.toDouble());
	}

	/**
	 * prueft zwei Brueche auf Gleichheit
	 * 
	 * @param b
	 *            der andere Bruch
	 * @return true wenn gleich
	 */
	public boolean equals(Bruch b) {
		return ((this.kuerze().getNenner() == b.kuerze().getNenner())
				&& this.kuerze().getZaehler() == b.kuerze().getZaehler());
	}

	/**
	 * prueft auf (Wert-)Gleichheit mit einer Integerzahl
	 * 
	 * @param i
	 *            die Integerzahl
	 * @return true wenn gleich
	 */
	public boolean equals(int i) {
		return ((this.kuerze().getNenner() == 1) && (this.kuerze().getZaehler() == i));
	}

	/**
	 * Vertauscht Zaehler und Nenner
	 * 
	 * @return der Kehrbruch
	 */
	public Bruch kehrBruch() {
		return new Bruch(this.nenner, this.zaehler);
	}

	/**
	 * Kuerzt einen Bruch auf teilerfremden Zaehler und Nenner
	 * 
	 * @return der gekuerzte Brusch
	 */
	public Bruch kuerze() {
		if (this.zaehler == 0)
			return new Bruch(0);
		int ggt = MyMath.ggt(zaehler, nenner);
		zaehler = zaehler / ggt;
		nenner = nenner / ggt;
		return this;
	}

	/**
	 * Erweitert den Bruch um einen Faktor
	 * 
	 * @param faktor
	 *            der Erweiterungsfaktor
	 * @return der erweiterte Bruch
	 */
	public Bruch erweitere(int faktor) {
		faktor = Math.abs(faktor);
		this.zaehler = this.zaehler * faktor;
		this.nenner = this.nenner * faktor;
		return this;
	}

	/**
	 * Prueft ob ein Bruch wertgleich mit einer Integerzahl ist
	 * 
	 * @return true wenn wertgleich
	 */
	public boolean isGanzzahl() {
		return (this.kuerze().getNenner() == 1);
	}

	/**
	 * Prueft, ob ein Bruch den Wert Null hat
	 * 
	 * @return true wenn Wert 0
	 */
	public boolean isZero() {
		return (this.zaehler == 0);
	}

	/**
	 * Gibt den Zaehler des Bruchs zurueck
	 * 
	 * @return der Zaehler
	 */
	public int getZaehler() {
		return zaehler;
	}

	/**
	 * Setzt den Zaehler des Bruchs auf den Wert zaehler
	 * 
	 * @param zaehler
	 *            der Wert fuer den Zaehler
	 */
	public void setZaehler(int zaehler) {
		this.zaehler = zaehler;
	}

	/**
	 * Gibt den Nenner des Bruchs zurueck
	 * 
	 * @return der Nenner
	 */
	public int getNenner() {
		return nenner;
	}

	/**
	 * Setzt den Nenner des Bruchs auf den Wert nenner
	 * 
	 * @param nenner
	 *            der Wert fuer den Nenner
	 */
	public void setNenner(int nenner) {
		this.nenner = nenner;
	}

	public String toString() {
		this.kuerze();
		if (zaehler == 0)
			return "0";
		if (nenner == 1)
			return zaehler + "";
		else
			return zaehler + "/" + nenner;
	}

	/**
	 * Gibt den Betrag des Bruchs zurueck
	 * 
	 * @return der Bertag des Bruchs
	 */
	public Bruch abs() {
		if (this.isPositiv())
			return this;
		else
			return this.mult(-1);
	}

	/**
	 * Bildet die Summe mit dem Bruch bruch
	 * 
	 * @param bruch
	 *            der Bruch, der addiert werden soll
	 * @return die Summe
	 */
	public Bruch add(Bruch bruch) {
		int kgv = MyMath.kgv(this.nenner, bruch.nenner);
		this.erweitere(kgv / this.nenner);
		bruch.erweitere(kgv / bruch.nenner);
		return new Bruch(this.getZaehler() + bruch.getZaehler(), this.nenner).kuerze();
	}

	/**
	 * Bildet die Summe mit der Integerzahl summand
	 * 
	 * @param summand
	 *            die Integerzahl, die addiert werden soll
	 * @return die Summe
	 */
	public Bruch add(int summand) {
		Bruch bruch = new Bruch(summand);
		int kgv = MyMath.kgv(this.nenner, bruch.nenner);
		this.erweitere(kgv / this.nenner);
		bruch.erweitere(kgv / bruch.nenner);
		return new Bruch(this.getZaehler() + bruch.getZaehler(), this.nenner).kuerze();
	}

	/**
	 * Subtrahiert den Bruch bruch
	 * 
	 * @param bruch
	 *            der Bruch, der subtrahiert werden soll
	 * @return die Differenz
	 */
	public Bruch sub(Bruch bruch) {
		return this.add(bruch.mult(-1));
	}

	/**
	 * Subtrahiert die Integerzahl zahl
	 * 
	 * @param zahl
	 *            die Integerzahl, die subtrahiert werden soll
	 * @return die Differenz
	 */
	public Bruch sub(int zahl) {
		Bruch bruch = new Bruch(zahl);
		return this.add(bruch.mult(-1)).kuerze();
	}

	/**
	 * Bildet das Produkt mit dem Bruch bruch
	 * 
	 * @param bruch
	 *            der Bruch, mit dem multipliziert werden soll
	 * @return das Produkt
	 */
	public Bruch mult(Bruch bruch) {
		return new Bruch(this.getZaehler() * bruch.getZaehler(), this.getNenner() * bruch.getNenner()).kuerze();
	}

	/**
	 * Bildet das Produkt mit der Integerzahl faktor
	 * 
	 * @param faktor
	 *            die Integerzahl, mit der multipliziert werden soll
	 * @return das Produkt
	 */
	public Bruch mult(int faktor) {
		return new Bruch(this.getZaehler() * faktor, this.getNenner()).kuerze();
	}

	/**
	 * Dividiert durch den Bruch bruch
	 * 
	 * @param bruch
	 *            der Bruch, durch den dividiert wird
	 * @return der Quotient
	 */
	public Bruch div(Bruch bruch) {
		return this.mult(bruch.kehrBruch()).kuerze();
	}

	/**
	 * Dividiert durch die Integerzahl quotient
	 * 
	 * @param quotient
	 *            die Integerzahl, durch die dividiert wird
	 * @return der Quotient
	 */
	public Bruch div(int quotient) {
		return this.mult(new Bruch(quotient).kehrBruch());
	}

	/**
	 * Berechnet die Potenz des Bruchs mit dem Exponeten exp
	 * 
	 * @param exp
	 *            der Exponent (Integer)
	 * @return die Potenz mit dem Exponeten exp
	 */
	public Bruch pow(int exp) {
		Bruch out = this;
		for (int i = 1; i < exp; i++) {
			out = out.mult(this);
		}
		if (exp < 0)
			out = out.kehrBruch();
		if (exp == 0)
			return new Bruch(1);
		return out.kuerze();
	}

	/**
	 * Gibt den Bruch als Dezimalbruch zurueck
	 * 
	 * @return der Dezimalbruch (Double)
	 */
	public double toDouble() {
		return 1.0 * this.zaehler / this.nenner;
	}

}
