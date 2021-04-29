package users;

import java.util.*;

import program.HelperFunctions;

public class Administrator extends User {

	private Scanner input;
	
	public Administrator(String username, String password, String name, String lastName, UserTypes type) {
		super(username, password, name, lastName, type);
		input = new Scanner(System.in);
	}
	
	public void ListAllUsers(List<User> users) {
		System.out.printf("%15s%15s%15s%30s\n\n", "Username", "Name","Last Name", "User Type");
		for(User user : users) {
			System.out.printf("%15s%15s%15s%30s\n", user.username, user.name, user.lastName, user.type);
		}
		System.out.println("");
		HelperFunctions.Pause();
	}
	
	public List<User> RegisterNewUser(List<User> users) {
		System.out.println("----------REGISTER A NEW USER----------");
		System.out.println("Enter username: ");
		
		String username;
		
		usernameInput: while (true) {
			username = input.next();
			for (User user : users) {
				if (username.equals(user.username)) {
					System.out.println("Username already taken, try again: ");
					continue usernameInput;
				}
			}
			break;
		}
		
		System.out.println("Enter password: ");
		String password = input.next();
		System.out.println("Repeat password: ");
		passwordVerification: while (true) {
			String passwordConfirmation = input.next();
			if (passwordConfirmation.equals(password)) {
				break passwordVerification;
			}
			System.out.println("Password does not match, try again: ");
		}
		
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
		
		users.add(newUser);
		System.out.println("User registerd successfully!");
		HelperFunctions.LogEvent(this.username + " registered " + username + " to the system");

		HelperFunctions.Pause();
		
		return users;
	}
	
	public List<User> ModifyUser(List<User> users) {
		
		User modifyUser;
		int index;
		System.out.println("Enter username of the user you want to modify: ");
		usernameInput: while(true) {
			String modifyUsername = input.next();
			for(User user : users) {
				if(user.username.equals(modifyUsername)) {
					modifyUser = user;
					index = users.indexOf(user);
					break usernameInput;
				}
			}
			System.out.println("There is no user with that username, try again: ");
		}
		
		String[] options = {"Username", "Password", "Name", "Last Name", "User Type", "Cancel"};
		int choice = HelperFunctions.DisplayMenu(options, "Select field to modify: ");
		
		switch(choice) {
		case 1:
			System.out.println("Enter new username: ");
			String newUsername;
			usernameCheck: while(true) {
				newUsername = input.next();
				for(User user : users) {
					if(user.username.equals(newUsername)) {
						System.out.println("Username already taken, try again: ");
						continue usernameCheck;
					}
				}
				break;
			}
			HelperFunctions.LogEvent(username + " changed username of " + modifyUser.username + " to " + newUsername);
			modifyUser.username = newUsername;
			break;
		case 2:
			System.out.println("Enter new password");
			String newPassword = input.next();
			System.out.println("Confirm password");
			while(true) {
				String confirmPassword = input.next();
				if(newPassword.equals(confirmPassword)) {
					break;
				}
				System.out.println("Passwords do not match, try again: ");
			}
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
		case 6:
			return users;
			
		}
		
		users.set(index, modifyUser);
		
		System.out.println("User modified successfully!");
		HelperFunctions.Pause();
		return users;
	}
	
	public List<User> DeleteUser(List<User> users) {
		
		System.out.println("Enter username of the user you want to delete: ");
		usernameInput: while(true) {
			String deleteUsername = input.next();
			for(User user : users) {
				if(user.username.equals(deleteUsername)) {
					users.remove(user);
					System.out.println("User deleted successfully!");
					HelperFunctions.LogEvent(this.username + " removed " + deleteUsername + " from the system");
					break usernameInput;
				}
			}
			System.out.println("There is no user with that username, try again: ");
		}
		HelperFunctions.Pause();
		return users;
	}

}
