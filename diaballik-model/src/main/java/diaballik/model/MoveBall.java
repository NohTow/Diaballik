package diaballik.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Objects;
//import java.util.Objects;

public class MoveBall extends Command {

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

	@Override
	public void commandDo(final Game game) {
		final Board board = game.getBoard();
		board.getPiece(newX, newY).setHasBall(true);
		board.getPiece(oldX, oldY).setHasBall(false);
		game.addSave(this);
	}

	@Override
	public void commandUndo(final Game game) {
		final Board board = game.getBoard();
		board.getPiece(newX, newY).setHasBall(false);
		board.getPiece(oldX, oldY).setHasBall(true);
		game.addUndo(this);
	}


}
