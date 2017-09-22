package gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import geometrie.GeoObject;

public class GeoArea extends JTextArea {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private GeoObject geo = null;
	TitledBorder tBorder = null;
	
	public GeoArea(GeoObject go, String name){
		super(go.toString());
		this.geo = go;
		tBorder = new TitledBorder(new LineBorder(Color.blue),name);
		this.setBorder(tBorder);
		this.setFont(new Font(Font.MONOSPACED,Font.PLAIN,12));
		this.setAlignmentX(CENTER_ALIGNMENT);
	}

	public void rename(String name){
		tBorder.setTitle(name);
	}
	
	public GeoArea(GeoObject go){
		this(go,go.getName());
	}

	public GeoObject getGeo() {
		return geo;
	}
	
}
