package gui.menus;

import java.awt.event.KeyEvent;

public class HelpMenu extends MyMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private HelpHelpMenu helphelpMenu = HelpHelpMenu.getInstance();

	private MyMenuItem aboutItem = new MyMenuItem("Über",
			"Versionsinfos", KeyEvent.VK_B);
	
	
	private static HelpMenu instance;
	
	public static HelpMenu getInstance(){
		if (instance == null) instance = new HelpMenu();
		return instance;
	}

	private HelpMenu(){
		this.setText("Hilfe");
		this.doubleadd(helphelpMenu);
		this.doubleadd(aboutItem);
	}

	public MyMenuItem getAboutItem() {
		return aboutItem;
	}
	
}
