package gui.dialog.control;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import exeptions.RVKollinearException;
import geometrie.Ebene;
import gui.dialog.DLGEbene;
import tools.Bruch;

public class DLGEbeneControl extends DLGGeoControl {

	private Ebene ebene;

	private DLGEbene dialog;
	
	//private Vector<Component> order = new Vector<Component>(16);
	
	

	public DLGEbeneControl(Frame parent) {
		super(parent, new DLGEbene(parent, "Neue Ebene", true));
		dialog = (DLGEbene) super.dialog;
		ebene = dialog.getEbene();
		super.geoObject = ebene;
		dialog.getTfName().setText(this.ebene.getName());
		dialog.getTfName().addFocusListener(fl);
		this.addFocusListener(dialog.getTfOV());
		this.addFocusListener(dialog.getTfRV1());
		this.addFocusListener(dialog.getTfRV2());
		this.addFocusListener(dialog.getTfNKoord(), 4);
		for (int j = 0; j < 3; j++) {
			this.addFocusListener(dialog.getTfP()[j]);
		}
		// dialog.getTfName().addFocusListener(fl);
		this.initButtons(dialog);
		dialog.getChkPunkt().addActionListener(ck);
		newPolicy = new MyFocusTraversalPolicy(policyOrder());
		dialog.setFocusTraversalPolicy(newPolicy);
		dialog.setVisible(true);
	}

	public DLGEbeneControl(Frame parent, Ebene e) {
		super(parent, new DLGEbene(parent, "Ebene editieren", true, e));
		dialog = (DLGEbene) super.dialog;
		ebene = dialog.getEbene();
		super.geoObject = ebene;
		dialog.getTfName().setText(this.ebene.getName());
		dialog.getTfName().addFocusListener(fl);
		this.addFocusListener(dialog.getTfOV());
		this.addFocusListener(dialog.getTfRV1());
		this.addFocusListener(dialog.getTfRV2());
		this.addFocusListener(dialog.getTfNKoord(), 4);
		for (int j = 0; j < 3; j++) {
			this.addFocusListener(dialog.getTfP()[j]);
		}
		// dialog.getTfName().addFocusListener(fl);
		this.initButtons(dialog);
		dialog.getChkPunkt().addActionListener(ck);
		newPolicy = new MyFocusTraversalPolicy(policyOrder());
		dialog.setFocusTraversalPolicy(newPolicy);
		dialog.setVisible(true);
	}
	
	private void addFocusListener(JTextField[] tf, int dim) {
		for (int i = 0; i < dim; i++) {
			tf[i].addFocusListener(fl);
		}
	}
	
	private int checkForChange()  {
		if (!ebene.getName().equals(dialog.getTfName().getText())) {
			return NAME_CHANGE;
		}
		try {
			if (!(ebene.getOrtsVektor().equals(toVektor(dialog.getTfOV())))) {
				return PARM_CHANGE;
			}
		} catch (ParseException e) {
			errorMessage(e.getMessage());
		}
		try {
			if (!ebene.getRichtungsVektor1().equals(toVektor(dialog.getTfRV1()))) {
				// System.out.println("Richtungsvektor1 changed!");
				return PARM_CHANGE;
			}
		} catch (ParseException e) {
			errorMessage(e.getMessage());
		}
		try {
			if (!ebene.getRichtungsVektor2().equals(toVektor(dialog.getTfRV2()))) {
				// System.out.println("Richtungsvektor2 changed!");
				return PARM_CHANGE;
			}
		} catch (ParseException e) {
			errorMessage(e.getMessage());
		}
		try {
			if (!ebene.getNormalenVektor().equals(toVektor(dialog.getTfNKoord()))) {
				// System.out.println("Normalenvektor changed!");
				return NORM_CHANGE;
			}
		} catch (ParseException e) {
			errorMessage(e.getMessage());
		}
		try {
			if (!ebene.getNormalformKonst().equals(new Bruch(dialog.getTfNKoord()[3].getText()))) {
				// System.out.println("Normalenvektor changed!");
				return NORM_CHANGE;
			}
		} catch (ParseException e) {
			errorMessage(e.getMessage());
		}
		if (dialog.getChkPunkt().isSelected()) {
			for (int i = 0; i < 3; i++) {
				try {
					if (!toVektor(dialog.getTfP()[i]).equals(dialog.getPunkt(i)))
						return PKT_CHANGE;
				} catch (ParseException e) {
					errorMessage(e.getMessage());
				}
			}
		}
		return NO_CHANGE;
	}

	protected void focusLostAction() {
		int change = checkForChange();
		switch (change) {
		case NO_CHANGE:
			break;
		case PARM_CHANGE:
			try {
				ebene = new Ebene(dialog.getTfName().getText(), toVektor(dialog.getTfOV()), toVektor(dialog.getTfRV1()),
						toVektor(dialog.getTfRV2()), 2);
				dialog.setEbene(ebene);
				dialog.setNormale();
				this.error = false;
				break;
			} catch (RVKollinearException e) {
				JOptionPane.showMessageDialog(dialog, "Die Richtungsvektoren sind kollinear!", "Fehler",
						JOptionPane.ERROR_MESSAGE);
				this.error = true;
				break;
			}catch(ParseException e){
				errorMessage(e.getMessage());
			}
		case NORM_CHANGE:
			try {
				ebene = new Ebene(dialog.getTfName().getText(), toVektor(dialog.getTfNKoord()),
						new Bruch(dialog.getTfNKoord()[3].getText()));
			} catch (ParseException e) {
				errorMessage(e.getMessage());
			}
			dialog.setEbene(ebene);
			dialog.setParam();
			break;
		case PKT_CHANGE:
			for (int i = 0; i < 3; i++) {
				try {
					dialog.setPunkt(toVektor(dialog.getTfP()[i]), i);
				} catch (ParseException e) {
					errorMessage(e.getMessage());
				}
			}
			try {
				ebene = new Ebene(dialog.getTfName().getText(), toVektor(dialog.getTfP()[0]),
						toVektor(dialog.getTfP()[1]), toVektor(dialog.getTfP()[2]), 0);
				dialog.setEbene(ebene);
				dialog.setNormale();
				dialog.setParam();
				this.error = false;
			} catch (RVKollinearException e1) {
				JOptionPane.showMessageDialog(dialog, "Die Punkte spannen keine Ebene auf!", "Fehler",
						JOptionPane.ERROR_MESSAGE);
				this.error = true;
			} catch (ParseException e) {
				errorMessage(e.getMessage());
			}
			break;
		case NAME_CHANGE:
			ebene.setName(dialog.getTfName().getText());
			dialog.setEbene(ebene);
		}
	}

	protected ActionListener ck = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent evt) {
			actionchk(evt);
		}
	};

	protected void actionchk(ActionEvent evt) {
		dialog.toggleMode();
	}

	protected void action(ActionEvent evt) {
		super.geoObject = ebene;
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
			out.add(dialog.getTfRV1()[i]);
		}
		for (int i = 0; i < 3; i++) {
			out.add(dialog.getTfRV2()[i]);
		}
		for (int i = 0; i < 4; i++) {
			out.add(dialog.getTfNKoord()[i]);
		}
		out.add(dialog.getChkPunkt());
		out.add(dialog.getBtnOK());
		out.add(dialog.getBtnCancel());
		return out;
	}


	
}
