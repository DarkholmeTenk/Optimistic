package kalah.exceptions;

import kalah.game.board.Player;

public class WrongPlayerException extends RuntimeException
{
	private final Player tried;

	/**
	 * @param triedToMove the player who tried to move despite it not being their turn
	 */
	public WrongPlayerException(Player triedToMove)
	{
		tried = triedToMove;
	}

	@Override
	public String getMessage()
	{
		return super.getMessage() + " - " + tried.toString() + " tried to move when it wasn't their turn";
	}
}
