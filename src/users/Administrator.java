package users;

import java.util.*;

import program.FileIO;
import program.HelperFunctions;
import program.UserTypes;

public class Administrator extends User {

	private Scanner input;
	
	public Administrator(String username, String password, String name, String lastName, UserTypes type) {
		super(username, password, name, lastName, type);
		input = new Scanner(System.in);
	}

	public void ListAllUsers() {
		String[] users = FileIO.ReadLines("users.txt");
		System.out.printf("%15s%15s%15s%30s\n\n", "Username", "Name","Last Name", "User Type");
		for(String user : users) {
			String[] fields = user.split(",");
			System.out.printf("%15s%15s%15s%30s\n", fields[0], fields[2], fields[3], fields[4]);
		}
		System.out.println("");
		HelperFunctions.Pause();
	}
	
	public void RegisterNewUser() {
		System.out.println("----------REGISTER A NEW USER----------");
		String[] users = FileIO.ReadLines("users.txt");
		System.out.println("Enter username: ");
		
		String username;
		
		usernameInput: while (true) {
			username = input.next();
			for (String user : users) {
				String[] fields = user.split(",");
				if (username.equals(fields[0])) {
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
		
		for(int i=0; i<UserTypes.values().length; i++) {
			options[i] = UserTypes.values()[i].toString();
		}
		
		int choice = HelperFunctions.DisplayMenu(options, "Select type of user: ");
		
		String line = username + "," + password + "," + name + "," + lastName + "," + UserTypes.values()[choice-1];
		if(FileIO.AppendLine("users.txt", line)) {
			System.out.println("User created successfully!");
			HelperFunctions.LogEvent(this.username + " registered " + username + " to the system");
		}else {
			System.out.println("Error while creating user!");
		}
	}
	
	public void ModifyUser() {
		String[] users = FileIO.ReadLines("users.txt");
		List<String> newUsers = new ArrayList<String>();
		for(String u : users) {
			newUsers.add(u);
		}
		
		System.out.println("Enter username of the user you want to modify: ");
		String modifyUsername;
		usernameInput: while(true) {
			modifyUsername = input.next();
			for(String user : newUsers) {
				String[] fields = user.split(",");
				if(fields[0].equals(modifyUsername)) {
					break usernameInput;
				}
			}
			System.out.println("There is no user with that username, try again: ");
		}
		
		String[] options = {"Username", "Password", "Name", "Last Name", "User Type"};
		int choice = HelperFunctions.DisplayMenu(options, "Select field to modify: ");
		
		switch(choice) {
		case 1:
			System.out.println("Enter new username: ");
			String newUsername;
			usernameCheck: while(true) {
				newUsername = input.next();
				for(String user : users) {
					String[] fields = user.split(",");
					if(fields[0].equals(newUsername)) {
						System.out.println("Username already taken, try again: ");
						continue usernameCheck;
					}
				}
				break;
			}
			for(int i=0; i<newUsers.size(); i++) {
				String[] fields = newUsers.get(i).split(",");
				if(fields[0].equals(modifyUsername)) {
					String newData = newUsername + "," + fields[1] + "," + fields[2] + "," + fields[3] + "," + fields[4];
					newUsers.set(i, newData);
					HelperFunctions.LogEvent(username + " changed the username of " + modifyUsername + " to " + newUsername);
					break;
				}
			}
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
			for(int i=0; i<newUsers.size(); i++) {
				String[] fields = newUsers.get(i).split(",");
				if(fields[0].equals(modifyUsername)) {
					String newData = fields[0] + "," + newPassword + "," + fields[2] + "," + fields[3] + "," + fields[4];
					newUsers.set(i, newData);
					HelperFunctions.LogEvent(username + " changed the password of " + modifyUsername + " to " + newPassword);
					break;
				}
			}
			break;
		case 3:
			System.out.println("Enter new name: ");
			String newName = input.next();
			for(int i=0; i<newUsers.size(); i++) {
				String[] fields = newUsers.get(i).split(",");
				if(fields[0].equals(modifyUsername)) {
					String newData = fields[0] + "," + fields[1] + "," + newName + "," + fields[3] + "," + fields[4];
					newUsers.set(i, newData);
					HelperFunctions.LogEvent(username + " changed the name of " + modifyUsername + " to " + newName);
					break;
				}
			}
			break;
		case 4:
			System.out.println("Enter new last name: ");
			String newLastName = input.next();
			for(int i=0; i<newUsers.size(); i++) {
				String[] fields = newUsers.get(i).split(",");
				if(fields[0].equals(modifyUsername)) {
					String newData = fields[0] + "," + fields[1] + "," + fields[2] + "," + newLastName + "," + fields[4];
					newUsers.set(i, newData);
					HelperFunctions.LogEvent(username + " changed the last name of " + modifyUsername + " to " + newLastName);
					break;
				}
			}
			break;
		case 5:
			String[] typeOptions = new String[UserTypes.values().length];
			
			for(int i=0; i<UserTypes.values().length; i++) {
				typeOptions[i] = UserTypes.values()[i].toString();
			}
			
			int typeChoice = HelperFunctions.DisplayMenu(typeOptions, "Select type of user: ");
			
			for(int i=0; i<newUsers.size(); i++) {
				String[] fields = newUsers.get(i).split(",");
				if(fields[0].equals(modifyUsername)) {
					String newData = fields[0] + "," + fields[1] + "," + fields[2] + "," + fields[3] + "," + UserTypes.values()[typeChoice-1];
					newUsers.set(i, newData);
					HelperFunctions.LogEvent(username + " changed the user type of " + modifyUsername + " to " + UserTypes.values()[typeChoice-1]);
					break;
				}
			}
			break;
			
		}
		FileIO.WriteLines("users.txt", newUsers.toArray(new String[newUsers.size()]));
		System.out.println("User modified successfully!");
	}
	
	public void DeleteUser() {
		String[] users = FileIO.ReadLines("users.txt");
		List<String> newUsers = new ArrayList<String>();
		for(String u : users) {
			newUsers.add(u);
		}
		
		System.out.println("Enter username of the user you want to delete: ");
		usernameInput: while(true) {
			String deleteUsername = input.next();
			for(String user : newUsers) {
				String[] fields = user.split(",");
				if(fields[0].equals(deleteUsername)) {
					newUsers.remove(user);
					System.out.println("User deleted successfully!");
					HelperFunctions.LogEvent(this.username + " removed " + deleteUsername + " from the system");
					break usernameInput;
				}
			}
			System.out.println("There is no user with that username, try again: ");
		}
		
		FileIO.WriteLines("users.txt", newUsers.toArray(new String[newUsers.size()]));
		
	}

}
