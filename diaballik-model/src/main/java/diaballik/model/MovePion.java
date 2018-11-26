package diaballik.model;

public class MovePion implements Command {
	private int oldX;
	private int oldY;
	private int newX;
	private int newY;

	public MovePion(final int oldX, final int oldY, final int newX, final int newY) {
		this.oldX = oldX;
		this.oldY = oldY;
		this.newX = newX;
		this.newY = newY;
	}
	/*public int getOldX(){
		return this.oldX;
	}
	public int getOldY(){
		return this.oldY;
	}
	public int getNewX(){
		return this.newX;
	}
	public int getNewY(){
		return this.newY;
	}*/

	@Override
	public void commandDo(final Game game) {
		final Board board = game.getBoard();
		board.setPiece(this.newX, this.newY, board.getPiece(this.oldX, this.oldY));
		board.setPiece(this.oldX, this.oldY, null);
		game.addSave(this);
	}

	@Override
	public void commandUndo(final Game game) {
		final Board board = game.getBoard();
		board.setPiece(this.oldX, this.oldY, board.getPiece(this.newX, this.newY));
		board.setPiece(this.newX, this.newY, null);
		game.addUndo(this);

	}
}
