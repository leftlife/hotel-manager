
import java.util.Scanner;

//½Ì±Û·ë 
public class Room {
	Scanner scanner = new Scanner(System.in);
	public int roomNumber;
	public String roomStatus;
	// public static Room[] roomList;
	public int price;
	public String numBed;

	public Room(int roomNumber, String roomStatus) {
		this.roomNumber = roomNumber;
		this.roomStatus = roomStatus;
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getRoomStatus() {
		return roomStatus;
	}

	public void setRoomStatus(String roomStatus) {
		this.roomStatus = roomStatus;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getNumBed() {
		return numBed;
	}

	public void setNumBed(String numBed) {
		this.numBed = numBed;
	}

	public Room() {

	}

	public void setprice() {
		for (int i = 0; i < RoomManager.roomList.length; i++) {
			int key = RoomManager.roomList[i].roomNumber % 2;
			switch (key) {
			case 1:
				// ½Ì±Û·ë
				RoomManager.roomList[i].price = 70000;
				RoomManager.roomList[i].numBed = "½Ì±Û";
				break;

			case 0:
				// ´õºí·ë
				RoomManager.roomList[i].price = 110000;
				RoomManager.roomList[i].numBed = "´õºí";
				break;
			}

		}
	}

}