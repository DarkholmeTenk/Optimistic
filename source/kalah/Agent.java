package kalah;

import kalah.engine.message.engine.*;

public class Agent {

  Agent() {}

  public void handle(EngineMessage message) {

    if (message instanceof MoveMessage) {
      handle((MoveMessage) message);
    } else if (message instanceof SwapMessage) {
      handle((SwapMessage) message);
    } else if (message instanceof StartMessage) {
      handle((StartMessage) message);
    } else if (message instanceof GameOverMessage) {
      handle((GameOverMessage) message);
    }

  }

  private void handle(MoveMessage message) {
    // Handle a MoveMessage
  }

  private void handle(SwapMessage message) {
    // Handle a SwapMessage
  }

  private void handle(StartMessage message) {
    // Handle a StartMessage
  }

  private void handle(GameOverMessage message) {
    // Handle a GameOverMessage
  }

}
