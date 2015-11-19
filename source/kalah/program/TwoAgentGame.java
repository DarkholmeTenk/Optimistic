package kalah.program;

import kalah.agent.AbstractAgent;
import kalah.game.board.Action;
import kalah.game.board.BoardState;
import kalah.game.board.Player;

public class TwoAgentGame
{
	private final AbstractAgent	agentOne;
	private final AbstractAgent	agentTwo;
	private BoardState			state;

	public TwoAgentGame(AbstractAgent p1, AbstractAgent p2)
	{
		agentOne = p1;
		agentTwo = p2;
	}

	private AbstractAgent getAgent(Player p)
	{
		switch(p)
		{
			case PLAYER1: return agentOne;
			default: return agentTwo;
		}
	}

	private AbstractAgent currentAgent()
	{
		return getAgent(state.getCurrentPlayerTurn());
	}

	private AbstractAgent notCurrentAgent()
	{
		return getAgent(state.getCurrentPlayerTurn().getOpponent());
	}

	public int play()
	{
		if(state == null) return 0;
		Action a = null;
		do
		{
			AbstractAgent cur = currentAgent();
			AbstractAgent not = notCurrentAgent();
			a = cur.getNextMove(state);
			if(a == null) break;
			not.opponentAction(state,a);
			state = state.takeAction(a);
		}
		while(a != null);
		return state.getScore(Player.PLAYER1) - state.getScore(Player.PLAYER2);
	}

	public int play(BoardState _state)
	{
		state = _state;
		return play();
	}

	public int play(int size, int counters)
	{
		state = new BoardState(size,counters);
		return play();
	}
}
