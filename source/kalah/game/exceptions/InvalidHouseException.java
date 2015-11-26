package kalah.game.exceptions;

public class InvalidHouseException extends RuntimeException
{
	public final int house;
	public final int size;
	public InvalidHouseException(int _house, int _size)
	{
		house = _house;
		size = _size;
	}

	@Override
	public String getMessage()
	{
		return house + " was attempted to be accessed despite the board being size " + size;
	}
}
