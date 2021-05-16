package program;

import java.io.*;
import java.util.*;

public class FileIO {

	public static boolean writeLines(String fileName, String[] lines) {
		//Try to write each string to file
		try {
			File file = new File(fileName);
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			for (String line : lines) {
				writer.write(line);
				writer.newLine();
			}
			writer.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean appendLine(String fileName, String line) {
		//Try to append to file
		try {
			File file = new File(fileName);
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
			writer.append(line);
			writer.newLine();
			writer.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static String[] readLines(String fileName) {
		//Read lines from file
		List<String> lines = new ArrayList<String>();
		try {
			File file = new File(fileName);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String readLine = null;
			while ((readLine = reader.readLine()) != null) {
				lines.add(readLine);
			}
			reader.close();
			return lines.toArray(new String[lines.size()]);
		} catch (Exception e) {
			return new String[0];
		}
	}
	
	public static boolean dirExists(String dirName) {
		//Check if directory exists
		File file = new File(dirName);
		return file.isDirectory();
	}
	
	public static boolean fileExists(String fileName) {
		//Check if file exists
		File file = new File(fileName);
		return file.exists();
	}
	
	public static void createFile(String fileName) {
		//create a file
		File file = new File(fileName);
		try {
			file.createNewFile();
		}catch(Exception e) {}
	}
	
	public static void createDir(String dirName) {
		//create a directory
		File file = new File(dirName);
		file.mkdir();
	}

}
