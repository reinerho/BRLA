package gui.control;

import java.util.Vector;

import geometrie.Ebene;
import geometrie.GeoObject;
import geometrie.Gerade;
import geometrie.LAVector;
import gui.KombiGUI;
import gui.KombiGUI.Schnittmode;
import tools.Etc;
import tools.Printer;

public class KombiPrint {

	public void printEbeneEbene(Ebene e1, Ebene e2, KombiGUI.Schnittmode mode) {
		/*
		 * Vector<String> out = new Vector<String>(); out.add("Kombination aus "
		 * + e1.getName() + " und " + e2.getName()); Vector<String> tmp1 =
		 * Etc.arrayToVector(e1.toStringArray()); Vector<String> tmp2 =
		 * Etc.arrayToVector(e2.toStringArray()); tmp1 = Etc.appendVector(tmp1,
		 * tmp2, Etc.HORIZONTAL); out.addAll(tmp1);
		 */
		Vector<String> out = basicPrint(e1, e2);
		out.add(" ");
		out.add(e1.toKoordinatenFormString() + " // " + e2.toKoordinatenFormString());
		out.add(" ");
		switch (mode) {
		default:
			return;
		case EbeneEbeneSchnitt:
			out.add("Die Ebenen schneiden sich in der Gerade: ");
			out.add(" ");
			Vector<String> tmp1 = Etc.arrayToVector(e1.getSchnittGerade(e2).toStringArray());
			out.addAll(tmp1);
			out.add("Der Schnittwinkel beträgt: " + e1.getWinkel(e2));
			break;
		case EbeneEbeneIndent:
			out.add(" ");
			out.add("Die Ebenen sind identisch");
			break;
		case EbeneEbeneParallel:
			out.add(" ");
			out.add("Die Ebenen sind parallel! Der Abstand beträgt:" + e1.getAbstand(e2));
		}
		new Printer(out);
	}

	public void printGeradeEbene(Gerade g1, Ebene e2, KombiGUI.Schnittmode mode) {
		/*
		 * Vector<String> out = new Vector<String>(); out.add("Kombination aus "
		 * + g1.getName() + " und " + e2.getName()); Vector<String> tmp1 =
		 * Etc.arrayToVector(g1.toStringArray()); Vector<String> tmp2 =
		 * Etc.arrayToVector(e2.toStringArray()); tmp1 = Etc.appendVector(tmp1,
		 * tmp2, Etc.HORIZONTAL); out.addAll(tmp1); out.add(" ");
		 */
		Vector<String> out = this.basicPrint(g1, e2);
		out.add(e2.toKoordinatenFormString());
		out.add(" ");
		switch (mode) {
		default:
			return;
		case EbeneGeradeSchnitt:
			out.add(" ");
			out.add("Die Gerade und die Ebene schneiden sich im Punkt " + e2.getSchnittPunkt(g1).toPointString(true));
			out.add(" ");
			out.add("Der Schnitwinkel beträgt " + e2.getWinkel(g1) + "°");
			break;
		case EbeneGeradeParallel:
			out.add(" ");
			out.add("Die Gerade und die Ebene sind parallel mit dem Abstand " + e2.getAbstand(g1));
		case EbeneGeradeTeilmenge:
			out.add(" ");
			out.add("Die Gerade ist Teil der Ebene (liegt in der Ebene");
			out.add(" ");
		}
		new Printer(out);
	}

	public void printVektorEbene(LAVector v, Ebene e, KombiGUI.Schnittmode mode) {
		Vector<String> out = this.basicPrint(v, e);
		switch (mode) {
		case EbeneVektorIn:
			out.add("Der Vektor zeigt auf einen Punkt der Ebene.");
			break;
		case EbeneVektorOut:
			out.add("Der Vektor zeigt auf einen Punkt außerhalb der Ebene!");
			out.add(" ");
			out.add("Der Abstand beträgt " + e.getAbstand(v));
			out.add(" ");
			out.add("Der Lotfußpunkt ist L" + e.getLotFussPunkt(v).toPointString(true));
			out.add(" ");
			out.add("Der Spiegelpunkt ist SP" + e.getSpiegelpunkt(v).toPointString(true));
		default:
			;
		}
		new Printer(out);
	}

	public void printGeradeGerade(Gerade g1, Gerade g2, KombiGUI.Schnittmode mode) {
		Vector<String> out = this.basicPrint(g1, g2);
		switch (mode) {
		case GeradeGeradeIdent:
			out.add("Die Geraden sind identisch!");
			break;
		case GeradeGeradeParallel:
			out.add("Die Geraden sind parallel!");
			out.add(" ");
			out.add("Ihr Abstand beträgt: " + g1.getAbstand(g2));
			out.add(" ");
			out.add("Sie spannen die folgende Ebene auf: ");
			out.add(" ");
			Ebene e = new Ebene("E", g1, g2.getOrtsVektor().sub(g1.getOrtsVektor()), true);
			out.addAll(Etc.arrayToVector(e.toStringArray()));
			out.add(" ");
			out.add(e.toKoordinatenFormString());
			break;
		case GeradeGeradeSchnitt:
			out.add("Die Geraden schneiden sich im Punkt S" + g1.getSchnittPunkt(g2).toPointString(true));
			out.add(" ");
			out.addElement(
					"Der Schnittwinkel beträgt " + g1.getRichtungsVektor().winkel(g2.getRichtungsVektor(), true) + "°");
			out.add(" ");
			out.add("Sie spannen die folgende Ebene auf: ");
			out.add(" ");
			e = new Ebene("E", g1, g2.getRichtungsVektor(), true);
			out.addAll(Etc.arrayToVector(e.toStringArray()));
			out.add(" ");
			out.add(e.toKoordinatenFormString());
			break;
		case GeradeGeradeWindschief:
			out.add("Die Geraden sind windschgief!");
			out.add(" ");
			out.add("Ihr Abstand beträgt: " + g1.getAbstand(g2));
		default:
			;
		}
		new Printer(out);
	}

	public void printGeradeVektor(Gerade g, LAVector v, KombiGUI.Schnittmode mode) {
		Vector<String> out = this.basicPrint(g, v);
		switch (mode) {
		case GeradeVektorIn:
			out.add("Der Vektor zeigt auf einen Punkt der Gerade.");
			break;
		case GeradeVektorOut:
			out.add("Der Vektor zeigt auf einen Punkt außerhalb der Gerade.");
			out.add(" ");
			out.add("Der Abstand beträgt " + g.getAbstand(v));
			out.add(" ");
			out.add("Der Lotfußpunkt ist L" + g.getLotFussPunkt(v));
			out.add(" ");
			out.add("Der Spiegelpunkt ist S" + g.getSpiegelpunkt(v));
			out.add(" ");
			Ebene e = new Ebene("E", g, v.sub(g.getOrtsVektor()), true);
			out.addAll(Etc.arrayToVector(e.toStringArray()));
			out.add(" ");
			out.addElement(e.toKoordinatenFormString());
		default:
			;
		}
		new Printer(out);
	}

	public void printVektorVektor(LAVector go1, LAVector go2, Schnittmode mode) {
		Vector<String> out = this.basicPrint(go1, go2);
		switch (mode) {
		case VektorVektorIdent:
			out.add("Die Vektoren sind identisch!");
			break;
		case VektorVektorKollinear:
			out.add("Die Vektoren sind kollinear!");
			break;
		case VektorVektorOut:
			out.add("Die Vektoren schließen einen Winkel von " + go1.winkel(go2, true) + "° ein.");
			out.add(" ");
			out.add("Differenz:");
			out.add(" ");
			out.addAll(Etc.arrayToVector(go1.sub(go2).toStringArray()));
			out.add("");
			out.add("Summe:");
			out.add(" ");
			out.addAll(Etc.arrayToVector(go1.add(go2).toStringArray()));
			out.add("");
			out.add("Kreuzprodukt:");
			out.add(" ");
			out.addAll(Etc.arrayToVector(go1.kreuzProdukt(go2).toStringArray()));
			out.add("");
			out.add("Skalarprodukt: "+go1.skalarProdukt(go2));
			out.add(" ");
			out.add("Die Vektoren spannen die folgende Gerade auf:");
			out.add(" ");
			out.addAll(Etc.arrayToVector(new Gerade("g",go1,go2,false).toStringArray()));
			out.add("");
		default:
			;
		}
		new Printer(out);
	}

	private Vector<String> basicPrint(GeoObject go1, GeoObject go2) {
		Vector<String> out = new Vector<String>();
		out.add("Kombination aus " + go1.getName() + " und " + go2.getName());
		out.addAll(Etc.appendVector(stringVector(go1), stringVector(go2), Etc.HORIZONTAL));
		out.add(" ");
		return out;
	}

	private Vector<String> stringVector(GeoObject go) {
		if (go instanceof Ebene) {
			Ebene e = (Ebene) go;
			return Etc.arrayToVector(e.toStringArray());
		}
		if (go instanceof Gerade) {
			Gerade g = (Gerade) go;
			return Etc.arrayToVector(g.toStringArray());
		}
		LAVector v = (LAVector) go;
		return Etc.arrayToVector(v.toStringArray());
	}


}
