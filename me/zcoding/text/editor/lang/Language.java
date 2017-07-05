package me.zcoding.text.editor.lang;

import java.util.HashMap;
import java.util.Map;

public class Language {

	private Map<String, String> langMap = new HashMap<>();

	private String name, file_name;

	public void add(String key, String lang) {
		this.langMap.put(key, lang);
	}

	public void setFileName(String shortc) {
		this.file_name = shortc;
	}

	public void setLangMap(Map<String, String> map) {
		this.langMap = map;
		if (map.get("name") == null) {
			this.name = "NONAME";
		} else {
			this.name = LanguageManager.getFromLanguage("name", this);
		}
	}

	public String getName() {
		return name;
	}

	public String getFile_name() {
		return file_name;
	}

	public Map<String, String> getLangMap() {
		return langMap;
	}

}
