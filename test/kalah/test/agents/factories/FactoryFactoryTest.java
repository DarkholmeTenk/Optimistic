package kalah.test.agents.factories;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import kalah.agent.factories.AbstractAgentFactory;
import kalah.agent.factories.AbstractAgentFactoryFactory;
import kalah.exceptions.NoSuchAgentException;

import org.junit.Test;

public class FactoryFactoryTest
{

	@Test
	public void getAgentFactoryTest()
	{
		AbstractAgentFactory factory = AbstractAgentFactoryFactory.get("puremc");
		assertNotNull("No factory given", factory);
	}

	@Test
	public void nonExistentAgentTest()
	{
		try
		{
			AbstractAgentFactory factory = AbstractAgentFactoryFactory.get("neverregisterthispls");
			fail("We actually got a thing?");
		}
		catch(NoSuchAgentException e)
		{

		}
	}

}
