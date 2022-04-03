package chinese_checkers.serwer;

import chinese_checkers.Exceptions.cantGetOutOfTheJaillException;
import chinese_checkers.Exceptions.invalidMoveException;
import chinese_checkers.Exceptions.occupiedException;
import chinese_checkers.Exceptions.outOfTheBoardException;
/**
 * 
 * @author Pawel
 *
 */
public class DefaultRules implements Rules {
	/**
	 * x-size of the board
	 */
	private int xSize;
	/**
	 * y-size of the board
	 */
	private int ySize;
	/**
	 * number of pieces each player has
	 */
	private int numberOfPieces;
	/**
	 * 
	 * @param x
	 * 	x-size of the board
	 * @param y
	 *  y-size of the board
	 */
	public DefaultRules(int x, int y, int numberOfPieces)
	{
		this.xSize = x;
		this.ySize = y;
		this.numberOfPieces = numberOfPieces;
	}
	/**
	 * returns 1 if its a basic move
	 * and 2 if its a jump move
	 */
	public int movePiece(int x, int y, int newX, int newY, int playerId, Board gameBoard) throws occupiedException, invalidMoveException, outOfTheBoardException, cantGetOutOfTheJaillException
	{
		boolean notJump = true;
		if(playerId == gameBoard.lastPlayerId && gameBoard.lastOutcome == 2)
		{
			if(x == gameBoard.lastX && y == gameBoard.lastY)
			{
				notJump = false;
			}
			else
			{
				throw new invalidMoveException();
			}
		}
		if(newX > xSize || newY > ySize)throw new outOfTheBoardException();
		if(!gameBoard.gameBoard[newY][newX].playable())throw new outOfTheBoardException();
		if(!gameBoard.gameBoard[newY][newX].isEmpty())throw new occupiedException();
		if(gameBoard.gameBoard[y][x].getPlayerId() != playerId)throw new invalidMoveException();
		if(gameBoard.gameBoard[y][x].isWinning() && gameBoard.gameBoard[newY][newX].getWinningId() != playerId)throw new cantGetOutOfTheJaillException();
		int dX = newX - x;
		int dY = newY - y;
		/*
		 * moves are diffrend base on the row, check that first
		 * allowed moves like:  x+1, x-1, y+1,     y-1,     y-1 x-1, y+1 x-1
		 * and jump moves like: x+2, x-2, y+2 x+1, y-2 x+1, y-2 x-1, y+2 x-1  
		 * for jump moves check if corresponding tile is occupied
		 */
		if(y%2 == 0)
		{
			if(dX == 1 && dY == 0 && notJump)//x+1
			{
				return 1;
			}
			else if(x+1 <= xSize && gameBoard.gameBoard[y][x+1].playable() && !gameBoard.gameBoard[y][x+1].isEmpty() && dX == 2 && dY == 0)//jump x+2
			{
				
				return 2;
			}
			else if(dX == -1 && dY == 0 && notJump)//x-1
			{
				return 1;
			}
			else if(x-1 >= 0 && gameBoard.gameBoard[y][x-1].playable() && !gameBoard.gameBoard[y][x-1].isEmpty() && dX == -2 && dY == 0)//jump x-2
			{
				return 2;
			}
			else if(dY == 1 && dX == 0 && notJump)//y+1
			{
				return 1;
			}
			else if(y+1 <= ySize && gameBoard.gameBoard[y+1][x].playable() && !gameBoard.gameBoard[y+1][x].isEmpty() && dX == 1 && dY == 2)//jump y+2 x+1
			{
				return 2;
			}
			else if(dY == -1 && dX == 0 && notJump)//y-1
			{
				return 1;
			}
			else if(y-1 >= 0 && gameBoard.gameBoard[y-1][x].playable() && !gameBoard.gameBoard[y-1][x].isEmpty() && dX == 1 && dY == -2)//jump y-2 x+1
			{
				return 2;
			}
			else if((dX == -1) && (dY == -1) && notJump)//y-1 x-1
			{
				return 1;
			}
			else if(y-1 >= 0 && x-1 >= 0 && gameBoard.gameBoard[y-1][x-1].playable() && !gameBoard.gameBoard[y-1][x-1].isEmpty() && dX == -1 && dY == -2)//jump y-2 x-1
			{
				return 2;
			}
			else if((dX == -1) && (dY == 1) && notJump)//y+1 x-1
			{
				return 1;
			}
			else if(y+1 <= ySize && x-1 >= 0 && gameBoard.gameBoard[y+1][x-1].playable() && !gameBoard.gameBoard[y+1][x-1].isEmpty() && dX == -1 && dY == 2)//jump y+2 x-1
			{
				return 2;
			}
			
			
			
		}
		/*
		 * allowed moves like:  x+1, x-1, y+1,     y-1,     y-1 x+1, y+1 x+1 
		 * and jump moves like: x+2, x-2, y+2 x-1, y-2 x-1, y-2 x+1, y+2 x+1
		 * for jump moves check if corresponding tile is occupied
		 */
		else if(y%2 == 1)
		{
			if(dX == 1 && dY == 0 && notJump)//x+1
			{
				return 1;
			}
			else if(x+1 <= xSize && gameBoard.gameBoard[y][x+1].playable() && !gameBoard.gameBoard[y][x+1].isEmpty() && dX == 2 && dY == 0)//jump x+2
			{
				return 2;
			}
			else if(dX == -1 && dY == 0 && notJump)//x-1
			{
				return 1;
			}
			else if(x-1 >= 0 && gameBoard.gameBoard[y][x-1].playable() && !gameBoard.gameBoard[y][x-1].isEmpty() && dX == -2 && dY == 0)//jump x-2
			{
				return 2;
			}
			else if(dY == 1 && dX == 0 && notJump)//y+1
			{
				return 1;
			}
			else if(y+1 <= ySize && gameBoard.gameBoard[y+1][x].playable() && !gameBoard.gameBoard[y+1][x].isEmpty() && dX == -1 && dY == 2)//jump y+2 x-1
			{
				return 2;
			}
			else if(dY == -1 && dX == 0 && notJump)//y-1
			{
				return 1;
			}
			else if(y-1 >= 0 && gameBoard.gameBoard[y-1][x].playable() && !gameBoard.gameBoard[y-1][x].isEmpty() && dX == -1 && dY == -2)//jump y-2 x-1
			{
				return 2;
			}
			else if((dX == 1) && (dY == -1) && notJump)//y-1 x+1
			{
				return 1;
			}
			else if(y-1 >= 0 && x+1<=xSize && gameBoard.gameBoard[y-1][x+1].playable() && !gameBoard.gameBoard[y-1][x+1].isEmpty() && dX == +1 && dY == -2)//jump y-2 x+1
			{
				return 2;
			}
			else if((dX == 1) && (dY == 1) && notJump)//y+1 x+1
			{
				return 1;
			}
			else if(y+1 <= ySize && x+1 <= xSize &&gameBoard.gameBoard[y+1][x+1].playable() && !gameBoard.gameBoard[y+1][x+1].isEmpty() && dX == 1 && dY == 2)//jump y+2 x+1
			{
				return 2;
			}
		}
		
		throw new invalidMoveException();
	}

	public boolean hasWon(int playerId, Board gameBoard) {
		int count = 0;
		for(int i = 0; i < ySize; i++)
		{
			for(int j = 0; j < xSize; j++)
			{
				if(gameBoard.gameBoard[i][j].getWinningId() == playerId && gameBoard.gameBoard[i][j].getPlayerId() == playerId)
				{
					count++;
				}
			}
		}
		if(count == this.numberOfPieces)return true;
		return false;
	}
}
