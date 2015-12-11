package kalah.game.board.storage;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import kalah.game.board.Action;
import kalah.game.board.BoardState;

public class StoredGameTreeInternal<D extends Serializable> implements Serializable
{
	private static final long	serialVersionUID	= 876870731121208780L;

	public final BoardState currentState;
	public StoredGameTreeInternal<D>[] branches;
	public D data;
	public final int depth;

	private transient List<Action> acts;
	protected boolean inited = false;
	protected StoredGameTreeInternal<D> parent;
	protected StoredGameTree<D> owned;

	public StoredGameTreeInternal(BoardState cState, StoredGameTreeInternal<D> pt, StoredGameTree<D> ow)
	{
		parent = pt;
		owned = ow;
		owned.intMap.put(cState, this);
		currentState = cState;
		depth = pt == null ? 0 : pt.depth + 1;
		inited = true;
		initActions();
	}

	private void initActions()
	{
		if(acts == null)
		{
			acts = currentState.getValidActions();
			if(branches == null || branches.length != acts.size())
				branches = new StoredGameTreeInternal[acts.size()];
		}
	}

	private int getActNumber(Action a)
	{
		initActions();
		int i = acts.indexOf(a);
		if(i == -1)
			throw new RuntimeException("Action not found");
		return i;
	}

	public StoredGameTreeInternal<D> takeAction(Action a)
	{
		int i = getActNumber(a);
		if(branches[i] == null)
			branches[i] = new StoredGameTreeInternal<D>(currentState.takeAction(a), this, owned);
		return branches[i];
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(branches);
		result = prime * result + (currentState == null ? 0 : currentState.hashCode());
		result = prime * result + (data == null ? 0 : data.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof StoredGameTreeInternal))
			return false;
		StoredGameTreeInternal other = (StoredGameTreeInternal) obj;
		if (!Arrays.equals(branches, other.branches))
			return false;
		if (currentState == null)
		{
			if (other.currentState != null)
				return false;
		}
		else if (!currentState.equals(other.currentState))
			return false;
		if (data == null)
		{
			if (other.data != null)
				return false;
		}
		else if (!data.equals(other.data))
			return false;
		return true;
	}
}
