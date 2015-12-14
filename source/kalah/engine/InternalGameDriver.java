package kalah.engine;

import kalah.agent.factories.AbstractAgentFactory;
import kalah.game.board.BoardState;
import kalah.game.board.Player;

public class InternalGameDriver extends GameDriver
{
	public InternalGameDriver(
			AbstractAgentFactory factoryOne,
			AbstractAgentFactory factoryTwo,
			Player factoryOneIs,
			BoardState board)
	{
		super(factoryOne, factoryTwo, factoryOneIs, board);
	}
}
