package kalah.test.agents;

import kalah.game.board.BoardState;

import org.junit.BeforeClass;

public class AgentTest
{
	public static BoardState start;
	public static int size = 4;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		start = new BoardState(size, size);
	}
}
