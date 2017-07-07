package me.zcoding.text.editor.gui.syntaxHighlighting;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import me.zcoding.text.editor.gui.Main_GUI;
import me.zcoding.text.editor.settings.Settings;

public class Colored_Doc extends DefaultStyledDocument {

	private List<ColoredKeyWord> coloredKeyWords = new ArrayList<>();

	private SimpleAttributeSet standard = new SimpleAttributeSet();

	Timer timer;

	public Colored_Doc() {
		timer = new Timer();
		StyleConstants.setForeground(standard, Color.BLACK);
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
				word.setAttributeSet(temp);
				this.coloredKeyWords.add(word);
			}
		} else {
			setColoredKeyWords(new DefaultSyntax().getColoredKeyWords());
		}
		try {
			colorizeTheText();
		} catch (BadLocationException e) {
			e.printStackTrace();
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

	@Override
	public void remove(int offs, int len) throws BadLocationException {
		super.remove(offs, len);

	}

	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
		setColoredKeyWords(Settings.curSyntax.getColoredKeyWords());
		super.insertString(offs, str, a);
		colorizeTheText();
		startTimer();
	}

	private void startTimer() {
		if (timer != null) {
			timer.cancel();
			timer.purge();
			timer = new Timer();
		}
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				save();
			}
		}, 5000);
	}

	private void save() {
		for (int i = 0; i < Main_GUI.INSTANCE.getTextPanes().size(); i++) {
			PaneFileCompound compound = Main_GUI.INSTANCE.getTextPanes().get(i);
			if (compound.getFile() != null) {
				Main_GUI.INSTANCE.save(i);
				System.out.println("saved: " + compound.getFile().getName() + "_" + i);
			}
		}
	}

	public void colorizeTheText() throws BadLocationException {
		checkTextForKeyWords(0, this.getLength());
	}

	private void checkTextForKeyWords(int pos, int length) throws BadLocationException {
		int wordStart = 0;
		for (String line : this.getText(pos, length).split("\n")) {
			for (String word : line.split(" ")) {
				int wordEnd = wordStart + word.length();
				if (getKeyFromList(word) != null) {
					replaceWithColoredKeyWord(wordStart, getKeyFromList(word));
				} else {
					replaceWithColoredString(wordStart, null, word);
				}
				wordStart = wordEnd + 1;
			}
		}
	}

	private void replaceWithColoredKeyWord(int offs, ColoredKeyWord word) throws BadLocationException {
		this.replaceWithColoredString(offs, word.getSet(), word.getKey());
	}

	private void replaceWithColoredString(int offs, SimpleAttributeSet set, String word) throws BadLocationException {
		SimpleAttributeSet toSet = set;
		if (set != null)
			toSet = set;
		this.remove(offs, word.length());
		super.insertString(offs, word, toSet);
	}

	private ColoredKeyWord getKeyFromList(String word) {
		for (ColoredKeyWord coloredKeyWord : this.coloredKeyWords) {
			if (coloredKeyWord.getKey().equals(word)) {
				return coloredKeyWord;
			}
		}
		return null;
	}
}