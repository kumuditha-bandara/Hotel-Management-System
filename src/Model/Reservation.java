package Model;

import java.sql.Date;

public class Reservation {

	 private int id;
	    private String roomId;
	    private String customerName;
	    private String packageType;
	    private double amount;
	    private Date reservationDate;
	    
	    
	    
		public Reservation(int id, String roomId, String customerName, String packageType, double amount,
				Date reservationDate) {
			super();
			this.id = id;
			this.roomId = roomId;
			this.customerName = customerName;
			this.packageType = packageType;
			this.amount = amount;
			this.reservationDate = reservationDate;
		}
		
		
		
		public Reservation(String roomId, String customerName, String packageType, double amount, Date reservationDate) {
			super();
			this.roomId = roomId;
			this.customerName = customerName;
			this.packageType = packageType;
			this.amount = amount;
			this.reservationDate = reservationDate;
		}





		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getRoomId() {
			return roomId;
		}
		public void setRoomId(String roomId) {
			this.roomId = roomId;
		}

		public String getCustomerName() {
			return customerName;
		}

		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}

		public String getPackageType() {
			return packageType;
		}
		public void setPackageType(String packageType) {
			this.packageType = packageType;
		}
		public double getAmount() {
			return amount;
		}
		public void setAmount(double amount) {
			this.amount = amount;
		}
		public Date getReservationDate() {
			return reservationDate;
		}
		public void setReservationDate(Date reservationDate) {
			this.reservationDate = reservationDate;
		}
	    
	    
}
