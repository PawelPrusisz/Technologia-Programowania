package chinese_checkers.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import chinese_checkers.Exceptions.cantGetOutOfTheJaillException;
import chinese_checkers.Exceptions.invalidMoveException;
import chinese_checkers.Exceptions.occupiedException;
import chinese_checkers.Exceptions.outOfTheBoardException;
import chinese_checkers.Exceptions.wrongNumberOfPlayersException;
import chinese_checkers.serwer.Board;
/**
 * 
 * @author Pawel
 *
 */
public class Client extends Thread {
	Socket socket;
	BufferedReader in;
	BufferedReader keyboard;
	PrintWriter out;
	ExecutorService listener = Executors.newFixedThreadPool(2);
	Board gameBoard = null;
	Draw screen;
	int numberOfPlayers;
	int playerid;
	boolean gameInit = false;
	boolean gameStarted = false;
	boolean ok = true;
	Gui gui;
	boolean prawda = false;
	public static void main(String[] args)
	{
		new Client();
	}
	/**
	 * constructor of client initializes threads for listening for IO stream and drawing the game on client screen
	 */
	public Client()
	{
		try
		{
			socket = new Socket("localhost", 8080);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
			keyboard = new BufferedReader(new InputStreamReader(System.in));
			listener.execute(this);
			Gui2 gui2 = new Gui2(socket);
			while(true)
			{
				if(gameInit && ok)
				{
					try
					{
						gameBoard = new Board(numberOfPlayers);
					}
					catch (wrongNumberOfPlayersException e)
					{
						e.printStackTrace();
					}
					screen = new Draw(gameBoard, socket, playerid);
					gui2.exit();
					ok = false;
					gui = new Gui(socket, playerid);
					prawda = true;
				}
				while(gameStarted)
				{
					screen.getCurrentBoard(gameBoard);
					try
					{
						Thread.sleep(200);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
				try
				{
					Thread.sleep(100);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
 	}
	/**
	 * run method for listening for input from server or keyboard and sending it to server
	 */
	public void run()
	{
		while(true)
		{
			try
			{
				String input;
				String typed;
				if(in.ready())
				{
					input = in.readLine();
					boolean tmp = true;
					if(input.startsWith("move"))
					{
						tmp = false;
						int index = input.indexOf(" ") + 1;
						String data = input.substring(index);
						String[] values = data.split(" ");
						for(int i = 0; i < values.length; i++)System.out.println(i+ " " + values[i]);
						int x = Integer.parseInt(values[0]);
						int y = Integer.parseInt(values[1]);
						int newX = Integer.parseInt(values[2]);
						int newY = Integer.parseInt(values[3]);
						int playerId = Integer.parseInt(values[4]);
						try
						{
							gameBoard.movePiece(x, y, newX, newY, playerId);
						}
						catch (occupiedException e)
						{
							e.printStackTrace();
						}
						catch (invalidMoveException e)
						{
							e.printStackTrace();
						}
						catch (outOfTheBoardException e)
						{
							e.printStackTrace();
						}
						catch (cantGetOutOfTheJaillException e)
						{
							e.printStackTrace();
						}
					}
					else if(input.startsWith("mouse"))
					{
						tmp = false;
						input = "move" + input.substring(input.indexOf(" "));
					}
					else if(input.startsWith("Game has Started"))
					{
						gameStarted = true;
					}
					else if(input.startsWith("readyButton"))
					{
						out.println("ready");
						out.flush();
					}
					else if(input.startsWith("Welcome new user"))
					{
						this.playerid = Integer.parseInt(input.substring(29));
					}
					else if(input.startsWith("Initialized with "))
					{
						String[] arr = input.split(" ");
						//for(int i = 0; i < arr.length; i++)System.out.println(arr[i]);
						numberOfPlayers = Integer.parseInt(arr[2]);
						gameInit = true;
					}
					if(prawda && tmp)
					{
						gui.text(input);
					}
					System.out.println(input);
				}
				if(keyboard.ready())
				{
					 typed = keyboard.readLine();
					 out.println(typed);
					 out.flush();
					 if(typed.equals("quit"))break;
				}
				
				Thread.sleep(10);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		try
		{
			in.close();
			out.close();
			keyboard.close();
			socket.close();
			listener.shutdown();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}