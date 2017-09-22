package gui.dialog.control;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import geometrie.Ebene;
import geometrie.GeoObject;
import geometrie.Gerade;
import gui.GeoGUI;
import gui.control.MainControl;
import gui.dialog.DLGTake;
import tools.Init;

public class DLGTakeControl {

	DLGTake dialog;
	Ebene ebene;
	Gerade gerade;
	GeoGUI iFrame;

	public DLGTakeControl(GeoGUI iFrame, String title, GeoObject geo, JFrame owner) {
		dialog = new DLGTake(iFrame, title, geo, owner);
		this.iFrame = iFrame;
		if (geo instanceof Ebene)
			ebene = (Ebene) geo;
		else
			gerade = (Gerade) geo;
		if (ebene != null)
			//System.out.println(ebene);
		for (int i = 0; i < 4; i++) {
			dialog.getBtnGerade()[i].addActionListener(l);
			dialog.getBtnPunkt()[i].addActionListener(l);
		}
		dialog.setVisible(true);
	}

	private ActionListener l = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent evt) {
			action(evt);
		}
	};

	private void action(ActionEvent evt) {
		JButton btn = (JButton) evt.getSource();
		for (int i = 0; i < 3; i++) {
			if (btn == dialog.getBtnGerade()[i]) {
				MainControl.getInstance().addObject(ebene.getSpurgerade(i));
				Init.setChange(true);
			}
			if (btn == dialog.getBtnPunkt()[i]) {
				if (ebene != null) {
					MainControl.getInstance().addObject(ebene.getAchsenSchnittpunkt(i));
				} else {
					MainControl.getInstance().addObject(gerade.getSpurpunkt(i));
				}
				Init.setChange(true);
			}
		}
		if (btn == dialog.getBtnGerade()[3]) {
			Point p = iFrame.getLocation();
			for (int i = 0; i < 3; i++) {
				MainControl.getInstance().addObject(ebene.getSpurgerade(i), increment(p, 30 * (i + 1)));
			}
			Init.setChange(true);
		}
		if (btn == dialog.getBtnPunkt()[3]) {
			Point p = iFrame.getLocation();
			if (ebene != null) {
				for (int i = 0; i < 3; i++) {
					MainControl.getInstance().addObject(ebene.getAchsenSchnittpunkt(i), increment(p, 30 * (i + 1)));
				}
			} else
				for (int i = 0; i < 3; i++) {
					MainControl.getInstance().addObject(gerade.getSpurpunkt(i), increment(p, 30 * (i + 1)));
				}
			Init.setChange(true);
		}
		dialog.setVisible(false);
		dialog.dispose();
	}

	private Point increment(Point p, int step) {
		int x = (int) p.getX() + step;
		int y = (int) p.getY() + step;
		return new Point(x, y);
	}

}
