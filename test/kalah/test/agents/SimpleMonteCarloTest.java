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

	@Test
	public void endGameTest()
	{
		Configuration.maxTimePerTurn = 4;
		BoardState bs = new BoardState(Player.PLAYER1, 4, new int[]{1,0,0,3, 0, 0,0,0,1, 0}, null, 4);
		Action a = SimpleMonteCarloAgent.playerOne.getNextMove(bs);
		assertTrue("No action picked", a != null);
		assertTrue("Wrong action picked " + a.house, a.house != 3);
	}

}
