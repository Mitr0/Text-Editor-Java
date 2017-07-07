package me.zcoding.text.editor;

import me.zcoding.text.editor.filesystem.FileManager;
import me.zcoding.text.editor.gui.Main_GUI;
import me.zcoding.text.editor.utils.Utils;

public class Main {

	public static void main(String[] main) {
		init();
	}

	private static void init() {
		try {
			if (!Utils.mainFolder.exists())
				Utils.mainFolder.mkdir();
			if (!Utils.langFolder.exists())
				Utils.langFolder.mkdir();
			if (!Utils.settingsFolder.exists())
				Utils.settingsFolder.mkdir();
			if (!Utils.syntaxFolder.exists())
				Utils.syntaxFolder.mkdir();

			new FileManager();

			new Main_GUI().init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
