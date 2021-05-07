package users;

import java.time.LocalDateTime;
import java.util.*;

import program.*;

public class Administrator extends User {

	private Scanner input;
	
	public Administrator(String username, String password, String name, String lastName, UserTypes type) {
		super(username, password, name, lastName, type);
		input = Main.input;
	}
	
	@Override
	public void UserMenu() {
		System.out.println("----------ADMINISTRATOR PANEL----------");
		String[] options = {"View users in system","Register a new user", "Modify existing user", "Delete existing user", "View Logs", "Log out"};
		
		int choice = 0;
		while(choice != 6) {
			choice = HelperFunctions.DisplayMenu(options, null);
			switch(choice) {
			case 1:
				ListAllUsers();
				break;
			case 2: 
				RegisterNewUser();
				break;
			case 3: 
				ModifyUser();
				break;
			case 4:
				DeleteUser();
				break;
			case 5:
				ListLogs();
				break;
			case 6: 
				HelperFunctions.LogEvent("user logout: " + username); 
				break;
			}
		}
	}
	
	private void ListAllUsers() {
		System.out.printf("%15s%15s%15s%30s\n\n", "Username", "Name","Last Name", "User Type");
		for(User user : Main.users) {
			System.out.printf("%15s%15s%15s%30s\n", user.username, user.name, user.lastName, user.type);
		}
		System.out.println("");
		HelperFunctions.Pause();
	}
	
	private void RegisterNewUser() {
		System.out.println("----------REGISTER A NEW USER----------");
		
		System.out.println("Enter username: ");
		
		String username;
		int userID;
		do {
			username = input.next();
			userID = HelperFunctions.FindUserByUsername(Main.users, username);
			if(userID != -1) {
				System.out.println("Username already taken, please try again: ");
			}
		}while(userID != -1);
		
		System.out.println("Enter password: ");
		String password = input.next();
		
		System.out.println("Repeat password: ");
		String passwordVerification;
		
		do {
			passwordVerification = input.next();
			if(!password.equals(passwordVerification)) {
				System.out.println("Password does not match, try again: ");
			}
		}while(!password.equals(passwordVerification));
		
		System.out.println("Enter user's name: ");
		String name = input.next();
		System.out.println("Enter user's last name: ");
		String lastName = input.next();
		
		String[] options = new String[UserTypes.values().length];
		
		UserTypes userType;
		for(int i=0; i<UserTypes.values().length; i++) {
			options[i] = UserTypes.values()[i].toString();
		}
		
		int choice = HelperFunctions.DisplayMenu(options, "Select type of user: ");
		
		userType = UserTypes.values()[choice-1];
		
		User newUser = null;
		switch(userType) {
		case Administrator:
			newUser = new Administrator(username, password, name, lastName, userType);
			break;
		case Driver:
			newUser = new Driver(username, password, name, lastName, userType);
			break;
		case WarehouseWorker:
			newUser = new WarehouseWorker(username, password, name, lastName, userType);
			break;
		case Null:
			newUser = new NullUser(username, password, name, lastName, userType);
			break;
		}
		
		Main.users.add(newUser);
		Main.WriteUsersToFile(Main.userFilename);
		System.out.println("User registerd successfully!");
		HelperFunctions.LogEvent(this.username + " registered " + username + " to the system");
		HelperFunctions.Pause();
		
	}
	
	private void ModifyUser() {
		
		User modifyUser;
		int userID = -1;
		System.out.println("Enter username of the user you want to modify: ");
		do {
			String username = input.next();
			userID = HelperFunctions.FindUserByUsername(Main.users, username);
			if(userID == -1) {
				System.out.println("There is no user with that username, try again: ");
			}
		}while(userID == -1);
		
		modifyUser = Main.users.get(userID);
		
		String[] options = {"Username", "Password", "Name", "Last Name", "User Type", "Back"};
		
		int choice;
		do {
		choice = HelperFunctions.DisplayMenu(options, "Select field to modify: ");
		switch(choice) {
		case 1:
			System.out.println("Enter new username: ");
			String newUsername;
			int newUserID;
			do {
				newUsername = input.next();
				newUserID = HelperFunctions.FindUserByUsername(Main.users, newUsername);
				if(newUserID != -1) {
					System.out.println("Username already taken, try again: ");
				}
			}while(newUserID != -1);
			
			HelperFunctions.LogEvent(username + " changed username of " + modifyUser.username + " to " + newUsername);
			modifyUser.username = newUsername;
			break;
		case 2:
			System.out.println("Enter new password");
			String newPassword = input.next();
			System.out.println("Confirm password");
			String confirmPassword;
			do {
				confirmPassword = input.next();
				if(!newPassword.equals(confirmPassword)) {
					System.out.println("Passwords do not match, try again: ");
				}
			}while(!newPassword.equals(confirmPassword));

			modifyUser.password = newPassword;
			HelperFunctions.LogEvent(username + " changed password of " + modifyUser.username + " to " + newPassword);

			break;
		case 3:
			System.out.println("Enter new name: ");
			String newName = input.next();
			modifyUser.name = newName;
			HelperFunctions.LogEvent(username + " changed name of " + modifyUser.username + " to " + newName);

			break;
		case 4:
			System.out.println("Enter new last name: ");
			String newLastName = input.next();
			modifyUser.lastName = newLastName;
			HelperFunctions.LogEvent(username + " changed last name of " + modifyUser.username + " to " + newLastName);

			break;
		case 5:
			String[] typeOptions = new String[UserTypes.values().length];
			
			for(int i=0; i<UserTypes.values().length; i++) {
				typeOptions[i] = UserTypes.values()[i].toString();
			}
			
			int typeChoice = HelperFunctions.DisplayMenu(typeOptions, "Select type of user: ");
			
			modifyUser.type = UserTypes.values()[typeChoice-1];
			
			HelperFunctions.LogEvent(username + " changed type of " + modifyUser.username + " to " + UserTypes.values()[typeChoice-1]);

			break;
		}
		}while(choice != 6);
		
		Main.users.set(userID, modifyUser);
		Main.WriteUsersToFile(Main.userFilename);
		System.out.println("User modified successfully!");
		HelperFunctions.Pause();
	}
	
	private void DeleteUser() {
		
		System.out.println("Enter username of the user you want to delete: ");
		
		int userID;
		String username;
		do {
			username = input.next();
			userID = HelperFunctions.FindUserByUsername(Main.users, username);
			if(userID == -1) {
				System.out.println("There is no user with that username, try again: ");
			}
		}while(userID == -1);
		
		Main.users.remove(userID);
		Main.WriteUsersToFile(Main.userFilename);
		HelperFunctions.LogEvent(this.username + " removed " + username + " from the system");
		System.out.println("User deleted successfully!");
		HelperFunctions.Pause();
	}
	
	private void ListLogs() {
		
		String[] options = {"Today", "Yesterday", "Last 7 days", "Last 30 days", "Date range", "Back"};
		
		int choice = 0;
		do {
			choice = HelperFunctions.DisplayMenu(options, null);
			switch(choice) {
			case 1:
				
				System.out.println("YYYY-MM-DD HH:MM:SS | LOG");
				for(Main.LogEvent event : Main.logs) {
					if(event.dateTime.getYear() == LocalDateTime.now().getYear() && event.dateTime.getDayOfYear() == LocalDateTime.now().getDayOfYear()) {
						System.out.println(event.dateTime.format(Main.formatter)+ " | " + event.logText);
					}
				}
				HelperFunctions.Pause();
				
				break;
			case 2:
				
				System.out.println("YYYY-MM-DD HH:MM:SS | LOG");
				for(Main.LogEvent event : Main.logs) {
					if(event.dateTime.getYear() == LocalDateTime.now().minusDays(1).getYear() && event.dateTime.getDayOfYear() == LocalDateTime.now().minusDays(1).getDayOfYear()) {
						System.out.println(event.dateTime.format(Main.formatter)+ " | " + event.logText);
					}
				}
				HelperFunctions.Pause();
				
				break;
			case 3:
				System.out.println("YYYY-MM-DD HH:MM:SS | LOG");
				for(Main.LogEvent event : Main.logs) {
					if(event.dateTime.isBefore(LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0))) {
						if(event.dateTime.isAfter(LocalDateTime.now().minusDays(8).withHour(23).withMinute(59).withSecond(59))) {
							System.out.println(event.dateTime.format(Main.formatter)+ " | " + event.logText);
						}
					}
				}
				HelperFunctions.Pause();
				break;
			case 4:
				System.out.println("YYYY-MM-DD HH:MM:SS | LOG");
				for(Main.LogEvent event : Main.logs) {
					if(event.dateTime.isBefore(LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0))) {
						if(event.dateTime.isAfter(LocalDateTime.now().minusMonths(1).minusDays(1).withHour(23).withMinute(59).withSecond(59))) {
							System.out.println(event.dateTime.format(Main.formatter)+ " | " + event.logText);
						}
					}
				}
				HelperFunctions.Pause();
				break;
			case 5: 
				input.nextLine();
				String startDateInput;
				String endDateInput;
				System.out.println("Enter starting date (YYYY-MM-DD HH:mm:ss)");
				LocalDateTime sdt;
				LocalDateTime edt;
				while (true) {
					startDateInput = input.nextLine();
					try {
						sdt = LocalDateTime.parse(startDateInput, Main.formatter);
						break;
					} catch (Exception e) {
						System.out.println("Invalid date format, try again: ");
					}
				}
				System.out.println("Enter ending date (YYYY-MM-DD HH:mm:ss)");
				while (true) {
					endDateInput = input.nextLine();
					try {
						edt = LocalDateTime.parse(endDateInput, Main.formatter);
						break;
					} catch (Exception e) {
						System.out.println("Invalid date format, try again: ");
					}
				}
				
				for(Main.LogEvent event : Main.logs) {
					if(event.dateTime.isBefore(edt)) {
						if(event.dateTime.isAfter(sdt)) {
							System.out.println(event.dateTime.format(Main.formatter)+ " | " + event.logText);
						}
					}
				}
				HelperFunctions.Pause();
				
				break;
			}
		}while(choice != 6);
	}

}
