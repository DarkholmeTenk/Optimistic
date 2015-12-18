package kalah.game.board;

/**
 * A class representing an action that is to be taken.
 * May be of class {@link SwapAction} which represents switching the board via the Pie Rule.
 * @author DarkholmeTenk
 *
 */
public class Action
{
	public final Player	player;
	public final int	house;

	public final static Action swapAction = new SwapAction();

	public Action(Player p, int h)
	{
		player = p;
		house = h;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + house;
		result = prime * result + (player == null ? 0 : player.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof Action)) return false;
		Action other = (Action) obj;
		if (house != other.house) return false;
		if (player != other.player) return false;
		return true;
	}

  public String toString()
  {
    return "MOVE " + (house + 1) + ", " + player;
  }

	/*
	 * Factory methods start here
	 */
	private static int			boardSize;
	private static Action[][]	actions;

	/**
	 * Called by the board to initialise the action cache
	 *
	 * @param _boardSize
	 */
	protected static void initActions(int _boardSize)
	{
		boardSize = _boardSize;
		if (actions == null) actions = new Action[Player.values().length][];
		for (Player p : Player.values())
		{
			int i = p.ordinal();
			actions[i] = new Action[boardSize];
			for (int j = 0; j < boardSize; j++)
				actions[i][j] = new Action(p, j);
		}
	}

	/**
	 * Get an action
	 *
	 * @param p
	 *            the player who is taking the action
	 * @param house
	 *            the house they want to move things from
	 * @return an action representing this
	 */
	public static Action get(Player p, int house)
	{
		return actions[p.ordinal()][house];
	}
}
