package diaballik.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.Objects;


public class MovePion extends Command {


	@JsonCreator
	public MovePion(@JsonProperty("oldX") final int oldX, @JsonProperty("oldY") final int oldY, @JsonProperty("newX") final int newX, @JsonProperty("newY") final int newY) {
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
		final MovePion movePion = (MovePion) o;
		return getOldX() == movePion.getOldX() &&
				getOldY() == movePion.getOldY() &&
				getNewX() == movePion.getNewX() &&
				getNewY() == movePion.getNewY();
	}

	@Override
	public int hashCode() {

		return Objects.hash(getOldX(), getOldY(), getNewX(), getNewY());
	}

	@Override
	public int getNewX() {
		return newX;
	}

	@Override
	public int getNewY() {
		return newY;
	}

	@Override
	public int getOldX() {
		return oldX;
	}

	@Override
	public int getOldY() {
		return oldY;
	}

	@Override
	public void commandDo(final Game game) {
		final Board board = game.getBoard();
		final Pawn p = board.getPiece(this.oldX, this.oldY);
		p.setPos(this.newX, this.newY);
		board.setPiece(this.newX, this.newY, p);
		board.setPiece(this.oldX, this.oldY, null);
		game.addSave(this);
	}

	@Override
	public void commandUndo(final Game game) {
		final Board board = game.getBoard();
		final Pawn p = board.getPiece(this.newX, this.newY);
		p.setPos(this.oldX, this.oldY);
		board.setPiece(this.oldX, this.oldY, p);
		board.setPiece(this.newX, this.newY, null);
		game.addUndo(this);

	}
}
