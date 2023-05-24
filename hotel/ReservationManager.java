import java.time.LocalDate;
import java.util.Scanner;

public class ReservationManager {
	private static Reservation[] reservations = new Reservation[80];
	private ClearConsole clearConsole;
	private Scanner SCANNER;
	private RoomManager rooms;
	public Room[] roomReserves;
	private CustomerManager customers;
	private int index;

	public ReservationManager(RoomManager rooms, CustomerManager customers) {
		SCANNER = new Scanner(System.in);
		clearConsole = new ClearConsole();
		this.rooms = rooms;
		this.customers = customers;
		// this.roomReserves = rooms.getList().clone();
		setRoomList();
		setReservationsList();
	}

	// 예약시 방 예약 가능 여부 화면에 뿌려주는 용도로 별도의 방 리스트 하나 더 만듬
	public void setRoomList() {
		int cnt = 201;

		Room[] roomList = rooms.getList();
		roomReserves = new Room[80];

		for (int i = 0; i < roomReserves.length; i++) {
			if (i % 20 == 0 && i > 19) {
				cnt = cnt + 80;
			}
			roomReserves[i] = new Room(cnt, "가능함");
			roomReserves[i].setNumBed(roomList[i].getNumBed());
			roomReserves[i].setPrice(roomList[i].getPrice());
			cnt++;
		}
	}

	// 가상으로 예약 리스트 추가
	public void setReservationsList() {
		int cnt = 201;
		for (int i = 0; i < 12; i++) {
			if (i % 20 == 0 && i >= 20) {
				cnt = cnt + 80;
			}
			int random1 = (int) (30 * Math.random() + 1);
			int random2 = (int) (5 * Math.random() + 1);
			int random3 = (int) (9 * Math.random());
			LocalDate checkInDate = LocalDate.now().plusDays(random2);
			LocalDate checkOutDate = checkInDate.plusDays(random1);
			int price = 0;

			if (cnt % 2 == 0) {
				price = 110000 * (checkOutDate.getDayOfYear() - checkInDate.getDayOfYear());
			} else {
				price = 70000 * (checkOutDate.getDayOfYear() - checkInDate.getDayOfYear());
			}
			Customer customer = customers.getCustomerList()[random3];
			reservations[i] = new Reservation(customer.getName(), customer.getPhoneNumber(), cnt, price, checkInDate,
					checkOutDate);
			reservations[i].setReserveNumber(cnt);
			cnt++;
			index++; // 예약 등록시마다 index 도 1씩 증가
		}
	}

	public int userInputNum() {
		String num = SCANNER.nextLine();
		if (!num.isEmpty() && isNumber(num)) {
			return Integer.parseInt(num);
		} else {
			System.out.print("  올바른 숫자값을 입력해 주세요 : ");
			return userInputNum();
		}
	}

	// 인덱스 값++
	public boolean add(Reservation r) {
		for (int i = 0; i < reservations.length; i++) {
			if (reservations[i] == null) {
				reservations[index] = r;
				reservations[index].setReserveNumber(index + 1);
				index++;
				return true;
			}
		}
		return false;
	}

	// (예약 정보 입력)
	public Reservation userInput() {
		String name = "";
		String phoneNumber = "";
		int roomNumber = 0;
		int price = 0;
		int checkInMonth = 0;
		int checkInDay = 0;
		LocalDate checkInDate;
		int checkOutDay = 0;
		LocalDate checkOutDate;

		System.out.println();
		System.out.print("  이름 : ");
		name = SCANNER.next();

		System.out.println();
		System.out.print("  전화번호 (잘못된 번호 입력 시 할인 불가) : ");
		phoneNumber = SCANNER.next();

		System.out.println();
		while (true) {
			System.out.print("  입실 Month : ");
			checkInMonth = SCANNER.nextInt();

			System.out.print("  입실 Day : ");
			checkInDay = SCANNER.nextInt();

			if (checkUserInput(checkInMonth, checkInDay)) {
				System.out.println("  재대로 된 날짜가 아닙니다.");
				SCANNER.nextLine();
				continue;
			}
			checkInDate = LocalDate.of(2023, checkInMonth, checkInDay);
			break;
		}

		System.out.println();
		while (true) {
			System.out.print("  숙박 일수 등록 : ");
			checkOutDay = SCANNER.nextInt();

			if (checkOutDay < 0) {
				System.out.println("  1박 2일 -> 1 / 2박 3일 -> 2  같이 입력해 주세요.");
				SCANNER.nextLine();
				continue;
			}
			checkOutDate = checkInDate.plusDays(checkOutDay);
			break;
		}

		while (true) {
			Room[] reserableRooms = getReserableRooms(checkInDate, checkOutDate);

			clearConsole.clean();
			Renderer.printHotel(reserableRooms);

			System.out.println(
					Renderer.colorCyan("  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
			System.out.println("  이    름 : " + name);
			System.out.println();
			System.out.println("  전화번호 : " + phoneNumber);
			System.out.println();
			System.out.println("  숙박기간 : " + checkInDate + " ~ " + checkOutDate);
			System.out.println(
					Renderer.colorCyan("  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));

			System.out.println();
			System.out.println("  싱글룸 : 70000 원 / 더블룸 : 110000 원");
			System.out.println();
			System.out.print("  호실 선택 : ");
			roomNumber = SCANNER.nextInt();

			if (rooms.existRoom(roomNumber)) {
				System.out.println(Renderer.colorRed("  등록 가능한 객실을 입력해 주세요."));
				SCANNER.nextLine();
				continue;
			}

			boolean result = isReserved(rooms.find(roomNumber), checkInDate, checkOutDate);
			if (result) {
				System.out.println(Renderer.colorRed("  이미 예약된 객실입니다."));
				SCANNER.nextLine();
				continue;
			}

			break;
		}
		price = getPrice(phoneNumber, roomNumber) * checkOutDay;
		System.out.println();
		System.out.println(Renderer.colorCyan("  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
		System.out.println("  숙 박 비 : " + price + " 원");

		Reservation reservation = new Reservation(name, phoneNumber, roomNumber, price, checkInDate, checkOutDate);

		return reservation;
	}

	public Room[] getReserableRooms(LocalDate checkInDate, LocalDate checkOutDate) {
		for (int i = 0; i < roomReserves.length; i++) {
			if (roomReserves[i] == null)
				continue;

			if (isReserved(roomReserves[i], checkInDate, checkOutDate)) {
				roomReserves[i].setRoomStatus("예약됨");
			} else {
				roomReserves[i].setRoomStatus("가능함");
			}
		}

		return roomReserves;
	}

	// 날짜 맞는지 확인
	public boolean checkUserInput(int checkInMonth, int checkInDay) {
		if (checkInDay < 31 && checkInDay > 0 && checkInMonth < 13 && checkInMonth > 0) {
			return false;
		}
		return true;
	}

	// 그 날, 그 방에 손님 있나 없나
	private boolean isReserved(Room room, LocalDate checkInDate, LocalDate checkOutDate) {
		for (Reservation reservation : reservations) {
			if (reservation == null) {
				return false;
			}
			if (reservation.getRoomNumber() == room.getRoomNumber()) {
				LocalDate reservationCheckInDate = reservation.getCheckInDate();
				LocalDate reservationCheckOutDate = reservation.getCheckOutDate();

				if (checkInDate.isBefore(reservationCheckOutDate) && checkOutDate.isAfter(reservationCheckInDate)) {
					return true; // 겹치는 예약에는 true
				}
			}
		}
		return false;
	}

	// customer room 정보 받아와서 가격 정하기
	private int getPrice(String phoneNumber, int roomNumber) {
		Customer cus = customers.findNUM(phoneNumber);
		float percent = 1;

		if (cus != null) {
			String degree = cus.getDegree();
			switch (degree) {
			case "SPECIAL":
				System.out.println("  SPECIAL 고객입니다.");
				percent = 0.95f;
				break;
			case "VIP":
				System.out.println("  VIP 고객입니다.");
				percent = 0.90f;
				break;
			case "PlATINUM":
				System.out.println("  PlATINUM 고객입니다.");
				percent = 0.80f;
				break;
			}
		} else {
			percent = 1;
		}
		int price = rooms.getRoomPrice(roomNumber);
		return (int) (price * percent);
	}

	// 예약 등록 (예약 정보)
	public Reservation regist() {
		Reservation r = userInput();
		boolean result = add(r);

		if (result) {
			System.out.println();
			System.out.println("  정상적으로 예약 되었습니다.");
			System.out.println(
					Renderer.colorCyan("  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
			return r;
		} else {
			System.out.println("  등록할 수 없습니다.");
			return null;
		}
	}

	// 예약 취소 (예약 번호)
	public void deleteReser() {
		System.out.println();
		System.out.println(
				Renderer.colorCyan("  +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
		for (int i = 0; i < reservations.length; i++) {
			if (reservations[i] != null) {
				String indexNumber = Integer.toString(i + 1);
				if (i + 1 < 10) {
					indexNumber = " " + indexNumber;
				}

				System.out.println("  " + indexNumber + ". " + reservations[i].getReserveNumber() + " ["
						+ reservations[i].getCheckInDate() + " ~ " + reservations[i].getCheckOutDate() + "] : "
						+ reservations[i].getRoomNumber() + "호 / " + reservations[i].getName() + " ("
						+ reservations[i].getPhoneNumber() + ")");
			}
		}
		System.out.println(
				Renderer.colorCyan("  +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
		System.out.println();
		System.out.print("  예약 번호 선택 : ");
		int num = userInputNum() - 1;

		if (reservations[num] != null) {
			rooms.reservToEmpty(reservations[num].getRoomNumber());
			reservations[num] = null;
			System.out.println();
			System.out.println(
					Renderer.colorCyan("  +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
			System.out.println("  예약 취소가 완료되었습니다.");
			System.out.println(
					Renderer.colorCyan("  +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
		} else {
			System.out.println();
			System.out.println(
					Renderer.colorCyan("  +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
			System.out.println("  올바른 번호를 입력하세요.");
			System.out.println(
					Renderer.colorCyan("  +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
		}
	}

	// 예약 상태 확인 (한 고객)
	public void findReser() {
		System.out.println();
		System.out.println(
				Renderer.colorCyan("  +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
		System.out.println(Renderer.colorCyan("  ++   예약번호                                                       ++"));
		System.out.println(
				Renderer.colorCyan("  +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));

		for (int i = 0; i < reservations.length; i++) {
			if (reservations[i] != null) {
				String indexNumber = Integer.toString(i + 1);
				if (i + 1 < 10) {
					indexNumber = " " + indexNumber;
				}

				System.out.println("  " + indexNumber + ". " + reservations[i].getReserveNumber() + " ["
						+ reservations[i].getCheckInDate() + " ~ " + reservations[i].getCheckOutDate() + "] : "
						+ reservations[i].getRoomNumber() + "호 / " + reservations[i].getName() + " ("
						+ reservations[i].getPhoneNumber() + ")");
			}
		}
		System.out.println(
				Renderer.colorCyan("  +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));

		System.out.println();
		System.out.print("  예약 번호 선택 : ");
		int num = userInputNum() - 1;

		if (num >= 0) {
			if (num < index && reservations[num] != null) {
				System.out.println(Renderer
						.colorCyan("  +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
				System.out.println(reservations[num].formatted());
				System.out.println(Renderer
						.colorCyan("  +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
			} else {
				System.out.println(Renderer
						.colorCyan("  +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
				System.out.println("  예약 번호를 정확히 선택해 주세요.");
				System.out.println(Renderer
						.colorCyan("  +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
			}
		}
	}

	// 예약 상태 확인 (모든 고객)
	public void findAllReser() {
		System.out.println();
		System.out.println(Renderer.colorCyan("  [예약 리스트]"));
		System.out.println(
				Renderer.colorCyan("  +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
		System.out.println(Renderer.colorCyan("  ++   예약번호                                                       ++"));
		System.out.println(
				Renderer.colorCyan("  +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));

		for (Reservation elem : reservations) {
			if (elem != null) {
				System.out.println("  " + elem.getReserveNumber() + " [" + elem.getCheckInDate() + " ~ "
						+ elem.getCheckOutDate() + "] : " + elem.getRoomNumber() + "호 / " + elem.getName() + " ("
						+ elem.getPhoneNumber() + ")");
			}
		}
		System.out.println(
				Renderer.colorCyan("  +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
	}

	private boolean isNumber(String word) {
		return word.chars().allMatch(Character::isDigit);
	}
}