
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
			System.out.println("  ��ϵ� ���� �����ϴ�.");
		}

		return customerList;
	}

	public boolean addCustomer(Customer d) {
		if (index >= 100) {
			System.out.println();
			System.out.println("  �� �̻� ����� �� �����ϴ�.");
			return false;
		}
		customerList[index] = d;
		index++;

		return true;
	}

	public void getFindCustomer(String phoneNumber) {
		if (index == 0) {
			System.out.println();
			System.out.println("  ��ϵ� ���� �����ϴ�.");
			return;
		}

		boolean isExistUser = false;
		for (int i = 0; i < index; i++) {
			if (phoneNumber.equals(customerList[i].getPhoneNumber())) {
				isExistUser = true;
				System.out.println();
				System.out.println(
						Renderer.colorCyan("  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
				System.out.println(Renderer.colorCyan("  ++      ����               ��ȭ��ȣ              �湮Ƚ��     ++"));
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
			System.out.println("  ã���ô� ���� �����ϴ�.");
			System.out.println(
					Renderer.colorCyan("  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
		}
	}

	public static void printInformation(Customer cus) { // ������ ��½� ȣ�� �Լ�
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
			} // ���� ������ ��ȭ��ȣ �ޱ� ����..
		}
		// System.out.println(" ��ϵ� ���� �����ϴ�.");

		return null;
	}

	// �ߺ� ���� ����
	public boolean getFindPhoneNumber(String phoneNumber) {
		for (int i = 0; i < index; i++) {

			if (phoneNumber.equals(customerList[i].getPhoneNumber())) {
				// System.out.println(" �̹� ��ϵ� ��ȣ�Դϴ�.");
				return true;
			}

		}

		// System.out.println(" �̵�� ��ȣ�Դϴ�.");
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
		System.out.print("  ã���ô� ���� ��ȭ��ȣ�� (-)���� �Է� : ");
		String phoneNumber = SCANNER.nextLine();
		getFindCustomer(phoneNumber);
	}

	public Customer userInput() {
		System.out.print("  �̸� �Է� : ");
		String name = SCANNER.nextLine();

		System.out.println();
		System.out.print("  ��ȭ��ȣ �Է� (�� : 01011112222) : ");
		String phoneNumber = SCANNER.nextLine();

		while (phoneNumber.length() != 11 || getFindPhoneNumber(phoneNumber)) {
			System.out.print("  �߸��� ��ȣ�Դϴ�. �ٽ� �Է��ϼ��� : ");
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
			System.out.println("  ����� �Ϸ� �Ǿ����ϴ�.");
			System.out.println(
					Renderer.colorCyan("  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
		} else {
			System.out.println();
			System.out.println(
					Renderer.colorCyan("  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
			System.out.println("  �� �̻� ����� �� �����ϴ�.");
			System.out.println(
					Renderer.colorCyan("  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
		}
	}

	public Customer customerResetProcess() {
		System.out.println();
		System.out.print("  �����Ͻ� ���� �̸� �Ǵ� ��ȭ��ȣ�� (-)���� �Է� : ");
		String input = SCANNER.nextLine();

		for (int i = 0; i < index; i++) {
			if (input.equals(customerList[i].getName()) || input.equals(customerList[i].getPhoneNumber())) {
				System.out.println();
				System.out.println("  [�� ������ �����մϴ�.]");
				System.out.println();
				System.out.print("  �̸� : ");
				String newName = SCANNER.nextLine();

				System.out.println();
				System.out.print("  ��ȭ��ȣ �Է� (�� : 01011112222) : ");
				String newPhoneNumber = SCANNER.nextLine();

				while (newPhoneNumber.length() != 11 || getFindPhoneNumber(newPhoneNumber)) {
					System.out.println("  �߸��� ��ȣ �Դϴ�. �ٽ� �Է��ϼ���");
					newPhoneNumber = SCANNER.nextLine();
				}

				Customer updatedCustomer = new Customer(newPhoneNumber, newName);
				customerList[i] = updatedCustomer;

				System.out.println();
				System.out.println(
						Renderer.colorCyan("  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
				System.out.println("  ������ �Ϸ� �Ǿ����ϴ�.");
				System.out.println();
				System.out.println(
						"  �� ��ȭ ��ȣ: " + updatedCustomer.getPhoneNumber() + ", �� �̸�: " + updatedCustomer.getName());
				System.out.println(
						Renderer.colorCyan("  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));

				return updatedCustomer;
			}
		}

		System.out.println();
		System.out.println(Renderer.colorCyan("  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
		System.out.println("  ã���ô� ���� �����ϴ�.");
		System.out.println(Renderer.colorCyan("  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));

		return null;

	}

}