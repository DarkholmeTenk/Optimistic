package kalah.agent.factories;

import java.util.List;

import kalah.agent.AbstractAgent;
import kalah.game.board.Player;

/**
 * A class for constructing agents of a particular types.
 *
 */
public abstract class AbstractAgentFactory
{
	/**
	 * Add to nameList all of the ids that you want your agent type to be registered
	 * @param nameList the list to add the names for your agent to
	 */
	protected abstract void getNames(List<String> nameList);

	/**
	 * Get an agent which represents the player provided
	 * @param p the player the agent should represent
	 * @return an abstract agent which represents player p
	 */
	public abstract AbstractAgent getAgent(Player p);

	/**
	 * @return the class of the agent this factory represents
	 */
	public abstract Class<? extends AbstractAgent> getAgentClass();
}
