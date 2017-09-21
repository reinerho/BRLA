package tools;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import gui.MainGUI;
import gui.control.MainControl;

public class FileTools {

	/*
	 * public static void save() { String newFile = null; if (Init.getFilename()
	 * != null) newFile = Init.getDataPath() + Init.getFilename(); else {
	 * newFile = chooseFile(); } if (newFile == null) {
	 * JOptionPane.showMessageDialog(null, "Es wurde nichts gespeichert!",
	 * "Abbruch", JOptionPane.INFORMATION_MESSAGE); return; } try {
	 * XML.save(newFile, MainControl.getInstance().xml());
	 * Init.setChange(Init.NOCHANGE); Init.setFilename(new
	 * File(newFile).getName()); } catch (IOException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } }
	 */

	public static void save(String filename) {
		String newFile = filename;
		if (newFile == null)
			newFile = chooseFile();
		if (newFile == null) {
			JOptionPane.showMessageDialog(null, "Es wurde nichts gespeichert!", "Abbruch",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (!newFile.contains(Init.getSeparator())) newFile = Init.getDataPath()+newFile;
		try {
			XML.save(newFile, MainControl.getInstance().xml());
			Init.setChange(Init.NOCHANGE);
			Init.setFilename(new File(newFile).getName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static String chooseFile() {
		String newFile = null;
		JFileChooser chooser = new JFileChooser(Init.getDataPath());
		FileNameExtensionFilter filter = new FileNameExtensionFilter("XML-Dateien", "xml");
		chooser.setFileFilter(filter);
		boolean success = false;
		while (!success) {
			success = true;
			int answer = chooser.showSaveDialog(MainGUI.getInstance());
			if (answer == JFileChooser.APPROVE_OPTION) {
				newFile = chooser.getSelectedFile().getAbsolutePath();
				Init.setDataPath(new File(newFile).getParent() + Init.separator);
				if (!newFile.endsWith(".xml"))
					newFile = newFile + ".xml";
				if (new File(newFile).exists()) {
					int result = JOptionPane.showConfirmDialog(MainGUI.getInstance(),
							"Die ausgewählte Datei existiert bereits!\nÜberschreiben?", "Warnung",
							JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					success = (result == JOptionPane.YES_OPTION);
				}
			} else
				return null;
		}
		return newFile;
	}

}
