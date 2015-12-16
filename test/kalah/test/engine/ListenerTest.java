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

  @Test
  public void testNextSequence() {
    String sequence =
      "CHANGE;4;9,8,1,9,8,8,8,1,8,1,8,0,9,9,9,2;YOU\n" +
      "CHANGE;1;10,9,1,0,9,9,9,2,0,3,10,2,11,10,10,3;YOU\n" +
      "CHANGE;7;11,10,2,1,10,1,11,3,2,5,12,3,12,11,0,4;YOU\n";

    InputStream stream = new ByteArrayInputStream(sequence.getBytes());
    Listener listener = new Listener(stream);

    try {
      assertEquals(((MoveMessage) listener.next()).getHouse(), 3);
      assertEquals(((MoveMessage) listener.next()).getHouse(), 0);
      assertEquals(((MoveMessage) listener.next()).getHouse(), 6);
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }
}
