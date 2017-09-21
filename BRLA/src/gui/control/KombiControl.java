package gui.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import geometrie.Ebene;
import geometrie.GeoObject;
import geometrie.Gerade;
import geometrie.LAVector;
import gui.KombiGUI;
import tools.Init;

public class KombiControl {

	private KombiGUI gui;
	private GeoObject go1, go2;

	public KombiControl(GeoObject go1, GeoObject go2) {
		this.go1 = go1;
		this.go2 = go2;
		if (this.go1.getLevel() > this.go2.getLevel()) { // Wenn geo1 höheres
															// Level hat, als
															// geo2 -> tauschen
			GeoObject tmp = this.go1;
			this.go1 = this.go2;
			this.go2 = tmp;
		}
		this.gui = new KombiGUI(this.go1, this.go2);
		this.gui.getBtnClose().addActionListener(bl);
		this.gui.getBtnPrint().addActionListener(bl);
		if (gui.getBtnTake() != null)
			gui.getBtnTake().addActionListener(bl);
		MainControl.getInstance().getMaingui().add(gui);
		gui.setVisible(true);
	}

	ActionListener bl = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent evt) {
			btnAction(evt);
		}
	};

	/**
	 * Aktionen beim Anklicken eines Buttons auf der KombiGUI.
	 * 
	 * Beim Klicken von btnClose ("Schließen") wird die KombiGUI geschlossen und die
	 * Ergebnisse verworfen
	 * 
	 * Beim Klicken von btnTake wird das erzeugte GeoObjekt als eigenständiges
	 * Objekt auf den Desktop gelegt. Gibt es mehrere Möglichkeiten, wird ein
	 * Auswahlmenü präsentiert.
	 * 
	 * @param evt
	 *            der Event, der die Aktion auslöst
	 */
	private void btnAction(ActionEvent evt) {
		JButton btn = (JButton) evt.getSource();
		Init.setChange(Init.CHANGE);
		if (btn == gui.getBtnClose()) {
			gui.setVisible(false);
			gui.dispose();
			return;
		}
		if (btn == gui.getBtnTake() && !gui.getMap().isEmpty()) {
			GeoObject geo = null;
			if (gui.getMapCount() > 1) {
				this.geoMenu();
				geo = gui.getGeoResult();
			}else {
				Iterator<String> i = gui.getMap().keySet().iterator();
				geo = gui.getMap().get((String) i.next());
			}
			MainControl.getInstance().addObject(geo);
		}
		if (btn == gui.getBtnPrint()) {
			/*System.out.println(this.go1.getName() + ": Level " + this.go1.getLevel() + " // " + this.go2.getName()
					+ ": Level " + this.go2.getLevel());*/
			switch (gui.getMode()) {
			case EbeneEbeneIndent:
				;
			case EbeneEbeneParallel:
				;
			case EbeneEbeneSchnitt:
				new KombiPrint().printEbeneEbene((Ebene) this.go1, (Ebene) this.go2, gui.getMode());
				break;
			case EbeneGeradeParallel:
				;
			case EbeneGeradeSchnitt:
				;
			case EbeneGeradeTeilmenge:
				new KombiPrint().printGeradeEbene((Gerade) this.go1, (Ebene) this.go2, gui.getMode());
				break;
			case EbeneVektorIn:
				;
			case EbeneVektorOut:
				new KombiPrint().printVektorEbene((LAVector) this.go1, (Ebene) this.go2, gui.getMode());
				break;
			case GeradeGeradeIdent:
				;
			case GeradeGeradeParallel:
				;
			case GeradeGeradeSchnitt:
				;
			case GeradeGeradeWindschief:
				new KombiPrint().printGeradeGerade((Gerade) this.go1, (Gerade) this.go2, gui.getMode());
				break;
			case GeradeVektorIn:
				;
			case GeradeVektorOut:
				new KombiPrint().printGeradeVektor((Gerade) this.go1, (LAVector) this.go2, gui.getMode());
				break;
			case VektorVektorIdent:
				;
			case VektorVektorKollinear:
				;
			case VektorVektorOut:
				new KombiPrint().printVektorVektor((LAVector) this.go1, (LAVector) this.go2, gui.getMode());
				break;
			default:
				JOptionPane.showMessageDialog(gui, "Noch kein Code implementiert", "Schade",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	/**
	 * Zeigt ein JOPtionPane, in dem das GeoObjekt ausgewählt werden kann, das als
	 * eigenständiges Objekt auf den Desktop übernommen wird.
	 */
	private void geoMenu() {
		Iterator<String> it = gui.getMap().keySet().iterator();
		Object[] object = new Object[gui.getMap().size() + 1];
		JRadioButton[] rButtons = new JRadioButton[gui.getMap().size()];
		object[0] = "Welches Ergebnis soll übernommen werden?";
		ButtonGroup bGroup = new ButtonGroup();
		int counter = 1;
		while (it.hasNext()) {
			String s = it.next();
			rButtons[counter - 1] = new JRadioButton(s);
			bGroup.add(rButtons[counter - 1]);
			object[counter] = rButtons[counter - 1];
			counter++;
		}
		JOptionPane.showMessageDialog(gui, object, "Objekt-Auswahl", JOptionPane.QUESTION_MESSAGE);
		for (JRadioButton r : rButtons) {
			if (r.isSelected()) {
				gui.setGeoResult(gui.getMap().get(r.getText()));
			}
		}
	}
}
