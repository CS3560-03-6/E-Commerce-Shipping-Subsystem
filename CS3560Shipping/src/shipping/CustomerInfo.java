package shipping;

public class CustomerInfo {
	private int customerInformationId;
	private String firstName;
	private String lastName;
	private String address;
	private String phoneNumber;
	private String email;
	
	public CustomerInfo(int customerInformationId, String firstName,
				 String lastName, String address, String phoneNumber,
				 String email){
		this.customerInformationId = customerInformationId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}
	
	public String[] getCustomerName() {
		String[] full_name = new String[2];
		full_name[0] = firstName;
		full_name[1] = lastName;
		
		return full_name;
	}
	
	public int getCustomerID() {
		return customerInformationId;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public String getEmail() {
		return email;
	}
}
	
