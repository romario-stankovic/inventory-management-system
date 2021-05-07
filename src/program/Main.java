package program;

import users.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Main {
	
	public static Scanner input = new Scanner(System.in);
	public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	public static final String userFilename = "data\\users.txt";
	public static final String logFilename = "data\\logs.txt";
	
	public static class LogEvent {
		public LocalDateTime dateTime;
		public String logText;
		public LogEvent(LocalDateTime dateTime, String logText) {
			this.dateTime = dateTime;
			this.logText = logText;
		}
	}
	
	public static List<User> users = new ArrayList<User>();
	
	public static List<LogEvent> logs = new ArrayList<LogEvent>();
	
	public static void main(String[] args) {
		
		HelperFunctions.LogEvent("system start");
		
		LoadUsersFromFile(userFilename);
		LoadLogsFromFile(logFilename);
		
		String[] options = { "Login", "Exit" };
		int choice = 0;
		do {
			User user = null;
			System.out.println("----------MAIN MENU---------");
			choice = HelperFunctions.DisplayMenu(options, null);
			if(choice == 1) {
				user = LoginMenu();
				HelperFunctions.LogEvent("user login: " + user.username);
				user.UserMenu();
			}
		} while(choice != 2);
		
		System.out.println("Goodbye! :)");
		HelperFunctions.LogEvent("system exit");

	}
	
	private static User LoginMenu() {
		System.out.println("----------LOGIN----------");
		
		int userID;
		System.out.println("Enter username: ");
		do {
			String username = input.next();
			userID = HelperFunctions.FindUserByUsername(users, username);
			if(userID == -1) {
				System.out.println("User does not exist, try again: ");
			}
		}while(userID == -1);
		
		System.out.println("Enter password: ");
		while(true) {
			String password;
			password = input.next();
			if(password.equals(users.get(userID).password)){
				return users.get(userID);
			}
			System.out.println("Password does not match, try again: ");
		}
		
	}
	
	private static void LoadUsersFromFile(String fileUrl) {
		String[] filelines = FileIO.ReadLines(fileUrl);
		for(String user : filelines) {
			String[] fields = user.split(",");
			UserTypes userType = UserTypes.valueOf(fields[4]);
			User userObject = null;
			switch(userType) {
				case Administrator:
					userObject = new Administrator(fields[0], fields[1], fields[2], fields[3], userType);
					break;
				case WarehouseWorker:
					userObject = new WarehouseWorker(fields[0], fields[1], fields[2], fields[3], userType);
					break;
				case Driver:
					userObject = new Driver(fields[0], fields[1], fields[2], fields[3], userType);
					break;
				case Null:
					userObject = new NullUser(fields[0], fields[1], fields[2], fields[3], userType);
					break;
			}
			users.add(userObject);
		}
	}
	
	private static void LoadLogsFromFile(String fileUrl) {
		String[] fileLines = FileIO.ReadLines(fileUrl);
		for(String line : fileLines) {
			String dateText = line.substring(line.indexOf('[')+1, line.indexOf(']'));
			String logText = line.substring(line.indexOf(']')+2);
			logs.add(new LogEvent(LocalDateTime.parse(dateText, formatter), logText));
		}
	}
	
	public static void WriteUsersToFile(String fileUrl) {
		List<String> lines = new ArrayList<String>();
		for(User user : users) {
			String line = user.username + "," + user.password + "," + user.name + "," + user.lastName + "," + user.type.toString();
			lines.add(line);
		}
		if(!FileIO.WriteLines(fileUrl, lines.toArray(new String[lines.size()]))) {
			System.out.println("Error while writing users to file...");
		}
	}

}
