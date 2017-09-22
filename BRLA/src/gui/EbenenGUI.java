package gui;

import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTextField;

import geometrie.Ebene;
import geometrie.LAVector;
import tools.Etc;
import tools.Wurzel;

public class EbenenGUI extends GeoGUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextField kordDarst; 
	private JTextField zeroDist; 
	private Ebene e;
	
	public EbenenGUI(String name, Ebene e){
		super(name,e);
		this.e = e;
		this.setup();
		this.pack();
	}
	
	public EbenenGUI(Ebene e){
		super(e);
		this.e = e;
		setup();
		this.pack();
	}

	private void setup(){
		this.XMLROOT="EbenenGUI";
		//this.getItem().setText("Ebene "+e.getName());
		
		this.darst.setText(e.toString());
		this.darst.setEditable(false);
		this.add(new JLabel("Koordinatenform"),"wrap, span, alignc");
		String eString = e.toKoordinatenFormString();
		kordDarst=new JTextField(eString);
		kordDarst.setEditable(false);
		this.add(kordDarst,"span, alignc, wrap");
		Wurzel dist = e.getAbstand(new LAVector(0, 0, 0));
		this.add(new JLabel("Abstand zum Ursprung:"),"wrap, span, alignc");
		zeroDist=new JTextField("d= "+dist);
		zeroDist.setEditable(false);
		this.add(zeroDist,"span,alignc,wrap");
		this.add(new  JLabel("Spurgeraden:"),"span, alignc,wrap");
		for (int i=0; i<3; i++){
			GeoArea g = new GeoArea(e.getSpurgerade(i), "S"+(i+1));
			g.setEditable(false);
			this.add(g);
		}
		this.add(new JLabel(),"wrap");
		this.add(new JLabel("Achsenschnittpunkte:"),"alignc,span,wrap");
		for (int i=0; i<3; i++){
			LAVector v = this.getE().getAchsenSchnittpunkt(i);
			v.setName("A"+(i+1));
			GeoArea g = new GeoArea(v);
			g.setEditable(false);
			this.add(g,"alignc");
		}
		this.add(new JLabel(),"wrap");
		//this.add(btnPrint,"alignc");
		//this.add(btnGet,"alignc");
		this.add(btnClose,"alignc, span, wrap");
		//System.out.println(this.getClass().getName()+": "+menuItem.getText());
	}
	
	public Vector<String> print(){
		Vector<String>out = super.print();
		out.add(" ");
		out.add(e.getName()+": "+e.toKoordinatenFormString());
		out.add(" ");
		out.add("Spurgeraden:");
		out.add(" ");
		Vector<String>s = this.e.getSpurgerade(0).print();
		s=Etc.appendVector(s, e.getSpurgerade(1).print(), Etc.HORIZONTAL);
		s=Etc.appendVector(s, e.getSpurgerade(2).print(), Etc.HORIZONTAL);
		out = Etc.appendVector(out, s, Etc.VERTICAL);
		out.add(" ");
		out.add("Abstand zum Ursprung");
		out.add(" ");
		out.add("d="+e.getAbstand(LAVector.nullVektor(3)));
		return out;
	}
	
	public Ebene getE() {
		return e;
	}

}
