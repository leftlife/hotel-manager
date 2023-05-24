
public class Customer {
	private int numberOfVisits = 0; // 방문횟수
	private String phoneNumber;
	private String name;
	private String degree = "common  ";

	public Customer() {

	}

	public Customer(String name, String phoneNumber, int numberOfVisits) { // 이름,전화번호, 방문회수 강제 입력을 위해 생성자 만듦
		this.numberOfVisits = numberOfVisits;
		this.phoneNumber = phoneNumber;
		this.name = name;

	}

	public Customer(String phoneNumber, String name) { // 실제 코드 입력시에는 전화번호와 이름만 입력하면 고객 생성가능
		this.phoneNumber = phoneNumber;
		this.name = name;

	}

	public int getNumberOfVisits() {
		return numberOfVisits;
	}

	public void setNumberOfVisits(String phoneNumber) { // 예약시 전화번호를 넘겨받아서 방문회수 +1 해줌
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

	public String getDegree() { // 다른 클래스에서 이 함수 호출하면 방문회차별로 등급이 출력됨
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