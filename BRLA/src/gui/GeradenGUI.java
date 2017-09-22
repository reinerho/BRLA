package gui;

import java.util.Vector;

import javax.swing.JLabel;

import geometrie.Gerade;
import tools.Etc;

public class GeradenGUI extends GeoGUI {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Gerade g;
	
	public GeradenGUI(String name, Gerade g) {
		super(name, g);
		this.g = g;
		setup();
		this.pack();
	}

	public GeradenGUI(Gerade g) {
		super(g);
		this.g = g;
		setup();
		this.pack();
	}
	
	public void setup(){
		this.XMLROOT="GeradenGUI";
		//this.getItem().setText("Gerade "+g.getName());
		this.darst.setText(g.toString());
		this.darst.setEditable(false);
		this.add(new JLabel("Spurpunkte"),"span, alignc, wrap");
		for (int i=0; i<3; i++){
			GeoArea va = new GeoArea(g.getSpurpunkt(i), "sp"+(i+1));
			this.add(va);
			va.setEditable(false);
			
		}
		this.add(new JLabel(),"wrap");
		this.add(new JLabel("Abstand zum Ursprung: d = "+g.getAbstandUrsprung()),"span,wrap, alignc");
		//this.add(btnPrint,"alignc");
		//this.add(btnGet,"alignc");
		this.add(btnClose,"alignc, span, wrap");
	}

	public Vector<String> print(){
		Vector<String>out = super.print();
		out.add("Spurpunkte:");
		Vector<String>s = this.g.getSpurpunkt(0).print();
		s=Etc.appendVector(s, this.g.getSpurpunkt(1).print(), Etc.HORIZONTAL);
		s=Etc.appendVector(s, this.g.getSpurpunkt(2).print(), Etc.HORIZONTAL);
		out=Etc.appendVector(out, s, Etc.VERTICAL);
		out.add( "Abstand zum Ursprung: "+this.g.getAbstandUrsprung());
		return out;
	}
	
}
