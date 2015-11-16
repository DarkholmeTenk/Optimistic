package kalah.game.exceptions;

public class InvalidHouseException extends RuntimeException
{
	private final int house;
	private final int size;
	public InvalidHouseException(int _house, int _size)
	{
		house = _house;
		size = _size;
	}

	@Override
	public String getMessage()
	{
		return super.getMessage() + " - " + house + " was attempted to be accessed despite the board being size " + size;
	}
}
