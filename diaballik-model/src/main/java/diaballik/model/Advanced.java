package diaballik.model;


import java.util.ArrayList;
import java.util.stream.Stream;

//import com.fasterxml.jackson.annotation.JsonCreator;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import java.util.Objects;

public class Advanced implements Strategy {
	public Advanced() {
		super();
	}

	@Override
	public void exec(final Game game, final ArrayList<Pawn> pawns) {
		//ToDo
		//---------------Recherche de la balle adverse--------------------------------------------------
		final Board gameBoard = game.getBoard();
		final Pawn balleAdv = null;
		final Stream<Pawn> pionsAdv = gameBoard.getList().stream().filter(p -> p.getColor() == Color.Green);
		pionsAdv.forEach(p -> {
			if (p.hasBall()) {
				balleAdv.setPos(p.getX(), p.getY());
				balleAdv.setColor(p.getColor());
				balleAdv.setHasBall(true);
			}
		});


	}
}
