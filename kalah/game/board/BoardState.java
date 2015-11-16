package kalah.game.board;

public class BoardState
{
	public BoardState(int size, int counters)
	{
		Action.initActions(size);
	}

	public BoardState takeAction(Action a)
	{
		return null;
		//TODO: Implement this
	}

	public int getCounters(Player p, int house)
	{
		return 0;
		//TODO: Implement this
	}

	public int getCountersInStore(Player p)
	{
		return 0;
		//TODO: Implement this
	}
}