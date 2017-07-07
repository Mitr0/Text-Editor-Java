package me.zcoding.text.editor.gui.syntaxHighlighting;

import java.awt.BorderLayout;
import java.io.File;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class PaneFileCompound {

	private File file;
	private JTextPane jTextPane;
	private JPanel panel;
	private JScrollPane scrollPane;
	private Colored_Doc colored_Doc;

	public PaneFileCompound(File file, ColoredKeyWordList defaultSyntaxList) {
		this.file = file;
		this.colored_Doc = new Colored_Doc();
		this.colored_Doc.setColoredKeyWords(defaultSyntaxList.getColoredKeyWords());
		panel = new JPanel();
		scrollPane = new JScrollPane();
		jTextPane = new JTextPane();
		panel.setLayout(new BorderLayout());
		panel.add(scrollPane, BorderLayout.CENTER);
		jTextPane.setDocument(colored_Doc);
		scrollPane.setViewportView(jTextPane);
	}

	public void stopTimer() {
		colored_Doc.timer.cancel();
		colored_Doc.timer.purge();
	}

	public void updateDoc(ColoredKeyWordList list) {
		this.colored_Doc.setColoredKeyWords(list.getColoredKeyWords());
		jTextPane.setDocument(colored_Doc);
	}

	public void setFile(File file) {
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public void setContent(List<String> data) {
		if (data.size() > 0) {
			String toAdd = "";
			for (String string : data) {
				toAdd += string + "\n";
			}
			System.out.println(toAdd);
			this.jTextPane.setText(toAdd);
		}
	}

	public JPanel getPanel() {
		return panel;
	}

	public String getContent() {
		return jTextPane.getText();
	}

}
