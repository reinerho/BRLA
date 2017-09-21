package gui.dialog;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import exeptions.RVKollinearException;
import geometrie.Ebene;
import geometrie.Gerade;
import geometrie.LAVector;
import net.miginfocom.swing.MigLayout;
import tools.Bruch;

public class DLGEbene extends DLGGeo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Ebene ebene = Ebene.koordinatenEbene(12);
	private JTextField[] tfRV1 = new JTextField[3];
	private JTextField[] tfRV2 = new JTextField[3];
	private JTextField[] tfNKoord = new JTextField[4];
	private JTextField[][] tfP = new JTextField[3][3];
	private JCheckBox chkPunkt = new JCheckBox("Ebene über Punkte festlegen");
	private Bruch konst = new Bruch(0);
	private LAVector[] punkte = new LAVector[3];
	public static final boolean VECTOR_MODE = true;
	public static final boolean PUNKT_MODE = false;

	public DLGEbene(Frame arg0, String arg1, boolean arg2) {
		super(arg0, arg1, arg2);
		this.frame = arg0;
		setup();
	}

	public DLGEbene(Frame arg0, String arg1, boolean arg2, LAVector v) {
		super(arg0, arg1, arg2);
		setup();
	}

	public DLGEbene(Frame arg0, String arg1, boolean arg2, Gerade g) {
		super(arg0, arg1, arg2);
		setup();
		this.setLocationByPlatform(true);
	}
	
	public DLGEbene(Frame arg0, String arg1, boolean arg2, Ebene e) {
		super(arg0, arg1, arg2);
		this.ebene = e;
		setup();
		this.setLocationByPlatform(true);
	}

	public void setup() {
		super.setup();
		MigLayout layout = new MigLayout();
		this.setLayout(layout);
		this.initLabels("E");
		this.initTF(tfOV, ebene.getOrtsVektor());
		this.initTF(tfRV1, ebene.getRichtungsVektor1());
		this.initTF(tfRV2, ebene.getRichtungsVektor2());
		this.initTF(tfNKoord, ebene.getNormalenVektor(), konst);
		this.initPoints();
		this.add(new JLabel("Eingabe einer neuen Ebene"), "span, wrap");
		this.tfName.setText(ebene.getName());
		this.add(new JLabel("Name:"));
		this.add(tfName, "span, wrap");
		// Dartellung in Parameterform
		this.add(new JLabel(), "wrap");
		this.add(new JLabel("Parameterform"), "span, alignc, wrap");
		for (int zeile = 0; zeile < 3; zeile++) {
			this.add(labels[zeile][0]);
			this.add(tfOV[zeile]);
			this.add(labels[zeile][1]);
			this.add(tfRV1[zeile]);
			this.add(labels[zeile][2]);
			this.add(tfRV2[zeile]);
			this.add(new JLabel());
			this.add(new JLabel(), "wrap");
		}
		// Darstellung in Koordinatenform
		this.add(new JLabel(), "wrap");
		this.add(new JLabel("Koordinatenform"), "span, alignc, wrap");
		this.add(new JLabel("E:x="));
		this.add(tfNKoord[0]);
		this.add(new JLabel("x+"));
		this.add(tfNKoord[1]);
		this.add(new JLabel("y+"));
		this.add(tfNKoord[2]);
		this.add(new JLabel("z="));
		this.add(tfNKoord[3], "wrap");
		// 3 Punkte
		this.add(
				new JLabel(
						"--------------------------------------------------------------------------------------------"),
				"span, wrap");
		this.add(this.chkPunkt, "span,wrap");
		this.add(new JLabel(), "wrap");
		for (int pkt = 0; pkt < 3; pkt++) {
			this.add(new JLabel("P" + (pkt + 1) + "("));
			for (int i = 0; i < 3; i++) {
				this.add(tfP[pkt][i]);
				tfP[pkt][i].setEnabled(false);
				if (i < 2)
					this.add(komma());
				else
					this.add(new JLabel(")"), "wrap");
			}
		}
		this.addButtons();
	}
	
	public void setNormale() throws RVKollinearException {
		if (this.ebene.getRichtungsVektor1().isKollinear(this.ebene.getRichtungsVektor2())
				|| this.ebene.getRichtungsVektor1().equals(LAVector.nullVektor(3))
				|| this.ebene.getRichtungsVektor2().equals(LAVector.nullVektor(3)))
			throw new RVKollinearException();
		for (int i = 0; i < 3; i++) {
			this.getTfNKoord()[i].setText(ebene.getNormalenVektor().get(i) + "");
		}
		this.getTfNKoord()[3].setText(ebene.getNormalformKonst() + "");
	}

	public void setParam() {
		for (int i = 0; i < 3; i++) {
			this.getTfOV()[i].setText(ebene.getOrtsVektor().get(i) + "");
			this.getTfRV1()[i].setText(ebene.getRichtungsVektor1().get(i) + "");
			this.getTfRV2()[i].setText(ebene.getRichtungsVektor2().get(i) + "");
		}
	}

	public void toggleMode() {
		this.toggleTextField(this.getTfOV());
		this.toggleTextField(this.getTfRV1());
		this.toggleTextField(this.getTfRV2());
		this.toggleTextField(this.getTfNKoord());
		for (int i = 0; i < 3; i++) {
			this.toggleTextField(this.getTfP()[i]);
		}
		this.getTfNKoord()[3].setEnabled(!this.getTfNKoord()[3].isEnabled());
	}

	private void toggleTextField(JTextField[] tf) {
		for (int i = 0; i < 3; i++) {
			tf[i].setEnabled(!tf[i].isEnabled());
		}
	}

	private void initPoints() {
		for (int punkt = 0; punkt < 3; punkt++) {
			this.punkte[punkt] = ebene.getRandomPoint();
			for (int i = 0; i < 3; i++) {
				tfP[punkt][i] = new JTextField(punkte[punkt].get(i).toString(), 7);
				tfP[punkt][i].setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
			}
		}

	}

	private void initTF(JTextField[] tf, LAVector v, Bruch konst) {
		initTF(tf, v);
		tf[3] = new JTextField(konst.toString(), 7);
	}

	private JLabel komma() {
		return new JLabel(",");
	}

	public Ebene getEbene() {
		return ebene;
	}

	public void setEbene(Ebene e) {
		this.ebene = e;
	}

	public JTextField[] getTfRV1() {
		return tfRV1;
	}

	public JTextField[] getTfRV2() {
		return tfRV2;
	}

	public JTextField[] getTfNKoord() {
		return tfNKoord;
	}

	public JTextField[][] getTfP() {
		return tfP;
	}

	public JCheckBox getChkPunkt() {
		return chkPunkt;
	}

	public LAVector[] getPunkte() {
		return punkte;
	}
	
	public LAVector getPunkt(int i){
		return punkte[i];
	}

	public void setPunkte(LAVector[] punkte) {
		this.punkte = punkte;
	}
	
	public void setPunkt(LAVector punkt, int i) {
		this.punkte[i] = punkt;
	}

}
