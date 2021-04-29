package users;

public abstract class User {
	public String username;
	public String password;
	public String name;
	public String lastName;
	public UserTypes type;
	
	
	public User (String username, String password, String name, String lastName, UserTypes type){
		this.username = username;
		this.password = password;
		this.name = name;
		this.lastName = lastName;
		this.type = type;
	}
	
	public void PrintInfo() {
		System.out.println("[" + type.toString() + "] " + name + " " + lastName);
	}
	
}
