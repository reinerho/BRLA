package gui;

import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;

import geometrie.GeoObject;
import gui.control.KombiDisplay;
import net.miginfocom.swing.MigLayout;

public class KombiGUI extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private GeoObject geo1;
	private GeoObject geo2;
	private GeoArea resultArea = null;
	private GeoObject geoResult = null;
	private JButton btnClose = new JButton("Schließen");
	private JButton btnTake = new JButton("Ergebnis übernehmen");
	private JButton btnPrint = new JButton("Drucken");
	private HashMap<String, GeoObject> map = new HashMap<String, GeoObject>();

	public static enum Schnittmode {
		EbeneEbeneSchnitt, EbeneEbeneParallel, EbeneEbeneIndent, EbeneGeradeSchnitt, EbeneGeradeParallel, EbeneGeradeTeilmenge, EbeneVektorIn, EbeneVektorOut, GeradeGeradeSchnitt, GeradeGeradeParallel, GeradeGeradeIdent, GeradeGeradeWindschief, GeradeVektorIn, GeradeVektorOut, VektorVektorIdent, VektorVektorOut, VektorVektorKollinear
	};

	private Schnittmode mode;

	public KombiGUI(GeoObject g1, GeoObject g2) {
		MigLayout layout = new MigLayout();
		this.setLayout(layout);
		this.geo1 = g1;
		this.geo2 = g2;
		GeoArea gA1 = new GeoArea(this.geo1);
		this.add(gA1, "alignc");
		GeoArea gA2 = new GeoArea(this.geo2);
		this.add(gA2, "alignc, wrap");
		gA1.setEditable(false);
		gA2.setEditable(false);
		switch (this.geo1.getLevel()) {
		case GeoObject.VEKTOR:
			switch (this.geo2.getLevel()) {
			case GeoObject.VEKTOR:
				KombiDisplay.doVektorAndVektor(this, geo1, geo2);
				break;
			case GeoObject.GERADE:
				KombiDisplay.doVektorAndGerade(this, geo1, geo2);
				break;
			default:
				KombiDisplay.doVektorAndEbene(this, geo1, geo2);
			}
			break;
		case GeoObject.GERADE:
			if (this.geo2.getLevel() == GeoObject.GERADE)
				KombiDisplay.doGeradeAndGerade(this, geo1, geo2);
			else
				KombiDisplay.doGeradeAndEbene(this, geo1, geo2);
			break;
		default:
			KombiDisplay.doEbeneAndEbene(this, this.geo1, this.geo2);
		}
		this.add(btnClose, "alignc");
		this.add(btnPrint, "alignc");
		if (resultArea != null) {
			this.add(btnTake, "alignc, wrap");
			this.resultArea.setEditable(false);
		} else
			this.add(new JLabel(), "wrap");
		pack();
	}
	
	public int getMapCount() {
		Iterator<String> it = map.keySet().iterator();
		int counter = 0;
		while (it.hasNext()) {
			it.next();
			counter++;
		}
		//System.out.println("KombiGUI.getMapCount: "+counter);
		return counter;
	}

	public GeoArea getResultArea() {
		return resultArea;
	}

	public void setResultArea(GeoArea resultArea) {
		this.resultArea = resultArea;
	}

	public JButton getBtnClose() {
		return btnClose;
	}

	public JButton getBtnTake() {
		return btnTake;
	}

	public JButton getBtnPrint() {
		return btnPrint;
	}

	public GeoObject getGeoResult() {
		return geoResult;
	}

	public void setGeoResult(GeoObject geoResult) {
		this.geoResult = geoResult;
	}

	public HashMap<String, GeoObject> getMap() {
		return map;
	}

	public Schnittmode getMode() {
		return mode;
	}

	public void setMode(Schnittmode mode) {
		this.mode = mode;
	}

}
