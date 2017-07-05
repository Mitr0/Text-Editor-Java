package me.zcoding.text.editor.lang;

import me.zcoding.text.editor.settings.Settings;

public class LanguageManager {

	private static final DefaultLanguage DEFAULT_LANGUAGE = new DefaultLanguage();

	public static String getFromLanguage(String toGet, Language l) {
		String getToReturn = DEFAULT_LANGUAGE.getLangMap().get(toGet);

		if (l != null) {
			if (l.getLangMap().containsKey(toGet)) {
				getToReturn = l.getLangMap().get(toGet);
			}
		}

		return getToReturn;
	}

	public static String getFromLanguage(String toGet) {
		return getFromLanguage(toGet, Settings.curLanguage);
	}

}
