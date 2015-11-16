package kalah.game.board;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kalah.game.exceptions.InvalidHouseException;
import kalah.game.exceptions.WrongPlayerException;
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

	private final int[]							board;
	public	final int							size;
	private final Player						currentPlayer;
	private final SoftReference<BoardState>[]	futureStates;

	public BoardState(int _size, int _counters)
	{
		this(Player.PLAYER1, _size, initialBoard(_size, _counters));
		Action.initActions(_size);
	}

	private BoardState(Player c, int _size, int[] newState)
	{
		board = newState;
		size = _size;
		if (Configuration.cacheBoardStates)
			futureStates = new SoftReference[size];
		else
			futureStates = null;
		currentPlayer = c;
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
		//TODO: Don't deposit seeds in opponents store
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
		int addToAll = inHouse / board.length;
		int howManyGetOne = inHouse % board.length;
		newBoard[initialPos] = 0;
		for (int i = 1; i <= board.length; i++)
		{
			int pos = (initialPos + i) % board.length;
			if (i <= howManyGetOne)
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
		BoardState newState = new BoardState(next, size, newBoard);
		if (Configuration.cacheBoardStates && futureStates != null)
			futureStates[a.house] = new SoftReference<BoardState>(newState);
		return newState;
	}

	private Player getPlayer(int position)
	{
		return position <= size + 1 ? Player.PLAYER1 : Player.PLAYER2;
	}

	private int getOffset(Player p)
	{
		return p.ordinal() * (size + 1);
	}

	private int getStorePos(Player p)
	{
		return getOffset(p) + size;
	}

	private int getOppositeHouse(int position)
	{
		return (position + size + 1) % board.length;
	}

	public int getCounters(Player p, int house)
	{
		if (house >= size || house < 0)
			throw new InvalidHouseException(house, size);
		return board[getOffset(p) + house];
	}

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
		int[] newBoard = new int[board.length];
		for (int i = 0; i < board.length; i++)
			newBoard[i] = i <= size ? board[i + size + 1] : board[i - (size + 1)];
			return new BoardState(o, size, newBoard);
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
}