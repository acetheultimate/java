//GUI Wrapper for xHotSpot

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

//ArrayList contains array of string

import java.util.ArrayList;


public class xHotSpot_Main
{
    private JFrame MainFrame;
    private JPanel mainPanel;
    private JTextArea detail_text_area;
    private JTextField name_input;
	private JPasswordField pass_input;
	private JButton change_input;
	private JButton update_status;
	
	//previous name and password
	private String pre_name="";
	private String pre_pass="";
	
	//xHotSpot xObj;
    xHotSpot xObj = new xHotSpot();
    
    //Preparing GUI Skeleton
    private void Skeleton()
    {
        //Creating MainFrame
        MainFrame = new JFrame("xHotSpot");

        //Creating Tab Pane
        JTabbedPane main = new JTabbedPane();
		//margin_l, margin_t, width, height
        MainFrame.setBounds(500, 100, 300, 600);

        //Creating a panel for details
        mainPanel = new JPanel(null);
        //mainPanel.setBounds(0, 0, 100,0);

        //Label for HotSpot Name
        JLabel name_label = new JLabel("SSID Name");
        name_label.setBounds(30, 30, 65, 20);
        mainPanel.add(name_label);

        //Hotspot input field
        name_input.setBounds(100, 30, 100, 20);
        mainPanel.add(name_input);

        //Label for password
        JLabel pass_label = new JLabel("Password");
        pass_label.setBounds(30, 60, 65, 20);
        mainPanel.add(pass_label);

        //Password input box
        pass_input.setBounds(100, 60, 100, 20);
        pass_input.setEchoChar('|');
        mainPanel.add(pass_input);
        
        
        //edit button++
        change_input = new JButton("<html><p style='color:blue'>Show/Change</p></html>");
        change_input.setBounds(200, 60, 70, 20);
        change_input.setFont(new Font("Arial", Font.PLAIN, 10));
        change_input.setBorder(BorderFactory.createEmptyBorder());
        change_input.setContentAreaFilled(false);
        change_input.setFocusPainted(false);
        change_input.setOpaque(false);
        change_input.setActionCommand("Show/Change");
        change_input.addActionListener(new getOperation());
        mainPanel.add(change_input);
        
        //Start Button
        JButton start_button = new JButton("Start HotSpot");
        start_button.setBounds(90,120,120,20);
        start_button.setActionCommand("Start");
        start_button.addActionListener(new getOperation());
        mainPanel.add(start_button);
        
        //Stop Button
        JButton stop_button = new JButton("Stop HotSpot");
        stop_button.setBounds(90, 150, 120, 20);
        stop_button.setActionCommand("Stop");
        stop_button.addActionListener(new getOperation());
        mainPanel.add(stop_button);
		
		//Refresh Button
		JButton refresh_button = new JButton("Refresh");
		refresh_button.setBounds(90,180,120,20);
		refresh_button.setActionCommand("Refresh");
		refresh_button.addActionListener(new getOperation());
		mainPanel.add(refresh_button);
		
		//Update Status
		update_status = new JButton("<html><p style='color:blue'>Update Status</p></html>");
        update_status.setBounds(90, 460, 120, 20);
        update_status.setFont(new Font("Arial", Font.PLAIN, 10));
        update_status.setBorder(BorderFactory.createEmptyBorder());
        update_status.setContentAreaFilled(false);
        update_status.setFocusPainted(false);
        update_status.setOpaque(false);
        update_status.setActionCommand("Update");
        update_status.addActionListener(new getOperation());
        mainPanel.add(update_status);
        
		//About tab
		JPanel aboutPanel = new JPanel();
		final String about = "<html><center>xHotSpot</center><br>Submitted by:<br>Yogesh<br>Lokesh Singh Raghav</html>";
		JLabel about_info = new JLabel(about, JLabel.CENTER);
		aboutPanel.add(about_info);
		
        //Jottting down
        main.addTab("Main", null, mainPanel, "xHotSpot Details");
		main.addTab("About", null, aboutPanel,"About us");
        MainFrame.add(main);
        MainFrame.setVisible(true);
        MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    protected void put_output()
    {
        detail_text_area = new JTextArea(5,20);
        detail_text_area.setEditable(false);
        detail_text_area.setBounds(0,230,300,230);
        detail_text_area.setText("Welcome :)");
        mainPanel.add(detail_text_area);
    }
    
    private int readOutput(int cmd, int mode)
    {
		ArrayList<String> output_array;
		if(cmd==4)
		{
			String name = name_input.getText();
			String pass = new String(pass_input.getPassword());
			String CMD = "";
			if(!pre_name.equals(name) && !pre_pass.equals(pass)) CMD = "netsh wlan set hostednetwork ssid=\""+name+"\" key=\""+pass+"\"";
			else if(!pre_name.equals(name)) CMD = "netsh wlan set hostednetwork ssid=\""+name+"\"";
			else if(!pre_pass.equals(pass)) CMD = "netsh wlan set hostednetwork key=\""+pass+"\"";
			detail_text_area.setText("Changed the details re-starting the hotspot...");
			xObj.cmd_output(CMD);
			this.readOutput(1,1);
			pre_name = name;
			pre_pass = pass;
			return 0;
		}
		cmd = cmd == 0 ? 1 : cmd;
        output_array = xObj.cmd_output(cmd);
        String output_string = "";
        for(String i: output_array)
        {
			if(i.contains("Password"))
			{
				continue;
			}
            output_string += i+"\n";
        }
		if(mode==1)
		{
			detail_text_area.setText(detail_text_area.getText()+"\n"+output_string);
		}
		return 0;
    }
    public static void main(String[] args)
    {
        xHotSpot_Main obj = new xHotSpot_Main();
        obj.Skeleton();
        obj.put_output();
    }
    
    xHotSpot_Main()
    {
		ArrayList<String> pre_output;
		pre_output = xObj.cmd_output(3);
		String name="";
		String pass="";
		for(String i: pre_output)
		{
			if(i.contains("SSID"))
			{
				name = i.split(" ")[1].split("\"")[1];
			}
			if(i.contains("Password"))
			{
				pass = i.split(" ")[1];
			}
		}
		pre_name = name;
		pre_pass = pass;
		name_input = new JTextField(name);
        pass_input = new JPasswordField(pass);
    }

   private class getOperation implements ActionListener
    {
        //@Override
        public void actionPerformed(ActionEvent e)
        {
            String output = e.getActionCommand();
			String name = name_input.getText();
			String pass = new String(pass_input.getPassword());
			int sure = -1;
			String msg = "";
            switch (output)
            {
				case "Hide":
					pass_input.setEchoChar('|');
					change_input.setText("<html><p style='color:blue'>Show/Change</p></html>");
					change_input.setActionCommand("Show/Change");
					break;
                case "Show/Change":
					detail_text_area.setText("Editing...");
					pass_input.setEchoChar((char)0);
					name_input.setEditable(true);
					pass_input.setEditable(true);
					change_input.setText("<html><p style='color:blue'>Hide</p></html>");
					change_input.setActionCommand("Hide");
                    readOutput(3,1);
                    break;
                case "Start":
					if(pre_name.equals(name) && pre_pass.equals(pass))
					{
						detail_text_area.setText("Turning Hotspot on...");
						sure = 0;
					}
					else
					{
						if(!pre_name.equals(name) && !pre_pass.equals(pass)) msg = "Start hotspot with changed name and password?";
						else if(!pre_name.equals(name)) msg = "Start hotspot with changed name?";
						else if(!pre_pass.equals(pass)) msg = "Start hotspot with changed password?";
					}
					
					if(sure == -1){ sure = JOptionPane.showConfirmDialog(MainFrame, msg, "xHotSpot", JOptionPane.YES_NO_OPTION); sure += 4; }
					
					if(sure == 0 || sure == 4)
					{
						change_input.setText("<html><p style='color:blue'>Show/Change</p></html>");
						change_input.setActionCommand("Show/Change");
						name_input.setEditable(false);
						pass_input.setEditable(false);
						pass_input.setEchoChar('|');
						readOutput(sure,1);
						readOutput(3,1);
					}
					else System.out.println("Aborting the mission ;)");
					
                    break;
                case "Stop":
                    detail_text_area.setText("Turning Hotspot off...");
					name_input.setEditable(true);
					pass_input.setEditable(true);
                    readOutput(2,1);
                    break;
				case "Refresh":
					detail_text_area.setText("Refreshing the hotspot...");
					name_input.setEditable(false);
					pass_input.setEditable(false);
					readOutput(2,0);
					readOutput(1,0);
					detail_text_area.setText(detail_text_area.getText()+"\nNetwork Refreshed!");
					readOutput(3,1);
					break;
				case "Update":
					detail_text_area.setText("Refreshing the status...");
					readOutput(3,1);
					break;
                default:
                    break;
            }
        }
    }
}
