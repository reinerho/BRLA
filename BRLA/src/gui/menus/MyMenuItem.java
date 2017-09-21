package gui.menus;

import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

public class MyMenuItem extends JMenuItem {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int parentID = -1;

	public MyMenuItem(String text, String toolTip, int mnemonic, ActionListener l) {
		super(text);
		this.setToolTipText(toolTip);
		this.setMnemonic(mnemonic);
		this.addActionListener(l);
	}

	public MyMenuItem(String text, String toolTip, int mnemonic) {
		super(text);
		this.setToolTipText(toolTip);
		this.setMnemonic(mnemonic);
	}

	public MyMenuItem(String text, String toolTip, int mnemonic, int parentID) {
		super(text);
		this.setToolTipText(toolTip);
		this.setMnemonic(mnemonic);
		this.parentID = parentID;
	}

	public int getParentID() {
		return this.parentID;
	}

}
