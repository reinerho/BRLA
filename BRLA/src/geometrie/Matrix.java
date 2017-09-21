package geometrie;

import tools.*;

/**
 * Stellt Methoden zum Erzeugen und Manipulieren von Matrizen zur Verfuegung.
 * Fokus ist dabei bisher der Einsatz zum Loesen von Gleichungssystemen
 * 
 * @author reiner
 *
 */
public class Matrix {

	private LAVector[] matrix;

	public static final int MATRIX_ZEILE = 0;
	public static final int MATRIX_SPALTE = 1;

	public static final boolean TRANSPONIERT = true;

	/**
	 * Erzeugt eine Null-Matrix mit rows Zeilen und cols Spalten
	 * 
	 * @param rows
	 *            int Zeilenzahl
	 * @param cols
	 *            int Spaltenzahl
	 */
	public Matrix(int rows, int cols) {
		this.matrix = new LAVector[cols];
		for (int c = 0; c < cols; c++) {
			matrix[c] = new LAVector(rows);
		}
		for (int r = 0; r < rows; r++)
			for (int c = 0; c < cols; c++) {
				matrix[c].set(r, new Bruch(0));
			}
	}

	/**
	 * Erzeugt eine Matrix aus einem LAVektor-Array
	 * 
	 * @param cols
	 *            das LAVektor-Array
	 */
	public Matrix(LAVector[] cols) {
		this.matrix = new LAVector[cols.length];
		for (int c = 0; c < cols.length; c++) {
			matrix[c] = new LAVector(cols[0].size());
		}
		for (int r = 0; r < cols[0].size(); r++)
			for (int c = 0; c < colCount(); c++) {
				matrix[c].set(r, cols[c].get(r));
			}
	}

	/**
	 * Transponiert die Matrix
	 * 
	 * @return die transponierte Matrix
	 */
	public Matrix transponiere() {
		Matrix matrix = new Matrix(this.colCount(), this.rowCount());
		for (int c = 0; c < this.colCount(); c++) {
			for (int r = 0; r < this.rowCount(); r++) {
				matrix.setElement(c, r, this.getElement(r, c));
			}
		}
		return matrix;
	}

	/**
	 * Dividiert alle Elemente der Zeile zeile durch den Faktor faktor
	 * 
	 * @param zeile
	 * @param faktor
	 */
	private void zDiv(int zeile, Bruch faktor) {
		for (int c = 0; c < this.colCount(); c++) {
			matrix[c].set(zeile, matrix[c].get(zeile).div(faktor));
		}
	}

	/**
	 * Prueft, ob eine Matrixzeile nur Nullen enthaelt
	 * 
	 * @param row
	 *            Nummer der zu pruefenden Zeile
	 * @return true, wenn nur Nullen in der Zeile stehen
	 */
	public boolean isNullZeile(int row) {
		for (int c = 0; c < this.colCount(); c++) {
			if (!this.getElement(row, c).isZero())
				return false;
		}
		return true;
	}

	/**
	 * Dividiert alle Werte der Zeile row durch den Wert der Zelle (row,row)
	 * 
	 * @param row
	 *            die Zeile, die normiert wird, indem durch den Wert der Zelle
	 *            [row,row] dividiert wird
	 */
	private int normiere(int row) {
		if (isNullZeile(row))
			return row;
		if (this.getElement(row, row).isZero()) { // wenn das Diagonalelement 0
													// ist, suche das naechste
													// von Null verschiedene
			int r = row;
			while (this.getElement(row, r).isZero() && r < this.colCount()) {
				r++;
			}
			for (int c = row; c < this.rowCount(); c++) {
				zDiv(row, this.getElement(row, r));
			}
			return r;
		}
		for (int c = row; c < this.rowCount(); c++) {
			zDiv(row, this.getElement(row, row));
		}
		return row;
	}

	/**
	 * Nimmt eine Kopie der Zeile row, multipliziert sie mit dem Wert der Zelle
	 * [row,col] und subtrahiert das Ergebnis von der Zeile rowTarget
	 * 
	 * @param row
	 *            die betroffene Reihe
	 * @param rowTarget
	 *            die Reihe, die veraendert wird
	 * @param col
	 *            die Spalte, in der der Faktor steht
	 */

	private void rowSub(int row, int rowTarget, int col) {
		Bruch faktor = this.getElement(rowTarget, col);
		for (int c = 0; c < colCount(); c++) {
			this.setElement(rowTarget, c, this.getElement(rowTarget, c).sub(this.getElement(row, c).mult(faktor)));
		}
	}

	/**
	 * Fuehrt die Methode rowSub fuer alle Zeilen aus, ausser fuer die Zeile row
	 * 
	 * @param row
	 *            die Referenzzeile, die unveraendert bleibt
	 * @param col
	 *            die Spalte, in der der Faktor fuer rowSub steht
	 */
	private void subOtherRows(int row, int col) {
		for (int i = 0; i < this.rowCount(); i++) {
			if (i == row)
				continue;
			rowSub(row, i, col);
		}
	}

	/**
	 * Tauscht die Zeilen r1 und r2 gegenainander aus
	 * 
	 * @param r1
	 *            erster Tauschpartner
	 * @param r2
	 *            zweiter Tauschpartner
	 */
	private void swichRows(int r1, int r2) {
		Bruch tmp = new Bruch(0);
		for (int c = 0; c < this.colCount(); c++) {
			tmp = this.getElement(r1, c);
			this.setElement(r1, c, this.getElement(r2, c));
			this.setElement(r2, c, tmp);
		}
	}

	/**
	 * Tauscht die Zeilen einer Matrix solange, bis in der Zelle (row/row) eine
	 * von Null verschiedene Zahl steht
	 * 
	 * @param row
	 *            die betroffene Zeile
	 */
	private void setToNonZero(int row) {
		int counter = row + 1;
		if (counter == rowCount())
			return;
		while (this.getElement(row, row).isZero()) {
			swichRows(row, counter); // tausche die Zeilen
			counter++;
			if (counter == rowCount())
				return;
		}

	}

	/**
	 * Wandelt eine Matrix in eine Diagonalmatrix um
	 * 
	 * @return die Diagonalmatrix
	 */
	public Matrix toDiagonal() {
		Matrix m = this;
		// System.out.println(this);
		for (int r = 0; r < this.rowCount(); r++) {
			m.setToNonZero(r);
			int r1 = m.normiere(r); // Dividiere Zeile r durch den Wert in der
			// zugehoerigen Spalte r
			if (r1 == r)
				m.subOtherRows(r, r);
			else
				m.subOtherRows(r, r1);
			// System.out.println("Matrix: \n"+m);
		}
		return m;
	}

	public String toString() {
		String out = "";
		for (int row = 0; row < this.rowCount(); row++) {
			for (int col = 0; col < this.colCount(); col++) {
				out = out + this.getElement(row, col) + "| ";
			}
			out = out + "\n";
		}
		return out;
	}

	/**
	 * Erweitert die Matrix entweder um eine Spalte (spalte==true) oder eine
	 * Zeile (spalte == false) und kopiert die Werte des LAVektors v in diese
	 * 
	 * @param v
	 *            der LAVector, dessen Werte angehaengt werden
	 * @param spalte
	 *            wenn true, wird eine Spalte angehaengt, sonst eine Zeile
	 * @return die um eine Zeile/Spalte erweiterte Matrix
	 */
	public Matrix erweitere(LAVector v, boolean spalte) {
		Matrix out = null;
		if (spalte) {
			out = new Matrix(this.rowCount(), this.colCount() + 1);
			for (int c = 0; c < colCount(); c++) {
				out.matrix[c] = this.getVector(c, MATRIX_SPALTE);
			}
			out.matrix[colCount()] = v;
		} else {
			out = new Matrix(this.rowCount() + 1, this.colCount()).transponiere();
			Matrix tmp = this.transponiere();
			for (int c = 0; c < tmp.colCount(); c++) {
				out.matrix[c] = tmp.getVector(c, MATRIX_SPALTE);
			}
			out.matrix[tmp.colCount()] = v;
			out.transponiere();
		}
		return out;
	}

	public LAVector[] getMatrix() {
		return matrix;
	}

	public void setMatrix(LAVector[] matrix) {
		this.matrix = matrix;
	}

	public int getRows() {
		return 3;
	}

	/**
	 * Gibt eine Zeile oder Spalte der Matrix als LAVector zurueck
	 * 
	 * @param index
	 *            die Nummer der der Zeile/Spalte
	 * @param direction
	 *            1 bzw. MATRIX_SPALTE wenn ein Spaltenvektor benoetigt wird,
	 *            sonst 0 bzw. MATRIX_ZEILE
	 * @return der Zeilen/Spalten-Vektor
	 */
	public LAVector getVector(int index, int direction) {
		if (direction == MATRIX_SPALTE)
			return this.matrix[index];
		else {
			LAVector zVector = new LAVector(this.colCount());
			for (int i = 0; i < colCount(); i++) {
				zVector.set(i, this.getElement(index, i));
			}
			return zVector;
		}
	}

	/**
	 * Gibt den Wert der Matrixzelle [row,col] zurueck
	 * 
	 * @param row
	 *            die Reihe, in der der Wert steht
	 * @param col
	 *            die Spalte, in der der Wert steht
	 * @return der gewuenschte Wert als Bruch
	 */
	public Bruch getElement(int row, int col) {
		return this.getMatrix()[col].get(row);
	}

	/**
	 * Setzt den Wert der Matrixzelle [row,column] auf den Wert value
	 * 
	 * @param row
	 *            die Reihe
	 * @param col
	 *            die Spalte
	 * @param value
	 *            der Wert
	 */
	public void setElement(int row, int col, Bruch value) {
		this.matrix[col].set(row, value);
	}

	/**
	 * Setzt die Matrixzelle [row,col] auf den Wert value
	 * 
	 * @param row
	 *            die Nummer der betroffnen Zeile
	 * @param col
	 *            die Nummer der betroffenen Spalte
	 * @param value
	 *            der Wert, auf den die Zelle gesetzt wird
	 */
	public void setElement(int row, int col, int value) {
		this.matrix[col].set(row, new Bruch(value));
	}

	/**
	 * Gibt die Anzahl der Spalten in der Matrix zurueck
	 * 
	 * @return die Anzahl der Spalten in der Matrix
	 */
	public int colCount() {
		return this.matrix.length;
	}

	/**
	 * Gibt die Anzahl der Zeilen in der Matrix zurueck
	 * 
	 * @return Anzahl der Zeilen in der Matrix
	 */
	public int rowCount() {
		return this.matrix[0].size();
	}

}
