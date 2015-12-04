package kalah.agent.factories;

import java.util.List;

import kalah.agent.AbstractAgent;
import kalah.agent.SimpleMonteCarloAgent;
import kalah.game.board.Player;

public class SimpleMonteCarloAgentFactory extends AbstractAgentFactory
{

	@Override
	public AbstractAgent getAgent(Player p)
	{
		switch(p)
		{
		case PLAYER1: return SimpleMonteCarloAgent.playerOne;
		case PLAYER2: return SimpleMonteCarloAgent.playerTwo;
		default: return null;
		}
	}

	@Override
	protected void getNames(List<String> nameList)
	{
		nameList.add("simplemc");
		nameList.add("simplemontecarlo");
		nameList.add("puremc");
		nameList.add("puremontecarlo");
	}

	@Override
	public Class<? extends AbstractAgent> getAgentClass()
	{
		return SimpleMonteCarloAgent.class;
	}

}
