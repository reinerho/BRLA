package gui.menus;

import java.awt.event.KeyEvent;

public class ObjectMenu extends MyMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MyMenuItem vektorItem = new MyMenuItem("Vektor",
			"Erzeugt einen neuen Vektor", KeyEvent.VK_V);
	private MyMenuItem geradenItem = new MyMenuItem("Gerade",
			"Erzeugt eine neue Gerade", KeyEvent.VK_G);
	private MyMenuItem ebenenItem = new MyMenuItem("Ebene",
			"Erzeugt eine neue Ebene", KeyEvent.VK_E);
	
	private static ObjectMenu instance;
	
	public static ObjectMenu getInstance(){
		if (instance == null) instance = new ObjectMenu();
		return instance;
	}
	private ObjectMenu() {
		this.setText("Objekt");
		this.doubleadd(vektorItem);
		this.doubleadd(geradenItem);
		this.doubleadd(ebenenItem);
	}
	public MyMenuItem getVektorItem() {
		return vektorItem;
	}
	public MyMenuItem getGeradenItem() {
		return geradenItem;
	}
	public MyMenuItem getEbenenItem() {
		return ebenenItem;
	}
	

}
