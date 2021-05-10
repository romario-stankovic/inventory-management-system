package users;

import program.Data.UserType;

public abstract class User implements UserActions{
	public String username;
	public String password;
	public String name;
	public String lastName;
	public UserType type;
	
	public User (String username, String password, String name, String lastName, UserType type) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.lastName = lastName;
		this.type = type;
	}
		
}
