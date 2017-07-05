package me.zcoding.text.editor.lang;

import java.util.HashMap;
import java.util.Map;

public class DefaultLanguage extends Language {

	public Map<String, String> getLangMap() {
		Map<String, String> langMap = new HashMap<>();
		{
			langMap.put("menu_files", "Datei");
			langMap.put("menu_files_save", "Speichern");
			langMap.put("menu_files_saveas", "Speichern Unter");
			langMap.put("menu_files_load", "Laden");
		}
		{
			langMap.put("menu_settings", "Einstellungen");
			langMap.put("menu_settings_syntax", "Syntax");
			langMap.put("menu_settings_language", "Sprache");
			langMap.put("menu_settings_language_default", "Standard");
		}

		return langMap;
	}

}
