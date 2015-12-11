package kalah.agent.factories;

import java.util.List;

import kalah.agent.AbstractAgent;
import kalah.agent.SimpleLearningMonteCarloAgent;
import kalah.game.board.Player;

public class SimpleLearningMonteCarloAgentFactory extends AbstractAgentFactory
{

	@Override
	protected void getNames(List<String> nameList)
	{
		nameList.add("slmca");
		nameList.add("simlearnmontecarlo");

	}

	@Override
	public AbstractAgent getAgent(Player p)
	{
		if(p == Player.PLAYER1)
			return SimpleLearningMonteCarloAgent.playerOne;
		return SimpleLearningMonteCarloAgent.playerTwo;
	}

	@Override
	public Class<? extends AbstractAgent> getAgentClass()
	{
		return SimpleLearningMonteCarloAgent.class;
	}

}
