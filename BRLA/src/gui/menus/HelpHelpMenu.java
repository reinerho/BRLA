package gui.menus;

import java.awt.event.KeyEvent;

public class HelpHelpMenu  extends MyMenu{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private MyMenuItem allItem = new MyMenuItem("Allgemein",
			"", KeyEvent.VK_A);
	private MyMenuItem fileItem = new MyMenuItem("Datei",
			"Hilfe zum Dateimenü", KeyEvent.VK_D);
	private MyMenuItem objectItem = new MyMenuItem("Objekt",
			"Hilfe zum Objekt-Menü", KeyEvent.VK_O);
	private MyMenuItem windowItem = new MyMenuItem("Fenster",
			"Hilfe zum Fenstermenü", KeyEvent.VK_F);
	
private static HelpHelpMenu instance;
	
	public static HelpHelpMenu getInstance(){
		if (instance == null) instance = new HelpHelpMenu();
		return instance;
	}

	private HelpHelpMenu(){
		this.setText("Hilfe");
		this.doubleadd(allItem);
		this.doubleadd(fileItem);
		this.doubleadd(objectItem);
		this.doubleadd(windowItem);
	}

	public MyMenuItem getAllItem() {
		return allItem;
	}

	public MyMenuItem getFileItem() {
		return fileItem;
	}

	public MyMenuItem getObjectItem() {
		return objectItem;
	}

	public MyMenuItem getWindowItem() {
		return windowItem;
	}

	
	
}
