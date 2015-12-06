package kalah.test.agents;

import kalah.agent.RandomAgent;
import kalah.game.board.BoardState;
import kalah.program.TwoAgentGame;

import org.junit.BeforeClass;
import org.junit.Test;

public class RandomAgentTest extends AgentTest
{
	@Test
	public void test()
	{
		TwoAgentGame game = new TwoAgentGame(RandomAgent.playerOne, RandomAgent.playerTwo);
		int score = game.play(start);
		System.out.println("Overall score: " + score);
	}

}
