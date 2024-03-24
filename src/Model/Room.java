package Model;

public class Room {
 private int id;
 private String roomNumber;
 private int categoryID; 
 private double price;
public Room(int id, String roomNumber, int categoryID, double price) {
	super();
	this.id = id;
	this.roomNumber = roomNumber;
	this.categoryID = categoryID;
	this.price = price;
}


public Room(String roomNumber, int categoryID, double price) {
	super();
	this.roomNumber = roomNumber;
	this.categoryID = categoryID;
	this.price = price;
}


public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getRoomNumber() {
	return roomNumber;
}
public void setRoomNumber(String roomNumber) {
	this.roomNumber = roomNumber;
}
public int getCategoryID() {
	return categoryID;
}
public void setCategoryID(int categoryID) {
	this.categoryID = categoryID;
}
public double getPrice() {
	return price;
}
public void setPrice(double price) {
	this.price = price;
}
 
 


}

