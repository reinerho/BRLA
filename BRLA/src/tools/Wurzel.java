package tools;

public class Wurzel {

	private Bruch value; // der Radikand
	private Bruch integerPart=new Bruch(1); //der rationale Vorfaktor
	private Bruch realPart=new Bruch(1);//der echte Radikand (nach partiellem Radizieren)
	private final String ca = "\u2248"; //das Wurzelzeichen
	
	/**
	 * Erzeugt eine Wurzel-Instanz mit dem Wert 0
	 */
	public Wurzel(){
		this.value=new Bruch(0);
	}
	
	/**
	 * Erzeugt eine Wurzelinstanz mit dem Radikanden "zahl"
	 * @param zahl der Radikand
	 */
	public Wurzel(int zahl){
		this.value = new Bruch(zahl);
		this.partWurzel();
	}
	
	/**
	 * Erzeugt eine Wurzelinstanz mit dem Radikanden "bruch"
	 * @param bruch die Bruchzahl als Radikand
	 */
	public Wurzel(Bruch bruch){
		this.value = bruch;
		this.partWurzel();
	}
	
	/**
	 * Bildet das Produkt der Wurzel mit der (Bruch-)Zahl b
	 * @param b der Faktor (Bruch)
	 * @return das Produkt aus der Wurzel und dem Faktor
	 */
	public Wurzel mult(Bruch b){
		Wurzel out = this;
		out.value = out.value.mult(b.pow(2));
		out.partWurzel();
		return out;
	}
	
	/**
	 * Liefert das Wurzelquadrat, als oden Radikanden zurück
	 * @return der Radikand
	 */
	public Bruch toQuadrat(){
		return this.value;
	}
	
	public Wurzel invers(){
		Wurzel out = this;
		out.value = out.value.kehrBruch();
		//out.integerPart = out.integerPart.kehrBruch();
		//out.realPart = out.realPart.kehrBruch();
		out.partWurzel();
		return out;
	}
	
	/**
	 * Gibt den Wert der Wurzel als Double zurück
	 * @return Wert der Wurzel als Double
	 */
	public double toDouble(){
		return Math.sqrt(value.toDouble());
	}
	
	public String toString(){
		if (getRealPart().equals(0)||getRealPart().equals(1)) return integerPart.mult(getRealPart())+"";
		if (integerPart.equals(1))
		return "\u221A("+value+") "+ca+" "+MyMath.round(this.toDouble(),4);
		else
			return integerPart+"* \u221A("+realPart+") "+ca+" "+MyMath.round(this.toDouble(),4);
	}
	
	/**
	 * Prueft, ob die Wurzel eine rationale Zahl darstellt
	 * @return true wenn Rational
	 */
	public boolean isRational(){
		return (MyMath.isGanzzahl(Math.sqrt(value.getZaehler()))&&MyMath.isGanzzahl(Math.sqrt(value.getNenner())));
	}
	
	/**
	 * Überprüft ob eine Wurzel gleich der gegebenen ist
	 * @param w die andere Wurzel
	 * @return true wenn gleich
	 */
	public boolean equals(Wurzel w){
		return this.value.equals(w.value);
	}
	
	/**
	 * Überprüft ob eine Wurzel gleich einer Bruchzahl (also rational)  ist
	 * @param b die Bruchzahl
	 * @return true wenn gleich
	 */
	public boolean equals(Bruch b){
		return (this.realPart.equals(b) && this.integerPart.equals(new Bruch(1)));
	}
	
	/**
	 * Partielles Wurzelberechnen: Ergibt einen rationalen Teil und einen Radikanden, der keine quadratischen Faktoren mehr enthaelt
	 */
	public void partWurzel(){
		int zQuad = MyMath.getQudrat(value.getZaehler());
		int nQuad= MyMath.getQudrat(value.getNenner());
		integerPart.setZaehler((int) Math.sqrt(zQuad));
		integerPart.setNenner((int) Math.sqrt(nQuad));
		realPart.setZaehler(value.getZaehler()/zQuad);
		realPart.setNenner(value.getNenner()/nQuad);
	}

	/**
	 * Gibt den Radikanden der Wurzel zurück
	 * @return der Radikand
	 */
	public Bruch getValue() {
		return value;
	}

	/**
	 * Setzt den Radikanden auf den Wert "value"
	 * @param value der neue Radikand
	 */
	public void setValue(Bruch value) {
		this.value = value;
	}

	/**
	 * Gibt den rationalen Anteil der Wurzel zurück
	 * @return der rationale Anteil
	 */
	public Bruch getIntegerPart() {
		return integerPart;
	}

	/**
	 * Setzt den rationalen Anteil der Wurzel auf den Wert "integerPart"
	 * @param integerPart der neue rationale Faktor
	 */
	public void setIntegerPart(Bruch integerPart) {
		this.integerPart = integerPart;
	}

	/**
	 * Gibt den echten Radikanden (um quadratische Anteile bereinigt) zurueck
	 * @return der echte Radikand
	 */
	public Bruch getRealPart() {
		return realPart;
	}

	
	public void setRealPart(Bruch realPart) {
		this.realPart = realPart;
	}
	
}
