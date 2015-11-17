package kalah.interface.message.agent;

import kalah.interface.message.Message;

public abstract class AgentMessage {

  private abstract getFragment();

  public String toString() {
    return getFragment() + '\n';
  }

}
