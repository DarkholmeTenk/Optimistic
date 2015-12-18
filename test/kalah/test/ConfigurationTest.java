package kalah.test;

import static org.junit.Assert.*;

import java.util.List;

import kalah.program.Configuration;

import org.junit.Test;

public class ConfigurationTest
{

	@Test
	public void testNumCallables()
	{
		Configuration.numCallables = 0;
		Configuration.readArgs("-c","7");
		assertEquals("Num callables was not set (Short)",Configuration.numCallables, 7);
		Configuration.readArgs("--numCallables","4");
		assertEquals("Num callables was not set (Full)",Configuration.numCallables, 4);
		Configuration.readArgs("-test","-test2","-c","5","test3");
		assertEquals("Num callables was not set (Extra)", Configuration.numCallables, 5);
		Configuration.readArgs("-c");
		assertEquals("Num callables was not defaulted", Configuration.numCallables, 4);
	}

	@Test
	public void testCache()
	{
		Configuration.cacheBoardStates = true;
		Configuration.readArgs("-C");
		assertFalse("Cache not set to false (Short)",Configuration.cacheBoardStates);
		Configuration.cacheBoardStates = true;
		Configuration.readArgs("--nocache");
		assertFalse("Cache not set to false (Long)",Configuration.cacheBoardStates);
		Configuration.cacheBoardStates = true;
		Configuration.readArgs("-Test","-C","test","tsdfsdf");
		assertFalse("Cache not set to false (Extra)",Configuration.cacheBoardStates);
	}

	@Test
	public void testNumGames()
	{
		Configuration.numGames = 0;
		Configuration.readArgs("-g","7");
		assertEquals("Num games was not set (Short)",Configuration.numGames, 7);
		Configuration.readArgs("--numGames","4");
		assertEquals("Num games was not set (Full)",Configuration.numGames, 4);
		Configuration.readArgs("-test","-test2","-g","5","test3");
		assertEquals("Num games was not set (Extra)", Configuration.numGames, 5);
		Configuration.readArgs("-g");
		assertEquals("Num games was not defaulted", Configuration.numGames, 20);
	}

	@Test
	public void testMaxTreeDepth()
	{
		String sh = "-M";
		String lo = "--maxTreeDepth";
		String name = "Tree depth";
		Configuration.maxSLMCTreeDepth = 0;
		Configuration.readArgs(sh,"7");
		assertEquals(name + " was not set (Short)",Configuration.maxSLMCTreeDepth, 7);
		Configuration.readArgs(lo,"4");
		assertEquals(name + " was not set (Full)",Configuration.maxSLMCTreeDepth, 4);
		Configuration.readArgs("-test","-test2",sh,"5","test3");
		assertEquals(name + " was not set (Extra)", Configuration.maxSLMCTreeDepth, 5);
		Configuration.readArgs(sh);
		assertEquals(name + " was not defaulted", Configuration.maxSLMCTreeDepth, 100);
	}

	@Test
	public void testFileLoc()
	{
		String sh = "-f";
		String lo = "--fileloc";
		Configuration.fileLoc = "";
		Configuration.readArgs(sh,"7");
		assertEquals("File location was not set (Short)",Configuration.fileLoc, "7");
		Configuration.readArgs(lo,"4");
		assertEquals("File location was not set (Full)",Configuration.fileLoc, "4");
		Configuration.readArgs("-test","-test2",sh,"5","test3");
		assertEquals("File location was not set (Extra)", Configuration.fileLoc, "5");
		Configuration.readArgs(sh);
		assertEquals("File location was not defaulted", Configuration.fileLoc, "");
	}

	@Test
	public void testUseScores()
	{
		Configuration.useScore = false;
		Configuration.readArgs("-s");
		assertTrue("Scores not set to true (Short)",Configuration.useScore);
		Configuration.useScore = false;
		Configuration.readArgs("--useScores");
		assertTrue("Scores not set to true (Long)",Configuration.useScore);
		Configuration.useScore = false;
		Configuration.readArgs("abc","-test", "-s", "-ttsdf");
		assertTrue("Scores not set to true (Extra)",Configuration.useScore);
	}

	@Test
	public void testLambda()
	{
		String sh = "-l";
		String lo = "--Lambda";
		String name = "Lambda";
		Configuration.lambda = 0;
		Configuration.readArgs(sh,"7");
		assertTrue(name + " was not set (Short)",Configuration.lambda == 7);
		Configuration.readArgs(lo,"4");
		assertTrue(name + " was not set (Full)",Configuration.lambda == 4);
		Configuration.readArgs("-test","-test2",sh,"5","test3");
		assertTrue(name + " was not set (Extra)", Configuration.lambda == 5);
		Configuration.readArgs(sh);
		assertTrue(name + " was not defaulted", Configuration.lambda == 0.85);
	}

	@Test
	public void testBoardSize()
	{
		String sh = "-b";
		String lo = "--boardsize";
		String name = "Board Size";
		Configuration.boardSize = 0;
		Configuration.readArgs(sh,"7");
		assertTrue(name + " was not set (Short)",Configuration.boardSize == 7);
		Configuration.readArgs(lo,"4");
		assertTrue(name + " was not set (Full)",Configuration.boardSize == 4);
		Configuration.readArgs("-test","-test2",sh,"5","test3");
		assertTrue(name + " was not set (Extra)", Configuration.boardSize == 5);
		Configuration.readArgs(sh);
		assertTrue(name + " was not defaulted", Configuration.boardSize == 7);
	}

	@Test
	public void testBoardCounters()
	{
		String sh = "-B";
		String lo = "--boardCounters";
		String name = "Board Counters";
		Configuration.boardC = 0;
		Configuration.readArgs(sh,"7");
		assertTrue(name + " was not set (Short)",Configuration.boardC == 7);
		Configuration.readArgs(lo,"4");
		assertTrue(name + " was not set (Full)",Configuration.boardC == 4);
		Configuration.readArgs("-test","-test2",sh,"5","test3");
		assertTrue(name + " was not set (Extra)", Configuration.boardC == 5);
		Configuration.readArgs(sh);
		assertTrue(name + " was not defaulted", Configuration.boardC == 7);
	}

	@Test
	public void testArgumentRemoval()
	{
		List<String> args = Configuration.readArgs("-B", "test", "-b" , "7", "agentType", "agentTwo");
		assertTrue("Args size != 2, is " + args.size(),args.size() == 2);
		assertTrue("Args does not contain agentType", args.contains("agentType"));
		assertTrue("Args does not contain agentTwo", args.contains("agentTwo"));
	}
}
