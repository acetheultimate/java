
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
public class ExCmd
{
	protected ArrayList<String> execute(String cmd)
	{
		ArrayList<String> output = new ArrayList<String>();
		//process variable of type Process which is present in java.util.*
		Process process;
		try
		{
			process = Runtime.getRuntime().exec(cmd);
			// .waitFor() will wait for the execution of the command
			process.waitFor();
			//process.getInputStream() will give a chracter Stream of output
			//which is being stored in InputStreamReader type variable inStream
			
			InputStreamReader inStream = new InputStreamReader(process.getInputStream());
			
			//Extra, so that its easy to change in string for later processing
			BufferedReader outread = new BufferedReader(inStream);
			String line = "";
			while((line = outread.readLine())!= null)
			{
				output.add(line);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return output;
	}	
}