package kalah.test.storage;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import kalah.game.board.Action;
import kalah.game.board.BoardState;
import kalah.game.board.storage.StoredGameTree;
import kalah.game.board.storage.StoredGameTreeInternal;

import org.junit.BeforeClass;
import org.junit.Test;

public class StoredGameTreeTest
{
	private static BoardState startState;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		startState = new BoardState(7,7);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test()
	{
		StoredGameTree<Double> sgt = new StoredGameTree<Double>(startState);
		StoredGameTreeInternal<Double> sgti = sgt.internalTree;
		sgti.data = 10d;
		for(int i = 0; i < 5; i++)
		{
			Action a = sgti.currentState.getValidActions().get(0);
			sgti = sgti.takeAction(a);
			sgti.data = (double) i;
		}
		try
		{
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bos);
			out.writeObject(sgt);
			byte[] data = bos.toByteArray();
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			ObjectInputStream in = new ObjectInputStream(bis);
			StoredGameTree<Double> newTree = (StoredGameTree<Double>) in.readObject();
			assertEquals("These should be equal",sgt,newTree);
			StoredGameTreeInternal<Double> newTreeIn = newTree.internalTree;
			assertTrue("Data on branch is wrong",newTreeIn.data.equals(10d));
			for(int i = 0; i < 5; i++)
			{
				Action a = newTreeIn.currentState.getValidActions().get(0);
				newTreeIn = newTreeIn.takeAction(a);
				assertTrue("Data on branch " + i + " is wrong",newTreeIn.data.equals((double) i));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

}
