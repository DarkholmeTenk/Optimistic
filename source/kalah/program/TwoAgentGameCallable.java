package kalah.program;

import java.util.concurrent.Callable;

import kalah.agent.AbstractAgent;
import kalah.game.board.BoardState;

public class TwoAgentGameCallable implements Callable<Double>
{
	private final TwoAgentGame game;
	private BoardState startState;
	private int numNanos = Configuration.maxTimePerTurn;
	private int numPlayed;

	public TwoAgentGameCallable(AbstractAgent p1, AbstractAgent p2)
	{
		game = new TwoAgentGame(p1,p2);
	}

	public void setMaxTime(int time)
	{
		if(time < 1)
			time = 1;
		numNanos = time * 1000000;
	}

	public void setState(BoardState state)
	{
		startState = state;
	}

	@Override
	public Double call() throws Exception
	{
		long startTime = System.nanoTime();
		long endTime = startTime + numNanos;
		long ct = startTime;
		long pt;
		double score = 0;
		numPlayed = 0;
		do
		{
			try
			{
				score += game.play(startState);
			}
			catch(Exception e){}
			numPlayed++;
			pt = ct;
		}
		while((ct = System.nanoTime()) < endTime);
		//System.out.println("CT:"+ ct/1000000 + "/PT:" + pt/1000000 + "/CT-PT:" + (ct-pt)/1000);
		return score / numPlayed;
	}

	public int getNumGamesPlayed()
	{
		return numPlayed;
	}

}
