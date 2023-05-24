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

	// ����� �� ���� ���� ���� ȭ�鿡 �ѷ��ִ� �뵵�� ������ �� ����Ʈ �ϳ� �� ����
	public void setRoomList() {
		int cnt = 201;

		Room[] roomList = rooms.getList();
		roomReserves = new Room[80];

		for (int i = 0; i < roomReserves.length; i++) {
			if (i % 20 == 0 && i > 19) {
				cnt = cnt + 80;
			}
			roomReserves[i] = new Room(cnt, "������");
			roomReserves[i].setNumBed(roomList[i].getNumBed());
			roomReserves[i].setPrice(roomList[i].getPrice());
			cnt++;
		}
	}

	// �������� ���� ����Ʈ �߰�
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
			index++; // ���� ��Ͻø��� index �� 1�� ����
		}
	}

	public int userInputNum() {
		String num = SCANNER.nextLine();
		if (!num.isEmpty() && isNumber(num)) {
			return Integer.parseInt(num);
		} else {
			System.out.print("  �ùٸ� ���ڰ��� �Է��� �ּ��� : ");
			return userInputNum();
		}
	}

	// �ε��� ��++
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

	// (���� ���� �Է�)
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
		System.out.print("  �̸� : ");
		name = SCANNER.next();

		System.out.println();
		System.out.print("  ��ȭ��ȣ (�߸��� ��ȣ �Է� �� ���� �Ұ�) : ");
		phoneNumber = SCANNER.next();

		System.out.println();
		while (true) {
			System.out.print("  �Խ� Month : ");
			checkInMonth = SCANNER.nextInt();

			System.out.print("  �Խ� Day : ");
			checkInDay = SCANNER.nextInt();

			if (checkUserInput(checkInMonth, checkInDay)) {
				System.out.println("  ���� �� ��¥�� �ƴմϴ�.");
				SCANNER.nextLine();
				continue;
			}
			checkInDate = LocalDate.of(2023, checkInMonth, checkInDay);
			break;
		}

		System.out.println();
		while (true) {
			System.out.print("  ���� �ϼ� ��� : ");
			checkOutDay = SCANNER.nextInt();

			if (checkOutDay < 0) {
				System.out.println("  1�� 2�� -> 1 / 2�� 3�� -> 2  ���� �Է��� �ּ���.");
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
			System.out.println("  ��    �� : " + name);
			System.out.println();
			System.out.println("  ��ȭ��ȣ : " + phoneNumber);
			System.out.println();
			System.out.println("  ���ڱⰣ : " + checkInDate + " ~ " + checkOutDate);
			System.out.println(
					Renderer.colorCyan("  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));

			System.out.println();
			System.out.println("  �̱۷� : 70000 �� / ����� : 110000 ��");
			System.out.println();
			System.out.print("  ȣ�� ���� : ");
			roomNumber = SCANNER.nextInt();

			if (rooms.existRoom(roomNumber)) {
				System.out.println(Renderer.colorRed("  ��� ������ ������ �Է��� �ּ���."));
				SCANNER.nextLine();
				continue;
			}

			boolean result = isReserved(rooms.find(roomNumber), checkInDate, checkOutDate);
			if (result) {
				System.out.println(Renderer.colorRed("  �̹� ����� �����Դϴ�."));
				SCANNER.nextLine();
				continue;
			}

			break;
		}
		price = getPrice(phoneNumber, roomNumber) * checkOutDay;
		System.out.println();
		System.out.println(Renderer.colorCyan("  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
		System.out.println("  �� �� �� : " + price + " ��");

		Reservation reservation = new Reservation(name, phoneNumber, roomNumber, price, checkInDate, checkOutDate);

		return reservation;
	}

	public Room[] getReserableRooms(LocalDate checkInDate, LocalDate checkOutDate) {
		for (int i = 0; i < roomReserves.length; i++) {
			if (roomReserves[i] == null)
				continue;

			if (isReserved(roomReserves[i], checkInDate, checkOutDate)) {
				roomReserves[i].setRoomStatus("�����");
			} else {
				roomReserves[i].setRoomStatus("������");
			}
		}

		return roomReserves;
	}

	// ��¥ �´��� Ȯ��
	public boolean checkUserInput(int checkInMonth, int checkInDay) {
		if (checkInDay < 31 && checkInDay > 0 && checkInMonth < 13 && checkInMonth > 0) {
			return false;
		}
		return true;
	}

	// �� ��, �� �濡 �մ� �ֳ� ����
	private boolean isReserved(Room room, LocalDate checkInDate, LocalDate checkOutDate) {
		for (Reservation reservation : reservations) {
			if (reservation == null) {
				return false;
			}
			if (reservation.getRoomNumber() == room.getRoomNumber()) {
				LocalDate reservationCheckInDate = reservation.getCheckInDate();
				LocalDate reservationCheckOutDate = reservation.getCheckOutDate();

				if (checkInDate.isBefore(reservationCheckOutDate) && checkOutDate.isAfter(reservationCheckInDate)) {
					return true; // ��ġ�� ���࿡�� true
				}
			}
		}
		return false;
	}

	// customer room ���� �޾ƿͼ� ���� ���ϱ�
	private int getPrice(String phoneNumber, int roomNumber) {
		Customer cus = customers.findNUM(phoneNumber);
		float percent = 1;

		if (cus != null) {
			String degree = cus.getDegree();
			switch (degree) {
			case "SPECIAL":
				System.out.println("  SPECIAL ���Դϴ�.");
				percent = 0.95f;
				break;
			case "VIP":
				System.out.println("  VIP ���Դϴ�.");
				percent = 0.90f;
				break;
			case "PlATINUM":
				System.out.println("  PlATINUM ���Դϴ�.");
				percent = 0.80f;
				break;
			}
		} else {
			percent = 1;
		}
		int price = rooms.getRoomPrice(roomNumber);
		return (int) (price * percent);
	}

	// ���� ��� (���� ����)
	public Reservation regist() {
		Reservation r = userInput();
		boolean result = add(r);

		if (result) {
			System.out.println();
			System.out.println("  ���������� ���� �Ǿ����ϴ�.");
			System.out.println(
					Renderer.colorCyan("  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
			return r;
		} else {
			System.out.println("  ����� �� �����ϴ�.");
			return null;
		}
	}

	// ���� ��� (���� ��ȣ)
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
						+ reservations[i].getRoomNumber() + "ȣ / " + reservations[i].getName() + " ("
						+ reservations[i].getPhoneNumber() + ")");
			}
		}
		System.out.println(
				Renderer.colorCyan("  +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
		System.out.println();
		System.out.print("  ���� ��ȣ ���� : ");
		int num = userInputNum() - 1;

		if (reservations[num] != null) {
			rooms.reservToEmpty(reservations[num].getRoomNumber());
			reservations[num] = null;
			System.out.println();
			System.out.println(
					Renderer.colorCyan("  +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
			System.out.println("  ���� ��Ұ� �Ϸ�Ǿ����ϴ�.");
			System.out.println(
					Renderer.colorCyan("  +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
		} else {
			System.out.println();
			System.out.println(
					Renderer.colorCyan("  +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
			System.out.println("  �ùٸ� ��ȣ�� �Է��ϼ���.");
			System.out.println(
					Renderer.colorCyan("  +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
		}
	}

	// ���� ���� Ȯ�� (�� ��)
	public void findReser() {
		System.out.println();
		System.out.println(
				Renderer.colorCyan("  +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
		System.out.println(Renderer.colorCyan("  ++   �����ȣ                                                       ++"));
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
						+ reservations[i].getRoomNumber() + "ȣ / " + reservations[i].getName() + " ("
						+ reservations[i].getPhoneNumber() + ")");
			}
		}
		System.out.println(
				Renderer.colorCyan("  +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));

		System.out.println();
		System.out.print("  ���� ��ȣ ���� : ");
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
				System.out.println("  ���� ��ȣ�� ��Ȯ�� ������ �ּ���.");
				System.out.println(Renderer
						.colorCyan("  +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
			}
		}
	}

	// ���� ���� Ȯ�� (��� ��)
	public void findAllReser() {
		System.out.println();
		System.out.println(Renderer.colorCyan("  [���� ����Ʈ]"));
		System.out.println(
				Renderer.colorCyan("  +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
		System.out.println(Renderer.colorCyan("  ++   �����ȣ                                                       ++"));
		System.out.println(
				Renderer.colorCyan("  +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));

		for (Reservation elem : reservations) {
			if (elem != null) {
				System.out.println("  " + elem.getReserveNumber() + " [" + elem.getCheckInDate() + " ~ "
						+ elem.getCheckOutDate() + "] : " + elem.getRoomNumber() + "ȣ / " + elem.getName() + " ("
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