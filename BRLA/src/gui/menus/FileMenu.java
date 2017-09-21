package gui.menus;

import java.awt.event.KeyEvent;

public class FileMenu extends MyMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MyMenuItem saveItem = new MyMenuItem("Speichern", "Speichert alle Daten", KeyEvent.VK_S);
	private MyMenuItem saveAsItem = new MyMenuItem("Speichern unter ...", "Speichert alle Daten in einer neuen Datei",
			KeyEvent.VK_U);
	private MyMenuItem openItem = new MyMenuItem("Öffnen", "Öffnet eine Geometriedatei", KeyEvent.VK_F);
	private MyMenuItem newItem = new MyMenuItem("Neu", "Löscht alle Daten", KeyEvent.VK_N);
	private MyMenuItem printItem = new MyMenuItem("Drucken", "Druckt alle Objekte", KeyEvent.VK_D);
	private MyMenuItem shutdownItem = new MyMenuItem("Beenden", "Beendet das Programm", KeyEvent.VK_E);

	private static FileMenu instance;

	public static FileMenu getInstance() {
		if (instance == null)
			instance = new FileMenu();
		return instance;
	}

	private FileMenu() {
		this.setText("Datei");
		this.doubleadd(saveItem);
		this.doubleadd(saveAsItem);
		this.doubleadd(openItem);
		this.doubleadd(newItem);
		this.doubleadd(printItem);
		this.doubleadd(shutdownItem);
	}

	public MyMenuItem getSaveItem() {
		return saveItem;
	}

	public MyMenuItem getSaveAsItem() {
		return saveAsItem;
	}

	public MyMenuItem getOpenItem() {
		return openItem;
	}

	public MyMenuItem getNewItem() {
		return newItem;
	}

	public MyMenuItem getShutdownItem() {
		return shutdownItem;
	}

	public MyMenuItem getPrintItem() {
		return printItem;
	}

}
