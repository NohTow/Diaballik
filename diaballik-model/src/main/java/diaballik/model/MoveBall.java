package diaballik.model;

public class MoveBall implements Command {
	private int oldX;
	private int oldY;
	private int newX;
	private int newY;

	public MoveBall(final int oldX, final int oldY, final int newX, final int newY) {
		this.oldX = oldX;
		this.oldY = oldY;
		this.newX = newX;
		this.newY = newY;
	}

	public void commandDo(final Game game) {
		final Board board = game.getBoard();
		board.getPiece(newX, newY).setHasBall(true);
		board.getPiece(oldX, oldY).setHasBall(false);
		game.addSave(this);
	}

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
