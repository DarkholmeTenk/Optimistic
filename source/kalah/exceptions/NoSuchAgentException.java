package kalah.exceptions;

public class NoSuchAgentException extends RuntimeException
{
	private final String id;
	public NoSuchAgentException(String id)
	{
		this.id = id;
	}

	@Override
	public String getMessage()
	{
		return "No agent found with ID: " + id;
	}
}
