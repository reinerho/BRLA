package gui.dialog.control;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.util.Vector;

import geometrie.Gerade;
import gui.dialog.DLGGerade;

public class DLGGeradeControl extends DLGGeoControl {
	private Gerade gerade;
	private DLGGerade dialog;

	private final int NO_CHANGE = -1;
	private final int PARM_CHANGE = 0;
	private final int NAME_CHANGE = 3;

	public DLGGeradeControl(Frame parent) {
		super(parent, new DLGGerade(parent, "Neue Gerade eingeben:", true));
		dialog = (DLGGerade) super.dialog;
		this.gerade = dialog.getGerade();
		dialog.getTfName().setText(this.gerade.getName());
		dialog.getTfName().addFocusListener(fl);
		this.addFocusListener(dialog.getTfOV());
		this.addFocusListener(dialog.getTfRV());
		dialog.getBtnOK().addActionListener(al);
		dialog.getBtnCancel().addActionListener(al);
		newPolicy = new MyFocusTraversalPolicy(policyOrder());
		dialog.setFocusTraversalPolicy(newPolicy);
		dialog.setVisible(true);
	}

	public DLGGeradeControl(Frame parent, Gerade g) {
		super(parent, new DLGGerade(parent, "Gerade editieren:", true,g));
		dialog = (DLGGerade) super.dialog;
		this.gerade = dialog.getGerade();
		dialog.getTfName().setText(this.gerade.getName());
		dialog.getTfName().addFocusListener(fl);
		this.addFocusListener(dialog.getTfOV());
		this.addFocusListener(dialog.getTfRV());
		dialog.getBtnOK().addActionListener(al);
		dialog.getBtnCancel().addActionListener(al);
		newPolicy = new MyFocusTraversalPolicy(policyOrder());
		dialog.setFocusTraversalPolicy(newPolicy);
		dialog.setVisible(true);
	}
	
	private int checkForChange() {
		if (!gerade.getName().equals(dialog.getTfName().getText()))
			return NAME_CHANGE;
		try {
			if (!(gerade.getOrtsVektor().equals(toVektor(dialog.getTfOV())))) {
				// System.out.println("Ortsvektor changed!");
				return PARM_CHANGE;
			}
		} catch (ParseException e) {
			errorMessage(e.getMessage());
		}
		try {
			if (!gerade.getRichtungsVektor().equals(toVektor(dialog.getTfRV()))) {
				// System.out.println("Richtungsvektor1 changed!");
				return PARM_CHANGE;
			}
		} catch (ParseException e) {
			errorMessage(e.getMessage());
		}
		return NO_CHANGE;
	}

	protected void focusLostAction() {
		int change = checkForChange();
		switch (change) {
		case NO_CHANGE:
			break;
		case NAME_CHANGE:
			gerade.setName(dialog.getTfName().getText());
			dialog.setGerade(gerade);
			break;
		case PARM_CHANGE:
			try {
				gerade = new Gerade(gerade.getName(), toVektor(dialog.getTfOV()), toVektor(dialog.getTfRV()), true);
			} catch (ParseException e) {
				errorMessage(e.getMessage());
			}
			dialog.setGerade(gerade);
			break;
		}
	}

	protected void action(ActionEvent evt) {
		super.geoObject = gerade;
		super.action(evt);
	}

	@Override
	protected Vector<Component> policyOrder() {
		Vector<Component> out = new Vector<Component>();
		out.add(dialog.getTfName());
		for (int i = 0; i < 3; i++) {
			out.add(dialog.getTfOV()[i]);
		}
		for (int i = 0; i < 3; i++) {
			out.add(dialog.getTfRV()[i]);
		}
		out.add(dialog.getBtnOK());
		out.add(dialog.getBtnCancel());
		return out;
	}

}
