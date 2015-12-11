package kalah.agent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import kalah.game.board.Action;
import kalah.game.board.BoardState;
import kalah.game.board.Player;
import kalah.game.board.storage.Pair;
import kalah.game.board.storage.StoredGameTree;
import kalah.game.board.storage.StoredGameTreeInternal;
import kalah.program.Configuration;
import kalah.program.TwoAgentGameCallable;

public class SimpleLearningMonteCarloAgent extends SimpleMonteCarloAgent
{
	public static final SimpleLearningMonteCarloAgent playerOne = new SimpleLearningMonteCarloAgent(Player.PLAYER1);
	public static final SimpleLearningMonteCarloAgent playerTwo = new SimpleLearningMonteCarloAgent(Player.PLAYER2);

	private List<TwoAgentGameCallable> callables = new ArrayList<TwoAgentGameCallable>();

	private StoredGameTree<Pair<Double,Integer>> dataTree;
	private SimpleLearningMonteCarloAgent(Player player)
	{
		super(player);
		for(int i = 0; i < Configuration.numCallables; i++)
			callables.add(new TwoAgentGameCallable(RandomAgent.playerOne, RandomAgent.playerTwo));
	}

	private StoredGameTreeInternal<Pair<Double,Integer>> getIntTree(BoardState state)
	{
		if(dataTree == null) return null;
		return dataTree.getInternalTree(state);
	}

	/**
	 * runs a bunch of simulations to evaluate the score of a given board state
	 * @param state
	 * @return
	 */
	@Override
	protected double getScore(BoardState state)
	{
		StoredGameTreeInternal<Pair<Double,Integer>> intTree = getIntTree(state);
		if(intTree == null)
			return super.getScore(state);
		double average = super.getScore(state);
		if(intTree.data == null)
			intTree.data = new Pair(average, 1);
		else
		{
			double oldAverage = intTree.data.a;
			int num = intTree.data.b;
			double mult = (double)num/(num+1);
			double newAverage = average/num;
			average = (oldAverage + newAverage) * mult;
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
		StoredGameTreeInternal<Pair<Double,Integer>> intTree = getIntTree(board);
		for(Action act : moves)
		{
			if(intTree != null && intTree.depth < Configuration.maxSLMCTreeDepth)
				intTree.takeAction(act);
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
		StoredGameTreeInternal intTree = getIntTree(state);
		if(intTree != null && intTree.depth < Configuration.maxSLMCTreeDepth)
			intTree.takeAction(action);
	}

	private File getFile(BoardState state)
	{
		String fn = Configuration.fileLoc + "slmc."+state.size + "." + state.numC + ".ser";
		return new File(fn);
	}

	@Override
	public void informOfState(BoardState board)
	{
		super.informOfState(board);
		File f = getFile(board);
		if(f.exists())
		{
			FileInputStream fis = null;
			try
			{
				fis = new FileInputStream(f);
				ObjectInputStream ois = new ObjectInputStream(fis);
				dataTree = (StoredGameTree<Pair<Double,Integer>>)ois.readObject();
				return;
			}
			catch(Exception e){}
			finally
			{
				if(fis != null)
					try
				{
						fis.close();
				}
				catch(IOException e){}
			}
		}
		dataTree = new StoredGameTree(board);
	}

	@Override
	public void finishGame()
	{
		super.finishGame();
		if(dataTree != null)
		{
			File f = getFile(dataTree.startingBoardState);
			FileOutputStream fos = null;
			try
			{
				fos = new FileOutputStream(f);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(dataTree);
				oos.close();
			}
			catch(Exception e){}
			finally
			{
				try
				{
					if(fos != null)
						fos.close();
				}
				catch(Exception e){}
			}
		}
	}

}
