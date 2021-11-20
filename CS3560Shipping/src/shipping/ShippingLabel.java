package shipping;

public class ShippingLabel {
	public int labelID;
	public String trackingNum;
	public String label;

	public void printShippingLabel() 
	{
		//We don't actually need anything here to physically print the label, I think? I'll figure this out later.
	}
	public int getLabelID() 
	{
		return labelID;
	}
	public String getTrackingNum() 
	{
		return trackingNum;
	}
	public String getLabel() 
	{
		return label;
	}


}
