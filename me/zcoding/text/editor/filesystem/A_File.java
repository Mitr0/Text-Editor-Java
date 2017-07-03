package me.zcoding.text.editor.filesystem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import me.zcoding.text.editor.utils.Utils;

public abstract class A_File {

	private String name;
	private File folder = Utils.mainFolder;

	private File curFile;

	public A_File(String name, File folder) {
		this.name = name;
		if (folder != null)
			this.folder = folder;
		this.curFile = new File(this.folder, name);
		if (!this.curFile.exists()) {
			try {
				this.curFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String getName() {
		return name;
	}

	public File getFolder() {
		return folder;
	}

	/**
	 * Writes the Data to the File
	 * 
	 * @return
	 */
	public abstract List<String> toWrite();

	public void write() {
		try {
			FileWriter writer = new FileWriter(curFile);
			BufferedWriter bufferedWriter = new BufferedWriter(writer);

			for (String string : toWrite()) {
				bufferedWriter.write(string);
				bufferedWriter.newLine();
			}
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
