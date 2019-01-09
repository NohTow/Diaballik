package diaballik.model;

import java.util.stream.IntStream;

public class Standard extends BoardBuilder {

	/**
	 * @return a board with a "Standard" organisation
	 */
	@Override
	public Board placerPieces() {
		final Board board = new Board();
		//Placer les pions sur le plateau dans le scénario standard
		IntStream.rangeClosed(0, 6).forEach(i -> {
			final Pawn temp = new Pawn(0, i, false, Color.Yellow);
			board.setPiece(0, i, temp);
		});
		IntStream.rangeClosed(0, 6).forEach(i -> {
			final Pawn temp = new Pawn(6, i, false, Color.Green);
			board.setPiece(6, i, temp);
		});


		//Placer la balle sur le pion du milieu de chaque ligne
		board.getPiece(0, 3).setHasBall(true);
		board.getPiece(6, 3).setHasBall(true);
		return board;
	}

}
