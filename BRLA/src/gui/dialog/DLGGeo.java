package gui.dialog;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import geometrie.LAVector;
import net.miginfocom.swing.MigLayout;

public abstract class DLGGeo extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected JLabel[][] labels = new JLabel[3][3];
	protected Frame frame;
	protected JButton btnOK = new JButton("OK");
	protected JButton btnCancel = new JButton("Abbrechen");
	protected JTextField[] tfOV = new JTextField[3];
	protected JTextField tfName = new JTextField(7);

	public DLGGeo(Frame arg0, String arg1, boolean arg2) {
		super(arg0, arg1, arg2);
		this.frame = arg0;
	}

	protected void setup() {
		//TODO In den Child-Modulen die Reihenfolge der Felder anpassen
		// TODO schließen durch "Enter" ermöglichen
		MigLayout layout = new MigLayout();
		this.setLayout(layout);
	}

	protected void initLabels(String name) {
		for (int zeile = 0; zeile < 3; zeile++)
			for (int spalte = 0; spalte < 3; spalte++) {
				this.labels[zeile][spalte] = new JLabel();
				this.labels[zeile][spalte].setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
			}
		labels[0][0].setText("  " + LAVector.PFEIL + " ");
		labels[1][0].setText(name + ":x=");
		labels[2][0].setText("    ");
		labels[0][1].setText("  ");
		labels[1][1].setText("+r");
		labels[2][1].setText("  ");
		labels[0][2].setText("  ");
		labels[1][2].setText("+s");
		labels[2][2].setText("  ");
	}

	protected void initTF(JTextField[] tf, LAVector v) {
		for (int i = 0; i < 3; i++) {
			tf[i] = new JTextField(v.get(i).toString(), 7);
		}
	}

	protected void addButtons() {
		//btnOK = new JButton("OK");
		//btnCancel = new JButton("Abbrechen");
		this.add(new JLabel());
		this.add(btnOK, "alignc");
		this.add(new JLabel());
		this.add(btnCancel, "alignc");
		this.add(new JLabel(), "span,wrap");
		this.pack();
		this.setLocationRelativeTo(frame);
	}

	public JTextField[] getTfOV() {
		return tfOV;
	}

	public JLabel[][] getLabels() {
		return labels;
	}

	public JButton getBtnOK() {
		return btnOK;
	}

	public JButton getBtnCancel() {
		return btnCancel;
	}

	public JTextField getTfName() {
		return tfName;
	}
	
	
	
}
