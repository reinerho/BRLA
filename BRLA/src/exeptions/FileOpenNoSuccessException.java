package exeptions;

public class FileOpenNoSuccessException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileOpenNoSuccessException(){
		super("Konnte Datei nicht öffnen!");
	}
	
}
