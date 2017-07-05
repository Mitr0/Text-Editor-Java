package me.zcoding.text.editor.gui.syntaxHighlighting;

import java.io.File;

import javax.swing.JTextPane;

public class PaneFileCompound {

	private File file;
	private JTextPane jTextPane;

	public PaneFileCompound(File file, JTextPane jTextPane) {
		this.file = file;
		this.jTextPane = jTextPane;
	}

	public File getFile() {
		return file;
	}

	public JTextPane getjTextPane() {
		return jTextPane;
	}

}
