package kalah.game.board;

public class SwapAction extends Action
{
	public SwapAction()
	{
		super(Player.PLAYER2, -1);
	}

  public String toString()
  {
    return "SWAP, " + player;
  }
}
