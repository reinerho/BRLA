package exeptions;

import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class ExceptionDialog {
	
	public ExceptionDialog(Frame frame, Exception e){
		JOptionPane.showMessageDialog(frame, e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
	}
	public ExceptionDialog(JDialog dlg, Exception e){
		JOptionPane.showMessageDialog(dlg, e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
	}
}
