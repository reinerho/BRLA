package gui.control;

import javax.swing.JLabel;

import geometrie.Ebene;
import geometrie.GeoObject;
import geometrie.Gerade;
import geometrie.LAVector;
import gui.GeoArea;
import gui.KombiGUI;
import gui.KombiGUI.Schnittmode;
import tools.MyMath;

public class KombiDisplay {

	private static GeoObject geoResult = null;


	private static JLabel schnittpunkt(GeoObject go1, GeoObject go2) {
		if (!(go1 instanceof Gerade))
			return null;
		LAVector sp = null;
		if (go2 instanceof Gerade)
			sp = ((Gerade) go1).getSchnittPunkt((Gerade) go2);
		else
			sp = ((Gerade) go1).getSchnittPunkt((Ebene) go2);
		return new JLabel(
				go1.getName() + " und " + go2.getName() + " schneiden sich im Punkt " + sp.toPointString(true));
	}

	private static JLabel lageBeziehung(GeoObject go1, GeoObject go2, String lage) {
		return new JLabel(go1.getName() + " und " + go2.getName() + " sind " + lage);
	}

	/**
	 * Zwei Ebenen werden verglichen (geo1 und geo2).
	 * 
	 * Sind sie identisch gibt es nur eine Meldung
	 * 
	 * Sind sie Parallel gibt es eine Meldung und die Angabe des Abstands
	 * 
	 * Schneiden sie sich, wird Schnittwinkel und Schnittgerade angegeben
	 */
	public static void doEbeneAndEbene(KombiGUI gui, GeoObject go1, GeoObject go2) {
		Ebene ebene1 = (Ebene) go1;
		Ebene ebene2 = (Ebene) go2;
		if (ebene1.isPartOf(ebene2)) {
			// gui.add(new JLabel("Die Ebenen sind identisch!"), "span,
			// wrap,alignc");
			gui.add(lageBeziehung(go1, go2, "identisch"), "span, wrap,alignc");
			gui.setMode(Schnittmode.EbeneEbeneIndent);
			return;
		}
		if (ebene1.isParallel(ebene2)) {
			// gui.add(new JLabel("Die Ebenen sind parallel!"), "span,
			// wrap,alignc");
			gui.add(lageBeziehung(go1, go2, "parallel"), "span, wrap,alignc");
			gui.add(new JLabel("Der Abstand beträgt: " + ebene1.getAbstand(ebene2)), "span, wrap,alignc");
			gui.setMode(Schnittmode.EbeneEbeneParallel);
			return;
		}
		gui.add(new JLabel("Die Ebenen schneiden sich in der Geraden:"), "span, wrap,alignc");
		gui.setMode(Schnittmode.EbeneEbeneSchnitt);
		geoResult = ebene1.getSchnittGerade(ebene2);
		gui.getMap().put("Schnittgerade", geoResult);
		geoResult.setName(ebene1.getName() + "-" + ebene2.getName());
		//System.out.println("KombiDisplay.doEbeneAndEbene: "+geoResult);
		gui.setResultArea(new GeoArea(geoResult));
		KombiDisplay.addResultArea(gui, geoResult);
		gui.add(new JLabel("Der Schnittwinkel beträgt: "
				+ ebene1.getNormalenVektor().winkel(ebene2.getNormalenVektor(), LAVector.GRAD) + "°"),
				"span, wrap,alignc");
	}

	/**
	 * Gerade und Ebene werden verglichen (geo1 und geo2).
	 * 
	 * Liegt g in E gibt es nur eine Meldung
	 * 
	 * Sind sie parallel gibt es eine Meldung und die Angabe des Abstands
	 * 
	 * Schneiden sie sich, wird Schnittwinkel und Schnittpunkt angegeben
	 */
	public static void doGeradeAndEbene(KombiGUI gui, GeoObject go1, GeoObject go2) {
		Gerade gerade = (Gerade) go1;
		Ebene ebene = (Ebene) go2;
		if (ebene.isParallel(gerade) && !gerade.isPartOf(ebene)) {
			gui.add(new JLabel(gerade.getName() + " und " + ebene.getName() + " sind parallel"), "span, wrap,alignc");
			gui.add(new JLabel("Der Abstand beträgt " + ebene.getAbstand(gerade)), "span, wrap, alignc");
			gui.setMode(Schnittmode.EbeneGeradeParallel);
		}
		if (gerade.isPartOf(ebene)) {
			gui.add(new JLabel(gerade.getName() + " liegt in " + ebene.getName()), "span, wrap, alignc");
			gui.setMode(Schnittmode.EbeneGeradeTeilmenge);
		}
		if (!ebene.isParallel(gerade)) {
			gui.setMode(Schnittmode.EbeneGeradeSchnitt);
			gui.add(schnittpunkt(go1, go2), "span,alignc,wrap");
			geoResult = ebene.getSchnittPunkt(gerade);
			gui.add(new JLabel("Der Schnittwinkel beträgt " + MyMath.round(ebene.getWinkel(gerade), 3) + "°"),
					"span, alignc, wrap");
			geoResult.setName(go1.getName() + "-" + go2.getName());
			gui.getMap().put("Schnittpunkt", geoResult);
			KombiDisplay.addResultArea(gui, geoResult);
		}
	}

	/**
	 * Vektor und Vektor werden verglichen (geo1 und geo2).
	 * 
	 * Sind sie identisch oder kollinear, gibt es nur eine Meldung
	 * 
	 * Wenn nicht werden eine Meldung , Summenvektor, Differenzvektor,
	 * Skalarprodukt, Kreuzprodukt und die aufgespannte Gerade angegeben
	 */
	public static void doVektorAndVektor(KombiGUI gui, GeoObject go1, GeoObject go2) {
		LAVector vektor1 = (LAVector) go1;
		LAVector vektor2 = (LAVector) go2;
		if (vektor1.equals(vektor2)) {
			gui.setMode(Schnittmode.VektorVektorIdent);
			gui.add(new JLabel("Die Vektoren sind identisch!"), "span, wrap,alignc");
			return;
		}
		if (vektor1.isKollinear(vektor2)) {
			gui.setMode(Schnittmode.VektorVektorKollinear);
			gui.add(new JLabel("Die Vektoren sind kollinear!"), "span, wrap,alignc");
			return;
		}
		gui.setMode(Schnittmode.VektorVektorOut);
		gui.add(new JLabel(
				"Die Vektoren schließen einen Winkel von " + vektor1.winkel(vektor2, LAVector.GRAD) + "° ein."),
				"span, wrap,alignc");
		gui.add(new JLabel("Der Differenzvektor ist: "), "alignc");
		LAVector tmp = vektor1.sub(vektor2);
		tmp.setName(vektor1.getName() + "-" + vektor2.getName());
		gui.getMap().put("Differenzvektor", tmp);
		gui.add(new GeoArea(tmp), " wrap, alignc");
		gui.add(new JLabel("Der Summenvektor ist: "), "alignc");
		tmp = vektor1.add(vektor2);
		tmp.setName(vektor1.getName() + "+" + vektor2.getName());
		gui.getMap().put("Summenvektor", tmp);
		gui.add(new GeoArea(tmp), " wrap, alignc");
		gui.add(new JLabel("Das Skalarprodukt beträgt: " + vektor1.skalarProdukt(vektor2)), "span, alignl, wrap");
		gui.add(new JLabel("Das Vektorprodukt ist: "), "alignc");
		tmp = vektor1.kreuzProdukt(vektor2);
		tmp.setName(vektor1.getName() + "X" + vektor2.getName());
		gui.getMap().put("Kreuzprodukt", tmp);
		gui.add(new GeoArea(tmp), " wrap, alignc");
		gui.add(new JLabel("Sie spannen eine Gerade auf:"), "span, wrap,alignc");
		geoResult = new Gerade("g", vektor1, vektor2, false);
		geoResult.setName(go1.getName() + "-" + go2.getName());
		gui.getMap().put("Gerade", geoResult);
		gui.setResultArea(new GeoArea(geoResult));
		KombiDisplay.addResultArea(gui, geoResult);
	}

	/**
	 * Vektor und Gerade werden verglichen (geo1 und geo2).
	 * 
	 * Zeigt v auf einen Punkt in g gibt es nur eine Meldung
	 * 
	 * Wenn nicht werden eine Meldung und der Abstand angegeben. Zusätzlich wird
	 * die aufgespannte Ebene bestimmt
	 */
	public static void doVektorAndGerade(KombiGUI gui, GeoObject geo1, GeoObject geo2) {
		LAVector vektor = (LAVector) geo1;
		Gerade gerade = (Gerade) geo2;
		if (vektor.isPartOf(gerade)) {
			gui.setMode(Schnittmode.GeradeVektorIn);
			gui.add(new JLabel("Der Punkt " + vektor.toPointString(true) + " liegt auf der Gerade!"),
					"span, wrap,alignc");
		} else {
			gui.setMode(Schnittmode.GeradeVektorOut);
			gui.add(new JLabel("Der Punkt " + vektor.toPointString(true) + " liegt nicht auf der Gerade!"),
					"span, wrap,alignc");
			gui.add(new JLabel("Der Abstand beträgt: " + gerade.getAbstand(vektor)), "span, wrap,alignc");
			LAVector tmp = gerade.getLotFussPunkt(vektor);
			tmp.setName("lfp");
			gui.add(new JLabel("Der Lotfußpunkt ist: " + tmp.toPointString(true)), "span,alignc,wrap");
			gui.getMap().put("Lotfußpunkt", tmp);
			tmp = gerade.getSpiegelpunkt(vektor);
			tmp.setName("sp");
			gui.add(new JLabel("Der Spiegelpunkt ist: " + tmp.toPointString(true)), "span,alignc,wrap");
			gui.getMap().put("Spiegelpunkt", tmp);
			gui.add(new JLabel("Sie spannen die folgende Ebene auf:"), "span, wrap,alignc");
			geoResult = new Ebene(gerade.getName() + "-" + vektor.getName(), gerade, vektor, false);
			gui.getMap().put("Ebene", geoResult);
			geoResult.setName(geo1.getName() + "-" + geo2.getName());
			KombiDisplay.addResultArea(gui, geoResult);
		}
	}

	/**
	 * Vektor und Ebene werden verglichen (geo1 und geo2).
	 * 
	 * Zeigt v auf einen Punkt in E gibt es nur eine Meldung
	 * 
	 * Wenn nicht werden eine Meldung und der Abstand angegeben. Zusätzlich
	 * werden der Lotfußpunkt und der Spiegelpunkt bestimmt
	 */
	public static void doVektorAndEbene(KombiGUI gui, GeoObject geo1, GeoObject geo2) {
		LAVector vektor = (LAVector) geo1;
		Ebene ebene = (Ebene) geo2;
		if (vektor.isPartOf(ebene)) {
			gui.setMode(Schnittmode.EbeneVektorIn);
			gui.add(new JLabel("Der Punkt " + vektor.toPointString(true) + " liegt in der Ebene!"),
					"span, wrap,alignc");
		} else {
			gui.setMode(Schnittmode.EbeneVektorOut);
			gui.add(new JLabel("Der Punkt " + vektor.toPointString(true) + " liegt nicht in der Ebene!"),
					"span, wrap,alignc");
			gui.add(new JLabel("Der Abstand beträgt " + ebene.getAbstand(vektor)), "span, wrap,alignc");
			LAVector lfp = ebene.getLotFussPunkt(vektor);
			lfp.setName("lp");
			gui.add(new JLabel("Der Lotfußpunkt ist " + lfp.toPointString(true)), "span, wrap,alignc");
			gui.getMap().put("Lotfußpunkt", lfp);
			LAVector sp = ebene.getSpiegelpunkt(vektor);
			sp.setName("sp");
			gui.add(new JLabel("Der Spiegelpunkt ist " + sp.toPointString(true)), "span, wrap,alignc");
			geoResult = sp;
			gui.getMap().put("Spiegelpunkt", sp);
			gui.setResultArea(new GeoArea(lfp));
		}
	}

	/**
	 * Gerade und Gerade werden verglichen (geo1 und geo2).
	 * 
	 * Sind sie identisch gibt es nur eine Meldung
	 * 
	 * Sind sie parallel gibt es eine Meldung, die Angabe des Abstands und die
	 * Angabe der Ebene, die durch die Geraden aufgespannt wird
	 * 
	 * Schneiden sie sich, wird Schnittwinkel und Schnittpunkt angegeben sowie
	 * die Angabe der Ebene, die durch die Geraden aufgespannt wird
	 * 
	 * Sind sie windschief, wird eine Meldung und die Angabe des Abstands
	 * ausgegeben
	 */
	public static void doGeradeAndGerade(KombiGUI gui, GeoObject geo1, GeoObject geo2) {
		Gerade gerade1 = (Gerade) geo1;
		Gerade gerade2 = (Gerade) geo2;
		if (gerade1.isIdent(gerade2)) {
			gui.setMode(Schnittmode.GeradeGeradeIdent);
			gui.add(new JLabel("Die Geraden sind identisch!"), "span, wrap,alignc");
			return;
		}
		if (gerade1.isParallel(gerade2)) {
			gui.setMode(Schnittmode.GeradeGeradeParallel);
			gui.add(new JLabel("Die Geraden sind parallel!"), "span, wrap,alignc");
			gui.add(new JLabel("Der Abstand beträgt: " + gerade1.getAbstand(gerade2)), "span,alignc,wrap");
			gui.add(new JLabel("Sie legen eine Ebene fest:"), "span, wrap,alignc");
			geoResult = new Ebene("E", gerade1, gerade1.getOrtsVektor().sub(gerade2.getOrtsVektor()).kuerze(), true);
			KombiDisplay.addResultArea(gui, geoResult);
			gui.getMap().put("Ebene", geoResult);
			return;
		}
		if (gerade1.isWindschief(gerade2)) {
			gui.setMode(Schnittmode.GeradeGeradeWindschief);
			gui.add(new JLabel("Die Geraden sind windschief!"), "span, wrap,alignc");
			gui.add(new JLabel("Der Abstand beträgt: " + gerade1.getAbstand(gerade2)), "span,alignc,wrap");
			return;
		}
		LAVector spv = gerade1.getSchnittPunkt(gerade2);
		gui.setMode(Schnittmode.GeradeGeradeSchnitt);
		spv.setName(geo1.getName() + "-" + geo2.getName());
		// gui.add(new JLabel("Die Geraden schneiden sich im Punkt " +
		// spv.toPointString(true)), "span, wrap,alignc");
		gui.add(schnittpunkt(geo1, geo2), "span, wrap,alignc");
		gui.getMap().put("Schnittvektor", spv);
		gui.add(new JLabel("Sie legen eine Ebene fest:"), "span, wrap,alignc");
		geoResult = new Ebene("E", gerade1, gerade2.getRichtungsVektor(), true);
		geoResult.setName(geo1.getName() + "-" + geo2.getName());
		KombiDisplay.addResultArea(gui, geoResult);
		gui.getMap().put("Ebene", geoResult);
	}

	private static void addResultArea(KombiGUI gui, GeoObject geo) {
		gui.setResultArea(new GeoArea(geo));
		gui.getResultArea().setEditable(false);
		gui.add(gui.getResultArea(), "span, alignc, wrap");
	}

	/*
	 * private static Component getComponent(){ return resultArea; }
	 */

}
