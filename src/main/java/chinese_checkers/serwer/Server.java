package chinese_checkers.serwer;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import chinese_checkers.Exceptions.wrongNumberOfPlayersException;


/**
 * 
 * @author Pawel
 *
 */

public class Server extends Thread {
	
	ServerSocket serverSocket;
	ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();
	ExecutorService pool = Executors.newFixedThreadPool(10);
	ExecutorService listener = Executors.newFixedThreadPool(2);
	int numberOfClients = 0;
	boolean gameStarted = false;
	boolean allReady = false;
	Board gameBoard = null;
	int curMove = 0;
	int place = 1;
	boolean gameEnded = false;
	
	public static void main(String[] args)
	{
		new Server();
	}
	/**
	 * Constructor for Server class, initializing the IO stream for server and the game board	
	 */
	public Server()
	{
		//Initialize the server
		boolean serverinit = false;
		while(!serverinit)
		{
			try
			{
				serverSocket = new ServerSocket(8080);
				serverinit = true;
			}
			catch (IOException e)
			{
				try
				{
					Thread.sleep(6000);
				}
				catch (InterruptedException e1)
				{
					e1.printStackTrace();
				}
				System.err.println("socket occupied or null");
			}
		}
		//server initialized, now open listener thread 
		listener.execute(this);
		while(true)
		{
			if(allReady)
			{
				System.out.println("[Server] Trying to start the game...");
				try
				{
					gameBoard = new Board(numberOfClients);
					gameStarted = true;
					Random rand = new Random();
					curMove = rand.nextInt(numberOfClients) + 1;
					System.out.println("[Server] Succesfuly started a new game");
					for(ClientHandler c : clients)
					{
						c.SendToOne("Initialized with " + numberOfClients + " players");
						c.SendToOne("Game has Started! its player " + curMove + " turn");
					}
				}
				catch (wrongNumberOfPlayersException e)
				{
					allReady = false;
					System.err.println("[Server] Failed to start the game, wrong number of players");
					for(ClientHandler c : clients)
					{
						c.SendToOne("Cannot start the game, wrongNumberOfPlayersException");
						c.isReady = false;
						c.SendToOne("You are no longer Ready to start the game");
					}
				}
			}
			while(gameStarted)
			{
				//System.out.println("[Server] Game is runing, its player " + curMove + " turn");
				//System.out.println(numberOfClients);
				try
				{
					Thread.sleep(1000);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
			try
			{
				Thread.sleep(5000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			if(gameEnded)break;
		}
	}
	/**
	 * listening for clients connections 
	 */
	public void run()
	{
		while(true)
		{
			System.out.println("[Server] Waiting for client connection...");
			try
			{
				Socket client = serverSocket.accept();
				System.out.println("[Server] Client connected");
				numberOfClients++;
				if(numberOfClients > 6 || gameStarted)
				{
					client.close();
					numberOfClients = 6;
				}
				else
				{
					ClientHandler clientThread = new ClientHandler(client, this, numberOfClients);
					clients.add(clientThread);
					pool.execute(clientThread);
				}
			}
			catch (IOException e)
			{
				System.err.println("IOExeption error");
				e.printStackTrace();
			}
			
		}
	}
}














