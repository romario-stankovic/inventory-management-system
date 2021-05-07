package users;

import program.*;

public class NullUser extends User {

	public NullUser(String username, String password, String name, String lastName, UserTypes type) {
		super(username, password, name, lastName, type);
	}
	
	@Override
	public void UserMenu() {
		System.out.println("You do not have an assigned role, logging out...");
		HelperFunctions.LogEvent("user logout: " + username); 
		HelperFunctions.Pause();
	}

}
