package kalah.program;

public class ShutdownThread extends Thread
{
	@Override
	public void run()
	{
		if(Configuration.log != null)
			Configuration.log.close();
	}
}
