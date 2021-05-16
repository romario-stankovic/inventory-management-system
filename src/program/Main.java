package program;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import users.*;

import program.Data.*;

public class Main {
	//Declare a scanner
	public static Scanner input = new Scanner(System.in);
	//Declare a DateTimeFormatter
	public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	//Create final fields that lead to file locations
	public static final String dirName = "data";
	public static final String userFilename = dirName + "\\users.txt";
	public static final String logFilename = dirName + "\\logs.txt";
	public static final String itemFilename = dirName + "\\items.txt";
	public static final String categoryFilename = dirName + "\\categories.txt";

	//List of items
	public static List<LogEvent> logs = new ArrayList<LogEvent>();
	public static List<User> users = new ArrayList<User>();
	
	public static List<Category> categories = new ArrayList<Category>();
	
	public static List<Item> items = new ArrayList<Item>();
	public static List<Item> inboundItems = new ArrayList<Item>();
	public static List<Item> outboundItems = new ArrayList<Item>();
	
	public static void main(String[] args) {

		//create data directory if it does not exist
		if(!FileIO.dirExists(dirName)) {
			FileIO.createDir(dirName);
		}
		
		//Go through each file and load data, if the files do not exist, create them
		if(FileIO.fileExists(logFilename)) {
			loadLogsFromFile();
		}else {
			FileIO.createFile(logFilename);
		}
		
		if(FileIO.fileExists(userFilename)) {
			loadUsersFromFile();
		}else {
			FileIO.createFile(userFilename);
		}
		
		if(FileIO.fileExists(categoryFilename)) {
			loadCategoriesFromFile();
		}else {
			FileIO.createFile(categoryFilename);
		}
		if(FileIO.fileExists(itemFilename)) {
			loadItemsFromFile();
		}else {
			FileIO.createFile(itemFilename);
		}
		
		//If there are no users in the system, create temp admin and create user
		while(users.size() == 0) {
			Administrator tempAdmin = (Administrator)UserFactory.getUser("", "", "", "", UserType.Administrator);
			//call the FirstTimeRegistrationFunction of the admin
			tempAdmin.FirstTimeRegistration();
		}
		
		//Log Event
		LogEvent("system ready");
		
		//Display options
		String[] options = { "Login", "Exit" };
		int choice = 0;
		//Display menu and try to get user
		do {
			User user = null;
			System.out.println("----------MAIN MENU---------");
			choice = HelperFunctions.DisplayMenu(options);
			if(choice == 1) {
				user = loginMenu();
				//If the user is not null, log him in
				if(user != null) {
					user.userMenu();
				}
			}
		}while(choice != 2);
		
		//If the user exits the menu, display a message
		System.out.println("Goodbye! :)");
		//Log event
		LogEvent("system stopped");

	}
	
	private static User loginMenu() {
		System.out.println("----------LOGIN----------");
		
		int userID;
		System.out.println("Enter username: ");
		//Ask the user to enter a username
		do {
			String username = input.next();
			if(username.equals("cancel")){
				return null;
			}
			userID = HelperFunctions.FindUserByUsername(users, username);
			if(userID == -1) {
				System.out.println("User does not exist, try again: ");
			}
		}while(userID == -1);
		
		//If we entered a correct username, ask the user to enter a password
		System.out.println("Enter password: ");
		String password;
		while(true) {
			password = input.next();
			
			if(password.equals("cancel")) {
				break;
			}
			//If password matches, return the user
			if(password.equals(users.get(userID).password)){
				return users.get(userID);
			}
			
			//if it does not match, display a message
				System.out.println("Password does not match, try again: ");
		}
		return null;
	}
	
	private static void loadUsersFromFile() {
		//Read lines from file
		String[] filelines = FileIO.readLines(userFilename);
		for(String user : filelines) {
			//foreach user, split the fileds
			String[] fields = user.split(",");
			//Get user type
			UserType userType = UserType.valueOf(fields[4]);
			//based on user type, call userFactory and get the user
			User userObject = UserFactory.getUser(fields[0], fields[1], fields[2], fields[3], userType);
			//Add users to list
			users.add(userObject);
		}
		//Log Event
		Main.LogEvent("loaded " + users.size() +" users");
	}
	
	private static void loadLogsFromFile() {
		//Load logs
		String[] fileLines = FileIO.readLines(logFilename);
		for(String line : fileLines) {
			//foreach log, read date and logText
			String dateText = line.substring(line.indexOf('[')+1, line.indexOf(']'));
			String logText = line.substring(line.indexOf(']')+2);
			//add logs to list
			logs.add(new LogEvent(LocalDateTime.parse(dateText, formatter), logText));
		}
		//Log Event
		Main.LogEvent("loaded " + logs.size() +" logs");
	}
	
	private static void loadCategoriesFromFile() {
		//load categories
		String[] fileLines = FileIO.readLines(categoryFilename);
		for(String line : fileLines) {
			//Split info
			String[] fields = line.split(",");
			int id;
			//get category id
			id = Integer.parseInt(fields[0]);
			//Add category to list
			categories.add(new Category(id, fields[1]));
		}
		//Log Event
		Main.LogEvent("loaded " + categories.size() +" categories");
	}
	
	private static void loadItemsFromFile() {
		//load items
		String[] fileLines = FileIO.readLines(itemFilename);
		for(String line : fileLines) {
			//foreach line, split info
			String[] fields = line.split(",");
			//add item to list
			items.add(new Item(fields[0], Float.parseFloat(fields[1]), Integer.parseInt(fields[2]), Integer.parseInt(fields[3])));
		}
	}
	
	public static void writeUsersToFile() {
		List<String> lines = new ArrayList<String>();
		for(User user : users) {
			String line = user.username + "," + user.password + "," + user.name + "," + user.lastName + "," + user.type.toString();
			lines.add(line);
		}
		if(!FileIO.writeLines(userFilename, lines.toArray(new String[lines.size()]))) {
			System.out.println("Error while writing users to file...");
		}
	}
	
	public static void writeLogToFile() {
		List<String> lines = new ArrayList<String>();
		for(LogEvent log : logs) {
			String line = "["+log.dateTime.format(formatter)+"] " + log.logText;
			lines.add(line);
		}
		
		if(!FileIO.writeLines(logFilename, lines.toArray(new String[lines.size()]))) {
			System.out.println("Error while writing logs to file...");
		}
		
	}
	
	public static void writeCategoriesToFile() {
		List<String> lines = new ArrayList<String>();
		for(Category category : categories) {
			String line = category.id + "," + category.name;
			lines.add(line);
		}
		
		if(!FileIO.writeLines(categoryFilename, lines.toArray(new String[lines.size()]))) {
			System.out.println("Error while writing categories to file...");
		}
	}

	public static void writeItemToFile() {
		List<String> lines = new ArrayList<String>();
		for(Item item : items) {
			String line = item.name + "," + item.massPerUnit + "," + item.numberOfUnits + "," + item.category;
			lines.add(line);
		}
		
		if(!FileIO.writeLines(itemFilename, lines.toArray(new String[lines.size()]))) {
			System.out.println("Error while writing items to file...");
		}
	}
	
	public static void LogEvent(String eventText) {
		logs.add(new LogEvent(LocalDateTime.now(), eventText));
		writeLogToFile();
	}
	
}
