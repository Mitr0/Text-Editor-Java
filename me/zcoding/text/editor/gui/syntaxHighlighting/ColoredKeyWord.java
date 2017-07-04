package me.zcoding.text.editor.gui.syntaxHighlighting;

import java.awt.Color;

public class ColoredKeyWord {
	private Color color;
	private boolean bold, itallic, underlined, strikethrough;

	private String key;

	public ColoredKeyWord(String key, Color c, boolean... data) {
		this.key = key;
		this.color = c;
		this.bold = data[0];
		this.itallic = data[1];
		this.underlined = data[2];
		this.strikethrough = data[3];
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
}
