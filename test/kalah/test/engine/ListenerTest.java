package kalah.test;

import static org.junit.Assert.*;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.IOException;

import kalah.engine.Listener;
import kalah.engine.message.engine.*;

public class ListenerTest {
  @Test
  public void testNext() {
    String message = "CHANGE;4;9,8,1,9,8,8,8,1,8,1,8,0,9,9,9,2;YOU\n";
    InputStream stream = new ByteArrayInputStream(message.getBytes());
    Listener listener = new Listener(stream);

    try {
      assertEquals(((MoveMessage) listener.next()).getHouse(), 3);
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }
}
