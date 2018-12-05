package diaballik.model;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
//import java.util.Objects;

/**
 * Class of a pawn
 */
public class Pawn extends Element {
	protected int x;
	protected int y;
	//protected int id;
	protected boolean hasBall;

	/**
	 * @return if the Pawn is in the Board
	 */


	/**
	 * @param x       the initial value of x position
	 * @param y       the initial value of y position
	 * @param hasBall does the Pawn own the ball or not
	 * @param color   the color of the player to who belong this pawn
	 */
	@JsonCreator
	public Pawn(@JsonProperty("x") final int x, @JsonProperty("y") final int y, @JsonProperty("hasBall") final boolean hasBall, @JsonProperty("color") final Color color) {
		super(color);
		this.x = x;
		this.y = y;
		//this.id = id;
		this.hasBall = hasBall;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public void setColor(final Color color) {
		this.color = color;
	}

	/*
	public void setX(final int x) {
		this.x = x;
	}
	*/

	/*public int getId() {
		return this.id;
	}*/

	public boolean hasBall() {
		return this.hasBall;
	}

	public void setPos(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	public void setHasBall(final boolean ball) {
		this.hasBall = ball;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final Pawn pawn = (Pawn) o;
		return getX() == pawn.getX() &&
				getY() == pawn.getY() &&
				hasBall == pawn.hasBall;
	}

	/**
	 * @return the hashCode of the Pawn
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getX(), getY(), hasBall);
	}

	public ArrayList<Command> movePlayable(final Game game) {
		final Board gameBoard = game.getBoard();
		final ArrayList<Command> res = new ArrayList<Command>();
		if (this.color != game.getColor()) {
			return res;
		}
		if (this.hasBall) {
			final Stream<Pawn> streamPiece = gameBoard.getList().stream().filter(p -> p.getColor() == this.color);
			streamPiece.forEach(p -> {
				if (this.canPassTo(p, gameBoard)) {
					res.add(new MoveBall(this.x, this.y, p.getX(), p.getY()));
				}
			});
		} else {
			res.addAll(this.getMovePiece(game));
		}
		return res;
	}

	public ArrayList<Command> getMovePiece(final Game game) {
		final Board gameBoard = game.getBoard();
		final ArrayList<Command> res = new ArrayList<Command>();
		if (x < 6 && gameBoard.getPiece(x + 1, y) == null) {
			res.add(new MovePion(x, y, x + 1, y));
		}
		if (y < 6 && gameBoard.getPiece(x, y + 1) == null) {
			res.add(new MovePion(x, y, x, y + 1));
		}
		if (x > 0 && gameBoard.getPiece(x - 1, y) == null) {
			res.add(new MovePion(x, y, x - 1, y));
		}
		if (y > 0 && gameBoard.getPiece(x, y - 1) == null) {
			res.add(new MovePion(x, y, x, y - 1));
		}
		return res;
	}

	public boolean canPassDiag(final int newX, final int newY, final Board gameBoard) {
		final ArrayList<Pawn> ennemie = new ArrayList<Pawn>();
		final int dx = (newX > this.x) ? 1 : -1;
		final int dy = (newY > this.y) ? 1 : -1;
		IntStream.iterate(this.x + dx, x -> x + dx).limit((Math.abs(this.x - newX)) - 1).forEach((int valX) -> {
			IntStream.iterate(this.y + dy, y -> y + dy).limit((Math.abs(this.y - newY)) - 1).forEach((int valY) -> {
				if (gameBoard.getPiece(valX, valY) != null) {
					ennemie.add(gameBoard.getPiece(valX, valY));
				}
			});
		});
		return ennemie.size() <= 0;
	}

	public boolean canPassLineX(final int newY, final Board gameBoard) {
		final ArrayList<Pawn> ennemie = new ArrayList<Pawn>();
		final int dy = (newY > this.y) ? 1 : -1;
		IntStream.iterate(this.y + dy, y -> y + dy).limit((Math.abs(this.y - newY)) - 1).forEach((int valY) -> {
			if (gameBoard.getPiece(this.x, valY) != null) {
				ennemie.add(gameBoard.getPiece(this.x, valY));
			}
		});
		return ennemie.size() <= 0;
	}

	public boolean canPassLineY(final int newX, final Board gameBoard) {
		final ArrayList<Pawn> ennemie = new ArrayList<Pawn>();
		final int dx = (newX > this.x) ? 1 : -1;
		IntStream.iterate(this.x + dx, x -> x + dx).limit((Math.abs(this.x - newX)) - 1).forEach((int valX) -> {
			if (gameBoard.getPiece(valX, this.y) != null) {
				ennemie.add(gameBoard.getPiece(valX, this.y));
			}
		});
		return ennemie.size() <= 0;
	}

	public boolean canPassTo(final Pawn p, final Board gameBoard) {
		if (this.isSamePos(p)) {
			return false;
		}
		if (this.x == p.getX() && this.y != p.getY() && this.canPassLineX(p.getY(), gameBoard)) {
			return true;
		} else if (this.y == p.getY() && this.x != p.getX() && this.canPassLineY(p.getX(), gameBoard)) {
			return true;
		} else if (Math.abs(this.x - p.getX()) == Math.abs(this.y - p.getY()) && this.canPassDiag(p.getX(), p.getY(), gameBoard)) {
			return true;
		}
		return false;
	}

	public boolean isSamePos(final Pawn p) {
		if (this.x == p.getX() && this.y == p.getY()) {
			return true;
		}
		return false;
	}

}
