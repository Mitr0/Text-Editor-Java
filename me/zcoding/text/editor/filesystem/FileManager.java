package me.zcoding.text.editor.filesystem;

import java.util.ArrayList;
import java.util.List;

import me.zcoding.text.editor.lang.DefaultLanguage;
import me.zcoding.text.editor.lang.LoadLanguage;
import me.zcoding.text.editor.utils.Settings;

public class FileManager {

	private List<A_File> allFiles = new ArrayList<>();

	public static FileManager INSTANCE;

	public FileManager() {
		INSTANCE = this;
		addAll();

		onLoad();
	}

	public void onLoad() {
		LoadLanguage language = new LoadLanguage();
		Settings.avaiableLanguages = language.getLanguages();

		if (Settings.curLanguage == null) {
			Settings.curLanguage = new DefaultLanguage();
		}
	}

	private void addAll() {

	}

	public void write() {
		for (A_File file : getAllFiles()) {
			file.write();
		}
	}

	private void addFile(A_File file) {
		this.getAllFiles().add(file);
	}

	public List<A_File> getAllFiles() {
		return allFiles;
	}

}
