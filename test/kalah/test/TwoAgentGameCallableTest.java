package kalah.test;

import static org.junit.Assert.*;
import kalah.agent.RandomAgent;
import kalah.game.board.BoardState;
import kalah.game.board.Player;
import kalah.program.TwoAgentGameCallable;

import org.junit.BeforeClass;
import org.junit.Test;

public class TwoAgentGameCallableTest
{
	private static BoardState state;
	private static TwoAgentGameCallable callable;
	private static final int numTests = 50;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		new BoardState(4,4);
		state = new BoardState(Player.PLAYER1, 4, new int[]{0,1,2,0, 0, 0,3,1,1, 0}, null);
		callable = new TwoAgentGameCallable(RandomAgent.playerOne, RandomAgent.playerTwo);
		callable.setState(state);
	}

	@Test
	public void test() throws Exception
	{
		int ttt = 10;
		callable.setMaxTime(ttt);
		for(int i = 0; i < numTests; i++)
		{
			long t = System.nanoTime();
			callable.call();
			long et = System.nanoTime();
			long expectedTime = t + (ttt + 2) * 1000000;
			assertTrue("Took too long - " + (et - t)/1000000 + "ms with " + callable.getNumGamesPlayed() +" - " + i, et < expectedTime);
		}
	}

}
