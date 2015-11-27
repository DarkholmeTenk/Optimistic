package kalah.agent.factories;

import java.util.List;

import kalah.agent.AbstractAgent;
import kalah.agent.RandomAgent;
import kalah.game.board.Player;

public class RandomAgentFactory extends AbstractAgentFactory
{

	@Override
	public AbstractAgent getAgent(Player p)
	{
		switch(p)
		{
			case PLAYER1: return RandomAgent.playerOne;
			case PLAYER2: return RandomAgent.playerTwo;
			default: return null;
		}
	}

	@Override
	protected void getNames(List<String> nameList)
	{
		nameList.add("random");
		nameList.add("ran");
	}

	@Override
	public Class<? extends AbstractAgent> getAgentClass()
	{
		return RandomAgent.class;
	}

}
