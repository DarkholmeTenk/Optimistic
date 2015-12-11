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

	private StoredGameTree<Pair<Double,Double>> dataTree;
	private SimpleLearningMonteCarloAgent(Player player)
	{
		super(player);
		for(int i = 0; i < Configuration.numCallables; i++)
			callables.add(new TwoAgentGameCallable(RandomAgent.playerOne, RandomAgent.playerTwo));
	}

	private StoredGameTreeInternal<Pair<Double,Double>> getIntTree(BoardState state)
	{
		if(dataTree == null) return null;
		return dataTree.getInternalTree(state);
	}

	private void addScore(StoredGameTreeInternal<Pair<Double,Double>> intTree, double score, double toAdd)
	{
		if(intTree.data == null)
			return;
		double oldAverage = intTree.data.a;
		double num = intTree.data.b;
		double mult = num/(num+toAdd);
		double newAverage = score/num;
		score = (oldAverage + newAverage) * mult;
		intTree.data = new Pair(score,num+toAdd);
	}

	/**
	 * runs a bunch of simulations to evaluate the score of a given board state
	 * @param state
	 * @return
	 */
	@Override
	protected double getScore(BoardState state)
	{
		StoredGameTreeInternal<Pair<Double,Double>> intTree = getIntTree(state);
		if(intTree == null)
			return super.getScore(state);
		double average = super.getScore(state);
		if(intTree.data == null)
			intTree.data = new Pair(average, (double)1);
		else
		{
			addScore(intTree, average, 1);
			double l = Configuration.lambda;
			intTree = intTree.parent;
			while(l > 0.1 && intTree != null)
			{
				addScore(intTree,average * l, l);
				l *= Configuration.lambda;
				intTree = intTree.parent;
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
		StoredGameTreeInternal<Pair<Double,Double>> intTree = getIntTree(board);
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
		String fn = Configuration.fileLoc + "slmc."+state.size + "." + state.numC +"." + Configuration.useScore + ".ser";
		return new File(fn);
	}

	@Override
	public void informOfState(BoardState board)
	{
		super.informOfState(board);
		if(dataTree != null && board.equals(dataTree.startingBoardState))
			return;
		if(dataTree != null)
			save();
		File f = getFile(board);
		if(f.exists())
		{
			FileInputStream fis = null;
			try
			{
				fis = new FileInputStream(f);
				ObjectInputStream ois = new ObjectInputStream(fis);
				dataTree = (StoredGameTree<Pair<Double,Double>>)ois.readObject();
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
	public void save()
	{
		super.save();
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
