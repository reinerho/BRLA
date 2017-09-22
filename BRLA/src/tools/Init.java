package tools;

import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.jdom.Element;
import org.jdom.JDOMException;

import exeptions.FileOpenNoSuccessException;
import gui.MainGUI;

public class Init {

	private static String iniFile = "BRLA.ini";
	private static String dataPath;
	public static String separator = System.getProperty("file.separator");
	private final static String XML_DATA_PATH = "DataPath";
	private final static String XML_COPYRIGHT = "ShowCopyright";
	public static boolean copyRight = true;
	public static boolean change = false;
	public static final boolean CHANGE = true;
	public static final boolean NOCHANGE = false;
	public static Point screenLocation;
	public static Dimension screenSize;
	private static String filename = null;
	public static final String TMP = "tmp";

	private static Init instance;

	public static Init getInstance() {
		if (instance == null)
			instance = new Init();
		return instance;
	}

	public final static String VERSION  = "0.2.1";
	public final static String DATUM = "17.09.2017";
	public final static String COPYRIGHT =" \u00A9";

	private Init() {
		try {
			this.load();
		} catch (JDOMException | FileOpenNoSuccessException e) {
			JOptionPane.showMessageDialog(null, "Ich finde kein \".ini\"-File\nArbeite mit Standardeinstellungen!",
					"Fehler", JOptionPane.ERROR_MESSAGE);
			setCopyRight(true);
		}
	}

	private static Element xml() {
		Element root = new Element("Settings");
		Element datapath = new Element(XML_DATA_PATH);
		datapath.addContent(dataPath);
		root.addContent(datapath);
		Element copy = new Element(XML_COPYRIGHT);
		copy.addContent(copyRight + "");
		root.addContent(copy);
		setScreenSize(MainGUI.getInstance().getSize());
		Element screen = new Element("ScreenSize");
		Element x = new Element("x");
		x.setText(getScreenSize().getWidth() + "");
		Element y = new Element("y");
		y.setText(getScreenSize().getHeight() + "");
		screen.addContent(x);
		screen.addContent(y);
		root.addContent(screen);
		setScreenLocation(MainGUI.getInstance().getLocation());
		screen = new Element("Location");
		x=new Element("x");
		x.setText(getScreenLocation().getX()+"");
		screen.addContent(x);
		y=new Element("y");
		y.setText(getScreenLocation().getY()+"");
		screen.addContent(y);
		root.addContent(screen);
		return root;
	}

	private void load() throws JDOMException, FileOpenNoSuccessException {
		Element root = XML.load(iniFile);
		setDataPath(root.getChildText(XML_DATA_PATH));
		setScreenParameter(root);
		if (!new File(getDataPath()).isDirectory()) {
			setDataPath(System.getProperty("user.home"));
			JOptionPane.showMessageDialog(MainGUI.getInstance(),
					"Der gespeicherte Datenpfad existiert nicht!\nIch benutze Standardeinstellungen", "Achtung!",
					JOptionPane.WARNING_MESSAGE);
		}
		setCopyRight(root.getChildText(XML_COPYRIGHT).equals("true"));
	}

	private void setScreenParameter(Element root){
		int x = (int) MyMath.toDouble((root.getChild("ScreenSize").getChildText("x")));
		int y = (int) MyMath.toDouble(root.getChild("ScreenSize").getChildText("y"));
		setScreenSize(new Dimension(x,y));
		x = (int) MyMath.toDouble((root.getChild("Location").getChildText("x")));
		y = (int) MyMath.toDouble(root.getChild("Location").getChildText("y"));
		setScreenLocation(new Point(x,y));
	}
	
	public static void save() {
		try {
			XML.save(iniFile, xml());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void shutdown() {
		if (change) {
			int result = JOptionPane.showConfirmDialog(MainGUI.getInstance(), "Es gibt Änderungen!\n\nSpeichern?",
					"Warnung", JOptionPane.YES_NO_CANCEL_OPTION);
			switch (result) {
			case JOptionPane.CANCEL_OPTION:
				return;
			case JOptionPane.YES_OPTION:
				FileTools.save(Init.getFilename());
			default:
			}
		}
		save();
		System.exit(0);
	}

	public static String getIniFile() {
		return iniFile;
	}

	public static void setIniFile(String iniFile) {
		Init.iniFile = iniFile;
	}

	public static String getDataPath() {
		return dataPath;
	}

	public static void setDataPath(String dataPath) {
		Init.dataPath = dataPath;
	}

	public static boolean isCopyRight() {
		return copyRight;
	}

	public static void setCopyRight(boolean showCopyRight) {
		Init.copyRight = showCopyRight;
	}

	public static String getSeparator() {
		return separator;
	}

	public static boolean isChange() {
		return change;
	}

	public static void setChange(boolean change) {
		Init.change = change;
	}

	public static Point getScreenLocation() {
		return screenLocation;
	}

	public static void setScreenLocation(Point screenLocation) {
		Init.screenLocation = screenLocation;
	}

	public static Dimension getScreenSize() {
		return screenSize;
	}

	public static void setScreenSize(Dimension screenSize) {
		Init.screenSize = screenSize;
	}

	public static String getFilename() {
		return filename;
	}

	public static void setFilename(String filename) {
		Init.filename = filename;
	}

}
