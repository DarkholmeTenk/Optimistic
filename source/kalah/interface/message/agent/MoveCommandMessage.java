package kalah.interface.message.agent;

public class MoveCommandMessage implements AgentMessage {

  private final Action action;

  public MoveCommandMessage(Action action) {
    this.action = action;
  }

  private String getFragment() {
    return "MOVE;" + (action.house + 1);
  }

}
