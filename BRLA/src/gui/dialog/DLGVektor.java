package gui.dialog;
import java.awt.Frame;

import javax.swing.JLabel;

import geometrie.LAVector;

public class DLGVektor extends DLGGeo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private LAVector vektor;


	public DLGVektor(Frame parent, String name, boolean modal) {
		super(parent, name, modal);
		vektor = LAVector.nullVektor(3);
		this.setup();
	}
	
	public DLGVektor(Frame parent, String name, boolean modal, LAVector v) {
		super(parent, name, modal);
		this.vektor = v;
		this.setup();
	}

	protected void setup() {
		super.setup();
		this.initLabels("v");
		this.initTF(tfOV, this.vektor);
		this.add(new JLabel("Eingabe eines neuen Vektors"), "span, wrap");
		this.add(new JLabel("Name:"));
		this.add(tfName, "span, wrap");
		this.add(new JLabel(), "wrap");
		for (int zeile = 0; zeile < 3; zeile++) {
			this.add(labels[zeile][0]);
			this.add(tfOV[zeile]);
			this.add(new JLabel());
			this.add(new JLabel(), "wrap");
		}
		this.add(btnOK, "alignc");
		this.add(new JLabel());
		this.add(btnCancel, "alignc");
		this.add(new JLabel(), "span,wrap");
		this.pack();
		this.setLocationRelativeTo(frame);
	}

	public LAVector getVector() {
		return vektor;
	}

	public void setVector(LAVector vector) {
		this.vektor = vector;
	}

	public LAVector getVektor() {
		return vektor;
	}	
}
