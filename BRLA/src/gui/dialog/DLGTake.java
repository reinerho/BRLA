package gui.dialog;

import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JSeparator;

import geometrie.Ebene;
import geometrie.GeoObject;
import net.miginfocom.swing.MigLayout;

public class DLGTake extends JDialog {

	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	private JButton[] btnGerade = new JButton[4];
	private JButton[] btnPunkt = new JButton[4];
	private JInternalFrame iFrame;

	public DLGTake(JInternalFrame iFrame, String title, GeoObject geo, JFrame owner) {
		super(owner, title, true);
		this.iFrame = iFrame;
		setup(geo);
	}

	private void setup(GeoObject geo) {
		this.setLayout(new MigLayout());
		
			for (int i = 0; i < 3; i++) {
				btnGerade[i] = new JButton(getGeradenName(i));
				if (geo instanceof Ebene) this.add(btnGerade[i], "alignc, wrap");
			}
			btnGerade[3] = new JButton("Alle Spurgeraden");
			if (geo instanceof Ebene)  this.add(btnGerade[3], "alignc, wrap");
			this.add(new JSeparator(), "wrap");
		
		for (int i = 0; i < 3; i++) {
			btnPunkt[i] = new JButton(getPunktName(i, geo));
			this.add(btnPunkt[i], "alignc, wrap");
		}
		btnPunkt[3] = new JButton("Alle Punkte");
		this.add(btnPunkt[3], "alignc, wrap");
		Point p = iFrame.getLocation();
		this.setLocation(p);
		pack();
	}

	private String getPunktName(int i, GeoObject geo) {
		String out = "";
		if (geo instanceof Ebene) {
			out = "x" + (i + 1) + "-Achsenschnitt";
		} else {
			out = "Spur ";
			switch (i) {
			case 0:
				out = out + "x2-x3";
				break;
			case 1:
				out = out + "x1-x3";
				break;
			default:
				out = out + "x1-x2";
			}
			out = out + "-Ebene";
		}
		return out;
	}

	private String getGeradenName(int i) {
		String out = "Spur: ";
		switch (i) {
		case 0:
			out = out + "x2-x3";
			break;
		case 1:
			out = out + "x1-x3";
			break;
		default:
			out = out + "x1-x2";
		}
		out = out + "-Ebene";
		return out;
	}

	public JButton[] getBtnGerade() {
		return btnGerade;
	}

	public JButton[] getBtnPunkt() {
		return btnPunkt;
	}

}
