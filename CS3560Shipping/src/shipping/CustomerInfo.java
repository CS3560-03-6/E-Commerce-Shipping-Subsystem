package shipping;

import java.util.ArrayList;
import java.util.HashMap;

import Utility.ConnectionFactory;

public class CustomerInfo
{
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

	public CustomerInfo(int customerInformationId)
	{
		ArrayList<HashMap<String, Object>> cust_info = ConnectionFactory.createCustomerInfoConnection().GetCustomerInfo(customerInformationId);
		this.customerInformationId = customerInformationId;
		this.firstName = (String) cust_info.get(0).get("firstName");
		this.lastName = (String) cust_info.get(0).get("lastName");
		this.address = (String) cust_info.get(0).get("address");
		this.phoneNumber = (String) cust_info.get(0).get("phoneNum");
		this.email = (String) cust_info.get(0).get("email");
	}

	public String[] getCustomerName()
	{
		String[] full_name = new String[2];
		full_name[0] = firstName;
		full_name[1] = lastName;

		return full_name;
	}

	public int getCustomerID()
	{
		return customerInformationId;
	}

	public String getAddress()
	{
		return address;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public String getEmail()
	{
		return email;
	}
}
