package me.zcoding.text.editor.lang;

import java.util.HashMap;
import java.util.Map;

public class Language {

	private Map<String, String> langMap = new HashMap<>();

	private String name;

	public void add(String key, String lang) {
		this.langMap.put(key, lang);
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

	public Map<String, String> getLangMap() {
		return langMap;
	}

}
