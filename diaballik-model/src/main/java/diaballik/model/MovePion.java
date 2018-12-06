package diaballik.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.Objects;


/**
 * Representation of the movement from a place to another one of a pawn
 */
public class MovePion extends Command {


	/**
	 * @param oldX old x coordinate
	 * @param oldY old y coordinate
	 * @param newX new x coordinate
	 * @param newY new y coordinate
	 */
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

	/**
	 * Old coordinate is set to null (so there's no pawn anymore)
	 * New coordinate is set to the Pawn
	 * The action is added to save list
	 */
	@Override
	public void commandDo(final Game game) {
		final Board board = game.getBoard();
		final Pawn p = board.getPiece(this.oldX, this.oldY);
		p.setPos(this.newX, this.newY);
		board.setPiece(this.newX, this.newY, p);
		board.setPiece(this.oldX, this.oldY, null);
		game.addSave(this);
	}

	/**
	 * Inverse of commandDo
	 * The action is added to undo list
	 */
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
