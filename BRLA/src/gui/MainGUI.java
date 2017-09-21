package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import gui.menus.FileMenu;
import gui.menus.HelpMenu;
import gui.menus.MyMenu;
import gui.menus.ObjectMenu;

public class MainGUI extends JFrame implements Printable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JMenuBar mBar = new JMenuBar();
	// private Menu fileMenu = new FileMenu("Datei",KeyEvent.VK_D,
	// "Dateioperationen");
	private FileMenu fileMenu = FileMenu.getInstance();
	private ObjectMenu objectMenu = ObjectMenu.getInstance();
	private JMenu windowMenu = new JMenu("Fenster");
	private HelpMenu helpMenu = HelpMenu.getInstance();
	private JDesktopPane desktop;

	private static MainGUI instance;

	public static MainGUI getInstance() {
		if (instance == null)
			instance = new MainGUI();
		return instance;
	}

	private MainGUI() {
		super("Programm zur Vektorrechnung");
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		desktop = new JDesktopPane();
		desktop.setBackground(Color.LIGHT_GRAY);
		this.setContentPane(desktop);
		this.getRootPane().setJMenuBar(mBar);
		mBar.add(fileMenu);
		fileMenu.setMnemonic(KeyEvent.VK_D);
		mBar.add(objectMenu);
		objectMenu.setMnemonic(KeyEvent.VK_O);
		mBar.add(windowMenu);
		windowMenu.setMnemonic(KeyEvent.VK_F);
		mBar.add(helpMenu);
		helpMenu.setMnemonic(KeyEvent.VK_H);
		Dimension screensize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		this.setPreferredSize(screensize);
		this.pack();
	}

	public MyMenu getObjectMenu() {
		return objectMenu;
	}

	public MyMenu getFileMenu() {
		return fileMenu;
	}

	public JMenu getWindowMenu() {
		return windowMenu;
	}

	//TODO das macht nix!
	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		Graphics2D g2d = (Graphics2D) graphics;
		double scaleVertical = pageFormat.getHeight() / getHeight();
		double scaleHorizontal = pageFormat.getWidth() / getWidth();
		if (scaleHorizontal < scaleVertical) {
			g2d.scale(scaleHorizontal, scaleHorizontal);
		} else {
			g2d.scale(scaleVertical, scaleVertical);
			printComponents(g2d);
			g2d.dispose();
			return PAGE_EXISTS;
		}
		return 0;
	}

}
