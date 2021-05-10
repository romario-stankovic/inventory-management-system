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
	}
	
	public static class Category {
		public int id;
		public String name;
		public Category(int id, String name) {
			this.id = id;
			this.name = name;
		}
	}
	
	public static class Item {
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
		
	}
	
}
