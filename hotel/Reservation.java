
import java.time.LocalDate;

public class Reservation {
	private int reserveNumber;
	private int roomNumber;
	private LocalDate checkInDate;
	private LocalDate checkOutDate;

	private String name;
	private String phoneNumber;
	private int price;

	public Reservation(String name, String phoneNumber, int roomNumber, int price, LocalDate checkInDate,
			LocalDate checkOutDate) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.roomNumber = roomNumber;
		this.price = price;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.reserveNumber = getReserveNumber();
	}

	public int getReserveNumber() {
		return reserveNumber;
	}

	public void setReserveNumber(int indexNumber) {
		this.reserveNumber = getCheckInDate().getYear() * 1000000 + getCheckInDate().getMonthValue() * 10000
				+ getCheckInDate().getDayOfMonth() * 100 + indexNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public LocalDate getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(LocalDate checkInDate) {
		this.checkInDate = checkInDate;
	}

	public LocalDate getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(LocalDate checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public String formatted() {
		String printRoomNumber = Integer.toString(roomNumber);
		if (roomNumber < 10) {
			printRoomNumber = " " + printRoomNumber;
		}

		return "  방 번 호 : " + printRoomNumber + "\n  이    름 : " + name + "\n  전화번호 : " + phoneNumber + "\n  가    격 : "
				+ price + "\n  체 크 인 : " + checkInDate + "\n  체크아웃 : " + checkOutDate + "\n  예약번호 : " + reserveNumber
				+ "";
	}

}