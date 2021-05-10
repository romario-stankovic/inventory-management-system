package users;

import java.util.Scanner;
import java.time.LocalDateTime;

import program.Main;
import program.HelperFunctions;

import program.Data.UserType;
import program.Data.LogEvent;


public class Administrator extends User {

	private Scanner input = Main.input;

	public Administrator(String username, String password, String name, String lastName, UserType type) {
		super(username, password, name, lastName, type);
	}

	@Override
	public void userMenu() {
		//call logIn function
		logIn();
		
		//List of menu items
		String[] options = { "View users in system", "Register a new user", "Modify existing user", "Delete existing user", "View Logs", "Logout" };
		//Admin choice
		int choice = 0;
		do {
			System.out.println("----------ADMINISTRATOR PANEL----------");
			choice = HelperFunctions.DisplayMenu(options);
			switch (choice) {
			case 1:
				listAllUsers();
				break;
			case 2:
				registerNewUser(false);
				break;
			case 3:
				modifyUser();
				break;
			case 4:
				deleteUser();
				break;
			case 5:
				listLogs();
				break;
			case 6:
				logOut();
				break;
			}
		}while (choice != 6);
	}

	@Override
	public void logIn() {
		//Log Event
		Main.LogEvent("admin login: " + username);
	}
	
	@Override
	public void logOut() {
		//Log Event
		Main.LogEvent("admin logout: " + username);
	}
	
	private void listAllUsers() {
		System.out.println("----------LIST ALL USERS----------");
		//Go through each user and display data
		for (User user : Main.users) {
			System.out.println(user.username + "," + user.name + "," + user.lastName + "," + user.type);
		}
		System.out.println("");
		HelperFunctions.Pause();
	}

	public void FirstTimeRegistration() {
		System.out.println("----------REGISTER AN ADMINISTRATOR----------");
		registerNewUser(true);
	}
	
	private void registerNewUser(boolean firstTime) {
		if(firstTime == false) {
			System.out.println("----------REGISTER A NEW USER----------");
		}
		System.out.println("Enter username: ");

		String username;
		//Ask the admin to enter the username until we find it available
		int userID = -1;
		do {
			username = input.next();
			userID = HelperFunctions.FindUserByUsername(Main.users, username);
			if (userID != -1) {
				System.out.println("Username already taken, please try again: ");
			}
		} while (userID != -1);

		//Ask the admin to enter the password and to verify it
		
		System.out.println("Enter password: ");
		String password = input.next();

		System.out.println("Repeat password: ");
		String passwordVerification;
		do {
			passwordVerification = input.next();
			//Check if the password matches
			if (!password.equals(passwordVerification)) {
				//If it doesn't, ask the admin to try again
				System.out.println("Password does not match, try again: ");
			}
		} while (!password.equals(passwordVerification));

		//Ask the admin to enter users name and last name
		System.out.println("Enter user's name: ");
		String name = input.next();
		System.out.println("Enter user's last name: ");
		String lastName = input.next();

		
		UserType userType;
		if(firstTime == false) {
			//Create a list from user types
			String[] options = new String[UserType.values().length];

			for (int i = 0; i < UserType.values().length; i++) {
				options[i] = UserType.values()[i].toString();
			}
			//Ask the admin to select type of user he is creating
			int choice = HelperFunctions.DisplayMenu(options, "Select type of user: ");

			userType = UserType.values()[choice - 1];
		}else {
			userType = UserType.Administrator;
		}
		//Create user of type
		User newUser = null;
		switch (userType) {
		case Administrator:
			newUser = new Administrator(username, password, name, lastName, userType);
			break;
		case Driver:
			newUser = new Driver(username, password, name, lastName, userType);
			break;
		case Clerk:
			newUser = new Clerk(username, password, name, lastName, userType);
			break;
		case Null:
			newUser = new NullUser(username, password, name, lastName, userType);
			break;
		}
		//Add user to list and write to file
		Main.users.add(newUser);
		Main.writeUsersToFile();
		//Log Event
		Main.LogEvent(this.username + " registered " + username + " to the system");
		//Display a message
		System.out.println("User registerd successfully!");
		HelperFunctions.Pause();

	}

	private void modifyUser() {
		System.out.println("----------MODIFY USER----------");
		User modifyUser;
		//Ask the admin to enter a username
		int userID = -1;
		System.out.println("Enter username of the user you want to modify: ");
		//Check if we have the user in the system
		do {
			String username = input.next();
			userID = HelperFunctions.FindUserByUsername(Main.users, username);
			if (userID == -1) {
				//if we don't, ask the admin to try again
				System.out.println("There is no user with that username, try again: ");
			}
		} while (userID == -1);

		modifyUser = Main.users.get(userID);

		String[] options = { "Username", "Password", "Name", "Last Name", "User Type", "Back" };
		//Ask the admin to chose what to modify
		int choice;
		do {
			choice = HelperFunctions.DisplayMenu(options, "Select field to modify: ");
			switch (choice) {
			case 1:
				//if the admin choose the username, check availability
				System.out.println("Enter new username: ");
				String newUsername;
				int newUserID;
				do {
					newUsername = input.next();
					newUserID = HelperFunctions.FindUserByUsername(Main.users, newUsername);
					if (newUserID != -1) {
						System.out.println("Username already taken, try again: ");
					}
				} while (newUserID != -1);
				//Log Event
				Main.LogEvent(username + " changed username of " + modifyUser.username + " to " + newUsername);
				//If the username is available, change the username of the user
				modifyUser.username = newUsername;
				break;
			case 2:
				//If the admin is changing the password, ask them to enter a new one and confirm it
				System.out.println("Enter new password");
				String newPassword = input.next();
				System.out.println("Confirm password");
				String confirmPassword;
				//Check if password matches
				do {
					confirmPassword = input.next();
					if (!newPassword.equals(confirmPassword)) {
						System.out.println("Passwords do not match, try again: ");
					}
				} while (!newPassword.equals(confirmPassword));
				//If the password matches, modify the user
				modifyUser.password = newPassword;
				//Log Event
				Main.LogEvent(username + " changed password of " + modifyUser.username + " to " + newPassword);

				break;
			case 3:
				//If the admin is modifying the name, ask them to enter a new one
				System.out.println("Enter new name: ");
				String newName = input.next();
				//Modify user
				modifyUser.name = newName;
				//Log Event
				Main.LogEvent(username + " changed name of " + modifyUser.username + " to " + newName);

				break;
			case 4:
				//If admin modifies the last name, allow them to enter a new one
				System.out.println("Enter new last name: ");
				String newLastName = input.next();
				//Modify user
				modifyUser.lastName = newLastName;
				//Log Event
				Main.LogEvent(username + " changed last name of " + modifyUser.username + " to " + newLastName);
				break;
			case 5:
				//if the admin is modifying userType
				//Create a list of options
				String[] typeOptions = new String[UserType.values().length];
				for (int i = 0; i < UserType.values().length; i++) {
					typeOptions[i] = UserType.values()[i].toString();
				}
				
				//Ask the admin to choose
				int typeChoice = HelperFunctions.DisplayMenu(typeOptions, "Select type of user: ");
				//Modify the user
				modifyUser.type = UserType.values()[typeChoice - 1];
				
				//When modifying the user type, we need to change the instance of the user
				switch (modifyUser.type) {
				case Administrator:
					modifyUser = new Administrator(modifyUser.username, modifyUser.password, modifyUser.name, modifyUser.lastName, modifyUser.type);
					break;
				case Clerk:
					modifyUser = new Clerk(modifyUser.username, modifyUser.password, modifyUser.name, modifyUser.lastName, modifyUser.type);
					break;
				case Driver:
					modifyUser = new Driver(modifyUser.username, modifyUser.password, modifyUser.name, modifyUser.lastName, modifyUser.type);
					break;
				case Null:
					modifyUser = new NullUser(modifyUser.username, modifyUser.password, modifyUser.name, modifyUser.lastName, modifyUser.type);
					break;
				}
				
				Main.users.set(userID, modifyUser);
				//Log event
				Main.LogEvent(username + " changed type of " + modifyUser.username + " to " + UserType.values()[typeChoice - 1]);

				break;
			}
		} while (choice != 6);
		
		//When we finish, write users to file
		Main.writeUsersToFile();
		//Display a message
		System.out.println("User modified successfully!");
		HelperFunctions.Pause();
	}

	private void deleteUser() {
		System.out.println("----------DELETE USER----------");
		//Ask the admin for the username to delete
		System.out.println("Enter username of the user you want to delete: ");
		
		int userID;
		String username;
		//Try and find a user with that username
		do {
			username = input.next();
			userID = HelperFunctions.FindUserByUsername(Main.users, username);
			if (userID == -1) {
				System.out.println("There is no user with that username, try again: ");
			}
		} while (userID == -1);
		//Remove user from the system
		Main.users.remove(userID);
		//Save user file
		Main.writeUsersToFile();
		//Log Event
		Main.LogEvent(this.username + " removed " + username + " from the system");
		//Display a message
		System.out.println("User deleted successfully!");
		HelperFunctions.Pause();
	}

	private void listLogs() {
		System.out.println("----------LIST LOGS----------");
		//Options
		String[] options = { "Today", "Yesterday", "Last 7 days", "Last 30 days", "Date range", "Back" };

		//Admin choice
		int choice = 0;
		do {
			choice = HelperFunctions.DisplayMenu(options);
			switch (choice) {
			case 1:
				//If admin choose to view logs from today, find every log with same year and day of year
				for (LogEvent event : Main.logs) {
					if (event.dateTime.getYear() == LocalDateTime.now().getYear() && event.dateTime.getDayOfYear() == LocalDateTime.now().getDayOfYear()) {
						System.out.println(event.dateTime.format(Main.formatter) + " | " + event.logText);
					}
				}
				HelperFunctions.Pause();
				break;
			case 2:
				//If admin choose to display logs from yesterday, find the date before today and display logs
				for (LogEvent event : Main.logs) {
					if (event.dateTime.getYear() == LocalDateTime.now().minusDays(1).getYear() && event.dateTime.getDayOfYear() == LocalDateTime.now().minusDays(1).getDayOfYear()) {
						System.out.println(event.dateTime.format(Main.formatter) + " | " + event.logText);
					}
				}
				HelperFunctions.Pause();
				break;
			case 3:
				//If the admin choose to display logs from 7 days ago, we check if the log date is in range
				//If it is, display it
				for (LogEvent event : Main.logs) {
					if (event.dateTime.isBefore(LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0))) {
						if (event.dateTime.isAfter(LocalDateTime.now().minusDays(8).withHour(23).withMinute(59).withSecond(59))) {
							System.out.println(event.dateTime.format(Main.formatter) + " | " + event.logText);
						}
					}
				}
				HelperFunctions.Pause();
				break;
			case 4:
				//If the admin choose to display logs from 30 days ago, we check if the log date is in range
				//If it is, display it
				for (LogEvent event : Main.logs) {
					if (event.dateTime.isBefore(LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0))) {
						if (event.dateTime.isAfter(LocalDateTime.now().minusMonths(1).minusDays(1).withHour(23).withMinute(59).withSecond(59))) {
							System.out.println(event.dateTime.format(Main.formatter) + " | " + event.logText);
						}
					}
				}
				HelperFunctions.Pause();
				break;
			case 5:
				//If we want the admin to enter a custom date
				input.nextLine();
				String startDateInput;
				String endDateInput;
				
				System.out.println("Enter starting date (YYYY-MM-DD HH:mm:ss)");
				LocalDateTime sdt;
				LocalDateTime edt;
				//Ask the admin to enter the starting date and check format
				while (true) {
					startDateInput = input.nextLine();
					try {
						sdt = LocalDateTime.parse(startDateInput, Main.formatter);
						break;
					} catch (Exception e) {
						System.out.println("Invalid date format, try again: ");
					}
				}
				//Ask the admin to enter the ending date and check format
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
				//If both formats match, display date
				for (LogEvent event : Main.logs) {
					if (event.dateTime.isBefore(edt)) {
						if (event.dateTime.isAfter(sdt)) {
							System.out.println(event.dateTime.format(Main.formatter) + " | " + event.logText);
						}
					}
				}
				HelperFunctions.Pause();
				break;
			}
		} while (choice != 6);
	}

}
