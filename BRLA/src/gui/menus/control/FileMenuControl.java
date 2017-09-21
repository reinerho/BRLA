package gui.menus.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jdom.JDOMException;

import exeptions.FileOpenNoSuccessException;
import geometrie.GeoObject;
import gui.MainGUI;
import gui.control.MainControl;
import gui.menus.FileMenu;
import tools.FileTools;
import tools.Init;
import tools.XML;

public class FileMenuControl {

	private static FileMenuControl instance;

	public static FileMenuControl getInstance() {
		if (instance == null)
			instance = new FileMenuControl();
		return instance;
	}

	private FileMenu menu = FileMenu.getInstance();

	private FileMenuControl() {
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
		if (item == menu.getSaveItem()) {
			if (MainControl.getInstance().getMap().isEmpty()) {
				JOptionPane.showMessageDialog(MainGUI.getInstance(),
						"Keine Objekte vorhanden!\nEs gibt nichts zu speichern!", "Fehler", JOptionPane.ERROR_MESSAGE);
				return;
			}
			FileTools.save(Init.getFilename());
		}
		if (item == menu.getSaveAsItem()){
			if (MainControl.getInstance().getMap().isEmpty()) {
				JOptionPane.showMessageDialog(MainGUI.getInstance(),
						"Keine Objekte vorhanden!\nEs gibt nichts zu speichern!", "Fehler", JOptionPane.ERROR_MESSAGE);
				return;
			}
			FileTools.save(null);
		}
		if (item == menu.getOpenItem()) {
			JFileChooser chooser = new JFileChooser(Init.getDataPath());
			FileNameExtensionFilter filter = new FileNameExtensionFilter("XML-Dateien", "xml");
			chooser.setFileFilter(filter);
			int answer = chooser.showOpenDialog(MainGUI.getInstance());
			if (answer == JFileChooser.APPROVE_OPTION) {
				String newFile = chooser.getSelectedFile().getAbsolutePath();
				try {
					Vector<GeoObject> v = XML.parseXML(XML.load(newFile));
					Init.setDataPath(new File(newFile).getParent() + Init.separator);
					Init.setFilename(new File(newFile).getName());
					for (GeoObject go : v) {
						// MainControl.getInstance().addWindow(go, go.getPos());
						MainControl.getInstance().addObject(go, go.getPos());
					}
				} catch (JDOMException | FileOpenNoSuccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Init.change=false;
			return;
		}
		if (item == menu.getShutdownItem()) {
			Init.shutdown();
		}
		if (item == menu.getNewItem()) {
			MainControl.getInstance().removeAllObjects();
			Init.setFilename(null);
			Init.setChange(false);
		}
		if (item == menu.getPrintItem()) {
			MainControl.getInstance().printAllObjects();
		}
	}
}
