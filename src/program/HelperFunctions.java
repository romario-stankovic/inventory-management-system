package program;

import java.time.LocalDateTime;
import java.util.*;

public class HelperFunctions {
	
	private static Scanner input;
	
	public static void LogEvent(String eventText) {
		LocalDateTime dateTime = LocalDateTime.now();
		
		String now = "[" + dateTime.getYear() + "-" + 
		dateTime.getMonthValue() + "-" + dateTime.getDayOfMonth() +
		" " + dateTime.getHour() + ":" + dateTime.getMinute() + 
		":" + dateTime.getSecond() + "] ";
		
		FileIO.AppendLine("logs.txt", now + eventText);
	}
	
	public static int DisplayMenu(String[] menuOptions, String customMessage) {
		input = new Scanner(System.in);
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
	
	public static void Pause() {
		input.nextLine();
		System.out.print("Press ENTER to continue...");
		input.nextLine();
	}
}
