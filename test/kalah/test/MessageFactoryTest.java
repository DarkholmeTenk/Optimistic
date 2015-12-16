package kalah.test;

import static org.junit.Assert.*;

import kalah.engine.message.engine.exceptions.*;
import kalah.engine.message.engine.EngineMessageFactory;
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
  public static void StartTestNorth(){
	  // START; North|South \n
	  StartMessage test;
	  test = EngineMessageFactory.getMessage("START;North\n");
	  // test that position is North
	  assertEquals(test.getPosition(),Position.North);
  }
  @Test
  public static void StartTestSouth(){
	  // START; North|South \n
	  StartMessage test;
	  test = EngineMessageFactory.getMessage("START;South\n");
	  // test that position is South
	  assertEquals(test.getPosition(),Position.South);
  }
  @Test(expected = InvalidPositionException.class)
  public static void StartTestException(){
	  // START; North|South \n
	  StartMessage test;
	  // test that Messages is invalid and throws exception
		  test = EngineMessageFactory.getMessage("START;north\n");
  }
  
  @Test
  public static void EndTest(){
	  // END\n
	  // test that a game overmessage has been recieved
	  assertEquals(EngineMessageFactory.getMessage("END\n"), new GameOverMessage());
  }
  @Test(expected = InvalidMessageNameException.class)
  public static void EndTestException(){
	  // END\n
	  // test that a exception is thorm from invalid Message EN
	  assertEquals(EngineMessageFactory.getMessage("EN\n"), new GameOverMessage());
  }
  
  @Test
  public static void ChangeTestSwap(){
	 assertEquals(EngineMessageFactory.getMessage("CHANGE;SWAP;7,7,7,7,7,7,7,0,7,7,7,7,7,7,7,0;YOU\n;"), new SwapMessage());
  }
  @Test
  public static void ChangeTestMove(){
	 assertEquals(EngineMessageFactory.getMessage("CHANGE;MOVE;0,8,8,8,8,8,8,1,7,7,7,7,7,7,7,0;YOU\n"), new MoveMessage());
  }
  @Test(expected = InvalidChangeTypeException.class)
  public static void ChangeTestException(){
	 EngineMessageFactory.getMessage("CHNGE;MOE;\n");
  }
}