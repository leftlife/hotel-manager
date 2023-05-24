
import java.util.Scanner;

public class CustomerManager {
	public static Customer[] customerList = new Customer[100];
	private int index = 0;
	private Scanner SCANNER;

	public CustomerManager(int index) {
		this.index = index;
		SCANNER = new Scanner(System.in);
	}

	public CustomerManager() {
		SCANNER = new Scanner(System.in);
	}

	public Customer[] getCustomerList() {
		if (index == 0) {
			System.out.println();
			System.out.println("  등록된 고객이 없습니다.");
		}

		return customerList;
	}

	public boolean addCustomer(Customer d) {
		if (index >= 100) {
			System.out.println();
			System.out.println("  더 이상 등록할 수 없습니다.");
			return false;
		}
		customerList[index] = d;
		index++;

		return true;
	}

	public void getFindCustomer(String phoneNumber) {
		if (index == 0) {
			System.out.println();
			System.out.println("  등록된 고객이 없습니다.");
			return;
		}

		boolean isExistUser = false;
		for (int i = 0; i < index; i++) {
			if (phoneNumber.equals(customerList[i].getPhoneNumber())) {
				isExistUser = true;
				System.out.println();
				System.out.println(
						Renderer.colorCyan("  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
				System.out.println(Renderer.colorCyan("  ++      고객명               전화번호              방문횟수     ++"));
				System.out.println(
						Renderer.colorCyan("  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
				printInformation(customerList[i]);
				System.out.println(
						Renderer.colorCyan("  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
			}
		}

		if (!isExistUser) {
			System.out.println();
			System.out.println(
					Renderer.colorCyan("  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
			System.out.println("  찾으시는 고객이 없습니다.");
			System.out.println(
					Renderer.colorCyan("  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
		}
	}

	public static void printInformation(Customer cus) { // 고객정보 출력시 호출 함수
		String phone1 = cus.getPhoneNumber().substring(0, 3);
		String phone2 = cus.getPhoneNumber().substring(3, 7);
		String phone3 = cus.getPhoneNumber().substring(7, 11);
		String phoneNumber = phone1 + "-" + phone2 + "-" + phone3;

		System.out.println("      " + cus.getName() + " ( " + cus.getDegree() + " )    " + phoneNumber + "      :   "
				+ cus.getNumberOfVisits() + " Count  ");
	}

	public Customer findNUM(String phoneNumber) {
		for (int i = 0; i < index; i++) {
			if (phoneNumber.equals(customerList[i].getPhoneNumber())) {
				return customerList[i];
			} // 리턴 값으로 전화번호 받기 위해..
		}
		// System.out.println(" 등록된 고객이 없습니다.");

		return null;
	}

	// 중복 가입 방지
	public boolean getFindPhoneNumber(String phoneNumber) {
		for (int i = 0; i < index; i++) {

			if (phoneNumber.equals(customerList[i].getPhoneNumber())) {
				// System.out.println(" 이미 등록된 번호입니다.");
				return true;
			}

		}

		// System.out.println(" 미등록 번호입니다.");
		return false;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public static void setCustomerList(Customer[] customerList) {
		CustomerManager.customerList = customerList;
	}

	public int nserIntInput() {
		int num = SCANNER.nextInt();

		return num;
	}

	public void findProcess() {
		System.out.println();
		System.out.print("  찾으시는 고객의 전화번호를 (-)없이 입력 : ");
		String phoneNumber = SCANNER.nextLine();
		getFindCustomer(phoneNumber);
	}

	public Customer userInput() {
		System.out.print("  이름 입력 : ");
		String name = SCANNER.nextLine();

		System.out.println();
		System.out.print("  전화번호 입력 (예 : 01011112222) : ");
		String phoneNumber = SCANNER.nextLine();

		while (phoneNumber.length() != 11 || getFindPhoneNumber(phoneNumber)) {
			System.out.print("  잘못된 번호입니다. 다시 입력하세요 : ");
			phoneNumber = SCANNER.nextLine();
		}

		return new Customer(phoneNumber, name);
	}

	public boolean add(Customer d) {
		return addCustomer(d);
	}

	public void inputProcess() {
		System.out.println();
		Customer d = userInput();

		boolean result = add(d);
		if (result) {
			System.out.println();
			System.out.println(
					Renderer.colorCyan("  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
			System.out.println("  등록이 완료 되었습니다.");
			System.out.println(
					Renderer.colorCyan("  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
		} else {
			System.out.println();
			System.out.println(
					Renderer.colorCyan("  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
			System.out.println("  더 이상 등록할 수 없습니다.");
			System.out.println(
					Renderer.colorCyan("  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
		}
	}

	public Customer customerResetProcess() {
		System.out.println();
		System.out.print("  변경하실 고객의 이름 또는 전화번호를 (-)없이 입력 : ");
		String input = SCANNER.nextLine();

		for (int i = 0; i < index; i++) {
			if (input.equals(customerList[i].getName()) || input.equals(customerList[i].getPhoneNumber())) {
				System.out.println();
				System.out.println("  [고객 정보를 변경합니다.]");
				System.out.println();
				System.out.print("  이름 : ");
				String newName = SCANNER.nextLine();

				System.out.println();
				System.out.print("  전화번호 입력 (예 : 01011112222) : ");
				String newPhoneNumber = SCANNER.nextLine();

				while (newPhoneNumber.length() != 11 || getFindPhoneNumber(newPhoneNumber)) {
					System.out.println("  잘못된 번호 입니다. 다시 입력하세요");
					newPhoneNumber = SCANNER.nextLine();
				}

				Customer updatedCustomer = new Customer(newPhoneNumber, newName);
				customerList[i] = updatedCustomer;

				System.out.println();
				System.out.println(
						Renderer.colorCyan("  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
				System.out.println("  변경이 완료 되었습니다.");
				System.out.println();
				System.out.println(
						"  고객 전화 번호: " + updatedCustomer.getPhoneNumber() + ", 고객 이름: " + updatedCustomer.getName());
				System.out.println(
						Renderer.colorCyan("  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));

				return updatedCustomer;
			}
		}

		System.out.println();
		System.out.println(Renderer.colorCyan("  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
		System.out.println("  찾으시는 고객이 없습니다.");
		System.out.println(Renderer.colorCyan("  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));

		return null;

	}

}