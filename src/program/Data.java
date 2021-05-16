package program;

import java.time.LocalDateTime;

public class Data {
	
	public enum UserType {
		Administrator,
		Clerk,
		Driver,
		Null
	}
	
	public static class LogEvent {
		public LocalDateTime dateTime;
		public String logText;
		public LogEvent(LocalDateTime dateTime, String logText) {
			this.dateTime = dateTime;
			this.logText = logText;
		}
		
		@Override
		public String toString() {
			return dateTime.format(Main.formatter) + " | " + logText;
		}
		
	}
	
	public static class Category {
		public int id;
		public String name;
		public Category(int id, String name) {
			this.id = id;
			this.name = name;
		}
		
		@Override
		public String toString() {
			return id + ": " + name;
		}
		
	}
	
	public static class Item implements Comparable<Item> {
		public String name;
		public float massPerUnit;
		public int numberOfUnits;
		float totalMass;
		public int category;
		
		public Item (String name, float massPerUnit, int numberOfUnits, int category) {
			this.name = name;
			this.massPerUnit = massPerUnit;
			this.numberOfUnits = numberOfUnits;
			this.category = category;
		}
		
		public float GetTotalMass() {
			return totalMass;
		}
		
		public void CalculateTotalMass() {
			totalMass = numberOfUnits * massPerUnit;
		}

		@Override
		public int compareTo(Item item) {
			return -item.name.compareTo(this.name);
		}
		
		@Override
		public String toString() {
			return numberOfUnits + "x " + name + " (Weight per unit: " + massPerUnit + ", Total weight: " + totalMass + ")";
		}
		
	}
	
}
