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
		long t = System.currentTimeMillis();
		callable.call();
		long et = System.currentTimeMillis();
		assertTrue("Took too long - " + (et - t) , System.currentTimeMillis() < t+ttt+2);
	}

}
