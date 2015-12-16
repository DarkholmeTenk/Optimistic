package kalah.game.board;

public enum Player
{
	PLAYER1,
	PLAYER2;

	public Player getOpponent()
	{
		if(this == PLAYER1)
			return PLAYER2;
		return PLAYER1;
	}

  public String toString()
  {
    return (this == PLAYER1 ? "Player 1 (South)" : "Player 2 (North)");
  }
}
