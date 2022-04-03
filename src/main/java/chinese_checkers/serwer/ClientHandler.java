package chinese_checkers.serwer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import chinese_checkers.Exceptions.cantGetOutOfTheJaillException;
import chinese_checkers.Exceptions.invalidMoveException;
import chinese_checkers.Exceptions.occupiedException;
import chinese_checkers.Exceptions.outOfTheBoardException;
/**
 * 
 * @author Pawel
 * Adapter for clients input into server commands
 */
public class ClientHandler extends Thread{
	Server server;
	Socket client;
	BufferedReader in;
	PrintWriter out;
	final int delay = 60;
	boolean isReady = false;
	boolean hasWon = false;
	int clientId;
	boolean jumpMove = false;
	public ClientHandler(Socket client, Server server, int id)
	{
		this.client = client;
		this.server = server;
		this.clientId = id;
	}
	/**
	 * sends string message to all clients
	 * @param text
	 * 	text to send
	 */
	public void SendToAll(String text)
	{
		for(ClientHandler c : server.clients)
		{
			c.SendToOne(text);
		}
	}
	/**
	 * sends string to current client
	 * @param text
	 */
	public void SendToOne(String text)
	{
		out.println(text);
		out.flush();
	}
	/**
	 * method for listening inputs from client on diffrent thread
	 */
	public void run()
	{
		try
		{
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintWriter(client.getOutputStream());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		SendToOne("Welcome new user, your id is " + clientId);
		while(true)
		{
			try
			{
				if(in.ready())
				{
					String input = in.readLine();
					System.out.println("dosta³em wiadomosc od klienta: " + input);
					if(input.startsWith("ALL"))
					{
						SendToAll("Player with id: " + clientId + " says:" + input.substring(input.indexOf(" ")));
					}
					else if(input.startsWith("quit"))
					{
						System.out.println("[Server] Player has left");
						System.out.println("[Server] Current number of players: " + server.numberOfClients);
						server.clients.remove(clientId - 1);
						int i = 1;
						for(ClientHandler c : server.clients)
						{
							c.clientId = i;
							c.SendToOne("Player has left, your new Id is: " + i);
							i++;
						}
						server.numberOfClients = i - 1;
						System.out.println("[Server] Uppdated number of players: " + server.numberOfClients);
					}
					else if(input.startsWith("ready"))
					{
						isReady = true;
						SendToOne("You are now Ready to start the game");
						boolean ready = true;
						for(ClientHandler c : server.clients)
						{
							if(!c.isReady)ready = false;
						}
						if(ready)server.allReady = true;
					}
					else if(input.startsWith("skip"))
					{
						if(server.curMove == clientId)
						{
							SendToOne("Player has skipped his turn");
							nextPlayerMove();
						}
						else
						{
							SendToOne("Cant skip, its not your move");
						}
					}
					else if(input.startsWith("move") || input.startsWith("mouse"))
					{
						if(!server.gameStarted)
						{
							SendToOne("The game havent started yet, sorry :(");
						}
						else
						{
							if(clientId != server.curMove)
							{
								SendToOne("Sorry, its not your turn to move");
							}
							else if(hasWon)
							{
								SendToOne("Cant move, you allready won");
							}
							else
							{
								int index = input.indexOf(" ") + 1;
								String data = input.substring(index);
								String[] values = data.split(" ");
								try
								{
									int x = Integer.parseInt(values[0]);
									int y = Integer.parseInt(values[1]);
									int newX = Integer.parseInt(values[2]);
									int newY = Integer.parseInt(values[3]);
									int playerId = clientId;
									try
									{
										int val = server.gameBoard.movePiece(x, y, newX, newY, playerId);
										SendToAll("move " + x + " " + y + " " + newX + " " + newY + " " + playerId);
										if(val == 2)jumpMove = true;
										if(server.gameBoard.hasWon(playerId))
										{
											hasWon = true;
											SendToAll("player " + playerId + " has won the game as " + server.place + " player");
											server.place++;
										}
										if(!jumpMove)
										{
											nextPlayerMove();
										}
										else
										{
											SendToOne("You can jump again with your piece, if another jump is possible, if not then skip your move");
											jumpMove = false;
										}
									}
									catch (occupiedException e)
									{
										SendToOne("The tile u try to move is allready occupied by another player");
									}
									catch (invalidMoveException e)
									{
										SendToOne("Your move is against current game rules");
									}
									catch (outOfTheBoardException e)
									{
										SendToOne("Your move goes out of the board");
									}
									catch (cantGetOutOfTheJaillException e)
									{
										SendToOne("Cannot move your piece out of the winning squares");
									}
								}
								catch (Exception e)
								{
									SendToOne("Wrong format of date");
									e.printStackTrace();
								}
								
							}
						}
					}
					else
					{
						SendToOne(input + " - cant respond to that");
					}
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
	}
	/**
	 * Checking with player should move next
	 * if no valid player is to be chosen, the game ends
	 */
	public void nextPlayerMove()
	{
		boolean allWon = true;
		for(ClientHandler c : server.clients)
		{
			if(!c.hasWon)allWon = false;
		}
		if(allWon)
		{
			SendToAll("Game ended, thanks for playing");
			server.gameEnded = true;
		}
		else
		{
			while(true)
			{
				server.curMove = (server.curMove%server.numberOfClients) + 1;
				boolean ok = false;
				for(ClientHandler c : server.clients)
				{
					if(c.clientId == server.curMove && !c.hasWon)ok = true;
				}
				if(ok)break;
			}
			SendToAll("player " + clientId + " have moved, now its player " + server.curMove + " move" );
		}
	}
	
}
