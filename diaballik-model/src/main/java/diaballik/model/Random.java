package diaballik.model;

import java.util.stream.IntStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


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
		final ArrayList<Integer> list = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6));
		Collections.shuffle(list);

		board.plateau[0][list.get(0)].hasBall = true; // A random value
		board.plateau[6][list.get(1)].hasBall = true; // A different random value

	}
}
