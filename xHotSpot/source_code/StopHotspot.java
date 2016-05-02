
import java.util.*;
public class StopHotspot extends xHotSpot
{
	protected String call()
	{
		ExCmd exObj = new ExCmd();
		if(os_type == 1)
		{
			String cmd = "netsh wlan stop hostednetwork";
			ArrayList<String> output = exObj.execute(cmd);
			for(String i: output)
			{
				return i;
			}
		}
		return "error";
	}
}
