
public class Customer {
	private int numberOfVisits = 0; // �湮Ƚ��
	private String phoneNumber;
	private String name;
	private String degree = "common  ";

	public Customer() {

	}

	public Customer(String name, String phoneNumber, int numberOfVisits) { // �̸�,��ȭ��ȣ, �湮ȸ�� ���� �Է��� ���� ������ ����
		this.numberOfVisits = numberOfVisits;
		this.phoneNumber = phoneNumber;
		this.name = name;

	}

	public Customer(String phoneNumber, String name) { // ���� �ڵ� �Է½ÿ��� ��ȭ��ȣ�� �̸��� �Է��ϸ� �� ��������
		this.phoneNumber = phoneNumber;
		this.name = name;

	}

	public int getNumberOfVisits() {
		return numberOfVisits;
	}

	public void setNumberOfVisits(String phoneNumber) { // ����� ��ȭ��ȣ�� �Ѱܹ޾Ƽ� �湮ȸ�� +1 ����
		this.numberOfVisits++;

	}

	public void setNumberOfVisitsMius(String phoneNumber) {
		this.numberOfVisits--;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhonNumber(String phonNumber) {
		this.phoneNumber = phonNumber;
	}

	public String getDegree() { // �ٸ� Ŭ�������� �� �Լ� ȣ���ϸ� �湮ȸ������ ����� ��µ�
		if (numberOfVisits > 15) {
			this.setDegree("PlATINUM");
		} else if (numberOfVisits > 10) {
			this.setDegree("VIP     ");
		} else if (numberOfVisits > 5) {
			this.setDegree("SPECIAL ");
		}

		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

}