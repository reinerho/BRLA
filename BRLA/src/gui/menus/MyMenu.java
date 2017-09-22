package gui.menus;

import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public abstract class MyMenu extends JMenu {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	protected  Vector<JMenuItem> menuList = new Vector<JMenuItem>();

	public MyMenu(){
		super();
	}
	
	public MyMenu(String titel){
		super(titel);
	}
	
	public void addActionListeners(ActionListener l){
		for (JMenuItem m:menuList){
			if (m instanceof MyMenuItem) m.addActionListener(l);
			else ((MyMenu)m).addActionListeners(l);
		}
	}
	
	public void doubleadd(JMenuItem item){
		this.add(item);
		menuList.addElement(item);
	}
	
	public void doubleRemove(JMenuItem item){
		this.remove(item);
		menuList.removeElement(item);
	}

	public Vector<JMenuItem> getMenuList() {
		return menuList;
	}
	
	public int  getItemPos(String text){
		for (int i = 0; i<this.getItemCount(); i++){
			if (this.getItem(i).getText().equals(text)) return i;
		}
		return -1;
	}
	
}
