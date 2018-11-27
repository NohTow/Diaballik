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
		final Pawn nouveau = board.getPiece(newX, newY);
		final Pawn ancien = board.getPiece(oldX, oldY);
		nouveau.setHasBall(true);
		ancien.setHasBall(false);
		game.addSave(this);
	}

	public void commandUndo(final Game game) {
		final Board board = game.getBoard();
		final Pawn nouveau = board.getPiece(newX, newY);
		final Pawn ancien = board.getPiece(oldX, oldY);
		nouveau.setHasBall(false);
		ancien.setHasBall(true);
		game.addUndo(this);
	}
}
