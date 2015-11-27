package kalah.agent.factories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kalah.agent.AbstractAgent;
import kalah.exceptions.NoSuchAgentException;

public class AbstractAgentFactoryFactory
{
	private static HashMap<String, AbstractAgentFactory> map = new HashMap();
	private static HashMap<Class<? extends AbstractAgent>,AbstractAgentFactory> secondMap = new HashMap();

	private static void register(AbstractAgentFactory factory)
	{
		secondMap.put(factory.getAgentClass(), factory);
		List<String> stringList = new ArrayList();
		factory.getNames(stringList);
		for(String name : stringList)
			register(name, factory);
	}

	private static void register(String id, AbstractAgentFactory factory)
	{
		map.put(id, factory);
	}

	static
	{
		registerOurAgents();
	}

	public static void registerOurAgents()
	{
		register(new RandomAgentFactory());
		register(new SimpleMonteCarloAgentFactory());
	}

	/**
	 * Use this method to get an agent factory for the string given
	 * @param id the id of the agent type to return a factory for
	 * @return a factory to construct agents of type
	 * throws NoSuchAgentException if it doesn't exist
	 */
	public static AbstractAgentFactory get(String id)
	{
		if(!map.containsKey(id))
			throw new NoSuchAgentException(id);
		return map.get(id);
	}

	/**
	 * This method will probably never be used, but can return an agent factory for an agent class.
	 * @param agentClass the class of agent to return a factory for
	 * @return an agent factory
	 * throws NoSuchAgentException if it doesn't exist
	 */
	public static AbstractAgentFactory get(Class<? extends AbstractAgent> agentClass)
	{
		if(!secondMap.containsKey(agentClass))
			throw new NoSuchAgentException(agentClass.toString());
		return secondMap.get(agentClass);
	}
}
