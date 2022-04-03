package chinese_checkers.serwer;
/**
 * 
 * @author Pawel
 *
 */
public class Tile {
	private int xCord;
	private int yCord;
	private int playerId;
	/**
	 * id of a player with the tile is winning for
	 */
	private int isWiningFor;
	/**
	 * Checking if a tile has a piece in it
	 * @return
	 * 	true if no player occupies this tile, false otherwise
	 */
	public boolean isEmpty()
	{
		if(this.playerId == 0)return true;
		else return false;
	}
	/**
	 * 
	 * @return
	 * 	true if tile is playable, false otherwise
	 */
	public boolean playable()
	{
		if(this.playerId== -1)return false;
		else return true;
	}
	/**
	 * Tile constructor
	 * @param xCord
	 * 	x-coordinate of a tile
	 * @param yCord
	 * 	y-coordinate of a tile
	 * @param playerId
	 * 	id of a player with piece in this tile, 0 if no player has his piece in this tile
	 * @param isWiningFor
	 * 	id of a player with needs to have his piece in this tile in order to win the game
	 */
	public Tile(int xCord, int yCord, Player player, int isWiningFor)
	{
		this.xCord = xCord;
		this.yCord = yCord;
		this.playerId = player.playerId;
		this.isWiningFor = isWiningFor;
		if(player.playerId != 0 && player.playerId != -1)
		{
			player.addPiece(this);
		}
	}
	public void setPlayerId(int id)
	{
		this.playerId = id;
	}
	/**
	 * 
	 * @return
	 * 	Id of a player with a pice in this tile.
	 * 	0 if empty.
	 *  -1 if not playable
	 */
	public int getPlayerId()
	{
		return this.playerId;
	}
	/**
	 * 
	 * @return
	 * 	return id of a player with this file is winning for.
	 *  0 if not aplicable
	 *  -1 if not playable
	 */
	public int getWinningId()
	{
		return this.isWiningFor;
	}
	/**
	 * 
	 * @return
	 * 	returns true if a player has his piece in winning tile
	 *  false otherwise
	 */
	public boolean isWinning()
	{
		if(this.playerId == this.isWiningFor)return true;
		else return false;
	}
	/**
	 * 
	 * @return
	 * 	x-coordinate of a tile
	 */
	public int getX()
	{
		return this.xCord;
	}
	/**
	 * 
	 * @return
	 * 	y-coordinate of a tile
	 */
	public int getY()
	{
		return this.yCord;
	}
}
