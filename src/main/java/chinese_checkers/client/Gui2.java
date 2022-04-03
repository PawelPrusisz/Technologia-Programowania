package chinese_checkers.client;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.*;
/**
 * 
 * @author Szymon
 *
 */
public class Gui2 implements ActionListener
{
	JTextField state;
	PrintWriter out;
	JFrame jfrm;
	Gui2(Socket socket)		
	{		
		try
		{
			out = new PrintWriter(socket.getOutputStream());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		jfrm = new JFrame("Ready State");
		jfrm.setSize(250, 250);
		jfrm.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		state = new JTextField(10);
		state.setText("not ready");
		JPanel submitPane = new JPanel();
		JButton submitButton = new JButton("Ready");
		submitButton.addActionListener(this);
		submitPane.add(submitButton);
		jfrm.add(submitPane,BorderLayout.NORTH);
		
		JPanel outFieldPane= new JPanel();
		outFieldPane.add(new JLabel("You are"));
		outFieldPane.add(state);
		jfrm.add(outFieldPane,BorderLayout.AFTER_LAST_LINE);
		jfrm.setAlwaysOnTop(true);
		jfrm.setResizable(false);
		jfrm.setVisible(true);
	}
	public void exit()
	{
		jfrm.dispatchEvent(new WindowEvent(jfrm, WindowEvent.WINDOW_CLOSING));
	}
	/**
	 * return ready when readybutton is pressed
	 * @return skip word to server
	 */
	public void actionPerformed(ActionEvent e)
	{
			state.setText("ready");
	        ready();
	}
	/**
	 *
	 * @return "ready" to server
	 */
    public String ready()
    {
    	out.println("readyButton");
    	out.flush();
    	return "ready";
    }
}