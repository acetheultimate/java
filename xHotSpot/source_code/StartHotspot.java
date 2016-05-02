
import java.util.*;
public class StartHotspot extends xHotSpot
{
	protected String call(String Mode)
	{
		ExCmd exObj = new ExCmd();
		if(os_type == 1)
		{
			if(Mode=="Disallowed")
			{
				String cmd = "netsh wlan set hostednetwork mode=allow";
				ArrayList<String> output = exObj.execute(cmd);
			}
			String cmd = "netsh wlan start hostednetwork";
			ArrayList<String> output = exObj.execute(cmd);
			int status = 0;
			for(String i: output)
			{
				if(i.contains("couldn't be started")) status = 1;
				else if(i.contains("not recognized")) status = 2;
				else if(i.contains("administrator")) status = 3;
			}
			if(status == 1)
			{
				return "Please check the drivers or turn on the wifi adapter.\n If problem persist, follow help.";
			}
			else if(status == 2)
			{
				return "System path is not set or invalid";
			}
			else if(status == 3)
			{
				return "Please run program with admin privilages";
			}
			else
			{
				return "Started :)";
			}
		}
		return "Error";
	}
	
}
