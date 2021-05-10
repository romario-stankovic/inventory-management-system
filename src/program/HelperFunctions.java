package program;

import users.*;
import java.util.Scanner;

import program.Data.*;

import java.util.List;

public class HelperFunctions {
	
	private static Scanner input;
	
	public static int DisplayMenu(String[] menuOptions) {
		return DisplayMenu(menuOptions, null);
	}
	
	public static int DisplayMenu(String[] menuOptions, String message) {
		input = Main.input;
		
		if(message == null) {
			message = "Select option: ";
		}
		
		System.out.println(message);
		
		for(int i=1; i<=menuOptions.length; i++) {
			System.out.println(i + ") " + menuOptions[i-1]);
		}
		
		int choice = -1;
		do {
			try {
				choice = input.nextInt();
			}catch (Exception e) {
				input.next();
				choice = -1;
			}
			if(choice < 1 || choice > menuOptions.length) {
				System.out.println("Invalid option, try again: ");
			}
		}while(choice < 1 || choice > menuOptions.length);
		
		return choice;
	}
	
	public static int FindUserByUsername(List<User> users, String username) {
		for(int i=0; i<users.size(); i++) {
			if(users.get(i).username.equals(username)) {
				return i;
			}
		}
		return -1;
	}
	
	public static int FindCategoryByName(List<Category> categories, String categoryName) {
		for(int i=0; i<categories.size(); i++) {
			if(categories.get(i).name.equals(categoryName)) {
				return categories.get(i).id;
			}
		}
		return 0;
	}
	
	public static int FindItemByName(List<Item> items, String itemName) {
		for(int i=0; i<items.size(); i++) {
			if(items.get(i).name.equals(itemName)) {
				return i;
			}
		}
		return -1;
	}
	
	public static void Pause() {
		input.nextLine();
		System.out.print("Press ENTER to continue...");
		input.nextLine();
	}
	
	public static void ClearScreen() {

	}
}
