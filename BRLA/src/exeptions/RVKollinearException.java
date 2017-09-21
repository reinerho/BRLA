package exeptions;

public class RVKollinearException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RVKollinearException(){
		super("Die Richtungsvektoren sind kollinear - keine Ebene möglich!");
	}

}
