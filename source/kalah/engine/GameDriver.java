package kalah.engine;

import kalah.agent.AbstractAgent;
import kalah.agent.factories.AbstractAgentFactory;
import kalah.game.board.Action;
import kalah.game.board.BoardState;
import kalah.game.board.Player;
import kalah.program.Configuration;

public abstract class GameDriver
{
	protected final AbstractAgent playerOne;
	protected final AbstractAgent playerTwo;
	protected BoardState board;

	protected GameDriver(
			AbstractAgentFactory factoryOne,
			AbstractAgentFactory factoryTwo,
			Player factoryOneIs,
			BoardState board)
	{
		if (factoryOneIs == Player.PLAYER1)
		{
			playerOne = factoryOne.getAgent(Player.PLAYER1);
			playerTwo = factoryTwo.getAgent(Player.PLAYER2);
		}
		else
		{
			playerTwo = factoryOne.getAgent(Player.PLAYER2);
			playerOne = factoryTwo.getAgent(Player.PLAYER1);
		}

		this.board = board;
	}

	private AbstractAgent getAgent(Player player)
	{
		if (player == Player.PLAYER1)
			return playerOne;
		else
			return playerTwo;
	}

	/**
	 * Steps through one turn of a game.
	 *
	 * @return False if the game cannot continue, true otherwise
	 */
	public boolean step()
	{
    Configuration.log(board.toString());

		Player active = board.getCurrentPlayerTurn();
		Player inactive = active.getOpponent();
		Action action = getAgent(active).getNextMove(board);

		if (action != null)
		{
			Configuration.log("Action: " + action.toString());

			BoardState newBoard = board.takeAction(action);
			getAgent(inactive).opponentAction(board, action);
			board = newBoard;
			return true;
		}
		else
			return false;
	}

	/**
	 * Runs through a complete game.
	 */
	public void complete()
	{
		while(step());

		playerOne.finishGame();
		playerTwo.finishGame();
	}

	public BoardState getGameState() { return board; }
}
