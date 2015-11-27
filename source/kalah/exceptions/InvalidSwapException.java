package kalah.exceptions;

public class InvalidSwapException extends InvalidHouseException
{
	public final int turn;

	public InvalidSwapException(int _turn, int size)
	{
		super(-1, size);
		turn = _turn;
	}

	@Override
	public String getMessage()
	{
		return "Swap was attempted when invalid on turn " + turn + " with board size " + size;
	}
}
