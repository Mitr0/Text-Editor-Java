package me.zcoding.text.editor.lang;

import java.util.HashMap;
import java.util.Map;

public class Language {

	private Map<String, String> langMap = new HashMap<>();

	public void add(String key, String lang) {
		this.langMap.put(key, lang);
	}

	public void setLangMap(Map<String, String> map) {
		this.langMap = map;
	}

	public Map<String, String> getLangMap() {
		return langMap;
	}

}
