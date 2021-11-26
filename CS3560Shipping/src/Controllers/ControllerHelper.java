package Controllers;

import java.util.ArrayList;
import java.util.HashMap;

public class ControllerHelper 
{
	protected static HashMap<String, Object> getCorrectObjectBasedOnId(String idName, int id, ArrayList<HashMap<String, Object>> list)
	{
		for(int i = 0; i < list.size(); i++)
		{
			if((int)list.get(i).get(idName) == id)
			{
				return list.get(i);
			}
		}
		return null;
	}
}
