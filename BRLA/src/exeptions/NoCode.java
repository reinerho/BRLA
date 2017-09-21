package exeptions;

import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class NoCode {

	public static void message(Frame frame) {
		JOptionPane.showMessageDialog(frame, "Noch kein Code implementiert!", "Sorry", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void message(JDialog dialog) {
		JOptionPane.showMessageDialog(dialog, "Noch kein Code implementiert!", "Sorry",
				JOptionPane.INFORMATION_MESSAGE);
	}
}
