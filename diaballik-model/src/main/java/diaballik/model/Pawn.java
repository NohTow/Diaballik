package diaballik.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Pawn extends Element {
	protected int x;
	protected int y;
	//protected int id;
	protected boolean hasBall;

	public boolean isInBoard() {
		if (this.getX() < 0 || this.getX() > 6 || this.getY() < 0 || this.getY() > 6) {
			return false;
		}else {
			return true;
		}
	}


	public Pawn(final int x, final int y, final boolean hasBall, final Color color) {
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
		this.color = color ;
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


	ArrayList<Command> movePlayable(final Board gameBoard) {
		if(this.hasBall){

		}
		final ArrayList<Command> res = new ArrayList<Command>();
		if (gameBoard.getPiece(x + 1, y) == null) {
			res.add(new MovePion(x, y, x + 1, y));
		}
		if (gameBoard.getPiece(x, y + 1) == null) {
			res.add(new MovePion(x, y, x, y + 1));
		}
		if (gameBoard.getPiece(x - 1, y) == null) {
			res.add(new MovePion(x, y, x - 1, y));
		}
		if (gameBoard.getPiece(x, y - 1) == null) {
			res.add(new MovePion(x, y, x, y - 1));
		}

		return res;
	}

	List<Command> moveBallPossible(final Board gameBoard) {
		final ArrayList<Command> res = new ArrayList<Command>();
		IntStream.range(1, 6).forEach(i -> {
			if (gameBoard.getPiece(x - i, y - i).isInBoard()) {
				if (gameBoard.getPiece(x - i, y - i) != null) {
					if (gameBoard.getPiece(x - i, y - i).color == this.getColor()) {
						res.add(new MoveBall(x, y, x - i, y - i));
					}
				}
			}
			if (gameBoard.getPiece(x + i, y + i).isInBoard()) {
				if (gameBoard.getPiece(x + i, y + i) != null) {
					if (gameBoard.getPiece(x + i, y + i).color == this.getColor()) {
						res.add(new MoveBall(x, y, x + i, y + i));
					}
				}
			}
			if (gameBoard.getPiece(x - i, y + i).isInBoard()) {
				if (gameBoard.getPiece(x - i, y + i) != null) {
					if (gameBoard.getPiece(x - i, y + i).color == this.getColor()) {
						res.add(new MoveBall(x, y, x - i, y + i));
					}
				}
			}
			if (gameBoard.getPiece(x + i, y - i).isInBoard()) {
				if (gameBoard.getPiece(x + i, y - i) != null) {
					if (gameBoard.getPiece(x + i, y - i).color == this.getColor()) {
						res.add(new MoveBall(x, y, x + i, y - i));
					}
				}
			}

			if (gameBoard.getPiece(x, y - i).isInBoard()) {
				if (gameBoard.getPiece(x, y - i) != null) {
					if (gameBoard.getPiece(x, y - i).color == this.getColor()) {
						res.add(new MoveBall(x, y, x, y - i));
					}
				}
			}
			if (gameBoard.getPiece(x - i, y).isInBoard()) {
				if (gameBoard.getPiece(x - i, y) != null) {
					if (gameBoard.getPiece(x - i, y).color == this.getColor()) {
						res.add(new MoveBall(x, y, x - i, y));
					}
				}
			}
			if (gameBoard.getPiece(x, y + i).isInBoard()) {
				if (gameBoard.getPiece(x, y + i) != null) {
					if (gameBoard.getPiece(x, y + i).color == this.getColor()) {
						res.add(new MoveBall(x, y, x, y + i));
					}
				}
			}
			if (gameBoard.getPiece(x + i, y).isInBoard()) {
				if (gameBoard.getPiece(x + i, y) != null) {
					if (gameBoard.getPiece(x + i, y).color == this.getColor()) {
						res.add(new MoveBall(x, y, x + i, y));
					}
				}
			}

		});
		return res;
	}

}
