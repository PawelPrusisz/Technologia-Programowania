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
public interface Rules {
	/**
	 * checking if the given movment is possible given the ruleset
	 * @param x
	 * 	initial x coordinate
	 * @param y
	 * 	initial y coordinate
	 * @param newX
	 *  new x coordinate
	 * @param newY
	 * 	new y coordinate
	 * @param playerId
	 * 	id of a player suposed to move
	 * @param gameBoard
	 * 	current state of a game 
	 * @return
	 * 	0 - if move failed;
	 * 	1 - if move was done and u cant do another move
	 *  2 - if player did a jump move and is able to do another jump
	 * @throws occupiedException
	 * 	if the tile to move is occupied
	 * @throws invalidMoveException
	 * 	if the move is invalid
	 * @throws outOfTheBoardException
	 * 	if the move goes out of the board
	 */
	public int movePiece(int x, int y, int newX, int newY, int playerId, Board gameBoard) throws occupiedException, invalidMoveException, outOfTheBoardException, cantGetOutOfTheJaillException;
	/**
	 * 
	 * @param playerId
	 * 	id of a player
	 * @param gameBoard
	 * 	current board state
	 * @return
	 * 	true if a player with given id has won the game, false otherwise
	 */
	public boolean hasWon(int playerId, Board gameBoard);
}
