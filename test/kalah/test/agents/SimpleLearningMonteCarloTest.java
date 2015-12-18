package kalah.test.agents;

import kalah.agent.AbstractAgent;
import kalah.agent.RandomAgent;
import kalah.agent.SimpleLearningMonteCarloAgent;
import kalah.agent.SimpleMonteCarloAgent;
import kalah.program.TwoAgentGame;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SimpleLearningMonteCarloTest extends AgentTest
{
	private int runs = 50;

	private AbstractAgent p1;
	private AbstractAgent p2;

	@Before
	public void setup()
	{
		p1 = SimpleLearningMonteCarloAgent.playerOne;
		p2 = SimpleLearningMonteCarloAgent.playerTwo;
	}

	private void test(AbstractAgent o, AbstractAgent t)
	{
		TwoAgentGame game = new TwoAgentGame(o, t);
		int wins = 0;
		int draws = 0;
		int winBy = 0;
		int score = 0;
		for(int i = 0; i < runs; i++)
		{
			int gs = game.play(start);
			score += gs;
			winBy += (gs > 0) ? 1 : (gs == 0 ? 0 : -1);
			wins += (gs > 0) ? 1 : 0;
			draws += gs == 0 ? 1 : 0;
		}
		System.out.println("---------------");
		System.out.println(o +" vs " + t);
		System.out.println("---------------");
		System.out.println("num wins: " + wins);
		System.out.println("num draws: " + draws);
		System.out.println("won by:" + winBy);
		System.out.println("score: " + score);
	}

	@Test
	public void test()
	{
		test(p1, RandomAgent.playerTwo);
	}

	@Test
	public void testP2()
	{
		test(RandomAgent.playerOne, p2);
	}

	@Test
	public void testTwo()
	{
		test(p1, SimpleMonteCarloAgent.playerTwo);
	}

	@Test
	public void testSelf()
	{
		test(p1,p2);
	}

	@After
	public void tearDown()
	{
		p1.save();
		p2.save();
	}

}
