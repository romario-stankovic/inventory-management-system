package program;

import users.*;

import java.util.*;

public class Main {
	
	static Scanner input = new Scanner(System.in);
	
	static final String userFilename = "data\\users.txt";
	static final String logFilename = "data\\logs.txt";
	
	static List<User> users = new ArrayList<User>();
	
	public static void main(String[] args) {
		
		HelperFunctions.LogEvent("system start");
		
		LoadUsersFromFile(userFilename);
		
		while (true) {
			User user = null;
			System.out.println("----------MAIN MENU---------");
			String[] options = { "Login", "Exit" };
			int choice = HelperFunctions.DisplayMenu(options, null);
			switch (choice) {
			case 1:
				user = LoginMenu();
				HelperFunctions.LogEvent("user login: " + user.username);
				if(user instanceof Administrator) {
					AdminMenu((Administrator)user);
				}else if(user instanceof NullUser) {
					System.out.println("Sorry, you do not have a defined role");
					System.out.println("Logging out...");
					HelperFunctions.LogEvent("user logout: " + user.username);
					HelperFunctions.Pause();
				}
				break;
			case 2:
				System.out.println("Goodbye! :)");
				HelperFunctions.LogEvent("system exit");
				System.exit(0);
				break;
			}
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
	
	private static void WriteUsersToFile(String fileUrl) {
		List<String> lines = new ArrayList<String>();
		for(User user : users) {
			String line = user.username + "," + user.password + "," + user.name + "," + user.lastName + "," + user.type.toString();
			lines.add(line);
		}
		if(!FileIO.WriteLines(fileUrl, lines.toArray(new String[lines.size()]))) {
			System.out.println("Error while writing users to file...");
		}
	}
	
	private static User LoginMenu() {
		System.out.println("----------LOGIN----------");
		while(true) {
			String username;
			String password;
			System.out.println("Enter username: ");
			usernameCheck: while(true) {
				username = input.next();
				for(User user : users) {
					if(user.username.equals(username)) {
						break usernameCheck;
					}
				}
				System.out.println("No user with that username, try again: ");
			}
			
			System.out.println("Enter password for " + username + ": ");
			while (true) {
				password = input.next();
				for (User user : users) {
					if (user.password.equals(password)) {
						return user;
					}
				}
				System.out.println("Invalid password, try again: ");
			}
		}
	}
	
	private static void AdminMenu(Administrator admin) {
		System.out.println("----------ADMINISTRATOR PANEL----------");
		String[] options = {"View users in system","Register a new user", "Modify existing user", "Delete existing user", "View Logs", "Log out"};
		
		while(true) {
			int choice = HelperFunctions.DisplayMenu(options, null);
			switch(choice) {
			case 1:
				admin.ListAllUsers(users);
				break;
			case 2: 
				users = admin.RegisterNewUser(users);
				WriteUsersToFile(userFilename);
				break;
			case 3: 
				users = admin.ModifyUser(users);
				WriteUsersToFile(userFilename);
				break;
			case 4:
				users = admin.DeleteUser(users);
				WriteUsersToFile(userFilename);
				break;
			case 5:
				//TODO: Logs
				break;
			case 6: 
				HelperFunctions.LogEvent("user logout: " + admin.username); 
				return;
			}
		}
		
	}

}
