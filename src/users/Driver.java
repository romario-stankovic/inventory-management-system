package users;

import java.util.Scanner;

import program.Main;
import program.HelperFunctions;

import program.Data.UserType;
import program.Data.Item;

public class Driver extends User {

	private Scanner input = Main.input;
	
	public Driver(String username, String password, String name, String lastName, UserType type) {
		super(username, password, name, lastName, type);
	}
	
	@Override
	public void userMenu() {
		//Call logIn function
		logIn();
		//List of menu options to display to the driver
		String[] options = {"Deliver items", "Take items for shipment","Logout"};
		
		//Driver choice
		int choice = 0;
		do {
			System.out.println("----------DRIVER MENU----------");
			choice = HelperFunctions.DisplayMenu(options);
			switch(choice) {
			case 1:
				deliverItems();
				break;
			case 2:
				takeShipment();
				break;
			case 3:
				logOut();
				break;
			}
		}while(choice != 3);
	}
	
	@Override
	public void logIn() {
		//Log event
		Main.LogEvent("driver login: " + username);
	}
	
	@Override
	public void logOut() {
		//Log event
		Main.LogEvent("driver logout: " + username);
	}
	
	private void deliverItems() {
		System.out.println("----------DELIVER ITEMS----------");
		//Item we want to deliver
		String itemName;
		
		//number of items delivered
		int numberOfDeliveredItems = 0;
		
		while(true) {
			System.out.println("Enter item name (Enter \"stop\" to finish)");
			itemName = input.next();
			//If the item name is stop, stop the function
			if(itemName.equals("stop")) {
				break;
			}
			
			//Search for the item in inbound queue
			Item item = null;
			int itemID = HelperFunctions.FindItemByName(Main.inboundItems, itemName);
			if(itemID != -1) {
				//if we found the item, set item to the item in queue
				item = Main.inboundItems.get(itemID);
				//Ask the user to enter quantity
				int quantity = quantityInput();
				item.numberOfUnits += quantity;
				numberOfDeliveredItems += quantity;
				continue;
			}

			//If we did not find the item in inbound queue, search for it in the main item list
			itemID = HelperFunctions.FindItemByName(Main.items, itemName);
			
			if(itemID != -1) {
				//if we found the item, clone it and add it to the inbound queue
				Item mainItem = Main.items.get(itemID);
				int quantity = quantityInput();
				Item cloneItem = new Item(mainItem.name, mainItem.massPerUnit, quantity);
				Main.inboundItems.add(cloneItem);
				numberOfDeliveredItems += quantity;
				continue;
			}
			
			//if we did not find the item at all, ask the driver to enter other data
			//and add the item to inbound queue
			float massPerUnit = massInput();
			int numberOfUnits = quantityInput();
			item = new Item(itemName, massPerUnit, numberOfUnits);
			Main.inboundItems.add(item);
			numberOfDeliveredItems += numberOfUnits;
		}
		//Log Event
		Main.LogEvent(username + " delivered: " + numberOfDeliveredItems + " items");
		//Display a message
		System.out.println("Items successfully added to delivery queue!");
		HelperFunctions.Pause();
	}
	
	private void takeShipment() {
		System.out.println("----------TAKE SHIPMENT----------");
		//Number of items
		int itemCount =0;
		//Check if we have any items in the outboundItems queue
		if(Main.outboundItems.size() == 0) {
			//If we don't, display a message, pause the display and return back to user menu
			System.out.println("There are no items for shipment");
			HelperFunctions.Pause();
			return;
		}
		
		//If we have one or more items, display those items on the screen
		for(Item item : Main.outboundItems) {
			System.out.println(item.numberOfUnits + "x " + item.name);
			itemCount += item.numberOfUnits;
		}
		//Ask the driver if they want to take shipment of these items:
		System.out.println("Do you want to take this shipment? (Y - Yes, N - No)");
		String choice;
		
		//Driver input
		do {
			choice = input.next().toUpperCase();
			if(!choice.equals("Y") && !choice.equals("N")) {
				//if the driver did not enter Y or N, ask for input again
				System.out.println("Invalid choice, try again: ");
			}
			
		}while(!choice.equals("Y") && !choice.equals("N"));
		
		//If the choice was Y, clear outboundItems queue (meaning that the driver took shipment)
		if(choice.equals("Y")) {
			Main.outboundItems.clear();
			Main.LogEvent(username + " took shipment of: " + itemCount + " items from the queue");
			//Display a message
			System.out.println("Shipment taken successfully!");
			HelperFunctions.Pause();
		}
		
	}
	
	private int quantityInput() {
		System.out.println("Enter number of units: ");
		int quantity = 0;
		//Ask the driver to enter a value greater than 0
		do {
			try {
				quantity = input.nextInt();
				if(quantity <= 0) {
					System.out.println("Invalid input, try again: ");
				}
			} catch (Exception e) {
				System.out.println("Invalid input, try again: ");
				input.next();
				quantity = 0;
			}
		} while (quantity <= 0);
		return quantity;
	}
	
	private float massInput() {
		//Ask the driver to enter a value greater than 0
		System.out.println("Enter mass per unit: ");
		float mass = 0;
		do {
			try {
				mass = input.nextFloat();
				if(mass <= 0) {
					System.out.println("Invalid input, try again: ");
				}
			} catch (Exception e) {
				System.out.println("Invalid input, try again: ");
				input.next();
				mass = 0;
			}
		} while (mass <= 0);
		return mass;
	}
	
}
