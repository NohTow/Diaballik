package diaballik.model;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Element {
	protected int x;
	protected int y;
	//protected int id;
	protected boolean hasBall;


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

	public void setX(final int x) {
		this.x = x;
	}

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


	List<Command> movePlayable(final Board gameBoard) {
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

}
