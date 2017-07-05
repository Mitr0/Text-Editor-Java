package me.zcoding.text.editor.gui.syntaxHighlighting;

import java.awt.Color;

import javax.swing.text.SimpleAttributeSet;

public class ColoredKeyWord {

	private Color color;
	private boolean bold, itallic, underlined, strikethrough;

	private SimpleAttributeSet set;

	private String key;

	public ColoredKeyWord(String key, Color c, boolean bold, boolean itallic, boolean underlined, boolean strike) {
		this.key = key;
		this.color = c;
		this.bold = bold;
		this.itallic = itallic;
		this.underlined = underlined;
		this.strikethrough = strike;
	}

	public String getKey() {
		return key;
	}

	public Color getColor() {
		return color;
	}

	public boolean isBold() {
		return bold;
	}

	public boolean isItallic() {
		return itallic;
	}

	public boolean isStrikethrough() {
		return strikethrough;
	}

	public boolean isUnderlined() {
		return underlined;
	}

	public void setAttributeSet(SimpleAttributeSet temp) {
		this.set = temp;
	}

	public SimpleAttributeSet getSet() {
		return set;
	}

}
