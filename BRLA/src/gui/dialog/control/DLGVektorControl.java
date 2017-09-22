package gui.dialog.control;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.ParseException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JTextField;

import geometrie.LAVector;
import gui.control.MainControl;
import gui.dialog.DLGVektor;
import tools.Init;

public class DLGVektorControl extends DLGGeoControl {
	private LAVector vector;
	private DLGVektor dialog;

	private final int NO_CHANGE = -1;
	private final int PARM_CHANGE = 0;
	private final int NAME_CHANGE = 3;

	public DLGVektorControl(Frame parent) {
		super(parent,new DLGVektor(parent, "Neuen Vektor eingeben:", true));
		dialog = (DLGVektor) super.dialog;
		this.vector = dialog.getVector();
		dialog.getTfName().setText(this.vector.getName());
		dialog.getTfName().addFocusListener(fl);
		this.addFocusListener(dialog.getTfOV());
		dialog.getBtnOK().addActionListener(al);
		dialog.getBtnCancel().addActionListener(al);
		dialog.setVisible(true);
	}
	
	public DLGVektorControl(Frame parent, LAVector v) {
		super(parent,new DLGVektor(parent, "Vektor editieren:", true,v));
		dialog = (DLGVektor) super.dialog;
		this.vector = dialog.getVector();
		dialog.getTfName().setText(this.vector.getName());
		dialog.getTfName().addFocusListener(fl);
		this.addFocusListener(dialog.getTfOV());
		dialog.getBtnOK().addActionListener(al);
		dialog.getBtnCancel().addActionListener(al);
		dialog.setVisible(true);
	}

	private int checkForChange() {
		if (!vector.getName().equals(dialog.getTfName().getText()))
			return NAME_CHANGE;
		try {
			if (!(vector.equals(toVektor(dialog.getTfOV())))) {
				// System.out.println("Ortsvektor changed!");
				return PARM_CHANGE;
			}
		} catch (ParseException e) {
			errorMessage(e.getMessage());
		}
		return NO_CHANGE;
	}


	private FocusListener fl = new FocusListener() {

		@Override
		public void focusLost(FocusEvent evt) {
			focusLostAction();
		}

		@Override
		public void focusGained(FocusEvent evt) {
			JTextField tf = (JTextField) evt.getSource();
			tf.selectAll();
		}
	};

	protected void focusLostAction() {
		int change = checkForChange();
		switch (change) {
		case NO_CHANGE:
			break;
		case NAME_CHANGE:
			vector.setName(dialog.getTfName().getText());
			dialog.setVector(vector);
			break;
		case PARM_CHANGE:
			try {
				vector = toVektor(dialog.getTfOV());
			} catch (ParseException e) {
				errorMessage(e.getMessage());
			}
			vector.setName(dialog.getTfName().getText());
			dialog.setVector(vector);
			break;
		}
	}

	protected void action(ActionEvent evt) {
		JButton btn = (JButton) evt.getSource();
		if (btn == dialog.getBtnCancel()) {
		}
		if (btn == dialog.getBtnOK()) {
			focusLostAction();
			// WindowControl.getInstance().addItem(e);
			MainControl.getInstance().addObject(vector);
			Init.setChange(Init.CHANGE);
		}
		dialog.setVisible(false);
		dialog.dispose();
		return;
	}

	@Override
	protected Vector<Component> policyOrder() {
		Vector<Component> out = new Vector<Component>();
		return out;
	}

}
