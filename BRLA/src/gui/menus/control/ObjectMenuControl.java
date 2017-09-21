package gui.menus.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JMenuItem;

import gui.MainGUI;
import gui.dialog.control.DLGEbeneControl;
import gui.dialog.control.DLGGeradeControl;
import gui.dialog.control.DLGVektorControl;
import gui.menus.ObjectMenu;

public class ObjectMenuControl {

	private static ObjectMenuControl instance;

	public static ObjectMenuControl getInstance() {
		if (instance == null)
			instance = new ObjectMenuControl();
		return instance;
	}

	private ObjectMenu menu = ObjectMenu.getInstance();

	private ObjectMenuControl() {
		Vector<JMenuItem> menuList = this.menu.getMenuList();
		for (JMenuItem item : menuList) {
			item.addActionListener(l);
		}
	}

	private ActionListener l = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent evt) {
			menuActionPerformed(evt);
		}
	};

	private void menuActionPerformed(ActionEvent evt) {
		JMenuItem item = (JMenuItem) evt.getSource();
		if (item == menu.getEbenenItem()){
			new DLGEbeneControl(MainGUI.getInstance());
		}
		if (item == menu.getGeradenItem()){
			new DLGGeradeControl(MainGUI.getInstance());
		}
		if (item == menu.getVektorItem()){
			new DLGVektorControl(MainGUI.getInstance());		}
	}
}
