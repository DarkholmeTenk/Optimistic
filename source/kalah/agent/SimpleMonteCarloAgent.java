package kalah.agent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import kalah.game.board.Action;
import kalah.game.board.BoardState;
import kalah.game.board.Player;
import kalah.program.Configuration;
import kalah.program.TwoAgentGameCallable;

public class SimpleMonteCarloAgent extends AbstractAgent
{
	public static final SimpleMonteCarloAgent playerOne = new SimpleMonteCarloAgent(Player.PLAYER1);
	public static final SimpleMonteCarloAgent playerTwo = new SimpleMonteCarloAgent(Player.PLAYER2);

	private List<TwoAgentGameCallable> callables = new ArrayList<TwoAgentGameCallable>();

	private SimpleMonteCarloAgent(Player player)
	{
		super(player);
		for(int i = 0; i < Configuration.numCallables; i++)
			callables.add(new TwoAgentGameCallable(RandomAgent.playerOne, RandomAgent.playerTwo));
	}

	/**
	 * runs a bunch of simulations to evaluate the score of a given board state
	 * @param state
	 * @return
	 */
	private double getScore(BoardState state)
	{
		double average = 0;
		synchronized(callables)
		{
			for(TwoAgentGameCallable call : callables)
				call.setState(state);
			try
			{
				List<Future<Double>> values = Configuration.executor.invokeAll(callables);
				int size = values.size();
				for(Future<Double> val : values)
					average += val.get() / size;
			}
			catch(Exception e)
			{
				throw new RuntimeException(e);
			}
		}
		return average;
	}

	private boolean isBetter(double score, double oldBest)
	{
		if(agentPlayer == Player.PLAYER1)
			return score > oldBest;
			return score < oldBest;
	}

	@Override
	public synchronized Action getNextMove(BoardState board)
	{
		if(board.getCurrentPlayerTurn() != agentPlayer) return null;
		List<Action> moves = board.getValidActions();
		if(moves.size() == 0) return null;
		if(moves.size() == 1) return moves.get(0);
		double bestScore = agentPlayer == Player.PLAYER1 ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		Action a = null;
		for(Action act : moves)
		{
			BoardState newState = board.takeAction(act);
			double score = getScore(newState);
			if(isBetter(score, bestScore))
			{
				a = act;
				bestScore = score;
			}
		}
		return a;
	}

	@Override
	public void opponentAction(BoardState state, Action action)
	{
		// Still don't really care about this
	}

}
