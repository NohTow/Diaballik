package diaballik.model;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;


/**
 * The representation of the transfer of the ball beetween 2 pawns
 */
public class MoveBall extends Command {

	/**
	 * @param oldX x coordinate of the giver pawn
	 * @param oldY y coordinate of the giver pawn
	 * @param newX x coordinate of the receiver pawn
	 * @param newY y coordinate of the receiver pawn
	 */
	@JsonCreator
	public MoveBall(@JsonProperty("oldX") final int oldX, @JsonProperty("oldY") final int oldY, @JsonProperty("newX") final int newX, @JsonProperty("newY") final int newY) {
		super(oldX, oldY, newX, newY);
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final MoveBall moveBall = (MoveBall) o;
		return getOldX() == moveBall.getOldX() &&
				getOldY() == moveBall.getOldY() &&
				getNewX() == moveBall.getNewX() &&
				getNewY() == moveBall.getNewY();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getOldX(), getOldY(), getNewX(), getNewY());
	}

	/**
	 * The giver pawn doesn't have the ball anymore, the receiver does
	 * The action is added to save list
	 */
	@Override
	public void commandDo(final Game game) {
		final Board board = game.getBoard();
		board.getPiece(newX, newY).setHasBall(true);
		board.getPiece(oldX, oldY).setHasBall(false);
		game.addSave(this);
	}

	/**
	 * Inverse of commandDo
	 * The action is added to undo list
	 */
	@Override
	public void commandUndo(final Game game) {
		final Board board = game.getBoard();
		board.getPiece(newX, newY).setHasBall(false);
		board.getPiece(oldX, oldY).setHasBall(true);
		game.addUndo(this);
	}


}
