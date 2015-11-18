package kalah.engine.message.engine.exceptions;

public class InvalidChangeTypeException extends RuntimeException {

  private final String changeType;

  public InvalidChangeTypeException(String changeType) {
		this.changeType = changeType;
	}

	@Override
	public String getMessage() {
		return super.getMessage() +
      " - invalid change type recieved: '" + changeType + "'";
	}

}
