package gui.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.HelpGUI;
import gui.MainGUI;

public class HelpGUIControl {
	
	private HelpGUI gui ;
	
	public HelpGUIControl(String text, String title){
		gui = new HelpGUI(text);
		gui.setTitle(title);
		gui.getBtnClose().addActionListener(bl);
		MainGUI.getInstance().add(gui);
		gui.setLocation((MainGUI.getInstance().getWidth()-this.gui.getWidth())/2, (MainGUI.getInstance().getHeight()-this.gui.getHeight())/2);
		gui.setVisible(true);
		gui.getTextArea().setEditable(false);
	}

	ActionListener bl = new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent evt) {
			buttonAction(evt);
		}
		
	};
	
	private void buttonAction(ActionEvent evt){
		gui.setVisible(false);
		gui.dispose();
	}
	
}
