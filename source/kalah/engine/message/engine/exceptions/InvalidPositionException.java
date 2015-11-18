package kalah.engine.message.engine.exceptions;

public class InvalidPositionException extends RuntimeException {

  private final String position;

  public InvalidPositionException(String position) {
		this.position = position;
	}

	@Override
	public String getMessage() {
		return super.getMessage() +
      " - invalid position recieved: '" + position + "'";
	}

}
