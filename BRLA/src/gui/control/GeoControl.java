package gui.control;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;

import exeptions.RVKollinearException;
import geometrie.Ebene;
import geometrie.GeoObject;
import geometrie.Gerade;
import geometrie.LAVector;
import gui.EbenenGUI;
import gui.GeoGUI;
import gui.GeradenGUI;
import gui.MainGUI;
import gui.VektorGUI;
import gui.dialog.control.DLGEbeneControl;
import gui.dialog.control.DLGGeradeControl;
import gui.dialog.control.DLGVektorControl;
import tools.Etc;
import tools.Init;
import tools.Printer;

public class GeoControl {

	private GeoGUI gui;

	public GeoControl(GeoObject gObject) {
		if (gObject instanceof Ebene)
			gui = new EbenenGUI((Ebene) gObject);
		if (gObject instanceof Gerade)
			gui = new GeradenGUI((Gerade) gObject);
		if (gObject instanceof LAVector)
			gui = new VektorGUI((LAVector) gObject);
		gui.getBtnClose().addActionListener(lbtn);
		gui.addMouseListener(lmouse);
	}

	public GeoControl(String name, GeoObject gObject) {
		if (gObject instanceof Ebene)
			gui = new EbenenGUI(guiName(name, gObject), (Ebene) gObject);
		if (gObject instanceof Gerade)
			gui = new GeradenGUI(guiName(name, gObject), (Gerade) gObject);
		if (gObject instanceof LAVector)
			gui = new VektorGUI(guiName(name, gObject), (LAVector) gObject);
		gui.setMenuItem(new JMenuItem(guiName(name, gObject)));
		gui.getDarst().setEditable(false);
		gui.getMenuItem().setText(gui.getName());
		gui.getBtnClose().addActionListener(lbtn);
		//gui.getBtnPrint().addActionListener(lbtn);
		//gui.getBtnGet().addActionListener(lbtn);
		gui.addMouseListener(lmouse);
		gui.setToolTipText("Rechtsklick für Kontextmenü, Doppelklick zum An- und Abwählen!");
	}

	public String guiName(String name, GeoObject gObject) {
		if (gObject instanceof Ebene)
			return "Ebene " + name;
		if (gObject instanceof Gerade)
			return "Gerade " + name;
		return "Vektor " + name;
	}

	private ActionListener lbtn = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent evt) {
			doAction(evt);
		}
	};

	private void doAction(ActionEvent evt) {
		JButton button = (JButton) evt.getSource();
		// GeoGUI gui = (GeoGUI) button.getParent().getParent().getParent().getParent();
		if (button == gui.getBtnClose())
			MainControl.getInstance().removeObject(this.gui);
		/*if (button == gui.getBtnPrint()) {
			new Printer(this.getGui().print());
		}
		if (button == gui.getBtnGet()) {
			new DLGTakeControl(gui, "Objekt auswählen", gui.getGeoObject(), MainGUI.getInstance());
		}*/
	}

	private MouseListener lmouse = new MouseListener() {

		@Override
		public void mouseClicked(MouseEvent evt) {
			mouseClickedAction(evt);
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}

	};

	private void mouseClickedAction(MouseEvent evt) {
		if (evt.getClickCount() == 2) {
			if (gui.getBackground() == Color.yellow) {
				gui.setBackground(gui.getNormColor());
				MainControl.getInstance().setMarkedGUI(null);
			} else {
				if (MainControl.getInstance().getMarkedGUI() == null) {
					gui.setBackground(Color.yellow);
					MainControl.getInstance().setMarkedGUI(gui);
				} else {
					new KombiControl(gui.getGeoObject(), MainControl.getInstance().getMarkedGUI().getGeoObject());
					MainControl.getInstance().getMarkedGUI().setBackground(gui.getNormColor());
					MainControl.getInstance().setMarkedGUI(null);
				}
			}
		}

		if (evt.getButton() == 3) { // rechte Maustaste?
			ButtonGroup radio = new ButtonGroup();
			String[] labels = { "x2-x3-Ebene", "x1-x3-Ebene", "x1-x2-Ebene", "Alle", "x1-Achse", "x2-Achse", "x3-Achse",
					"Alle", "Editieren/Umbenennen", "Duplizieren", "Drucken" };
			JRadioButton[] buttons = new JRadioButton[12];
			Object[] options = new Object[19];
			for (int i = 0; i < 11; i++) {
				buttons[i] = new JRadioButton(labels[i]);
				radio.add(buttons[i]);
			}
			// options[0]="Spurgerade ...";
			JLabel lbl0 = new JLabel("Spurgerade...");
			options[0] = lbl0;
			// options 1,2,3,4 - buttons 0,1,2,3
			for (int i = 0; i < 4; i++) {
				options[i + 1] = buttons[i];
			}
			JLabel lbl5 = new JLabel("... übernehmen.");
			options[5] = lbl5;
			JSeparator sep6 = new JSeparator();
			options[6] = sep6;
			JLabel lbl7 = new JLabel("Spurpunkt...");
			options[7] = lbl7;
			// options 8,9,10,11 - buttons 4,5,6,7
			for (int i = 4; i < 8; i++) {
				options[i + 4] = buttons[i];
			}
			JLabel lbl12 = new JLabel("... übernehmen.");
			options[12] = lbl12;
			JSeparator sep13 = new JSeparator();
			options[13] = sep13;
			options[14] = "Objekt ...";
			// options 15,16,17,18 - buttons 8,9,10,11
			for (int i = 8; i < 12; i++) {
				options[i + 7] = buttons[i];
			}
			if (!(gui.getGeoObject() instanceof Ebene)) {
				for (int i = 0; i < 4; i++)
					buttons[i].setVisible(false);
				lbl0.setVisible(false);
				lbl5.setVisible(false);
				sep6.setVisible(false);
			}
			if (gui.getGeoObject() instanceof LAVector) {
				for (int i = 4; i < 8; i++)
					buttons[i].setVisible(false);
				lbl7.setVisible(false);
				lbl12.setVisible(false);
				sep13.setVisible(false);
			}
			JOptionPane.showConfirmDialog(gui, options, "Was soll passieren?", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE);
			int counter = 0;
			for (int i = 0; i < 11; i++) {
				if (!buttons[i].isSelected())
					counter++;
				else
					break;
			}
			if (counter<11) choiceAction(counter);
		}
	}

	public GeoGUI getGui() {
		return gui;
	}

	private void choiceAction(int i) {
		GeoObject geo = gui.getGeoObject();
		Point geoPos = gui.getLocation();
		switch (i) {
		case 0:
		case 1:
		case 2:
			if (geo instanceof Ebene) {
				Ebene e = (Ebene) geo;
				Gerade g = e.getSpurgerade(i);
				if (g.isLeerGerade()) {
					JOptionPane.showMessageDialog(gui, "Die Spurgerade " + (i + 1) + " existiert nicht!", "Fehler",
							JOptionPane.ERROR_MESSAGE);
				} else
					MainControl.getInstance().addObject(g, Etc.inc(geoPos));
			}
			break; // Spurgerade 0-2
		case 3:
			if (geo instanceof Ebene) {
				Ebene e = (Ebene) geo;
				for (int k = 0; k < 3; k++) {
					Gerade g = e.getSpurgerade(k);
					if (g.isLeerGerade()) {
						JOptionPane.showMessageDialog(gui, "Die Spurgerade " + (k + 1) + " existiert nicht!", "Fehler",
								JOptionPane.ERROR_MESSAGE);

					} else {
						geoPos = Etc.inc(geoPos);
						MainControl.getInstance().addObject(g, geoPos);
					}
				}
			}
			break; // alle Spurgeraden
		case 4:

		case 5:

		case 6:

		case 7:
			doSpurpunkt(geo, i - 4, geoPos);
			break; // alle Spurpunkte
		case 8:
			/*JOptionPane.showMessageDialog(gui, "Noch kein Code implementiert", "Sorry",
					JOptionPane.INFORMATION_MESSAGE);*/
			doEdit(geo);
			break; // Editieren
		case 9:
			duplicate(geo);
			break; // Duplizieren
		default:
			new Printer(this.getGui().print()); // Drucken
		}

	}
	
	
	private void doEdit(GeoObject geo) {
		if (geo instanceof Ebene) {
			Ebene e = (Ebene)geo;
			MainControl.getInstance().removeObject(this.gui);
			new DLGEbeneControl(MainGUI.getInstance(),e);
		}
		if (geo instanceof Gerade) {
			Gerade g = (Gerade)geo;
			MainControl.getInstance().removeObject(this.gui);
			new DLGGeradeControl(MainGUI.getInstance(),g);
		}if (geo instanceof LAVector) {
			LAVector v = (LAVector)geo;
			MainControl.getInstance().removeObject(this.gui);
			new DLGVektorControl(MainGUI.getInstance(),v);
		}
	}

	private void doSpurpunkt(GeoObject geo, int i, Point p) {
		if (geo instanceof Ebene) {
			Ebene e = (Ebene) geo;
			if (i < 3)
				displaySpurPunkt(e, i, Etc.inc(p));
			else
				for (int z = 0; z < 3; z++) {
					p = Etc.inc(p);
					displaySpurPunkt(e, z, p);
				}
		}
		if (geo instanceof Gerade) {
			Gerade g = (Gerade) geo;
			if (i < 3) {
				this.displaySpurPunkt(g, i, Etc.inc(p));
			} else
				for (int z = 0; z < 3; z++) {
					p = Etc.inc(p);
					this.displaySpurPunkt(g, z, p);
				}
		}
	}

	private void displaySpurPunkt(Ebene e, int i, Point p) {
		LAVector v = e.getAchsenSchnittpunkt(i);
		if (!(v.equals(LAVector.noVector))) {
			v.setName("A" + (i + 1));
			MainControl.getInstance().addObject(v, p);
			Init.setChange(true);
		} else
			JOptionPane.showMessageDialog(gui, "Der Achsenschnittpunkt " + (i + 1) + " existiert nicht!", "Fehler",
					JOptionPane.ERROR_MESSAGE);
	}

	private void displaySpurPunkt(Gerade g, int i, Point p) {
		LAVector v = g.getSpurpunkt(i);
		if (!(v.equals(LAVector.noVector))) {
			v.setName("S" + (i + 1));
			MainControl.getInstance().addObject(v, p);
			Init.setChange(true);
		} else
			JOptionPane.showMessageDialog(gui, "Der Spurpunkt " + (i + 1) + " existiert nicht!", "Fehler",
					JOptionPane.ERROR_MESSAGE);
	}

	private void duplicate(GeoObject geo) {
		if (geo instanceof Ebene) {
			Ebene e = (Ebene) geo;
			try {
				MainControl.getInstance().addObject(new Ebene(e));
			} catch (RVKollinearException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return;
			}
		}
		if (geo instanceof Gerade) {
			Gerade g = (Gerade) geo;
			MainControl.getInstance().addObject(new Gerade(g));
			return;
		}
		LAVector v = (LAVector) geo;
		MainControl.getInstance().addObject(new LAVector(v));
	}

}
