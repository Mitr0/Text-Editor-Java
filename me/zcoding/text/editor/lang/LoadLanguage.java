package me.zcoding.text.editor.lang;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.zcoding.text.editor.utils.Utils;

public class LoadLanguage {

	private File[] languageFiles;

	private List<Language> languages;

	public File[] getLanguageFiles() {
		return languageFiles;
	}

	public LoadLanguage() {
		languages = new ArrayList<>();
		loadLanguages();
	}

	public List<Language> getLanguages() {
		return languages;
	}

	public void loadLanguages() {
		load loadLanguagesThread = new load();
		loadLanguagesThread.start();
	}

	private Map<String, String> readLanguageFile(File langFile) {
		Map<String, String> data = new HashMap<>();
		try {
			FileReader fileReader = new FileReader(langFile);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String curLine;
			while ((curLine = bufferedReader.readLine()) != null) {
				if (!curLine.isEmpty()) {
					if (curLine.contains("=")) {
						String[] line = curLine.split("=");
						if (!line[0].isEmpty() && !line[1].isEmpty()) {
							data.put(line[0], line[1]);
						}
					}
				}
			}
			bufferedReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	public class load extends Thread {
		public void run() {
			languageFiles = Utils.langFolder.listFiles();
			for (File file : languageFiles) {
				if (file.getName().endsWith(".lang")) {
					Language current = new Language();
					current.setFileName(file.getName().split(".lang")[0]);
					current.setLangMap(readLanguageFile(file));
					languages.add(current);
				}
			}
		}
	}

}
