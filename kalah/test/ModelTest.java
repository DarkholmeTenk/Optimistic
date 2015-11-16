package kalah.test;

import static org.junit.Assert.*;

import kalah.game.board.Action;
import kalah.game.board.BoardState;
import kalah.game.board.Player;
import kalah.program.Configuration;

import org.junit.BeforeClass;
import org.junit.Test;

public class ModelTest
{
	public static final int size = 4;
	public static BoardState start;

	@BeforeClass
	public static void setup()
	{
		Configuration.cacheBoardStates = true;
		start = new BoardState(size,size);
	}

	@Test
	public void startBoardTest()
	{
		for(Player p : Player.values())
		{
			for(int i = 0; i < size; i++)
				assertEquals("Counters not " + size,start.getCounters(p, i),size);
			assertEquals("Store not 0", start.getCountersInStore(p),0);
			try
			{
				start.getCounters(p, size);
				fail("Did not fail on accessing large thing");
			}
			catch(RuntimeException e)
			{
			}
		}
	}

	@Test
	public void actionCreationTest()
	{
		Action a = Action.get(Player.PLAYER1, 0);
		Action b = Action.get(Player.PLAYER1, 0);
		assertSame("Actions not same",a,b);
		assertSame("Not correct player", a.player, Player.PLAYER1);
		assertSame("Not correct house", a.house, 0);
	}

	@Test
	public void moveTestStoreEnd()
	{
		Action doAction = Action.get(Player.PLAYER1, 0);
		BoardState newState = start.takeAction(doAction);
		BoardState newState2 = start.takeAction(doAction);
		assertNotSame("Start state still", newState, start);
		assertSame("States not same", newState, newState2);
		assertSame("Player changed on ending in store", newState.getCurrentPlayerTurn(), Player.PLAYER1);
		assertEquals("Still stuff in house 0", newState.getCounters(Player.PLAYER1, 0), 0);
		for(int i = 1; i < size; i++)
		{
			int count = newState.getCounters(Player.PLAYER1, i);
			assertEquals("Wrong counters in house: " + i + ":" + count, count, size+1);
		}
		assertEquals("Nothing new in store", newState.getCountersInStore(Player.PLAYER1), 1);
	}

	@Test
	public void moveTestNormalEnd()
	{
		Action doAction = Action.get(Player.PLAYER1, 1);
		BoardState newState = start.takeAction(doAction);
		assertNotSame("Start state still",newState, start);
		assertSame("Player 1 still on normal move", newState.getCurrentPlayerTurn(), Player.PLAYER2);
		assertSame("Player 2 did not gain a counter", newState.getCounters(Player.PLAYER2, 0),size + 1);
	}

	/*	@Test
	public void captureTest()
	{
		BoardState oldState = start;
		BoardState newState = start;
		int[] actionList = new int[]{3,3,2,2,3,3,1,2,3,0,2,3,0,1};//Sequence only works on size 4 and 5
		for(int i : actionList)
		{
			oldState = newState;
			Action a = Action.get(newState.getCurrentPlayerTurn(), i);
			newState = newState.takeAction(a);
		}
		System.out.println(oldState);
		System.out.println(newState);
	}*/

}
