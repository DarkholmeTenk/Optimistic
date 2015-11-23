package kalah.agent;

import java.util.List;
import java.util.Random;

import kalah.game.board.Action;
import kalah.game.board.BoardState;
import kalah.game.board.Player;

public class RandomAgent extends AbstractAgent
{
	public static final RandomAgent playerOne = new RandomAgent(Player.PLAYER1);
	public static final RandomAgent playerTwo = new RandomAgent(Player.PLAYER2);
	public static final Random r = new Random();

	private RandomAgent(Player player)
	{
		super(player);
	}

	@Override
	public Action getNextMove(BoardState board)
	{
		if(board.getCurrentPlayerTurn() != agentPlayer) return null;
		List<Action> moves = board.getValidActions();
		if(moves.size() == 0) return null;
		return moves.get(r.nextInt(moves.size()));
	}

	@Override
	public void opponentAction(BoardState state, Action action)
	{
		//Does nothing, not smart enough to care
	}

}
