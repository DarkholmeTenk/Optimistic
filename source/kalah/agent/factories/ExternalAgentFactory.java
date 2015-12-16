package kalah.agent.factories;

import java.util.List;

import kalah.agent.AbstractAgent;
import kalah.agent.ExternalAgent;
import kalah.engine.Listener;
import kalah.engine.Speaker;
import kalah.game.board.Player;

public class ExternalAgentFactory extends AbstractAgentFactory
{
  private Listener listener;
  private Speaker speaker;

  public ExternalAgentFactory(Listener listener, Speaker speaker)
  {
    this.listener = listener;
    this.speaker = speaker;
  }

	@Override
	public AbstractAgent getAgent(Player p)
	{
		return new ExternalAgent(p, listener, speaker);
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
