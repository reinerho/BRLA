package tools;

import java.util.Vector;

import tools.Bruch;

public class MyMath {

	/**
	 * Wandelt die Darstellung des Winkels "rad" in Grad um
	 * 
	 * @param rad
	 *            Winkel in radiant
	 * @return Winkel in Grad
	 */
	public static double toGrad(double rad) {
		return 180 * rad / Math.PI;
	}

	/**
	 * Wandelt den Winkel "rad" in Grad um. Rundet auf eine vorgegebene Anzahl
	 * von Dezimalen
	 * 
	 * @param rad
	 *            der Winkel in radiant
	 * @param dezimalen
	 *            Anzahl der Dezimalen
	 * @return der (gerundete) WInkel in Grad
	 */
	public static double toGrad(double rad, int dezimalen) {
		return round(180 * rad / Math.PI, dezimalen);
	}

	/**
	 * Rechnet einen WInkel von Grad in Radiant um
	 * 
	 * @param grad
	 *            der Winkel in Grad
	 * @return der Winkel in Radiant
	 */
	public static double toRad(double grad) {
		return Math.PI * grad / 180;
	}

	/**
	 * Wandelt den String s in eine Integerzahl um
	 * 
	 * @param s
	 *            der String
	 * @return die Integerzahl
	 */
	public static int toInt(String s) {
		// TODO NumberFormatException abfangen
		return Integer.valueOf(s).intValue();
	}

	/**
	 * Wandelt den String s in eine Double um
	 * 
	 * @param s
	 *            der String
	 * @return die Doublezahl
	 */
	public static double toDouble(String s) {
		return Double.valueOf(s).doubleValue();
	}

	/**
	 * Rundet eine Double-Zahl auf eine vorgegebene Anzahl von Dezimalen
	 * 
	 * @param zahl
	 *            die Double-Zahl
	 * @param digits
	 *            Anzahl der Dezimalen
	 * @return die gerundete zahl
	 */
	public static double round(double zahl, int digits) {
		return Math.round(zahl * Math.pow(10, digits)) / Math.pow(10, digits);
	}

	/**
	 * Gibt eine Double-Zahl als String mit vorangestelltem Vorzeichen aus (auch
	 * "+"
	 * 
	 * @param d
	 *            die Zahl
	 * @return der Zahlstring mit Vorzeichen
	 */
	public static String praedikat(double d) {
		if (d < 0)
			return d + "";
		if (d > 0)
			return "+" + d;
		return "0.0";
	}

	/**
	 * Gibt eine bruch-Zahl als String mit vorangestelltem Vorzeichen aus (auch
	 * "+"
	 * 
	 * @param b
	 *            die Zahl
	 * @return der Zahlstring mit Vorzeichen
	 */
	public static String praedikat(Bruch b) {
		if (b.lt(0))
			return b.toString();
		if (b.gt(0))
			return "+" + b;
		return "+0";
	}

	/**
	 * Prüft, ob eine Double-Zahl ganzzahlig ist
	 * 
	 * @param f
	 *            die Double-Zahl
	 * @return true, wenn Ganzzahl
	 */
	public static boolean isGanzzahl(double f) {
		return f - (int) f == 0;
	}

	/**
	 * Zerlegt eine (Integer-)Zahl in ihre Primfaktoren. Die Faktoren werden als
	 * Vector  zurueckgegeben
	 * 
	 * @param zahl
	 *            die Integerzahl
	 * @return der Vector mit den Primfaktoren
	 */
	public static Vector<Integer> primFaktor(int zahl) {
		int limit = zahl / 2 + 1;
		Vector<Integer> out = new Vector<Integer>();
		int prim = 2;
		while (prim <= limit) {
			if (isGanzzahl(1.0 * zahl / prim)) {
				while (isGanzzahl(1.0 * zahl / prim)) {
					if (prim != 0)
						out.add(prim);
					zahl = (int) zahl / prim;
				}
				prim++;
			} else
				prim++;
		}
		if (out.isEmpty())
			out.add(zahl);
		// System.out.println(out);
		return out;
	}

	/**
	 * Extrahiert aus einer Zahl ggf. einen quadratischen Faktor
	 * 
	 * @param zahl die zu untersuchende Zahl
	 * @return Ein Faktor, der eine Quadratzahl darstellt
	 */
	public static int getQudrat(int zahl) {
		Vector<Integer> faktoren = primFaktor(zahl);
		int out = 1;
		while (!faktoren.isEmpty()) {
			if (hasDouble(faktoren)) {
				int r = faktoren.get(0);
				out = out * r * r;
				remove(faktoren, r);
				remove(faktoren, r);
			} else
				faktoren.remove(0);
		}
		return out;
	}

	/**
	 * Untersucht, ob in dem Vector<Integer> Zahlen doppelt auftreten (Suche
	 * nach Quadratischen Faktoren)
	 * 
	 * @param v
	 *            der Vector mit primfaktoren einer Zahl
	 * @return true, wenn doppelte Primfaktoren gefunden wurden
	 */

	private static boolean hasDouble(Vector<Integer> v) {
		int tmp = v.get(0);
		for (int i = 1; i < v.size(); i++) {
			if (v.get(i) == tmp)
				return true;
		}
		return false;
	}

	/**
	 * Entfernt (eine) Zahl z aus dem Vector<Integer>
	 * 
	 * @param v
	 *            der Vector mit Zahlen
	 * @param z
	 *            die zu entfernede Zahl
	 */
	private static void remove(Vector<Integer> v, int z) {
		for (int i = 0; i < v.size(); i++) {
			if (v.get(i) == z) {
				v.remove(i);
				return;
			}
		}
	}

	/**
	 * Bildet das Produkt aus allen Komponenten eines Vector<Integer>
	 * 
	 * @param v
	 *            der Vektor
	 * @return das Produkt aus allen Vektorkomponenten
	 */
	private static int produkt(Vector<Integer> v) {
		int out = 1;
		for (int i : v) {
			out = out * i;
		}
		return out;
	}

	/**
	 * Bestimmt das kleinste gemeinsame Vielfache (kgV) zweier Zahlen
	 * @param a die erste Zahl (Integer)
	 * @param b die zweite Zahl (Integer)
	 * @return das kgV aus a und b
	 */
	public static int kgv(int a, int b) {
		a = Math.abs(a);
		b = Math.abs(b);
		if (a < b) {
			int tmp = a;
			a = b;
			b = tmp;
		}
		if (b == 0)
			return a;
		Vector<Integer> primA = primFaktor(a);
		Vector<Integer> primB = primFaktor(b);
		Vector<Integer> faktoren = new Vector<Integer>();
		for (int i : primA) {
			faktoren.add(i);
			if (primB.contains(i)) {
				remove(primB, i);
			}
		}
		for (int i : primB) {
			faktoren.add(i);
		}
		// System.out.println("kgv("+a+";"+b+")="+produkt(faktoren));
		return produkt(faktoren);
	}

	/**
	 * Bestimmt den groessten gemeinsamen Teiler (ggT) aus zwei Zahlen
	 * @param a die erste (Integer-)Zahl
	 * @param b die zweite (Integer-)Zahl
	 * @return der ggT
	 */
	public static int ggt(int a, int b) {
		if (Math.abs(a) < Math.abs(b)) {
			int tmp = a;
			a = b;
			b = tmp;
		}
		if (b == 0)
			return a;
		Vector<Integer> primA = primFaktor(Math.abs(a));
		Vector<Integer> primB = primFaktor(Math.abs(b));
		Vector<Integer> faktoren = new Vector<Integer>();
		for (int i : primA) {
			if (primB.contains(i) && (i != 0)) {
				faktoren.add(i);
				remove(primB, i);
			}
		}
		return produkt(faktoren);
	}
	
	/**
	 * Prüft, ob man einen String in eine Zahl (Integer / Double / Bruch) parsen kann
	 * @param s der String
	 * @return true, wenn es ein gültiger Zahlstring ist
	 */
	public static boolean isZahlString(String s) {
		String legal = "[+-]{0,1}[0-9]*[/.]{0,1}[0-9]*";
		return s.matches(legal);
	}

	public static boolean isBruchString(String s) {
		return (isZahlString(s) && s.contains("/"));
	}

	public static boolean isDoubleString(String s) {
		return (isZahlString(s) && s.contains("."));
	}

	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static int toInteger(String s) {
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return -99999;
		}
	}
}
