package kalah.game.board;

public class Action
{
	public final Player	player;
	public final int	house;

	private Action(Player p, int h)
	{
		player = p;
		house = h;
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
