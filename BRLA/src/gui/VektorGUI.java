package gui;

import java.util.Vector;

import javax.swing.JTextField;

import geometrie.LAVector;

public class VektorGUI extends GeoGUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTextField lang;
	private JTextField punkt;
	private LAVector v;
	// private String name;

	public VektorGUI(String name, LAVector v) {
		super(name, v);
		this.v = v;
		setup();
		this.pack();
	}
	
	public VektorGUI(LAVector v) {
		super(v);
		this.v = v;
		setup();
		this.pack();
	}

	private void setup() {
		this.XMLROOT="VektorGUI";
		//this.getItem().setText("Vektor "+v.getName());
		this.setToolTipText("Vektor: " + name);
		this.setPunkt();
		this.add(punkt, "wrap,alignc,span");
		this.setLang();
		this.add(lang, "wrap,span,alignc");
		this.setKoord(v);
		//this.add(btnPrint,"alignc");
		this.add(btnClose,"alignc, span, wrap");
	}

	public void rename(String name){
		super.rename(name);
		this.getPunkt().setText(name.toUpperCase()+v.toPointString(true));
	}
	
	public GeoArea getKoord() {
		return darst;
	}

	public void setKoord(GeoArea koord) {
		this.darst = koord;
	}

	public void setKoord(LAVector v) {
		this.darst.setText(v.toString());
	}

	public JTextField getLang() {
		return lang;
	}

	public void setLang() {
		this.lang = new JTextField("Länge: " + v.length() + " LE");
	}

	public JTextField getPunkt() {
		return punkt;
	}

	public void setPunkt() {
		if (this.getName()==null) this.setName("");
		this.punkt = new JTextField(extractName(name).toUpperCase() + v.toPointString(true));
	}

	public Vector<String> print(){
		Vector<String>out = super.print();
		out.add("Länge: "+this.v.length());
		return out;
	}
	
}
