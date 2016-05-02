
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.*;
public class StatusCheck extends xHotSpot
{
	protected Map<String, String> call()
	{
		Map<String, String> StatusDict = new HashMap<String, String>();
		ExCmd exObj = new ExCmd();
		if(os_type == 1)
		{
			String cmd = "netsh wlan show hostednetwork";
			ArrayList<String> output = exObj.execute(cmd);
			Pattern name_p = Pattern.compile("(SSID name).+:[ ](.+)");
			Pattern setting_p = Pattern.compile("(Setting).+:[ ](.+)");
			Pattern status_p = Pattern.compile("(Status).+:[ ](.+)");
			Pattern no_client_p = Pattern.compile("(Number).+:[ ](.+)");
			Pattern mode_p = Pattern.compile("(Mode).+:[ ](.+)");
			Pattern pass_p = Pattern.compile("(User security key      :)[ ](.+)");
			for(String i: output)
			{
				Matcher name_m = name_p.matcher(i);
				if(name_m.find())
				{
					StatusDict.put("SSID", name_m.group(2));
				}
				Matcher status_m = status_p.matcher(i);
				if(status_m.find())
				{
					StatusDict.put(status_m.group(1), status_m.group(2));
				}
				Matcher no_client_m = no_client_p.matcher(i);
				if(no_client_m.find())
				{
					StatusDict.put(no_client_m.group(1), no_client_m.group(2));
					int cur_index = (output.indexOf(i));
					int number = Integer.parseInt(no_client_m.group(2));
					for(int c=cur_index+1; c<=cur_index+number; c++)
					{
						StatusDict.put(String.valueOf(c-cur_index), output.get(c));
					}
				}
				Matcher setting_m = setting_p.matcher(i);
				if(setting_m.find())
				{
					StatusDict.put(setting_m.group(1),setting_m.group(2));
				}
				Matcher mode_m = mode_p.matcher(i);
				if(mode_m.find())
				{
					StatusDict.put(mode_m.group(1), mode_m.group(2));
				}
				
			}
			cmd = "netsh wlan show hostednetwork setting=security";
			output = exObj.execute(cmd);
			for(String i: output)
			{
				Matcher pass_m = pass_p.matcher(i);
				if(pass_m.find())
				{
					StatusDict.put("Password", pass_m.group(2));
				}
			}
		}
		return StatusDict;
	}
}
