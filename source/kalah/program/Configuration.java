package kalah.program;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Configuration
{
	public static ExecutorService executor = Executors.newCachedThreadPool();
	public static boolean cacheBoardStates = true;
	public static int numCallables = 4;
	public static int numGames = 20;
	public static int maxSLMCTreeDepth = 100;
	public static String fileLoc = "";
	public static boolean useScore = false;
	public static double lambda = 0.85;

	public static int boardSize = 7;
	public static int boardC = 7;

	public static List<String> readArgs(String... args)
	{
		List<String> remaining = new ArrayList<String>();
		for(int i = 0; i < args.length; i++)
		{
			String a = args[i];
			if(a.startsWith("-"))
			{
				String b = null;
				if(i < args.length - 1)
				{
					String ns = args[i+1];
					if(!ns.startsWith("-"))
					{
						b = ns;
						i++;
					}
				}
				if(!handleArg(a, b) && b != null)
					remaining.add(b);
			}
			else
				remaining.add(a);
		}
		return remaining;
	}

	public static int toInt(String s, int defaultInt)
	{
		try
		{
			return Integer.parseInt(s);
		}
		catch(NumberFormatException e)
		{
			System.err.println(s + " is not a number, using " + defaultInt + " instead");
		}
		catch(NullPointerException e)
		{
			System.err.println("Err received, using " + defaultInt + " instead");
		}
		return defaultInt;
	}

	public static double toDouble(String s, double defaultDouble)
	{
		try
		{
			return Double.parseDouble(s);
		}
		catch(NumberFormatException e)
		{
			System.err.println(s + " is not a number, using " + defaultDouble + " instead");
		}
		catch(NullPointerException e)
		{
			System.err.println("Err received, using " + defaultDouble + " instead");
		}
		return defaultDouble;
	}

	/**
	 * @param arg the name of the argument
	 * @param val the value the argument takes
	 * @return true if the val argument is consumed, false if not
	 */
	public static boolean handleArg(String arg, String val)
	{
		if(arg.length() > 2)
			arg = arg.toLowerCase();
		switch(arg)
		{
			case "-c": case "--numcallables": numCallables = toInt(val,4); break;
			case "-C": case "--nocache": cacheBoardStates = false; return false;
			case "-g": case "--numgames": numGames = toInt(val, 20); break;
			case "-M": case "--maxtreedepth": maxSLMCTreeDepth = toInt(val,100); break;
			case "-f": case "--fileloc": fileLoc = val!=null?val:""; break;
			case "-s": case "--usescores": useScore=true; return false;
			case "-l": case "--lambda": lambda=toDouble(val,0.85); break;
			case "-b": case "--boardsize": boardSize=toInt(val,7); break;
			case "-B": case "--boardcounters": boardC=toInt(val,7); break;
		}
		return true;
	}
}
