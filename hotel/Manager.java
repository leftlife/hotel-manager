
//Manager클래스가 총괄매니저라고 보시면 됩니다.
import java.util.Scanner;

//아직 고객이 등록되지 않았으므로 미리 가상의 고객 입력함. 이 데이터를 이용해서 예약도  실행 가능할듯 
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
		// 가상의 고객 등록
		customerManager.addCustomer(new Customer("김정숙", "01099996666", 11));
		customerManager.addCustomer(new Customer("이서연", "01012345678", 12));
		customerManager.addCustomer(new Customer("박영식", "01098745632", 2));
		customerManager.addCustomer(new Customer("이성수", "01066663333", 3));
		customerManager.addCustomer(new Customer("이영자", "01055555555", 5));
		customerManager.addCustomer(new Customer("최수빈", "01033333333", 8));
		customerManager.addCustomer(new Customer("김서아", "01044444444", 22));
		customerManager.addCustomer(new Customer("이영철", "01011111111", 2));
		customerManager.addCustomer(new Customer("박영호", "01022222222", 1));

		customer = new Customer();
		roomManager = new RoomManager();
		roomManager.setRoomList();
		reserv = new ReservationManager(roomManager, customerManager);
	}

	public void run() {
		// 사용자가 0 입력하여 종료하기 전까지 무한 반복
		while (true) {
			// 상단 호텔 방 리스트 출력
			clearConsole.clean();
			Renderer.printHotel(roomManager.getList());
			// 메뉴 내용 출력
			System.out.println("  0.프로그램 종료\n");
			System.out.println("  1.예약 관리");
			System.out.println("  2.고객 관리");
			System.out.println("  3.객실 관리\n");
			System.out.println();
			printLine();

			System.out.println();
			int flag = userInputNumber();

			switch (flag) {
			// 프로그램 종료
			case 0:
				System.exit(0);
				break;
			// 예약 관리
			case 1:
				reservationMenu();
				break;
			// 고객 관리
			case 2:
				customerMenu();
				break;
			// 객실 관리
			case 3:
				roomManager.changeRoomStatus();
				break;
			}
		}
	}

	public void reservationMenu() {
		while (bool) {
			// 상단 호텔 방 리스트 출력
			clearConsole.clean();
			Renderer.printHotel(roomManager.getList());
			// 메뉴 내용 출력
			System.out.println("  0.메인 화면으로 : <엔터키>\n");
			System.out.println("  1.예약 등록");
			System.out.println("  2.예약 취소");
			System.out.println("  3.예약 확인");
			System.out.println("  4.모든 예약 확인\n");
			printLine();

			System.out.println();
			int flag = userInputNumber();

			switch (flag) {
			// 메인 화면으로
			case 0:
				bool = false;
				break;
			// 예약 등록
			case 1:
				Reservation reservation = reserv.regist();
				customer.setNumberOfVisits(reservation.getPhoneNumber());
				sc.nextLine();
				break;
			// 예약 취소
			case 2:
				reserv.deleteReser();
				sc.nextLine();
				break;
			// 예약 확인
			case 3:
				reserv.findReser();
				sc.nextLine();
				break;
			// 모든 예약 확인
			case 4:
				reserv.findAllReser();
				sc.nextLine();
				break;
			}
		}
		bool = true;
	}

	public void customerMenu() { // 메뉴 실행 메뉴얼
		while (bool) {
			// 상단 호텔 방 리스트 출력
			clearConsole.clean();
			Renderer.printHotel(roomManager.getList());
			// 메뉴 내용 출력
			System.out.println("  0.메인 화면으로 : <엔터키>\n");
			System.out.println("  1.고객 등록");
			System.out.println("  2.고객 목록");
			System.out.println("  3.고객 정보 변경");
			System.out.println("  4.고객 찾기\n");
			printLine();

			System.out.println();
			int flag = userInputNumber();

			switch (flag) {
			// 메인 화면으로
			case 0:
				bool = false;
				break;
			// 고객 등록
			case 1:
				customerManager.inputProcess();
				sc.nextLine();
				break;
			// 고객 목록
			case 2:
				// PrintStream out = new PrintStream(System.out, false);//출력버퍼를 강제로 멈추게함
				Renderer.printCustomerList(customerManager);
				sc.nextLine();
				break;
			// 고객 정보 변경
			case 3:
				customerManager.customerResetProcess();
				sc.nextLine();
				break;
			// 고객 찾기
			case 4:
				customerManager.findProcess();
				System.out.println("  엔터를 누르시면 이전메뉴로 이동합니다.");
				sc.nextLine();
				break;
			}
		}
		bool = true;
	}

	public int userInputNumber() {
		System.out.print("  메뉴를 선택하세요 : ");
		String line = sc.nextLine();

		// 정상적인 메뉴 번호를 받을 때까지 계속 돌림
		while (true) {
			// 사용자 입력값이 없거나 숫자가 아닐 경우..
			if (line.isEmpty() || !line.chars().allMatch(Character::isDigit)) {
				System.out.print(Renderer.colorRed("  잘못된 입력입니다.                              "));
				int currentRow = 50;
				int currentColumn = 23;
				System.out.print("\u001B[" + currentRow + ";" + currentColumn + "H"); // 커서 위치 이동
				System.out.print("\u001B[2K" + "\u001B[" + 100 + "D"); // 해당 라인 내용 삭제

				System.out.print("  메뉴를 선택하세요 : ");
				line = sc.nextLine();
			} else {
				// 사용자 입력값이 메뉴 번호 범위가 아닐 경우..
				int number = Integer.parseInt(line);
				if (number < 0 || number > 5) {
					System.out.print(Renderer.colorRed("  메뉴 번호를 정확히 입력하세요.                      "));
					int currentRow = 50;
					int currentColumn = 23;
					System.out.print("\u001B[" + currentRow + ";" + currentColumn + "H"); // 커서 위치 이동
					System.out.print("\u001B[2K" + "\u001B[" + 100 + "D"); // 해당 라인 내용 삭제

					System.out.print("  메뉴를 선택하세요 : ");
					line = sc.nextLine();
				} else {
					// 정상적인 값을 받았으므로 while 문 종료
					break;
				}
			}
		}

		int number = Integer.parseInt(line); // 문자열로 받은 값을 숫자값으로 다시 전환

		return number;
	}

	private void printLine() {
		for (int j = 0; j < 5; j++)
			System.out.print(Renderer.colorGray("--------------------------"));
		System.out.print("\n");
	}
}