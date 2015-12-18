package kalah.program;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
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
	public static boolean verbose = false;

	static PrintStream log;

	static
	{
		// try
		// {
		// 	Runtime.getRuntime().addShutdownHook(new ShutdownThread());
		// 	FileOutputStream f = new FileOutputStream("output."+System.currentTimeMillis()+".log");
		// 	log = new PrintStream(f);
		// 	System.setErr(log);
		// }
		// catch(IOException e)
		// {
		// 	e.printStackTrace();
		// }
    log = null;
	}

	public static void log(String message)
	{
		if(log == null) return; //Log failed to open
		if(verbose)
			log.println(message);
	}

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

    if      (arg.equals("-c") || arg.equals("--numcallables")) { numCallables = toInt(val,4); }
    else if (arg.equals("-C") || arg.equals("--numcallables")) { cacheBoardStates = false; return false; }
    else if (arg.equals("-g") || arg.equals("--numcallables")) { numGames = toInt(val, 20); }
    else if (arg.equals("-M") || arg.equals("--numcallables")) { maxSLMCTreeDepth = toInt(val,100); }
    else if (arg.equals("-f") || arg.equals("--numcallables")) { fileLoc = val!=null?val:""; }
    else if (arg.equals("-s") || arg.equals("--numcallables")) { useScore=true; return false; }
    else if (arg.equals("-l") || arg.equals("--numcallables")) { lambda=toDouble(val,0.85); }
    else if (arg.equals("-b") || arg.equals("--numcallables")) { boardSize=toInt(val,7); }
    else if (arg.equals("-B") || arg.equals("--numcallables")) { boardC=toInt(val,7); }
    else if (arg.equals("-v") || arg.equals("--numcallables")) { verbose = true; return false; }

		return true;
	}
}
