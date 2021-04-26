package program;

import java.io.*;
import java.util.*;

public class FileIO {

	public static boolean WriteLines(String fileName, String[] lines) {
		try {
			File file = new File(fileName);
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			for (String line : lines) {
				writer.write(line);
				writer.newLine();
			}
			writer.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public static boolean AppendLine(String fileName, String line) {
		try {
			File file = new File(fileName);
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
			writer.append(line);
			writer.newLine();
			writer.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public static String[] ReadLines(String fileName) {
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
		} catch (IOException e) {
			return new String[0];
		}
	}

}
