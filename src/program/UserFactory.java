package program;

import program.Data.UserType;

import users.*;

//Factory design pattern
public class UserFactory {
	
	public static User getUser(String username, String password, String name, String lastName, UserType type) {
		
		if(username == null || password == null || name == null || lastName == null || type == null) {
			return null;
		}
		
		switch(type) {
		case Administrator:
			return new Administrator(username, password, name, lastName, type);
		case Clerk:
			return new Clerk(username, password, name, lastName, type);
		case Driver:
			return new Driver(username, password, name, lastName, type);
		case Null:
			return new NullUser(username, password, name, lastName, type);
		default:
			return null;
		}
		
	}
	
}
