package me.zcoding.text.editor.gui.syntaxHighlighting;

import java.util.ArrayList;
import java.util.List;

public class ColoredKeyWordList {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private List<ColoredKeyWord> coloredKeyWords = new ArrayList<>();

	public void setColoredKeyWords(List<ColoredKeyWord> coloredKeyWords) {
		this.coloredKeyWords = coloredKeyWords;
	}

	public List<ColoredKeyWord> getColoredKeyWords() {
		return coloredKeyWords;
	}

}
