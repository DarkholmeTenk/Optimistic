package kalah.game.board.storage;

import java.io.Serializable;
import java.util.HashMap;

import kalah.game.board.BoardState;

public class StoredGameTree<D extends Serializable> implements Serializable
{
	private static final long	serialVersionUID	= 8855443499265788007L;
	public final BoardState startingBoardState;
	public final StoredGameTreeInternal<D> internalTree;
	protected HashMap<BoardState,StoredGameTreeInternal<D>> intMap;

	public StoredGameTree(BoardState startState)
	{
		startingBoardState = startState;
		intMap = new HashMap<BoardState,StoredGameTreeInternal<D>>();
		internalTree = new StoredGameTreeInternal<D>(startState,null,this);
	}

	public StoredGameTreeInternal getInternalTree(BoardState state)
	{
		return intMap.get(state);
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (internalTree == null ? 0 : internalTree.hashCode());
		result = prime * result + (startingBoardState == null ? 0 : startingBoardState.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof StoredGameTree))
			return false;
		StoredGameTree other = (StoredGameTree) obj;
		if (internalTree == null)
		{
			if (other.internalTree != null)
				return false;
		}
		else if (!internalTree.equals(other.internalTree))
			return false;
		if (startingBoardState == null)
		{
			if (other.startingBoardState != null)
				return false;
		}
		else if (!startingBoardState.equals(other.startingBoardState))
			return false;
		return true;
	}
}
