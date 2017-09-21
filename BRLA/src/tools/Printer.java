package tools;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.Vector;

/**
 * Über die Klasse Printer können Strings auf einem Drucker ausgegeben werden.
 * Die Strings müssen in einem Vector übergeben werden und werden dann der Reihe nach gedruckt
 * @author reiner
 *
 */
public class Printer implements Printable {
	private Vector<String> printString;

	/**
	 * Die Printmethode
	 * @param printIt der Vector mit den zu druckenden Strings
	 * 
	 */
	public Printer(Vector<String> printIt) {
		printString = printIt;
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable((Printable) this);
		boolean ok = job.printDialog();
		if (ok) {
			try {
				job.print();
			} catch (PrinterException ex) {
				
			}
		}
	}

	@Override
	public int print(Graphics graphics, PageFormat pf, int pageIndex) throws PrinterException {
		if (pageIndex > 0) { /*
								 * We have only one page, and 'page' is zero-based
								 */
			return NO_SUCH_PAGE;
		}

		/*
		 * User (0,0) is typically outside the imageable area, so we must
		 * translate by the X and Y values in the PageFormat to avoid clipping
		 */
		Graphics2D g2d = (Graphics2D) graphics;
		g2d.setFont(new Font(Font.MONOSPACED,Font.PLAIN,12));
		g2d.translate(pf.getImageableX(), pf.getImageableY());

		/* Now we perform our rendering */
		int i=0;
		for (String s : printString) {
			graphics.drawString(s, 20, 20+12*i);
			i++;
		}

		/* tell the caller that this page is part of the printed document */
		return PAGE_EXISTS;
	}


}
