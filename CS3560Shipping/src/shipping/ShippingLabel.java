package shipping;

public class ShippingLabel {
	public int labelId;
	public String trackingNum;
	public byte[] label;

	public ShippingLabel(int labelId, String trackingNum, byte[] label)
	{
		this.labelId = labelId;
		this.trackingNum = trackingNum;
		this.label = label;
	}
	public int getLabelId() 
	{
		return labelId;
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
