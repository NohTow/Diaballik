package diaballik.model;

import java.util.stream.IntStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


/**
 *
 */
public class Random extends BoardBuilder {

	/**
	 * @return a board with the "random" organisation
	 */
	@Override
	public Board placerPieces() {
		final Board board = new Board();
		//Placer les pions sur le plateau dans le scénario standard
		IntStream.rangeClosed(0, 6).forEach(i -> {
			final Pawn temp = new Pawn(0, i, false, Color.Yellow);
			board.setPiece(0, i, temp);
			//board.plateau[6][i].id = i + 7;
		});
		IntStream.rangeClosed(0, 6).forEach(i -> {
			final Pawn temp = new Pawn(6, i, false, Color.Green);
			board.setPiece(6, i, temp);
		});

		//Placer la balle sur un pion au hasard de chaque ligne :
		final ArrayList<Integer> list = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6));
		Collections.shuffle(list);
		board.getPiece(0, list.get(0)).setHasBall(true);
		board.getPiece(6, list.get(1)).setHasBall(true);
		return board;
	}
}
