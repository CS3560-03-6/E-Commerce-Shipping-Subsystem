package shipping;

public class CustomerInfo {
	private int customer_information_id;
	private String first_name;
	private String last_name;
	private String[] address;
	private String phone_number;
	private String email;
	
	/* CustomerInfo(int customer_information_id, String first_name,
				 String last_name, String[] address, String phone_number,
				 String email){
		this.customer_information_id = customer_information_id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.address = address;
		this.phone_number = phone_number;
		this.email = email;
	}*/
	
	String[] getCustomerName() {
		String[] full_name = new String[2];
		full_name[0] = first_name;
		full_name[1] = last_name;
		
		return full_name;
	}
	
	int getCustomerID() {
		return customer_information_id;
	}
	
	String[] getAddress() {
		return address;
	}
	
	String getPhoneNumber() {
		return phone_number;
	}
	
	String getEmail() {
		return email;
	}
}
	
