package kalah.engine.message.agent;

import kalah.game.board.Action;

public class MoveCommandMessage extends AgentMessage
{

	private final Action	action;

	public MoveCommandMessage(Action action)
	{
		this.action = action;
	}

	@Override
	protected String getFragment()
	{
		return "MOVE;" + (action.house + 1);
	}

}
