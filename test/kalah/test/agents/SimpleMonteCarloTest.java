package kalah.test.agents;

import static org.junit.Assert.*;
import kalah.agent.RandomAgent;
import kalah.agent.SimpleMonteCarloAgent;
import kalah.game.board.Action;
import kalah.game.board.BoardState;
import kalah.game.board.Player;
import kalah.program.Configuration;
import kalah.program.TwoAgentGame;

import org.junit.Test;

public class SimpleMonteCarloTest extends AgentTest
{
	private int runs = 20;

	@Test
	public void test()
	{
		Configuration.maxTimePerTurn = 4;
		TwoAgentGame game = new TwoAgentGame(SimpleMonteCarloAgent.playerOne, RandomAgent.playerTwo);
		double score = 0;
		for(int i = 0; i < runs; i++)
			score += game.play(start) / (double) runs;
		if(score < 0)
			fail("No way the monte carlo should have lost");
		System.out.println("Overall score: " + score);
	}

}
