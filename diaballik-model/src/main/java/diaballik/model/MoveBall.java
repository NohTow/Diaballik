package diaballik.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
//import java.util.Objects;

public class MoveBall implements Command {
	private int oldX;
	private int oldY;
	private int newX;
	private int newY;

	@JsonCreator
	public MoveBall(@JsonProperty("oldX") final int oldX, @JsonProperty("oldY") final int oldY, @JsonProperty("newX") final int newX, @JsonProperty("newY") final int newY) {
		this.oldX = oldX;
		this.oldY = oldY;
		this.newX = newX;
		this.newY = newY;
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
}
