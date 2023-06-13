package prova2;

public class Customer {
	private int customerId;
	private String custName;
	private String city;
	private int grade;
	private int salesmanId;

    public Customer() {
    }

    public Customer(int customerId) {
        this.customerId = customerId;
    }

    public Customer(int customerId, String custName, String city, int grade, int salesmanId) {
        this(custName, city, grade, salesmanId);
        this.customerId = customerId;
    }

    public Customer(String custName, String city, int grade, int salesmanId) {
        this.custName = custName;
        this.city = city;
        this.grade = grade;
        this.salesmanId = salesmanId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getSalesmanId() {
        return salesmanId;
    }

    public void setSalesmanId(int salesmanId) {
        this.salesmanId = salesmanId;
    }
}
