package program;

import users.*;

import java.util.*;

public class Main {
	
	static Scanner input = new Scanner(System.in);
	
	public static void main(String[] args) {
				
		HelperFunctions.LogEvent("system start");
		
		User user = null;
		
		while (true) {
			System.out.println("----------MAIN MENU---------");
			String[] options = { "Login", "Exit" };
			int choice;
			choice = HelperFunctions.DisplayMenu(options, null);
			switch (choice) {
			case 1:
				user = LoginMenu();
				HelperFunctions.LogEvent("user login: " + user.username);
				if(user instanceof Administrator) {
					AdminMenu((Administrator)user);
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
	
	private static User LoginMenu() {
		System.out.println("----------LOGIN----------");
		String[] users = FileIO.ReadLines("users.txt");
		String username;
		String password;
		
		while(true) {
			System.out.println("Enter username: ");
			usernameCheck: while(true) {
				username = input.next();
				for(String user : users) {
					String[] fields = user.split(",");
					if(fields[0].equals(username)) {
						break usernameCheck;
					}
				}
				System.out.println("No user with that username, try again: ");
			}
			
			System.out.println("Enter password for " + username + ": ");
			while (true) {
				password = input.next();
				for (String user : users) {
					String[] fields = user.split(",");

					if (fields[1].equals(password)) {
						UserTypes userType = UserTypes.valueOf(fields[4]);
						User u = null;

						switch (userType) {
						case Administrator:
							u = new Administrator(fields[0], fields[1], fields[2], fields[3],
									UserTypes.valueOf(fields[4]));
							break;
						case ShippingAndReceivingWorker:
							u = new ShippingAndReceivingWorker(fields[0], fields[1], fields[2], fields[3],
									UserTypes.valueOf(fields[4]));
							break;
						case WarehouseSorter:
							u = new WarehouseSorter(fields[0], fields[1], fields[2], fields[3],
									UserTypes.valueOf(fields[4]));
							break;
						case Driver:
							u = new Driver(fields[0], fields[1], fields[2], fields[3], UserTypes.valueOf(fields[4]));
							break;
						case Null:
							// TODO: Add Null User
							break;
						}

						return u;
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
				admin.ListAllUsers();
				break;
			case 2: 
				admin.RegisterNewUser();
				break;
			case 3: 
				admin.ModifyUser();
				break;
			case 4:
				admin.DeleteUser();
				break;
			case 6: 
				HelperFunctions.LogEvent("User logout: " + admin.username); 
				return;
			}
		}
		
	}

}
