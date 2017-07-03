package me.zcoding.text.editor;

import me.zcoding.text.editor.filesystem.FileManager;
import me.zcoding.text.editor.gui.Main_GUI;
import me.zcoding.text.editor.utils.Utils;

public class Main {

	public static void main(String[] main) {
		try {
			if (!Utils.mainFolder.exists())
				Utils.mainFolder.mkdir();

			FileManager fileManager = new FileManager();

			Main_GUI main_GUI = new Main_GUI();
			main_GUI.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
