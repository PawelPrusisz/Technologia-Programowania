package chinese_checkers.client;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.*;

import chinese_checkers.serwer.Board;
/**
 * Initializing user interface
 * @author Szymon
 *
 */
public class Gui implements ActionListener
{
	private PrintWriter out;
	private Socket socket;
	private JTextField mess;
	/**
	 * 
	 * @param socket client socket for sending the data between gui and client class
	 */
	Gui(Socket socket, int playerid)		
	{				
		try
		{
			out = new PrintWriter(socket.getOutputStream());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		   JFrame frame = new JFrame("Player " + playerid);
	       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	       frame.setSize(200,1000);
	       frame.setBounds(800,0, 550, 200);
		   JPanel submitPane = new JPanel();
	       JButton button = new JButton("Skip");	
	       button.addActionListener(this);
	       submitPane.add(button);
	       frame.add(submitPane,BorderLayout.AFTER_LAST_LINE);
	       mess = new JTextField("Witamy w grze! Powodzenia, daj z siebie wszytko :)                                ");
	       mess.setEditable(false);
	       
	       JPanel panel = new JPanel(); // the panel is not visible in output	        
	       panel.add(new JLabel("Server: \n"));
	       panel.add(mess);
	       panel.setVisible(true);
	       frame.add(panel);
	       frame.setVisible(true);
	       //frame.setResizable(false);
	       frame.setAlwaysOnTop(true);
	}
	/**
	 * return skip when skipp button is pressed
	 * @return skip word to server
	 */
    public String skip()
    {
    	out.println("skipButton");
    	out.flush();
    	return "skip";
    }
	/**
	 * Display text massage on the screen
	 * @param s massage
	 */
	public void text(String s)
	{
			mess.setText(s);
	}

	
	public void actionPerformed(ActionEvent e) {
		skip();
	}
}