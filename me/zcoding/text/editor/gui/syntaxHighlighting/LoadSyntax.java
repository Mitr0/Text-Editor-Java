package me.zcoding.text.editor.gui.syntaxHighlighting;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.zcoding.text.editor.utils.Utils;

public class LoadSyntax {

	private File[] syntaxFiles;

	private List<ColoredKeyWordList> coloredKeyWords;

	public File[] getSyntaxFiles() {
		return syntaxFiles;
	}

	public LoadSyntax() {
		coloredKeyWords = new ArrayList<>();
		loadSyntaxes();
	}

	public List<ColoredKeyWordList> getColoredKeywords() {
		return coloredKeyWords;
	}

	public void loadSyntaxes() {
		load loadSyntThread = new load();
		loadSyntThread.start();
	}

	private Map<String, String> readSyntaxFile(File syntFile) {
		Map<String, String> data = new HashMap<>();
		try {
			FileReader fileReader = new FileReader(syntFile);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String curLine;
			while ((curLine = bufferedReader.readLine()) != null) {
				if (!curLine.isEmpty()) {
					if (curLine.contains("=")) {
						String[] line = curLine.split("=");
						if (!line[0].isEmpty() && !line[1].isEmpty()) {
							data.put(line[0], line[1]);
						}
					}
				}
			}
			bufferedReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	public class load extends Thread {
		public void run() {
			syntaxFiles = Utils.syntaxFolder.listFiles();
			for (File file : syntaxFiles) {
				if (file.getName().endsWith(".syn")) {
					Map<String, String> synt = readSyntaxFile(file);
					ColoredKeyWordList coloredKeyWordList = new ColoredKeyWordList();
					coloredKeyWordList.setName(file.getName().split(".syn")[0]);
					coloredKeyWordList.setColoredKeyWords(parse(synt));
					coloredKeyWords.add(coloredKeyWordList);
				}
			}
		}

		private List<ColoredKeyWord> parse(Map<String, String> in) {
			List<ColoredKeyWord> words = new ArrayList<>();
			for (String key : in.keySet()) {
				String data = in.get(key);
				String[] datas = data.split(";");
				String color = "#000000";
				boolean bold = false, itallic = false, underl = false, strikethr = false;
				try {
					color = datas[0];
				} catch (Exception e) {
				}
				try {
					bold = Boolean.parseBoolean(datas[1]);
				} catch (Exception e) {
				}
				try {
					itallic = Boolean.parseBoolean(datas[2]);
				} catch (Exception e) {
				}
				try {
					underl = Boolean.parseBoolean(datas[3]);
				} catch (Exception e) {
				}
				try {
					strikethr = Boolean.parseBoolean(datas[4]);
				} catch (Exception e) {
				}
				ColoredKeyWord keyWord = new ColoredKeyWord(key, Color.decode(color), bold, itallic, underl, strikethr);
				words.add(keyWord);
			}
			return words;
		}
	}
}
