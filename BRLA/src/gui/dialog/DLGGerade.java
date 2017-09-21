package gui.dialog;
import java.awt.Frame;

import javax.swing.JLabel;
import javax.swing.JTextField;

import geometrie.Gerade;

public class DLGGerade extends DLGGeo {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Gerade gerade;
	private JTextField[] tfRV;
	
	public DLGGerade(Frame parent, String name, boolean modal){
		super(parent, name, modal);
		gerade = Gerade.getAchse(Gerade.X1_ACHSE);
		this.setup();
	}

	public DLGGerade(Frame parent, String name, boolean modal, Gerade g){
		super(parent, name, modal);
		this.gerade = g;
		this.setup();
	}
	
	protected void setup(){
		super.setup();
		this.initLabels("g");
		 tfRV = new JTextField[3];
		this.initTF(tfOV, gerade.getOrtsVektor());
		this.initTF(tfRV, gerade.getRichtungsVektor());
		this.add(new JLabel("Eingabe einer neuen Gerade"), "span, wrap");
		//this.tfName.setText(gerade.getName());
		this.add(new JLabel("Name:"));
		this.add(tfName, "span, wrap");
		this.add(new JLabel(), "wrap");
		this.add(new JLabel("Parameterform"), "span, alignc, wrap");
		for (int zeile = 0; zeile < 3; zeile++) {
			this.add(labels[zeile][0]);
			this.add(tfOV[zeile]);
			this.add(labels[zeile][1]);
			this.add(tfRV[zeile]);
			this.add(new JLabel());
			this.add(new JLabel(), "wrap");
		}
		addButtons();
	}
	
	public Gerade getGerade() {
		return gerade;
	}

	public void setGerade(Gerade g) {
		this.gerade = g;
	}

	public JTextField[] getTfRV() {
		return tfRV;
	}

}
