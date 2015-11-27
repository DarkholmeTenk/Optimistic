package kalah.agent.exceptions;

public class CannotActException extends RuntimeException
{
  private final String reason;

	public CannotActException(String reason)
	{
		this.reason = reason;
	}

	@Override
	public String getMessage()
	{
		return reason;
	}
}
