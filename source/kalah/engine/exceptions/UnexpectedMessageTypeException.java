package kalah.engine.exceptions;

import kalah.engine.message.engine.EngineMessage;

public class UnexpectedMessageTypeException extends RuntimeException
{
	private final EngineMessage got;
	private final Class expected;

	public UnexpectedMessageTypeException(EngineMessage got, Class expected)
	{
		this.got = got;
		this.expected = expected;
	}

	@Override
	public String getMessage()
	{
		return "Expected '" + expected +"', but got '" + got + "'";
	}
}
