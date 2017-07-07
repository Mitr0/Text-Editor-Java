package me.zcoding.text.editor.settings;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.zcoding.text.editor.filesystem.A_File;
import me.zcoding.text.editor.lang.Language;
import me.zcoding.text.editor.utils.Utils;

public class SettingsFile extends A_File {

	public SettingsFile() {
		super("language.set", Utils.settingsFolder);
	}

	@Override
	public List<String> toWrite() throws Exception {
		List<String> aList = new ArrayList<>();
		aList.add(Settings.curLanguage.getFile_name());
		return aList;
	}

	@Override
	public void onRead(List<String> data) throws Exception {
		if (data.size() > 0) {
			for (File file : Utils.langFolder.listFiles()) {
				if (file.getName().endsWith(".lang")) {
					for (Language language : Settings.avaiableLanguages) {
						if (language.getFile_name().equals(data.get(0))) {
							Settings.curLanguage = language;
							System.out.println(Settings.curLanguage.getFile_name());
							break;
						}
					}
				}
			}
		}
	}

	public boolean replace() {
		return true;
	}

}