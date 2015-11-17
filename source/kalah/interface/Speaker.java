package kalah.interface;

import java.io.DataOutputStream;
import java.net.Socket;

/**
 * Sends messages to a given socket.
 */
public class Speaker {

  private final Socket socket;

  public Speaker(Socket socket) {
    this.socket = socket;
  }

  /**
   * Makes speaker say something to its socket
   *
   * @param message The AgentMessage to send to the game engine
   */
  public void say(AgentMessage message) throws IOException {
    DataOutputStream output = new DataOutputStream(socket.getOutputStream());
    output.writeBytes(message + '\n');
  }

}
