
public class Renderer {
	public static final String red = "\u001B[31m";
	public static final String green = "\u001B[32m";
	public static final String yellow = "\u001B[33m";
	public static final String blue = "\u001B[34m";
	public static final String purple = "\u001B[35m";
	public static final String cyan = "\u001B[36m";
	public static final String gray = "\u001B[90m";
	public static final String exit = "\u001B[0m";

	// ȣ�� ������ �ǹ��� �����ϰ� ���
	public static void printHotel(Room[] roomList) {
		printWall();
		System.out.print(colorPurple("|")
				+ "                                                                                                                                 "
				+ colorPurple("|") + "\n");
		System.out.print(colorPurple("|") + "           +                         +                      "
				+ colorRed(" J A V A ") + "                       +                        +           "
				+ colorPurple("|") + "\n");
		System.out.print(colorPurple("|")
				+ "                                                                                                                                 "
				+ colorPurple("|") + "\n");
		System.out.print(colorPurple("|") + "                +                            +              "
				+ colorRed("H O T E L") + "                +                           +               "
				+ colorPurple("|") + "\n");
		System.out.print(colorPurple("|")
				+ "                                                                                                                                 "
				+ colorPurple("|") + "\n");
		printWall();

		for (int i = 60; i <= 79; i++) {
			if (i % 5 == 0 && i >= 65) {
				System.out.print(colorPurple("|") + "\n");
				printThinWall();
			}
			printRoomInfo(roomList[i]);
		}
		System.out.print(colorPurple("|") + "\n");
		printWall();

		for (int i = 40; i <= 59; i++) {
			if (i % 5 == 0 && i >= 45) {
				System.out.print(colorPurple("|") + "\n");
				printThinWall();
			}
			printRoomInfo(roomList[i]);
		}
		System.out.print(colorPurple("|") + "\n");
		printWall();

		for (int i = 20; i <= 39; i++) {
			if (i % 5 == 0 && i >= 25) {
				System.out.print(colorPurple("|") + "\n");
				printThinWall();
			}
			printRoomInfo(roomList[i]);
		}
		System.out.print(colorPurple("|") + "\n");
		printWall();

		for (int i = 0; i <= 19; i++) {
			if (i % 5 == 0 && i >= 5) {
				System.out.print(colorPurple("|") + "\n");
				printThinWall();
			}
			printRoomInfo(roomList[i]);
		}
		System.out.print(colorPurple("|") + "\n");
		printWall();

		System.out.println();
	}

	private static void printThinWall() {
		System.out.print(colorPurple("+"));
		for (int j = 0; j < 5; j++)
			System.out.print(colorGray("-------------------------" + colorPurple("+")));
		System.out.print("\n");
	}

	private static void printWall() {
		System.out.print(colorPurple("+"));
		for (int j = 0; j < 5; j++)
			System.out.print(colorPurple("=========================" + colorPurple("+")));
		System.out.print("\n");
	}

	private static void printRoomInfo(Room room) {
		System.out.printf(colorPurple("|") + "   %dȣ (%s) %s   ", room.getRoomNumber(), room.getNumBed(),
				changeRoomStatus(room.getRoomStatus()));
	}

	private static String changeRoomStatus(String roomStatus) {
		String colorRoomStatus = "";

		return colorRoomStatus;
	}

	public static void findRoom(Room[] roomList) {
		int cnt = 0;
		for (int i = 0; i < roomList.length; i++) {
			if (roomList[i].roomStatus.equals("�󰴽�")) {
				cnt++;
				System.out.printf("%d%s  ", roomList[i].roomNumber, "ȣ");
				if (cnt % 5 == 0 && cnt >= 5) {
					System.out.println();
				}
			}
		}
		if (cnt == 0) {
			System.out.println("  �󰴽��� �����ϴ�.");
		}
		System.out.println();
	}

	public static void findRoom1(Room[] roomList) {
		int cnt = 0;
		for (int i = 0; i < roomList.length; i++) {
			if (roomList[i].roomStatus.equals("������")) {
				System.out.printf("%d%s  ", roomList[i].roomNumber, "ȣ");
				cnt++;
				if (cnt % 5 == 0 && cnt >= 5) {
					System.out.println();
				}
			}
		}
		if (cnt == 0) {
			System.out.println("  �������� ������ �����ϴ�.");
		}
		System.out.println();
	}

	public static void printCustomerList(CustomerManager customers) { // �� ���� ���� ���
		System.out.println();
		System.out.println(colorCyan("  [�� ���� ����Ʈ]"));
		System.out.println(colorCyan("  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
		System.out.println(colorCyan("  ++      ����               ��ȭ��ȣ              �湮Ƚ��     ++"));
		System.out.println(colorCyan("  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));

		Customer[] cus = customers.getCustomerList();
		for (Customer elem : cus) {
			if (elem != null) {
				printInformation(elem);
			}
		}
		System.out.println(colorCyan("  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
	}

	public static void printInformation(Customer cus) { // ������ ��½� ȣ�� �Լ�
		String phone1 = cus.getPhoneNumber().substring(0, 3);
		String phone2 = cus.getPhoneNumber().substring(3, 7);
		String phone3 = cus.getPhoneNumber().substring(7, 11);
		String phoneNumber = phone1 + "-" + phone2 + "-" + phone3;

		System.out.println("      " + cus.getName() + " ( " + cus.getDegree() + " )    " + phoneNumber + "      :   "
				+ cus.getNumberOfVisits() + " Count  ");
	}

	public static String colorRed(String word) {
		return red + word + exit;
	}

	public static String colorGreen(String word) {
		return green + word + exit;
	}

	public static String colorYellow(String word) {
		return yellow + word + exit;
	}

	public static String colorBlue(String word) {
		return blue + word + exit;
	}

	public static String colorPurple(String word) {
		return purple + word + exit;
	}

	public static String colorCyan(String word) {
		return cyan + word + exit;
	}

	public static String colorGray(String word) {
		return gray + word + exit;
	}
}