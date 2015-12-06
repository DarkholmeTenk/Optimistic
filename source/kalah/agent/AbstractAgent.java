package kalah.agent;

import kalah.game.board.Action;
import kalah.game.board.BoardState;
import kalah.game.board.Player;

/**
 * Agents should extend this class and be constructed with the player they are playing as.
 * @author Shane
 *
 */
public abstract class AbstractAgent
{
	public final Player agentPlayer;

	/**
	 * Generate the agent for the player specified
	 * @param player the player who this agent represents
	 */
	public AbstractAgent(Player player)
	{
		agentPlayer = player;
	}

	/**
	 * This move is where the agent decides what action to take
	 * @param board the current state of the game board
	 * @return the action that the agent wants to take. Return null if no action can be taken!
	 */
	public abstract Action getNextMove(BoardState board);

	/**
	 * This method is called whenever the opponent makes a move.
	 * It's not necessary to do anything, for this.
	 * @param state the state the action was taken from
	 * @param action the action the opponent has taken
	 */
	public abstract void opponentAction(BoardState state, Action action);
}
