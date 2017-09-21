package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import javax.swing.JInternalFrame;

public class GrafikGUI extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public GrafikGUI(){
		super("Grafik", true, false, true, true);
		//Surface surface = new Surface();
		//this.getContentPane().add(surface);
		//Graphics2D gr = (Graphics2D) this.getGraphics();
		this.setSize(new Dimension(200,200));
		this.paint(this.getContentPane().getGraphics());
		pack();
	}
	
	public void paint(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.draw(new Line2D.Double(10, 10, 100, 100));
	}

}
