package kalah.program;

import java.util.concurrent.Callable;

import kalah.agent.AbstractAgent;
import kalah.game.board.BoardState;

public class TwoAgentGameCallable implements Callable<Double>
{
	private final TwoAgentGame game;
	private BoardState startState;
	private int	numGames = Configuration.numGames;

	public TwoAgentGameCallable(AbstractAgent p1, AbstractAgent p2)
	{
		game = new TwoAgentGame(p1,p2);
	}

	public void setNumGames(int num)
	{
		numGames = num;
	}

	public void setState(BoardState state)
	{
		startState = state;
	}

	@Override
	public Double call() throws Exception
	{
		double score = 0;
		for(int i = 0; i < numGames; i++)
			score += game.play(startState) / (double) numGames;
		return score;
	}

}
