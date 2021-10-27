package shipping;

public class CustomerInfo {
	private int customerInformationId;
	private String firstName;
	private String lastName;
	private String address;
	private String phoneNumber;
	private String email;
	
	CustomerInfo(int customerInformation_id, String firstName,
				 String lastName, String address, String phoneNumber,
				 String email){
		this.customerInformationId = customerInformationId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}
	
	String[] getCustomerName() {
		String[] full_name = new String[2];
		full_name[0] = first_name;
		full_name[1] = last_name;
		
		return full_name;
	}
	
	int getCustomerID() {
		return customerInformationId;
	}
	
	String getAddress() {
		return address;
	}
	
	String getPhoneNumber() {
		return phoneNumber;
	}
	
	String getEmail() {
		return email;
	}
}
	
