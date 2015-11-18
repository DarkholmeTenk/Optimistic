package kalah.engine.message.agent;

public class SwapCommandMessage extends AgentMessage
{

	@Override
	protected String getFragment()
	{
		return "SWAP";
	}

}
