package gui.dialog.control;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.ParseException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import geometrie.GeoObject;
import geometrie.LAVector;
import gui.control.MainControl;
import gui.dialog.DLGGeo;
import tools.Init;

public abstract class DLGGeoControl {

	protected final int NO_CHANGE = -1;
	protected final int PARM_CHANGE = 0;
	protected final int NORM_CHANGE = 1;
	protected final int PKT_CHANGE = 2;
	protected final int NAME_CHANGE = 3;
	
	protected DLGGeo dialog;
	
	protected GeoObject geoObject;
	
	protected boolean error = false;
	
	protected static MyFocusTraversalPolicy newPolicy;

	public DLGGeoControl(Frame parent, DLGGeo dialog) {
		this.dialog = dialog;
		this.dialog.getRootPane().setDefaultButton(dialog.getBtnOK());
	}

	protected LAVector toVektor(JTextField[] tf) throws ParseException {
		return new LAVector(tf[0].getText(), tf[1].getText(), tf[2].getText());
	}

	protected void addFocusListener(JTextField[] tf) {
		for (int i = 0; i < 3; i++) {
			tf[i].addFocusListener(fl);
		}
	}
	
	protected FocusListener fl = new FocusListener() {

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
	
	protected ActionListener al = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent evt) {
			action(evt);
		}
	};
	
	protected void action(ActionEvent evt) {
		JButton btn = (JButton) evt.getSource();
		if (btn == dialog.getBtnCancel()) {
		}
		if (btn == dialog.getBtnOK()) {
			focusLostAction();
			if (this.error)
				return;
			MainControl.getInstance().addObject(geoObject);
			Init.setChange(Init.CHANGE);
		}
		dialog.setVisible(false);
		dialog.dispose();
		return;
	}
	
	protected void errorMessage(String error){
		JOptionPane.showMessageDialog(dialog, "Die Eingabe enthält unerlaubte Zeichen: "+error, "Fehler", JOptionPane.ERROR_MESSAGE);
	}
	protected void initButtons(DLGGeo dialog){
		//this.dialog = dialog;
		dialog.getBtnOK().addActionListener(al);
		dialog.getBtnCancel().addActionListener(al);
	}
	
	protected abstract void focusLostAction();
	
	protected abstract Vector<Component> policyOrder();
	
	protected class MyFocusTraversalPolicy extends FocusTraversalPolicy{
		
		private Vector<Component> order;

		public MyFocusTraversalPolicy(Vector<Component> order){
			this.order = new Vector<Component>(order.size());
			this.order.addAll(order);
		}
		
		@Override
		public Component getComponentAfter(Container focusCycleRoot, Component aComponent) {
			int idx = (order.indexOf(aComponent) + 1) % order.size();
            return order.get(idx);
		}

		@Override
		public Component getComponentBefore(Container focusCycleRoot, Component aComponent) {
			 int idx = order.indexOf(aComponent) - 1;
	            if (idx < 0) {
	                idx = order.size() - 1;
	            }
	            return order.get(idx);
		}

		@Override
		public Component getDefaultComponent(Container focusCycleRoot) {
			return order.get(0);
		}

		@Override
		public Component getFirstComponent(Container focusCycleRoot) {
			return order.get(0);
		}

		@Override
		public Component getLastComponent(Container focusCycleRoot) {
			return order.lastElement();
		}
		
	};
	
}
