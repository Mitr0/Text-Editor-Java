package me.zcoding.text.editor.editing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class LoadSaveManager {

	public static List<String> loadFile(File toload) {
		List<String> list = new ArrayList<>();
		try {
			FileReader fileReader = new FileReader(toload);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				list.add(line);
			}
			bufferedReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static void saveFile(List<String> toSave, File toSaves) {
		try {
			FileWriter fileWriter = new FileWriter(toSaves);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			for (String string : toSave) {
				bufferedWriter.write(string);
				bufferedWriter.newLine();
			}
			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
