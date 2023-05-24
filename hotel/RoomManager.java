import java.util.Scanner;

public class RoomManager {
	public static Room[] roomList;
	Room room;
	Scanner scanner;

	public RoomManager() {
		this.room = new Room();
		this.scanner = new Scanner(System.in);
	}

	public void setRoomList() {
		roomList = new Room[80];
		int cnt = 201;

		for (int i = 0; i < roomList.length; i++) {
			if (i % 20 == 0 && i > 19) {
				cnt = cnt + 80;
			}
			if (i % 11 == 0) {
				roomList[i] = new Room(cnt, "������");
			} else if (i % 21 == 0) {
				roomList[i] = new Room(cnt, "û����");
			} else {
				roomList[i] = new Room(cnt, "�󰴽�");
			}
			cnt++;
		}
		room.setprice();
	}

	// roomList ��ü�� ��ȯ
	public Room[] getList() {
		return roomList;
	}

	// ��� �� ���� �뵵
	public void printRoom(int cnt) {
		if (cnt % 5 == 0 && cnt >= 5) {
			System.out.println();
		}
	}

	// ������ �ִ��� ������
	public boolean existRoom(int roomNumber) {
		for (int i = 0; i < roomList.length; i++) {
			if (roomNumber == roomList[i].getRoomNumber()) {
				return false;
			}
		}
		return true;
	}

	// ���� roomNumber�� �����Ǵ� roomList[i] ��ȯ, ������ �ȳ��޽����� null ��ȯ
	public Room find(int roomNumber) {
		for (int i = 0; i < roomList.length; i++) {
			if (roomNumber == roomList[i].getRoomNumber()) {
				return roomList[i];
			}
		}
		return null;
	}

	// ȣ�� �Ű������� �޾Ƽ� �� ���� ����
	public int getRoomPrice(int roomNumber) {
		Room a = find(roomNumber);
		return a.getPrice();
	}

	// ���� ����
	public Room cleanToReserv(int roomNumber) {
		Room a = find(roomNumber);
		a.setRoomStatus("������");
		return a;
	}

	public Room reservToEmpty(int roomNumber) {
		Room a = find(roomNumber);
		a.setRoomStatus("û����");
		return a;
	}

	public Room emptyToClean(int roomNumber) {
		Room a = find(roomNumber);
		a.setRoomStatus("�󰴽�");
		return a;
	}

	public void changeRoomStatus() {
		System.out.println();
		int roomNumber = userInputRoomNumber();

		System.out.println();
		int roomStatus = userInputRoomStatus();

		switch (roomStatus) {
		case 1:
			cleanToReserv(roomNumber);
			break;
		case 2:
			reservToEmpty(roomNumber);
			break;
		case 3:
			emptyToClean(roomNumber);
			break;
		}

		System.out.println();
		System.out.println();
		System.out.println("  " + Renderer.colorGreen(Integer.toString(roomNumber)) + "ȣ �� ������ ���������� ����Ǿ����ϴ�!!");
		scanner.nextLine();
	}

	public int userInputRoomNumber() {
		System.out.print("  Step1. ���¸� �����ϰ� ���� ���� ȣ�� �Է� : ");
		String roomNumber = scanner.nextLine();

		while (true) {
			if (roomNumber.isEmpty() || !roomNumber.chars().allMatch(Character::isDigit)) {
				System.out.print(Renderer.colorRed("  �߸��� �Է��Դϴ�.                             "));
				int currentRow = 52;
				int currentColumn = 53;
				System.out.print("\u001B[" + currentRow + ";" + currentColumn + "H"); // Ŀ�� ��ġ �̵�
				System.out.print("\u001B[2K" + "\u001B[" + 100 + "D"); // �ش� ���� ���� ����
				System.out.print("  Step1. ���¸� �����ϰ� ���� ���� ȣ�� �Է� : ");

				roomNumber = scanner.nextLine();
			} else {
				if (find(Integer.parseInt(roomNumber)) == null) {
					System.out.print(Renderer.colorRed("  �� ��ȣ�� ��Ȯ�� �Է��ϼ���.                       "));
					int currentRow = 52;
					int currentColumn = 53;
					System.out.print("\u001B[" + currentRow + ";" + currentColumn + "H"); // Ŀ�� ��ġ �̵�
					System.out.print("\u001B[2K" + "\u001B[" + 100 + "D"); // �ش� ���� ���� ����
					System.out.print("  Step1. ���¸� �����ϰ� ���� ���� ȣ�� �Է� : ");

					roomNumber = scanner.nextLine();
				} else {
					break;
				}
			}
		}
		int number = Integer.parseInt(roomNumber);

		return number;
	}

	public int userInputRoomStatus() {
		System.out.print("  Step2. ������ ���ϴ� ���� (1.�Խ� 2.��� 3.û�ҿϷ�) : ");
		String roomStatus = scanner.nextLine();

		// �������� �� ���°� ���� ������ ��� ����
		while (true) {
			// ����� �Է°��� ���ų� ���ڰ� �ƴ� ���..
			if (roomStatus.isEmpty() || !roomStatus.chars().allMatch(Character::isDigit)) {
				System.out.print(Renderer.colorRed("  �߸��� �Է��Դϴ�.                             "));
				int currentRow = 54;
				int currentColumn = 57;
				System.out.print("\u001B[" + currentRow + ";" + currentColumn + "H"); // Ŀ�� ��ġ �̵�
				System.out.print("\u001B[2K" + "\u001B[" + 100 + "D"); // Ŀ���� ��ġ�� ������ ���� ����

				System.out.print("  Step2. ������ ���ϴ� ���� (1.�Խ� 2.��� 3.û�ҿϷ�) : ");
				roomStatus = scanner.nextLine();
			} else {
				// ����� �Է°��� �޴� ��ȣ ������ �ƴ� ���..
				int number = Integer.parseInt(roomStatus);
				if (number < 1 || number > 3) {
					System.out.print(Renderer.colorRed("  ���� ��ȣ�� ��Ȯ�� �Է��ϼ���.                     "));
					int currentRow = 54;
					int currentColumn = 57;
					System.out.print("\u001B[" + currentRow + ";" + currentColumn + "H"); // Ŀ�� ��ġ �̵�
					System.out.print("\u001B[2K" + "\u001B[" + 100 + "D"); // Ŀ���� ��ġ�� ������ ���� ����

					System.out.print("  Step2. ������ ���ϴ� ���� (1.�Խ� 2.��� 3.û�ҿϷ�) : ");
					roomStatus = scanner.nextLine();
				} else {
					// �������� ���� �޾����Ƿ� while �� ����
					break;
				}
			}
		}

		int number = Integer.parseInt(roomStatus); // ���ڿ��� ���� ���� ���ڰ����� �ٽ� ��ȯ

		return number;
	}

}