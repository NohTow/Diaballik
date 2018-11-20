package diaballik.model;

import java.util.stream.IntStream;

public class Random extends BoardBuilder {

	@Override
	public void placerPieces(final Board board) {

		//Placer les pions sur le plateau dans le scénario standard
		IntStream.range(0, 6).forEach(i -> {
			final Pawn temp = new Pawn(0, i, false, Color.Yellow);
			board.setPiece(0, i, temp);
			//board.plateau[6][i].id = i + 7;
		});
		IntStream.range(0, 6).forEach(i -> {
			final Pawn temp = new Pawn(6, i, false, Color.Green);
			board.setPiece(6, i, temp);
		});
		//Placer la balle sur un pion au hasard de chaque ligne :
	}
}
