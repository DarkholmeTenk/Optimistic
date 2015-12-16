package kalah.agent;

import kalah.engine.Listener;
import kalah.engine.Speaker;
import kalah.engine.message.engine.*;
import kalah.engine.message.agent.*;
import kalah.game.board.*;

import java.io.IOException;

public class ExternalAgent extends AbstractAgent
{
	private final Listener listener;
	private final Speaker speaker;

	public ExternalAgent(Player player, Listener listener, Speaker speaker)
	{
		super(player);
		this.listener = listener;
		this.speaker = speaker;
	}

	@Override
	public void opponentAction(BoardState state, Action action)
	{
		try
		{
			if(action instanceof SwapAction)
				speaker.say(new SwapCommandMessage());
			else
				speaker.say(new MoveCommandMessage(action));
		}
		catch (IOException e)
		{
			throw new RuntimeException("Speaker broke! " + e);
		}
	}

	@Override
	public Action getNextMove(BoardState board)
	{
		try
		{
			EngineMessage message = listener.next();

			if(message instanceof MoveMessage)
				return new Action(agentPlayer, ((MoveMessage) message).getHouse());
			else if (message instanceof SwapMessage)
				return new SwapAction();
			else
				return null;
		}
		catch (IOException e)
		{
			throw new RuntimeException("Listener broke! " + e);
		}
	}

}
