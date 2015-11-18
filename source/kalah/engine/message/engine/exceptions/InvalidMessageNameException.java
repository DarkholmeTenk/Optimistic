package kalah.engine.message.engine.exceptions;

public class InvalidMessageNameException extends RuntimeException {

  private final String messageName;

  public InvalidMessageNameException(String messageName) {
		this.messageName = messageName;
	}

	@Override
	public String getMessage() {
		return super.getMessage() +
      " - invalid message name recieved: '" + messageName + "'";
	}

}
