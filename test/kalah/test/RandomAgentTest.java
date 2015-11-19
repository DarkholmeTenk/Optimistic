package kalah.test;

import kalah.agent.RandomAgent;
import kalah.game.board.BoardState;
import kalah.game.board.Player;
import kalah.program.TwoAgentGame;

import org.junit.BeforeClass;
import org.junit.Test;

public class RandomAgentTest
{
	public static BoardState start;
	public static int size = 4;
	public static RandomAgent p1 = new RandomAgent(Player.PLAYER1);
	public static RandomAgent p2 = new RandomAgent(Player.PLAYER2);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		start = new BoardState(size, size);
	}

	@Test
	public void test()
	{
		TwoAgentGame game = new TwoAgentGame(p1, p2);
		int score = game.play(start);
		System.out.println("Overall score: " + score);
	}

}
