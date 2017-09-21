package gui;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JTextPane;

import net.miginfocom.swing.MigLayout;

public class HelpGUI extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextPane textArea;
	private String helpText = "";
	private JButton btnClose = new JButton("Schließen");
	
	public HelpGUI(String text){
		this.setLayout(new MigLayout());
		this.helpText = text;
		this.textArea = new JTextPane();
		this.textArea.setContentType("text/html");
		this.textArea.setText(this.helpText);
		this.add(textArea, "wrap");
		this.add(btnClose, "alignc, wrap");
		this.pack();
	}

	public JButton getBtnClose() {
		return btnClose;
	}

	public JTextPane getTextArea() {
		return textArea;
	}

}
