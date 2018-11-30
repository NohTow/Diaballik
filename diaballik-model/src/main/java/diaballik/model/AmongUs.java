package diaballik.model;

import java.util.stream.IntStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

//import com.fasterxml.jackson.annotation.JsonCreator;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import java.util.Objects;

public class AmongUs extends BoardBuilder {
	@Override
	public Board placerPieces() {
		final Board board = new Board();
		final ArrayList<Integer> list = new ArrayList<>(Arrays.asList(0, 1, 2, 4, 5, 6));
		Collections.shuffle(list);

		final int y1 = list.get(0);
		final int y2 = list.get(1);

		//Placer les pions sur le plateau dans le scénario standard
		IntStream.rangeClosed(0, 6).forEach(i -> {
			//Placer des ennemis sur la ligne :
			if (i == y1 || i == y2) {
				final Pawn temp = new Pawn(0, i, false, Color.Green);
				board.setPiece(0, i, temp);
			} else {
				final Pawn temp = new Pawn(0, i, false, Color.Yellow);
				board.setPiece(0, i, temp);
			}
		});

		IntStream.rangeClosed(0, 6).forEach(i -> {
			//Placer des ennemis sur la ligne :
			if (i == y1 || i == y2) {
				final Pawn temp = new Pawn(6, i, false, Color.Yellow);
				board.setPiece(6, i, temp);
			} else {
				final Pawn temp = new Pawn(6, i, false, Color.Green);
				board.setPiece(6, i, temp);
			}
		});

		//Placer la balle sur le pion du milieu :
		board.getPiece(0, 3).setHasBall(true);
		board.getPiece(6, 3).setHasBall(true);
		return board;
	}
}
