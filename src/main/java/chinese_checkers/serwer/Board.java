package chinese_checkers.serwer;

import chinese_checkers.Exceptions.cantGetOutOfTheJaillException;
import chinese_checkers.Exceptions.invalidMoveException;
import chinese_checkers.Exceptions.occupiedException;
import chinese_checkers.Exceptions.outOfTheBoardException;
import chinese_checkers.Exceptions.wrongNumberOfPlayersException;

/**
 * 
 * @author Pawel
 *
 */
public class Board {
	private final int n = -1;
	private int xSize;
	private int ySize;
	public int lastX = -1;
	public int lastY = -1;
	public int lastPlayerId = -1;
	public int lastOutcome = -1;
	
	private Rules curentRule;
	/**
	 * board with players' pices
	 */
	public Tile[][] gameBoard;
	private int numberOfPlayers;
	private Player[] playerList;
	/**
	 * 
	 * @param numberOfPlayers
	 * 	number of players playing on the board. can be only 2, 3, 4 or 6
	 * @throws wrongNumberOfPlayersException
	 * 	if number pf players is not 2, 3, 4 or 6 throws an exception
	 */
	public Board(int numberOfPlayers) throws wrongNumberOfPlayersException
	{
		xSize = 12;
		ySize = 16;
		curentRule = new DefaultRules(this.xSize, this.ySize, 10);
		if(numberOfPlayers != 2 && numberOfPlayers != 3 && numberOfPlayers != 4 && numberOfPlayers != 6)
		{
			throw new wrongNumberOfPlayersException();
		}
		Player noPlayer = new Player(-1);
		Player emptyPlayer = new Player(0);
		this.playerList = new Player[7];
		for(int i = 1; i <= numberOfPlayers; i++)
		{
			this.playerList[i] = new Player(i);
		}
		for(int i = numberOfPlayers+1; i <= 6; i++)
		{
			this.playerList[i] = new Player(i);
		}
		int[][] board = {   {n,n,n,n,n,n,1,n,n,n,n,n,n},
							{n,n,n,n,n,1,1,n,n,n,n,n,n},
							{n,n,n,n,n,1,1,1,n,n,n,n,n},
							{n,n,n,n,1,1,1,1,n,n,n,n,n},
							{6,6,6,6,0,0,0,0,0,2,2,2,2},
							{6,6,6,0,0,0,0,0,0,2,2,2,n},
							{n,6,6,0,0,0,0,0,0,0,2,2,n},
							{n,6,0,0,0,0,0,0,0,0,2,n,n},
							{n,n,0,0,0,0,0,0,0,0,0,n,n},
							{n,5,0,0,0,0,0,0,0,0,3,n,n},
							{n,5,5,0,0,0,0,0,0,0,3,3,n},
							{5,5,5,0,0,0,0,0,0,3,3,3,n},
							{5,5,5,5,0,0,0,0,0,3,3,3,3},
							{n,n,n,n,4,4,4,4,n,n,n,n,n},
							{n,n,n,n,n,4,4,4,n,n,n,n,n},
							{n,n,n,n,n,4,4,n,n,n,n,n,n},
							{n,n,n,n,n,n,4,n,n,n,n,n,n},
														};
		this.numberOfPlayers = numberOfPlayers;
		this.gameBoard = new Tile[17][13];
		for(int i = 0; i < 17; i++)
		{
			for(int j = 0; j < 13; j++)
			{
				if(numberOfPlayers == 2)
				{
					if(board[i][j] == 1)
					{
						this.gameBoard[i][j] = new Tile(j, i, playerList[1], 2);
					}
					else if (board[i][j] == 4)
					{
						this.gameBoard[i][j] = new Tile(j, i, playerList[2], 1);
					}
					else if (board[i][j] == -1)
					{
						this.gameBoard[i][j] = new Tile(j, i, noPlayer, -1);
					}
					else
					{
						this.gameBoard[i][j] = new Tile(j, i, emptyPlayer, 0);
					}
				}
				else if(numberOfPlayers == 3)
				{
					if(board[i][j] == 1)
					{
						this.gameBoard[i][j] = new Tile(j, i, playerList[1], 0);
					}
					else if (board[i][j] == 2)
					{
						this.gameBoard[i][j] = new Tile(j, i, emptyPlayer, 3);
					}
					else if (board[i][j] == 3)
					{
						this.gameBoard[i][j] = new Tile(j, i, playerList[2], 0);
					}
					else if (board[i][j] == 4)
					{
						this.gameBoard[i][j] = new Tile(j, i, emptyPlayer, 1);
					}
					else if (board[i][j] == 5)
					{
						this.gameBoard[i][j] = new Tile(j, i, playerList[3], 0);
					}
					else if (board[i][j] == 6)
					{
						this.gameBoard[i][j] = new Tile(j, i, emptyPlayer, 2);
					}
					else if (board[i][j] == -1)
					{
						this.gameBoard[i][j] = new Tile(j, i, noPlayer, -1);
					}
					else
					{
						this.gameBoard[i][j] = new Tile(j, i, emptyPlayer, 0);
					}
				}
				else if(numberOfPlayers == 4)
				{
					if(board[i][j] == 1)
					{
						this.gameBoard[i][j] = new Tile(j, i, playerList[1], 3);
					}
					else if (board[i][j] == 2)
					{
						this.gameBoard[i][j] = new Tile(j, i, playerList[2], 4);
					}
					else if (board[i][j] == 4)
					{
						this.gameBoard[i][j] = new Tile(j, i, playerList[3], 1);
					}
					else if (board[i][j] == 5)
					{
						this.gameBoard[i][j] = new Tile(j, i, playerList[4], 2);
					}
					else if (board[i][j] == -1)
					{
						this.gameBoard[i][j] = new Tile(j, i, noPlayer, -1);
					}
					else
					{
						this.gameBoard[i][j] = new Tile(j, i, emptyPlayer, 0);
					}
				}
				else if(numberOfPlayers == 6)
				{
					if(board[i][j] == 1)
					{
						this.gameBoard[i][j] = new Tile(j, i, playerList[1], 4);
					}
					else if (board[i][j] == 2)
					{
						this.gameBoard[i][j] = new Tile(j, i, playerList[2], 5);
					}
					else if (board[i][j] == 3)
					{
						this.gameBoard[i][j] = new Tile(j, i, playerList[3], 6);
					}
					else if (board[i][j] == 4)
					{
						this.gameBoard[i][j] = new Tile(j, i, playerList[4], 1);	
					}
					else if (board[i][j] == 5)
					{
						this.gameBoard[i][j] = new Tile(j, i, playerList[5], 2);
					}
					else if (board[i][j] == 6)
					{
						this.gameBoard[i][j] = new Tile(j, i, playerList[6], 3);
					}
					else if (board[i][j] == -1)
					{
						this.gameBoard[i][j] = new Tile(j, i, noPlayer, -1);
					}
					else
					{
						this.gameBoard[i][j] = new Tile(j, i, emptyPlayer, 0);
					}
				}
				else
				{
					throw new wrongNumberOfPlayersException();
				}
			}
		}
	}
	/**
	 * constructor of Board class
	 * @param boardSize
	 * 	size of the board
	 * @param players
	 * 	number of players
	 */
	public Board(int boardSize, int numberOfPlayers)
	{
		//TODO: implement constructor for difrent boards and games 
	}
	/**
	 * Moves a piece from one tile to another
	 * @param x
	 * 	x-coordinate of a tile 
	 * @param y
	 * 	y-coordinate of a tile
	 * @param newX
	 * 	x-coordinate of a tile to move
	 * @param newY
	 * 	y-coordiante of a tile to move
	 * @return
	 * 	true if a move was valid and compleated, false otherwise
	 */
	public int movePiece(int x, int y, int newX, int newY, int playerId) throws occupiedException, invalidMoveException, outOfTheBoardException, cantGetOutOfTheJaillException
	{
		int pId;
		int outcome = 0;
		try
		{
			outcome = this.curentRule.movePiece(x, y, newX, newY, playerId, this);
			pId =  this.gameBoard[newY][newX].getPlayerId();
			this.gameBoard[newY][newX].setPlayerId(gameBoard[y][x].getPlayerId());
			this.gameBoard[y][x].setPlayerId(pId);
			this.lastX = newX;
			this.lastY = newY;
			this.lastOutcome = outcome;
			this.lastPlayerId = playerId;
			return outcome;
			
		}
		catch (occupiedException e)
		{
			throw new occupiedException();
		}
		catch (invalidMoveException e)
		{
			throw new invalidMoveException();
		}
		catch (outOfTheBoardException e)
		{
			throw new outOfTheBoardException();
		}
		catch (cantGetOutOfTheJaillException e)
		{
			throw new cantGetOutOfTheJaillException();
		}
		
	}
	/**
	 * 
	 * @param playerId
	 * 	id of a player
	 * @return
	 * 	true if player with given id has won, false otherwise
	 */
	public boolean hasWon(int playerId)
	{
		return this.curentRule.hasWon(playerId, this);
	}
	public int getXSize()
	{
		return this.xSize;
	}
	public int getYSize()
	{
		return this.ySize;
	}
	public Tile[][] getBoard()
	{
		return this.gameBoard;
	}
	public Tile findTile(int x, int y)
	{
		return this.gameBoard[y][x];
	}
	public int getNumberOfPlayers()
	{
		return this.numberOfPlayers;
	}
}
