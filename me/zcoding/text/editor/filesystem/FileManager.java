package me.zcoding.text.editor.filesystem;

import java.util.ArrayList;
import java.util.List;

import me.zcoding.text.editor.gui.syntaxHighlighting.LoadSyntax;
import me.zcoding.text.editor.lang.LoadLanguage;
import me.zcoding.text.editor.settings.Settings;

public class FileManager {

	private List<A_File> allFiles = new ArrayList<>();

	public static FileManager INSTANCE;

	public FileManager() {
		INSTANCE = this;
		addAll();
		read();

		onLoad();
	}

	public LoadLanguage loadLanguage;
	public LoadSyntax loadSyntax;

	public void onLoad() {
		loadLanguage = new LoadLanguage();
		loadSyntax = new LoadSyntax();
		Settings.allSyntaxes = loadSyntax.getColoredKeywords();
		Settings.avaiableLanguages = loadLanguage.getLanguages();
	}

	private void addAll() {
	}

	public void write() {
		for (A_File file : getAllFiles()) {
			file.write();
		}
	}

	public void read() {
		for (A_File file : getAllFiles()) {
			file.read();
		}
	}

	private void addFile(A_File file) {
		this.getAllFiles().add(file);
	}

	public List<A_File> getAllFiles() {
		return allFiles;
	}

}
