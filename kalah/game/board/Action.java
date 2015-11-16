package kalah.game.board;

public class Action
{
	private final Player	player;
	private final int		house;

	private Action(Player p, int h)
	{
		player = p;
		house = h;
	}


	/*
	 * Factory methods start here
	 */
	private static int			boardSize;
	private static Action[][] 	actions;
	protected static void initActions(int _boardSize)
	{
		boardSize = _boardSize;
		if(actions == null)
			actions = new Action[Player.values().length][];
		for(Player p : Player.values())
		{
			int i = p.ordinal();
			actions[i] = new Action[boardSize];
			for(int j = 0; j < boardSize; j++)
				actions[i][j] = new Action(p,j);
		}
	}

	public static Action get(Player p, int house)
	{
		return actions[p.ordinal()][house];
	}
}
