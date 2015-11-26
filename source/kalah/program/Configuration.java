package kalah.program;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Configuration
{
	public static ExecutorService executor = Executors.newCachedThreadPool();
	public static boolean cacheBoardStates = true;
	public static int numCallables = 4;
	public static int numGames = 20;
	public static boolean			allowSwap			= true;
}
