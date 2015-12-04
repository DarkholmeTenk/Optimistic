package kalah.agent.factories;

import java.util.List;

import kalah.agent.AbstractAgent;
import kalah.agent.ExternalAgent;
import kalah.engine.Listener;
import kalah.game.board.Player;

public class ExternalAgentFactory extends AbstractAgentFactory
{
  private Listener listener;

  public ExternalAgentFactory(Listener listener)
  {
    this.listener = listener;
  }

	@Override
	public AbstractAgent getAgent(Player p)
	{
		return new ExternalAgent(p, listener);
	}

	@Override
	protected void getNames(List<String> nameList)
	{
		nameList.add("external");
		nameList.add("ext");
	}

	@Override
	public Class<? extends AbstractAgent> getAgentClass()
	{
		return ExternalAgent.class;
	}

}
