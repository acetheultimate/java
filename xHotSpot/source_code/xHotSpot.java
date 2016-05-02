
import java.util.*;
public class xHotSpot
{
    //os_type: 1 for Windows, 2 for Linux, 3 for Android
    protected static int os_type;
    private StatusCheck show_stat;

    //game would happen here ;)
    xHotSpot()
    {

            //Checking os
            String os_name = System.getProperty("os.name").toLowerCase();
            String os_agent = System.getProperty("http.agent")==null ? "null" : System.getProperty("http.agent");
            if(os_name.contains("windows"))
            {
                    os_type = 1;
            }
            else if(os_name.contains("linux") && os_agent.toLowerCase().contains("android"))
            {
                    os_type = 2;
            }
            else
            {
                    os_type = 3;
            }
    }
    protected ArrayList<String> cmd_output(int operation)
    {
        ArrayList<String> output_array = new ArrayList<>();
        show_stat = new StatusCheck();
        Map<String, String> StatusDict;
        StatusDict = show_stat.call();
        xHotSpot mainObj = new xHotSpot();
        boolean on = StatusDict.get("Status").contains("Started");
        switch (operation)
        {
            case 1:
                if(on) output_array.add("It's On already.");
                else
                {
	
                    if("Not Available".equals(StatusDict.get("Setting")))
                    {
                        output_array.add("You haven't Set yet! Please Create a Hostednetwork first from the Menu");
                    }
                    else
                    {
                        StartHotspot start_obj = new StartHotspot();
                        String Mode = "null";
                        if(StatusDict.get("Mode").equals("Disallowed")) Mode = "Disallowed";
                        output_array.add(start_obj.call(Mode));
                    }
                }
                break;

            case 2:
                if(on)
                {
                    StopHotspot stop_obj = new StopHotspot();
                    output_array.add(stop_obj.call());
                }
                else output_array.add("It's already turned off.");
                break;

            case 3:
                String [] Attributes = {"SSID", "Mode", "Status", "Number", "Settings", "Password"};
                for(String i: Attributes)
                {
                    String p = StatusDict.get(i) == null ?  "null" : i;
                    if(!"null".equals(p))
                    {
						String key = i.length()<7 ? "\t"+i+"\t: " : "\t"+i+": ";
                        output_array.add(key+StatusDict.get(i));
                        StatusDict.remove(i);
                    }
                }
				output_array.add("\n");
                String[] user_key = StatusDict.keySet().toArray(new String[StatusDict.keySet().size()]);
                for(String i: user_key)
                {
                    output_array.add("         "+StatusDict.get(i));
                }
                break;

            default:
                break;
			
        }
        return output_array;
    }
	protected ArrayList<String> cmd_output(String cmd)
	{
		System.out.println("Got "+cmd+" in xHotSpot");
		ArrayList<String> output_array = new ArrayList<>();
		ExCmd exObj = new ExCmd();
		output_array = exObj.execute(cmd);
		return output_array;
	}
}
