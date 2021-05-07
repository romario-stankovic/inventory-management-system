package program;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.List;

import users.*;

public class HelperFunctions {
	
	private static Scanner input;
	
	public static void LogEvent(String eventText) {
		LocalDateTime dateTime = LocalDateTime.now();
		String now = "[" + dateTime.format(Main.formatter) + "] ";
		Main.logs.add(new Main.LogEvent(dateTime, eventText));
		FileIO.AppendLine(Main.logFilename, now + eventText);
	}
	
	public static int DisplayMenu(String[] menuOptions, String customMessage) {
		input = Main.input;
		
		if(customMessage != null) {
			System.out.println(customMessage);
		}else {
			System.out.println("Select option: ");
		}
		
		for(int i=1; i<=menuOptions.length; i++) {
			System.out.println(i + ") " + menuOptions[i-1]);
		}
		
		int choice = -1;
		do {
			try {
				choice = input.nextInt();
			}catch (Exception e) {
				input.next();
				choice = -1;
			}
			if(choice < 1 || choice > menuOptions.length) {
				System.out.println("Invalid option, try again: ");
			}
		}while(choice < 1 || choice > menuOptions.length);
		
		return choice;
	}
	
	public static int FindUserByUsername(List<User> users, String username) {
		for(int i=0; i<users.size(); i++) {
			if(users.get(i).username.equals(username)) {
				return i;
			}
		}
		return -1;
	}
	
	public static void Pause() {
		input.nextLine();
		System.out.print("Press ENTER to continue...");
		input.nextLine();
	}
	
	public static void ClearScreen() {
		
	}
}
