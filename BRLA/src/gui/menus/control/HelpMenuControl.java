package gui.menus.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import gui.MainGUI;
import gui.menus.HelpMenu;
import tools.Init;

public class HelpMenuControl {

	private static HelpMenuControl instance;

	public static HelpMenuControl getInstance() {
		if (instance == null)
			instance = new HelpMenuControl();
		return instance;
	}

	private HelpMenu menu = HelpMenu.getInstance();

	private HelpMenuControl() {
		Vector<JMenuItem> menuList = this.menu.getMenuList();
		HelpHelpMenuControl.getInstance();
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
		if (item == menu.getAboutItem()) {
			JOptionPane.showMessageDialog(MainGUI.getInstance(),
					"Programm zur Vektorrechnung Version " + Init.VERSION + "\n vom "+Init.DATUM+"\n"+Init.COPYRIGHT+" Reiner Hornischer", "Über BRLA",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
