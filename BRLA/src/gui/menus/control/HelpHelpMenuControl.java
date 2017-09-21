package gui.menus.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JMenuItem;

import gui.HelpText;
import gui.control.HelpGUIControl;
import gui.menus.HelpHelpMenu;

public class HelpHelpMenuControl {
	
	private static HelpHelpMenuControl instance;

	public static HelpHelpMenuControl getInstance() {
		if (instance == null)
			instance = new HelpHelpMenuControl();
		return instance;
	}

	private HelpHelpMenu menu = HelpHelpMenu.getInstance();

	private HelpHelpMenuControl() {
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
		if (item == menu.getAllItem()) {
			new HelpGUIControl(HelpText.ALLGEMEIN,"Hilfe Allgemein");
		}
		if (item == menu.getFileItem()) {
			new HelpGUIControl(HelpText.DATEI,"Hilfe Datei");
		}
		if (item == menu.getObjectItem()) {
			new HelpGUIControl(HelpText.OBJEKT,"Hilfe Objekt");
		}
		if (item == menu.getWindowItem()) {
			new HelpGUIControl(HelpText.FENSTER,"Hilfe Fenster");
		}
	}
	
}
