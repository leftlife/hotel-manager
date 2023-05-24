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
				roomList[i] = new Room(cnt, "투숙중");
			} else if (i % 21 == 0) {
				roomList[i] = new Room(cnt, "청소중");
			} else {
				roomList[i] = new Room(cnt, "빈객실");
			}
			cnt++;
		}
		room.setprice();
	}

	// roomList 전체를 반환
	public Room[] getList() {
		return roomList;
	}

	// 출력 시 개행 용도
	public void printRoom(int cnt) {
		if (cnt % 5 == 0 && cnt >= 5) {
			System.out.println();
		}
	}

	// 객실이 있는지 없는지
	public boolean existRoom(int roomNumber) {
		for (int i = 0; i < roomList.length; i++) {
			if (roomNumber == roomList[i].getRoomNumber()) {
				return false;
			}
		}
		return true;
	}

	// 정수 roomNumber에 대응되는 roomList[i] 반환, 없으면 안내메시지와 null 반환
	public Room find(int roomNumber) {
		for (int i = 0; i < roomList.length; i++) {
			if (roomNumber == roomList[i].getRoomNumber()) {
				return roomList[i];
			}
		}
		return null;
	}

	// 호실 매개변수로 받아서 그 방의 가격
	public int getRoomPrice(int roomNumber) {
		Room a = find(roomNumber);
		return a.getPrice();
	}

	// 상태 변경
	public Room cleanToReserv(int roomNumber) {
		Room a = find(roomNumber);
		a.setRoomStatus("투숙중");
		return a;
	}

	public Room reservToEmpty(int roomNumber) {
		Room a = find(roomNumber);
		a.setRoomStatus("청소중");
		return a;
	}

	public Room emptyToClean(int roomNumber) {
		Room a = find(roomNumber);
		a.setRoomStatus("빈객실");
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
		System.out.println("  " + Renderer.colorGreen(Integer.toString(roomNumber)) + "호 방 정보가 정상적으로 변경되었습니다!!");
		scanner.nextLine();
	}

	public int userInputRoomNumber() {
		System.out.print("  Step1. 상태를 변경하고 싶은 방의 호실 입력 : ");
		String roomNumber = scanner.nextLine();

		while (true) {
			if (roomNumber.isEmpty() || !roomNumber.chars().allMatch(Character::isDigit)) {
				System.out.print(Renderer.colorRed("  잘못된 입력입니다.                             "));
				int currentRow = 52;
				int currentColumn = 53;
				System.out.print("\u001B[" + currentRow + ";" + currentColumn + "H"); // 커서 위치 이동
				System.out.print("\u001B[2K" + "\u001B[" + 100 + "D"); // 해당 라인 내용 삭제
				System.out.print("  Step1. 상태를 변경하고 싶은 방의 호실 입력 : ");

				roomNumber = scanner.nextLine();
			} else {
				if (find(Integer.parseInt(roomNumber)) == null) {
					System.out.print(Renderer.colorRed("  방 번호를 정확히 입력하세요.                       "));
					int currentRow = 52;
					int currentColumn = 53;
					System.out.print("\u001B[" + currentRow + ";" + currentColumn + "H"); // 커서 위치 이동
					System.out.print("\u001B[2K" + "\u001B[" + 100 + "D"); // 해당 라인 내용 삭제
					System.out.print("  Step1. 상태를 변경하고 싶은 방의 호실 입력 : ");

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
		System.out.print("  Step2. 변경을 원하는 상태 (1.입실 2.퇴실 3.청소완료) : ");
		String roomStatus = scanner.nextLine();

		// 정상적인 방 상태값 받을 때까지 계속 돌림
		while (true) {
			// 사용자 입력값이 없거나 숫자가 아닐 경우..
			if (roomStatus.isEmpty() || !roomStatus.chars().allMatch(Character::isDigit)) {
				System.out.print(Renderer.colorRed("  잘못된 입력입니다.                             "));
				int currentRow = 54;
				int currentColumn = 57;
				System.out.print("\u001B[" + currentRow + ";" + currentColumn + "H"); // 커서 위치 이동
				System.out.print("\u001B[2K" + "\u001B[" + 100 + "D"); // 커서가 위치한 라인의 내용 삭제

				System.out.print("  Step2. 변경을 원하는 상태 (1.입실 2.퇴실 3.청소완료) : ");
				roomStatus = scanner.nextLine();
			} else {
				// 사용자 입력값이 메뉴 번호 범위가 아닐 경우..
				int number = Integer.parseInt(roomStatus);
				if (number < 1 || number > 3) {
					System.out.print(Renderer.colorRed("  상태 번호를 정확히 입력하세요.                     "));
					int currentRow = 54;
					int currentColumn = 57;
					System.out.print("\u001B[" + currentRow + ";" + currentColumn + "H"); // 커서 위치 이동
					System.out.print("\u001B[2K" + "\u001B[" + 100 + "D"); // 커서가 위치한 라인의 내용 삭제

					System.out.print("  Step2. 변경을 원하는 상태 (1.입실 2.퇴실 3.청소완료) : ");
					roomStatus = scanner.nextLine();
				} else {
					// 정상적인 값을 받았으므로 while 문 종료
					break;
				}
			}
		}

		int number = Integer.parseInt(roomStatus); // 문자열로 받은 값을 숫자값으로 다시 전환

		return number;
	}

}