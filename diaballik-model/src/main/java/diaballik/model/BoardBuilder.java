package diaballik.model;

public abstract class BoardBuilder {

	public Board monterPlateau() {

		final Board board = new Board();
		return board;
	}

	public abstract void placerPieces(final Board board);
}
