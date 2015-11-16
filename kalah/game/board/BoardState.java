package kalah.game.board;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import kalah.game.exceptions.InvalidHouseException;
import kalah.game.exceptions.WrongPlayerException;

public class BoardState
{
	private final int[] board;
	private final int size;
	private final Player currentPlayer;

	public BoardState(int _size, int _counters)
	{
		size = _size;
		Action.initActions(size);
		board = new int[size * 2 + 2];
		int playerSize = size + 1;
		for(int i = 0; i < board.length; i++)
			board[i] = i % playerSize == size ? 0 : _counters;
		currentPlayer = Player.PLAYER1;
	}

	private BoardState(Player c, int[] newState)
	{
		board = newState;
		size = (newState.length - 2) / 2;
		currentPlayer = c;
	}

	/**
	 * Take an action to get to a new board state
	 * @param a the action to take
	 * @return a new board state representative of the new state of the game
	 */
	public BoardState takeAction(Action a)
	{
		Player p = a.player;
		if(p != currentPlayer)
			throw new WrongPlayerException(p);
		if(a.house < 0 || a.house >= size)
			throw new InvalidHouseException(a.house, size);
		int initialPos = getOffset(p) + a.house;
		int inHouse = getCounters(a.player, a.house);
		int[] newBoard = board.clone();
		int addToAll = inHouse / board.length;
		int howManyGetOne = inHouse % board.length;
		newBoard[initialPos] = 0;
		for(int i = 1; i <= board.length; i++)
		{
			int pos = (initialPos + i) % board.length;
			if(i <= howManyGetOne)
				newBoard[pos] += addToAll + 1;
			else
				newBoard[pos] += addToAll;
		}
		int endPos = (initialPos + inHouse) % board.length;
		int endHouse = endPos % (size + 1);
		Player whoseSide = getPlayer(endPos);
		Player next = p.getOpponent();
		if(endHouse == size) //We're in a store
		{
			if(whoseSide == currentPlayer) //and it's ours
				next = p; //well it's still our go then
		}
		else if(board[endPos] == 0 && whoseSide == currentPlayer) //Place we ended up was empty and ours
		{
			int oppositePos = getOppositeHouse(endPos);
			if(board[oppositePos] != 0) //and the opponents opposite is not empty
			{
				newBoard[getStorePos(currentPlayer)] = newBoard[endPos] + newBoard[oppositePos]; //move all the counters to our store
				newBoard[endPos] = 0;
				newBoard[oppositePos] = 0;
			}
		}
		return new BoardState(next, newBoard);
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
		return position + (size + 1) % board.length;
	}

	public int getCounters(Player p, int house)
	{
		if(house >= size || house < 0)
			throw new InvalidHouseException(house,size);
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

	/**
	 * Rotates the board and sets the player to be the opposing player
	 * @return a board state representing that change
	 */
	public BoardState switchPlayers()
	{
		Player o = currentPlayer.getOpponent();
		int[] newBoard = new int[board.length];
		for(int i = 0; i < board.length; i++)
			newBoard[i] = i<=size ? board[i + size+1] : board[i - (size + 1)];
			return new BoardState(o, newBoard);
	}

	/**
	 * @return a collection representing all the actions that can be taken at this juncture
	 */
	public Collection<Action> getValidActions()
	{
		HashSet<Action> validActions = new HashSet<Action>();
		for(int i = 0; i < size; i++)
			if(getCounters(currentPlayer, i) > 0)
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
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof BoardState)) return false;
		BoardState other = (BoardState) obj;
		if (!Arrays.equals(board, other.board)) return false;
		if (currentPlayer != other.currentPlayer) return false;
		if (size != other.size) return false;
		return true;
	}
}