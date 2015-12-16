package kalah.test;

import static org.junit.Assert.*;

import kalah.engine.message.engine.exceptions.*;
import kalah.engine.message.engine.*;
import kalah.game.board.*;

import kalah.game.board.Position;

import org.junit.BeforeClass;
import org.junit.Test;

/**
*  set of tests to make sure the GIU ( listerner and speaker) are accepting the correct
*  values and returning the right messages when being called. assunming a Listener is
*  the one supplying the messages correctly
*  START
*  CHANGE
*  END
*/

public class MessageFactoryTest{

  @Test
  public void StartTestNorth(){
	  // START; North|South \n
	  StartMessage test;
	  test = (StartMessage) EngineMessageFactory.getMessage("START;North");
	  // test that position is North
	  assertEquals(test.getPosition(),Position.North);
  }
  @Test
  public void StartTestSouth(){
	  // START; North|South \n
	  StartMessage test;
	  test = (StartMessage) EngineMessageFactory.getMessage("START;South");
	  // test that position is South
	  assertEquals(test.getPosition(),Position.South);
  }
  @Test(expected = InvalidPositionException.class)
  public void StartTestException(){
	  // START; North|South \n
	  StartMessage test;
	  // test that Messages is invalid and throws exception
		  test = (StartMessage) EngineMessageFactory.getMessage("START;north");
  }

  @Test
  public void EndTest(){
	  // END\n
	  // test that a game overmessage has been recieved
	  assertTrue(EngineMessageFactory.getMessage("END") instanceof GameOverMessage);
  }
  @Test(expected = InvalidMessageNameException.class)
  public void EndTestException(){
	  // END\n
	  // test that a exception is thorm from invalid Message EN
	  assertTrue(EngineMessageFactory.getMessage("EN") instanceof GameOverMessage);
  }

  @Test
  public void ChangeTestSwap(){
	 assertTrue(EngineMessageFactory.getMessage("CHANGE;SWAP;7,7,7,7,7,7,7,0,7,7,7,7,7,7,7,0;YOU") instanceof SwapMessage);
  }
  @Test
  public void ChangeTestMove(){
	 assertEquals(((MoveMessage) EngineMessageFactory.getMessage("CHANGE;1;0,8,8,8,8,8,8,1,7,7,7,7,7,7,7,0;YOU")).getHouse(), 0);
  }
  @Test(expected = InvalidChangeTypeException.class)
  public void ChangeTestException(){
	 EngineMessageFactory.getMessage("CHANGE;MOE;");
  }
}
