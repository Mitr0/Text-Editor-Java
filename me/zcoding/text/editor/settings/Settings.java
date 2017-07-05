package me.zcoding.text.editor.settings;

import java.util.ArrayList;
import java.util.List;

import me.zcoding.text.editor.gui.syntaxHighlighting.ColoredKeyWordList;
import me.zcoding.text.editor.gui.syntaxHighlighting.DefaultSyntax;
import me.zcoding.text.editor.lang.DefaultLanguage;
import me.zcoding.text.editor.lang.Language;

public class Settings {

	/**
	 * Language
	 */
	public static Language curLanguage = new DefaultLanguage();
	public static List<Language> avaiableLanguages = new ArrayList<>();

	/**
	 * Syntax
	 */
	public static ColoredKeyWordList curSyntax = new DefaultSyntax();
	public static List<ColoredKeyWordList> allSyntaxes = new ArrayList<>();

}
