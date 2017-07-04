package me.zcoding.text.editor.lang;

import me.zcoding.text.editor.utils.Settings;

public class LanguageManager {

	public static String getFromLanguage(String toGet) {
		String getToReturn = Settings.curLanguage.getLangMap().get(toGet);

		if (getToReturn.isEmpty() || getToReturn == null) {
			getToReturn = new DefaultLanguage().getLangMap().get(toGet);
		}

		return getToReturn;
	}

	public static String getFromLanguage(String toGet, Language l) {
		String getToReturn = l.getLangMap().get(toGet);

		if (getToReturn.isEmpty() || getToReturn == null) {
			getToReturn = new DefaultLanguage().getLangMap().get(toGet);
		}

		return getToReturn;
	}

}
