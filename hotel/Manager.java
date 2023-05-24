
//ManagerŬ������ �Ѱ��Ŵ������ ���ø� �˴ϴ�.
import java.util.Scanner;

//���� ���� ��ϵ��� �ʾ����Ƿ� �̸� ������ �� �Է���. �� �����͸� �̿��ؼ� ���൵  ���� �����ҵ� 
public class Manager {
	ClearConsole clearConsole;
	CustomerManager customerManager;
	Customer customer;
	RoomManager roomManager;
	ReservationManager reserv;
	Scanner sc;
	boolean bool = true;

	public Manager() {
		sc = new Scanner(System.in);
		clearConsole = new ClearConsole();
		customerManager = new CustomerManager();
		// ������ �� ���
		customerManager.addCustomer(new Customer("������", "01099996666", 11));
		customerManager.addCustomer(new Customer("�̼���", "01012345678", 12));
		customerManager.addCustomer(new Customer("�ڿ���", "01098745632", 2));
		customerManager.addCustomer(new Customer("�̼���", "01066663333", 3));
		customerManager.addCustomer(new Customer("�̿���", "01055555555", 5));
		customerManager.addCustomer(new Customer("�ּ���", "01033333333", 8));
		customerManager.addCustomer(new Customer("�輭��", "01044444444", 22));
		customerManager.addCustomer(new Customer("�̿�ö", "01011111111", 2));
		customerManager.addCustomer(new Customer("�ڿ�ȣ", "01022222222", 1));

		customer = new Customer();
		roomManager = new RoomManager();
		roomManager.setRoomList();
		reserv = new ReservationManager(roomManager, customerManager);
	}

	public void run() {
		// ����ڰ� 0 �Է��Ͽ� �����ϱ� ������ ���� �ݺ�
		while (true) {
			// ��� ȣ�� �� ����Ʈ ���
			clearConsole.clean();
			Renderer.printHotel(roomManager.getList());
			// �޴� ���� ���
			System.out.println("  0.���α׷� ����\n");
			System.out.println("  1.���� ����");
			System.out.println("  2.�� ����");
			System.out.println("  3.���� ����\n");
			System.out.println();
			printLine();

			System.out.println();
			int flag = userInputNumber();

			switch (flag) {
			// ���α׷� ����
			case 0:
				System.exit(0);
				break;
			// ���� ����
			case 1:
				reservationMenu();
				break;
			// �� ����
			case 2:
				customerMenu();
				break;
			// ���� ����
			case 3:
				roomManager.changeRoomStatus();
				break;
			}
		}
	}

	public void reservationMenu() {
		while (bool) {
			// ��� ȣ�� �� ����Ʈ ���
			clearConsole.clean();
			Renderer.printHotel(roomManager.getList());
			// �޴� ���� ���
			System.out.println("  0.���� ȭ������ : <����Ű>\n");
			System.out.println("  1.���� ���");
			System.out.println("  2.���� ���");
			System.out.println("  3.���� Ȯ��");
			System.out.println("  4.��� ���� Ȯ��\n");
			printLine();

			System.out.println();
			int flag = userInputNumber();

			switch (flag) {
			// ���� ȭ������
			case 0:
				bool = false;
				break;
			// ���� ���
			case 1:
				Reservation reservation = reserv.regist();
				customer.setNumberOfVisits(reservation.getPhoneNumber());
				sc.nextLine();
				break;
			// ���� ���
			case 2:
				reserv.deleteReser();
				sc.nextLine();
				break;
			// ���� Ȯ��
			case 3:
				reserv.findReser();
				sc.nextLine();
				break;
			// ��� ���� Ȯ��
			case 4:
				reserv.findAllReser();
				sc.nextLine();
				break;
			}
		}
		bool = true;
	}

	public void customerMenu() { // �޴� ���� �޴���
		while (bool) {
			// ��� ȣ�� �� ����Ʈ ���
			clearConsole.clean();
			Renderer.printHotel(roomManager.getList());
			// �޴� ���� ���
			System.out.println("  0.���� ȭ������ : <����Ű>\n");
			System.out.println("  1.�� ���");
			System.out.println("  2.�� ���");
			System.out.println("  3.�� ���� ����");
			System.out.println("  4.�� ã��\n");
			printLine();

			System.out.println();
			int flag = userInputNumber();

			switch (flag) {
			// ���� ȭ������
			case 0:
				bool = false;
				break;
			// �� ���
			case 1:
				customerManager.inputProcess();
				sc.nextLine();
				break;
			// �� ���
			case 2:
				// PrintStream out = new PrintStream(System.out, false);//��¹��۸� ������ ���߰���
				Renderer.printCustomerList(customerManager);
				sc.nextLine();
				break;
			// �� ���� ����
			case 3:
				customerManager.customerResetProcess();
				sc.nextLine();
				break;
			// �� ã��
			case 4:
				customerManager.findProcess();
				System.out.println("  ���͸� �����ø� �����޴��� �̵��մϴ�.");
				sc.nextLine();
				break;
			}
		}
		bool = true;
	}

	public int userInputNumber() {
		System.out.print("  �޴��� �����ϼ��� : ");
		String line = sc.nextLine();

		// �������� �޴� ��ȣ�� ���� ������ ��� ����
		while (true) {
			// ����� �Է°��� ���ų� ���ڰ� �ƴ� ���..
			if (line.isEmpty() || !line.chars().allMatch(Character::isDigit)) {
				System.out.print(Renderer.colorRed("  �߸��� �Է��Դϴ�.                              "));
				int currentRow = 50;
				int currentColumn = 23;
				System.out.print("\u001B[" + currentRow + ";" + currentColumn + "H"); // Ŀ�� ��ġ �̵�
				System.out.print("\u001B[2K" + "\u001B[" + 100 + "D"); // �ش� ���� ���� ����

				System.out.print("  �޴��� �����ϼ��� : ");
				line = sc.nextLine();
			} else {
				// ����� �Է°��� �޴� ��ȣ ������ �ƴ� ���..
				int number = Integer.parseInt(line);
				if (number < 0 || number > 5) {
					System.out.print(Renderer.colorRed("  �޴� ��ȣ�� ��Ȯ�� �Է��ϼ���.                      "));
					int currentRow = 50;
					int currentColumn = 23;
					System.out.print("\u001B[" + currentRow + ";" + currentColumn + "H"); // Ŀ�� ��ġ �̵�
					System.out.print("\u001B[2K" + "\u001B[" + 100 + "D"); // �ش� ���� ���� ����

					System.out.print("  �޴��� �����ϼ��� : ");
					line = sc.nextLine();
				} else {
					// �������� ���� �޾����Ƿ� while �� ����
					break;
				}
			}
		}

		int number = Integer.parseInt(line); // ���ڿ��� ���� ���� ���ڰ����� �ٽ� ��ȯ

		return number;
	}

	private void printLine() {
		for (int j = 0; j < 5; j++)
			System.out.print(Renderer.colorGray("--------------------------"));
		System.out.print("\n");
	}
}