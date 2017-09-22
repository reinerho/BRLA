package gui.control;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.jdom.Element;

import geometrie.GeoObject;
import gui.GeoGUI;
import gui.MainGUI;
import gui.menus.control.FileMenuControl;
import gui.menus.control.HelpMenuControl;
import gui.menus.control.ObjectMenuControl;
import tools.Init;
import tools.Printer;

public class MainControl {

	private MainGUI maingui;

	private HashMap<JMenuItem, GeoControl> map = new HashMap<JMenuItem, GeoControl>();

	private static MainControl instance;

	private GeoGUI markedGUI = null;

	public static MainControl getInstance() {
		if (instance == null)
			instance = new MainControl();
		return instance;
	}

	private MainControl() {
		maingui = MainGUI.getInstance();
		maingui.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				customClose();
			}

		});
		maingui.addKeyListener(mainkey);
		FileMenuControl.getInstance();
		ObjectMenuControl.getInstance();
		HelpMenuControl.getInstance();
		maingui.setVisible(true);
		if (Init.copyRight) {
			showHello();
		}
	}

	private void showHello() {
		String message = "Programm zur Vektorrechnung Version " + Init.VERSION + "\n+ vom " + Init.DATUM + "\n"
				+ Init.COPYRIGHT
				+ " Reiner Hornischer\n\nWählen Sie über den Menüpunkt \"Objekt\" Objekte aus,\nmit denen Sie arbeiten wollen.\n\n";
		JCheckBox chk = new JCheckBox("Diese Nachricht dauerhaft nicht mehr anzeigen!");
		chk.setSelected(false);
		Object[] msg = { message, chk };
		JOptionPane.showMessageDialog(maingui, msg, "Information", JOptionPane.INFORMATION_MESSAGE);
		boolean result = !chk.isSelected();
		Init.setCopyRight(result);
	}

	public void addObject(GeoObject go) {
		//if (((LAVector) go).get(0)==null) return;
		GeoControl gc = new GeoControl(go.getName(), go);
		this.map.put(gc.getGui().getMenuItem(), gc);
		gc.getGui().getMenuItem().addActionListener(l);
		this.getMaingui().getWindowMenu().add(gc.getGui().getMenuItem());
		this.getMaingui().add(gc.getGui());
		gc.getGui().setLocation((this.getMaingui().getWidth() - gc.getGui().getWidth()) / 2,
				(this.getMaingui().getHeight() - gc.getGui().getHeight()) / 2);
		gc.getGui().setVisible(true);
	}

	public void addObject(GeoObject go, Point pos) {
		GeoControl gc = new GeoControl(go.getName(), go);
		this.map.put(gc.getGui().getMenuItem(), gc);
		gc.getGui().getMenuItem().addActionListener(l);
		this.getMaingui().getWindowMenu().add(gc.getGui().getMenuItem());
		this.getMaingui().add(gc.getGui());
		// gc.getGui().setLocation(go.getPos());
		gc.getGui().setLocation(pos);
		gc.getGui().setVisible(true);
	}

	public void renameObject(GeoControl gc, String neuName) {
		GeoControl gcNeu = this.map.get(gc.getGui().getMenuItem());
		String gName = gc.guiName(neuName, gc.getGui().getGeoObject());
		gcNeu.getGui().getMenuItem().setText(gName);
		gcNeu.getGui().getGeoObject().setName(neuName);
		gcNeu.getGui().rename(gName);
	}

	public void removeObject(GeoGUI gui) {
		GeoControl gc = getControl(gui);
		JMenuItem item = gc.getGui().getMenuItem();
		this.maingui.getWindowMenu().remove(item);
		gc.getGui().setVisible(false);
		gc.getGui().dispose();
		map.remove(gc.getGui().getMenuItem());
		Init.setChange(Init.CHANGE);
		if (map.isEmpty())
			Init.setChange(Init.NOCHANGE);
	}

	public void removeAllObjects() {
		if (Init.isChange()) {
			int result = JOptionPane.showConfirmDialog(maingui,
					"Alle nicht gespeicherten Objekte gehen verloren!\nWollen Sie das?", "Warnung",
					JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.NO_OPTION)
				return;
		}
		Iterator<JMenuItem> it = map.keySet().iterator();
		Vector<GeoGUI> v = new Vector<GeoGUI>();
		while (it.hasNext()) {
			v.add(map.get(it.next()).getGui());
		}
		for (GeoGUI gg : v) {
			this.removeObject(gg);
		}
		Init.setChange(false);
	}

	public void printAllObjects() {
		// TODO Ebenenname wird nicht erkannt, Zusatzinfos mitdrucken ...
		if (this.getMap().isEmpty()) {
			JOptionPane.showMessageDialog(maingui, "Es gibt nichts zu Drucken!", "Sorry", JOptionPane.ERROR_MESSAGE);
			return;
		}
		Vector<String> print = new Vector<String>();
		Iterator<JMenuItem> it1 = map.keySet().iterator();
		while (it1.hasNext()) {
			GeoControl gc = map.get(it1.next());
			for (String s : gc.getGui().print()) {
				print.add(s);
			}
		}
		new Printer(print);
	}

	private GeoControl getControl(GeoGUI gui) {
		Iterator<JMenuItem> it = map.keySet().iterator();
		while (it.hasNext()) {
			GeoControl gc = map.get(it.next());
			if (gc.getGui().equals(gui))
				return gc;
		}
		return null;
	}

	private ActionListener l = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent evt) {
			menuActionPerformed(evt);
		}
	};

	private void menuActionPerformed(ActionEvent evt) {
		JMenuItem item = (JMenuItem) evt.getSource();
		if (this.getMap().size() < 1)
			return;
		GeoGUI wgui = this.getMap().get(item).getGui();
		wgui.toFront();
	}

	public MainGUI getMaingui() {
		return maingui;
	}

	private void customClose() {
		Init.shutdown();
	}

	public void removeGUI(GeoGUI gui) {
		gui.setVisible(false);
		this.getMaingui().remove(gui);
		gui.dispose();
	}

	public GeoGUI getMarkedGUI() {
		return markedGUI;
	}

	public void setMarkedGUI(GeoGUI markedGUI) {
		this.markedGUI = markedGUI;
	}

	public HashMap<JMenuItem, GeoControl> getMap() {
		return map;
	}

	public GeoGUI getGeoGUI(JMenuItem item) {
		Iterator<JMenuItem> it = map.keySet().iterator();
		while (it.hasNext()) {
			GeoControl gc = map.get(it.next());
			if (gc.getGui().getMenuItem().equals(item))
				return gc.getGui();
		}
		return null;
	}

	public Element xml() {
		Element root = new Element("BRLA");
		Iterator<JMenuItem> it = this.map.keySet().iterator();
		while (it.hasNext()) {
			GeoGUI gui = map.get(it.next()).getGui();
			root.addContent(gui.xml());
		}
		return root;
	}
	
	private KeyListener mainkey = new KeyListener() {

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			keyAction(e);
		}
	};
	
	private void keyAction(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_F1) {
			//TODO Helpaction
		}
	}
	
}
