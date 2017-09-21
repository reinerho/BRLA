package gui;

import java.awt.Color;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;

import org.jdom.Element;

import geometrie.GeoObject;
import net.miginfocom.swing.MigLayout;
import tools.XML;

/**
 * @author hornischer
 *
 */
public abstract class GeoGUI extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// protected JTextArea darst;
	protected GeoArea darst;
	protected String name;
	protected GeoObject o;
	protected JButton btnClose = new JButton("Schließen");
	//protected JButton btnPrint = new JButton("Drucken");
	//protected JButton btnGet = new JButton("Übernehmen...");
	protected Color normColor = new Color(238, 238, 238);
	protected String XMLROOT = "GeoGUI";
	protected JMenuItem menuItem = new JMenuItem("GeoObject");

	public GeoGUI(String name, GeoObject o) {
		super(name, true, false, true, true);
		this.name = name;
		init(o);
	}

	public GeoGUI(GeoObject o) {
		super("Name", true, false, true, true);
		this.name = o.getName();
		init(o);
	}

	protected void init(GeoObject o) {
		this.o = o;
		this.darst = new GeoArea(o, extractName(name));
		this.setLayout(new MigLayout());
		this.add(darst, "span, alignc, wrap");
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}

	public void rename(String name) {
		this.setTitle(name);
		this.getDarst().rename(extractName(name));
		this.getMenuItem().setText(name);
	}

	public static String extractName(String s) {
		String out = "";
		if (s.contains("Ebene "))
			out = s.replace("Ebene ", "");
		if (s.contains("Gerade "))
			out = s.replace("Gerade ", "");
		if (s.contains("Vektor "))
			out = s.replace("Vektor ", "");
		return out;
	}

	public Element xml() {
		Element root = o.xml();
		Element pos = new Element("Position");
		pos.addContent(XML.myElement("x", this.getX()));
		pos.addContent(XML.myElement("y", this.getY()));
		root.addContent(pos);
		return root;
	}

	public GeoObject getGeoObject() {
		return o;
	}

	public double getID() {
		return o.getiD();
	}

	public void setID(int id) {
		this.o.setiD(id);
	}

	public Color getNormColor() {
		return normColor;
	}

	public GeoArea getDarst() {
		return darst;
	}

	public String getName() {
		return name;
	}

	public JButton getBtnClose() {
		return btnClose;
	}

	/*public JButton getBtnPrint() {
		return btnPrint;
	}

	public JButton getBtnGet() {
		return btnGet;
	}*/

	public JMenuItem getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(JMenuItem item) {
		this.menuItem = item;
	}

	public Vector<String> print(){
		return this.getGeoObject().print();
	}

}
