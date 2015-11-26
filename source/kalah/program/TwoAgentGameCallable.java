package kalah.program;

import java.util.concurrent.Callable;

import kalah.agent.AbstractAgent;
import kalah.game.board.BoardState;

public class TwoAgentGameCallable implements Callable<Double>
{
	private final TwoAgentGame game;
	private BoardState startState;
	private long startTime;
	private int numMillis = Configuration.maxTimePerTurn;

	public TwoAgentGameCallable(AbstractAgent p1, AbstractAgent p2)
	{
		game = new TwoAgentGame(p1,p2);
	}

	public void setMaxTime(int time)
	{
		if(time < 1)
			time = 1;
		numMillis = time;
	}

	public void setState(BoardState state)
	{
		startState = state;
	}

	@Override
	public Double call() throws Exception
	{
		startTime = System.currentTimeMillis();
		long endTime = startTime + numMillis;
		double score = 0;
		int n = 0;
		do
		{
			score += game.play(startState);
			n++;
		}
		while(System.currentTimeMillis() < endTime);

		return score / n;
	}

}
