package shipping;

public class ShippingLabel {
	public int labelID;
	public String trackingNum;
	public byte[] label;

	public void printShippingLabel() 
	{
		//We don't actually need anything here to physically print the label, I think? I'll figure this out later.
	}
	public int getLabelId() 
	{
		return labelID;
	}
	public String getTrackingNum() 
	{
		return trackingNum;
	}
	public byte[] getLabel() 
	{
		return label;
	}


}
