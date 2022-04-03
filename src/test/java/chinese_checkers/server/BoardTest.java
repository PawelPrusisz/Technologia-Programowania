package chinese_checkers.server;

import static org.junit.Assert.*;

import org.junit.Test;

import chinese_checkers.Exceptions.cantGetOutOfTheJaillException;
import chinese_checkers.Exceptions.invalidMoveException;
import chinese_checkers.Exceptions.occupiedException;
import chinese_checkers.Exceptions.outOfTheBoardException;
import chinese_checkers.Exceptions.wrongNumberOfPlayersException;
import chinese_checkers.serwer.Board;
import chinese_checkers.serwer.Tile;
/**
 * 
 * @author Pawel
 *
 */
public class BoardTest {
	/*
	 * tests related to number of players
	 */
	@Test
	public void testNumberOfPlayers() throws wrongNumberOfPlayersException {
		Board gameBoard = new Board(6);
		assertEquals(6, gameBoard.getNumberOfPlayers());
	}
	@Test(expected = wrongNumberOfPlayersException.class)
	public void testWrongNumberOfPlayersException1() throws wrongNumberOfPlayersException
	{
		Board gameBoard = new Board(5);
		
	}
	
	@Test(expected = wrongNumberOfPlayersException.class)
	public void testWrongNumberOfPlayersException2() throws wrongNumberOfPlayersException
	{
		Board gameBoard = new Board(10);
		
	}
	/*
	 * tests related to basic moves (without jumps) 
	 */
	@Test
	public void testBasicMove() throws wrongNumberOfPlayersException, occupiedException, invalidMoveException, outOfTheBoardException, cantGetOutOfTheJaillException
	{
		Board gameBoard = new Board(6);
		int playerId = gameBoard.findTile(7, 3).getPlayerId();
		assertEquals(1, gameBoard.movePiece(7, 3, 7, 4, 1)); //vallid move, should have moved 
		assertNotEquals(playerId, gameBoard.findTile(7, 3).getPlayerId()); // check if it in fact moved
	}
	
	@Test(expected = occupiedException.class)
	public void testBasicMove2() throws wrongNumberOfPlayersException, occupiedException, invalidMoveException, outOfTheBoardException, cantGetOutOfTheJaillException
	{
		Board gameBoard = new Board(6);
		gameBoard.movePiece(6, 0, 6, 1, 1);
	}
	
	@Test(expected = invalidMoveException.class)
	public void testBasicMove3() throws wrongNumberOfPlayersException, occupiedException, invalidMoveException, outOfTheBoardException, cantGetOutOfTheJaillException
	{
		Board gameBoard = new Board(6);
		gameBoard.movePiece(7, 3, 9, 9, 1);
	}
	
	@Test(expected = invalidMoveException.class)
	public void testBasicMove4() throws  occupiedException, invalidMoveException, outOfTheBoardException, cantGetOutOfTheJaillException, wrongNumberOfPlayersException
	{
		Board gameBoard = new Board(6);
		gameBoard.movePiece(7, 3, 6, 4, 1);
	}
	@Test
	public void testBasicMove5() throws  occupiedException, invalidMoveException, outOfTheBoardException, cantGetOutOfTheJaillException, wrongNumberOfPlayersException
	{
		Board gameBoard = new Board(3);
		gameBoard.movePiece(1, 9, 2, 8, 3);
	}
	/*
	 * tests related to jump moves
	 */
	@Test
	public void testJumpMove() throws wrongNumberOfPlayersException, occupiedException, invalidMoveException, outOfTheBoardException, cantGetOutOfTheJaillException
	{
		Board gameBoard = new Board(6);
		gameBoard.movePiece(7, 3, 8, 4, 1);
		assertEquals(1, gameBoard.findTile(8, 4).getPlayerId());
		assertTrue(gameBoard.findTile(8, 4).playable());
		gameBoard.movePiece(9, 4, 7, 4, 2);
		assertEquals(2, gameBoard.findTile(7, 4).getPlayerId());
	}
	@Test
	public void testJumpMoveChain() throws wrongNumberOfPlayersException, occupiedException, invalidMoveException, outOfTheBoardException, cantGetOutOfTheJaillException
	{
		Board gameBoard = new Board(6);
		gameBoard.movePiece(7, 3, 8, 4, 1);
		gameBoard.movePiece(6, 3, 6, 4, 1);
		assertEquals(2,gameBoard.movePiece(9, 4, 7, 4, 2));
		assertEquals(2,gameBoard.movePiece(7, 4, 5, 4, 2));
		assertEquals(0, gameBoard.findTile(7, 4).getPlayerId());
		
		assertEquals(2, gameBoard.findTile(5, 4).getPlayerId());
	}
	@Test(expected = invalidMoveException.class)
	public void testJumpMoveChainBreak() throws wrongNumberOfPlayersException, occupiedException, invalidMoveException, outOfTheBoardException, cantGetOutOfTheJaillException
	{
		Board gameBoard = new Board(6);
		gameBoard.movePiece(7, 3, 8, 4, 1);
		gameBoard.movePiece(6, 3, 6, 4, 1);
		gameBoard.movePiece(10, 7, 9, 7, 2);
		assertEquals(2,gameBoard.movePiece(9, 4, 7, 4, 2));
		assertEquals(2,gameBoard.movePiece(7, 4, 5, 4, 2));
		gameBoard.movePiece(10, 6, 9, 8, 2);
		
	}
	/*
	 * tests if piece in winning position can escape it 
	 */
	@Test(expected = cantGetOutOfTheJaillException.class)
	public void testGetOutOfTheJailFreeCardNotFound() throws wrongNumberOfPlayersException, occupiedException, invalidMoveException, outOfTheBoardException, cantGetOutOfTheJaillException
	{
		Board gameBoard = new Board(6);
		assertEquals(1, gameBoard.movePiece(6, 13, 7, 12, 4));
		assertEquals(1, gameBoard.movePiece(6, 3, 6, 4, 1));
		assertEquals(1, gameBoard.movePiece(6, 4, 6, 5, 1));
		assertEquals(1, gameBoard.movePiece(6, 5, 6, 6, 1));
		assertEquals(1, gameBoard.movePiece(6, 6, 6, 7, 1));
		assertEquals(1, gameBoard.movePiece(6, 7, 6, 8, 1));
		assertEquals(1, gameBoard.movePiece(6, 8, 6, 9, 1));
		assertEquals(1, gameBoard.movePiece(6, 9, 6, 10, 1));
		assertEquals(1, gameBoard.movePiece(6, 10, 6, 11, 1));
		assertEquals(1, gameBoard.movePiece(6, 11, 6, 12, 1));
		assertEquals(1, gameBoard.movePiece(6, 12, 6, 13, 1));
		gameBoard.movePiece(6, 13, 6, 12, 1); // :(
	}
}
