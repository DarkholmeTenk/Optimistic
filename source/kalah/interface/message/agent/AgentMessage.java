package kalah.interface.message.agent;

public abstract class AgentMessage {

  /**
   * Gets a string representing the message, minus the new line that terminates
   * all messages.
   */
  private abstract String getFragment();

  public String toString() {
    return getFragment() + '\n';
  }

}
