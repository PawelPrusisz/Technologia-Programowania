package chinese_checkers.serwer;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Pawel
 *
 */
public class Player {
	public int playerId;
	private List<Tile> playerPieces;
	//private ClientSocket;
	public Player(int playerId)
	{
		this.playerId = playerId;
		this.playerPieces = new ArrayList<Tile>();
	}
	
	public void addPiece(Tile piece)
	{
		this.playerPieces.add(piece);
	}
	
	public boolean hasWon()
	{
		for(Tile p : playerPieces)
		{
			if(p.isWinning())return false;
		}
		return true;
	}
}
