package me.zcoding.text.editor.gui.syntaxHighlighting;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

@SuppressWarnings("serial")
public class Colored_Doc extends DefaultStyledDocument {

	private List<ColoredKeyWord> coloredKeyWords = new ArrayList<>();
	private final List<WritingMode> writingModes = new ArrayList<WritingMode>() {
		{
			add(new WritingMode("text", 0));
			// TODO: Add more Writingmodes
		}
	};

	private SimpleAttributeSet attributeSet = new SimpleAttributeSet();

	private int currentPosition;
	private String curWord = "";

	public Colored_Doc() {
		StyleConstants.setForeground(attributeSet, Color.red);
	}

	/**
	 * Sets the keywordList
	 * 
	 * @param words
	 */
	public void setColoredKeyWords(List<ColoredKeyWord> words) {
		if (words != null) {
			for (ColoredKeyWord word : words) {

				SimpleAttributeSet temp = new SimpleAttributeSet();
				StyleConstants.setForeground(temp, word.getColor());

				StyleConstants.setBold(temp, word.isBold());
				StyleConstants.setItalic(temp, word.isItallic());
				StyleConstants.setStrikeThrough(temp, word.isStrikethrough());
				StyleConstants.setUnderline(temp, word.isUnderlined());

				this.coloredKeyWords.add(word);
			}
		}
	}

	/**
	 * Returns the Keywordlist
	 * 
	 * @return
	 */
	public List<ColoredKeyWord> getColoredKeyWords() {
		return coloredKeyWords;
	}

	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
		super.insertString(offs, str, a);

		int stringLength = str.length();
		int stringEndPosition = offs + stringLength;
		int stringPosition;

		for (int i = offs; i < stringEndPosition; i++) {
			currentPosition = i;
			stringPosition = i - offs;
			processCharacter(str.charAt(stringPosition));
		}
		currentPosition = offs;
	}

	private void processCharacter(char c) {
		char[] characterString = new char[1];
		characterString[0] = c;
		checkTextForKeyWords();
		String string = new String(characterString);
		processCharString(string);
	}

	private void processCharString(String s) {
		char strChar = s.charAt(0);
		// TODO: Handle String processing
		curWord += s;
	}

	private void insertStringWord(String str, int pos, SimpleAttributeSet simpleAttributeSet) throws Exception {
		this.remove(pos - str.length(), pos);
		super.insertString(pos - str.length(), str, simpleAttributeSet);

	}

	private void insertStringText(String s, int pos) throws Exception {
		this.remove(pos, s.length());
		super.insertString(pos, s, attributeSet);
	}

	private void checkTextForKeyWords() {
		try {
			processString(this.getText(0, this.getLength()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void processString(String in) {
		if (in.contains("ComputerCamp")) {
			int pos = in.indexOf("ComputerCamp");
			int end = pos + "ComputerCamp".length();
			try {
				insertStringWord("ComputerCamp", end, attributeSet);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}

class WritingMode {

	private final String modeName;
	private final int modeID;

	public WritingMode(String name, int mode) {
		this.modeID = mode;
		this.modeName = name;
	}

	public int getModeID() {
		return modeID;
	}

	public String getModeName() {
		return modeName;
	}
}