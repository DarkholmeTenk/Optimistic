package kalah.game.board;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kalah.exceptions.InvalidHouseException;
import kalah.exceptions.InvalidSwapException;
import kalah.exceptions.WrongPlayerException;
import kalah.program.Configuration;

public class BoardState
{
	private static int[] initialBoard(int size, int counters)
	{
		int[] tempBoard = new int[size * 2 + 2];
		int playerSize = size + 1;
		for (int i = 0; i < tempBoard.length; i++)
			tempBoard[i] = i % playerSize == size ? 0 : counters;
		return tempBoard;
	}

	private final int							turnNumber;
	private final int[]							board;
	public	final int							size;
	private final Player						currentPlayer;
	private final SoftReference<BoardState>[]	futureStates;
	private final WeakReference<BoardState>		parent;

	/**
	 * Initialize a new Board
	 * @param _size the number of houses each player has (excluding store)
	 * @param _counters the number of counters which are in each house to start off with
	 */
	public BoardState(int _size, int _counters)
	{
		this(Player.PLAYER1, _size, initialBoard(_size, _counters), null);
		Action.initActions(_size);
	}

	/**
	 * This should only be used in tests and this class
	 */
	public BoardState(Player c, int _size, int[] newState, BoardState _parent)
	{
		board = newState;
		size = _size;
		if (Configuration.cacheBoardStates)
			futureStates = new SoftReference[size];
		else
			futureStates = null;
		currentPlayer = c;
		if(_parent == null)
			parent = null;
		else
			parent = new WeakReference<BoardState>(_parent);
		turnNumber = _parent != null ? _parent.turnNumber + 1 : 0;
	}

	public boolean isValidToSwap()
	{
		return (turnNumber == 1 || turnNumber == 2) && currentPlayer == Player.PLAYER2;
	}

	/**
	 * Take an action to get to a new board state
	 *
	 * @param a
	 *            the action to take
	 * @return a new board state representative of the new state of the game
	 */
	public BoardState takeAction(Action a)
	{
		if(a instanceof SwapAction)
		{
			if(isValidToSwap())
				return switchPlayers();
			throw new InvalidSwapException(turnNumber, size);
		}
		Player p = a.player;
		if (p != currentPlayer)
			throw new WrongPlayerException(p);
		if (a.house < 0 || a.house >= size)
			throw new InvalidHouseException(a.house, size);
		if (Configuration.cacheBoardStates)
			if (futureStates[a.house] != null && futureStates[a.house].get() != null)
				return futureStates[a.house].get();
		int initialPos = getOffset(p) + a.house;
		int inHouse = getCounters(a.player, a.house);
		int[] newBoard = board.clone();
		int addToAll = inHouse / (board.length - 1);
		int howManyGetOne = inHouse % (board.length - 1);
		int enemyStorePos = getStorePos(p.getOpponent());
		newBoard[initialPos] = 0;
		for (int i = 1; i <= board.length; i++)
		{
			int pos = (initialPos + i) % board.length;
			if(pos == enemyStorePos)
				continue;
			if (howManyGetOne-- > 0)
				newBoard[pos] += addToAll + 1;
			else
				newBoard[pos] += addToAll;
		}

		int endPos = (initialPos + inHouse) % board.length; // Where do we end up
		int endHouse = endPos % (size + 1); // End house (independant of player)
		Player whoseSide = getPlayer(endPos);
		Player next = p.getOpponent();
		if (endHouse == size) // We're in a store
		{
			if (whoseSide == currentPlayer) // and it's ours
				next = p; // well it's still our go then
		}
		else if (board[endPos] == 0 && whoseSide == currentPlayer) // Place we ended up was empty and ours
		{
			int oppositePos = getOppositeHouse(endPos);
			if (board[oppositePos] != 0) // and the opponents opposite is not empty
			{
				newBoard[getStorePos(currentPlayer)] += newBoard[endPos] + newBoard[oppositePos]; // move all the counters to our store
				newBoard[endPos] = 0;
				newBoard[oppositePos] = 0;
			}
		}
		BoardState newState = new BoardState(next, size, newBoard, this);
		if (Configuration.cacheBoardStates && futureStates != null)
			futureStates[a.house] = new SoftReference<BoardState>(newState);
		return newState;
	}

	/**
	 * @param position
	 * @return the player who owns position
	 */
	private Player getPlayer(int position)
	{
		return position <= size + 1 ? Player.PLAYER1 : Player.PLAYER2;
	}

	/**
	 * @param p
	 * @return the position which p's houses start at
	 */
	private int getOffset(Player p)
	{
		return p.ordinal() * (size + 1);
	}

	/**
	 * @param p
	 * @return the position of p's store
	 */
	private int getStorePos(Player p)
	{
		return getOffset(p) + size;
	}

	/**
	 * @param position
	 * @return the house directly opposite position
	 */
	private int getOppositeHouse(int position)
	{
		return 2 * size - position;
	}

	/**
	 * @param p the player the house belongs to
	 * @param house the house to check the counters of (houses are left->right from player's view)
	 * @return the number of counters in the house
	 */
	public int getCounters(Player p, int house)
	{
		if (house >= size || house < 0)
			throw new InvalidHouseException(house, size);
		return board[getOffset(p) + house];
	}

	/**
	 * @param p
	 * @return
	 */
	public int getCountersInStore(Player p)
	{
		return board[getStorePos(p)];
	}

	public Player getCurrentPlayerTurn()
	{
		return currentPlayer;
	}

	public int getScore(Player p)
	{
		int o = getOffset(p);
		int sum = 0;
		for(int i = o; i <= o+size; i++)
			sum += board[i];
		return sum;
	}

	/**
	 * Rotates the board and sets the player to be the opposing player
	 *
	 * @return a board state representing that change
	 */
	public BoardState switchPlayers()
	{
		Player o = currentPlayer.getOpponent();
		//int[] newBoard = board.clone();
		int[] newBoard = new int[board.length];
		for (int i = 0; i < board.length; i++)
			newBoard[i] = board[(i+board.length / 2) % board.length];
		return new BoardState(o, size, newBoard, this);
	}

	/**
	 * @return a collection representing all the actions that can be taken at this juncture
	 */
	public List<Action> getValidActions()
	{
		List<Action> validActions = new ArrayList<Action>();
		for (int i = 0; i < size; i++)
			if (getCounters(currentPlayer, i) > 0)
				validActions.add(Action.get(currentPlayer, i));
		if(isValidToSwap())
			validActions.add(Action.swapAction);
		return validActions;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(board);
		result = prime * result + (currentPlayer == null ? 0 : currentPlayer.hashCode());
		result = prime * result + size;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BoardState))
			return false;
		BoardState other = (BoardState) obj;
		if (!Arrays.equals(board, other.board))
			return false;
		if (currentPlayer != other.currentPlayer)
			return false;
		if (size != other.size)
			return false;
		return true;
	}

	private String toString(Player p)
	{
		String s = p.toString() + "- S:" + getCountersInStore(p) + "	:";
		for(int i = 0; i < size; i++)
			s += String.format("%2d ", getCounters(p,i));
		return s;
	}

	@Override
	public String toString()
	{
		return String.format("%s%n%s%n%s","Player turn: " + currentPlayer,toString(Player.PLAYER1),toString(Player.PLAYER2));
	}

	/**
	 * @return true if the object should have a parent, false otherwise.
	 */
	public boolean hasParent()
	{
		return parent != null;
	}

	/**
	 * Null does not indicate no parent, it may just be that the parent has been garbage collected.
	 * Use hasParent to check if a parent should exist
	 * This should only be used if this and its parent are known to be strongly referenced.
	 * @return parent if it exists, null if not.
	 */
	public BoardState getParent()
	{
		if(parent == null) return null;
		return parent.get();
	}
}