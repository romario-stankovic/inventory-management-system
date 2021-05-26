package users;

import program.Data.UserType;

import program.Main;
import program.HelperFunctions;

public class NullUser extends User {

	public NullUser(String username, String password, String name, String lastName, UserType type) {
		super(username, password, name, lastName, type);
	}
	
	@Override
	public void userMenu() {
		logIn();
		System.out.println("You do not have an assigned role, logging out...");
		HelperFunctions.pause();
		logOut();
	}
	
	@Override
	public void logIn() {
		//Log Event
		Main.logEvent("user login: " + username); 
	}

	@Override
	public void logOut() {
		//Log Event
		Main.logEvent("user logout: " + username); 
	}


}
