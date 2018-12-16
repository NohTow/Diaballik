package diaballik.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Stream;

//import com.fasterxml.jackson.annotation.JsonCreator;
////import com.fasterxml.jackson.annotation.JsonProperty;
////import java.util.Objects;

/**
 * The noob level of an IA
 */
public class Noob implements Strategy {
	public Noob() {
		super();
	}

	/**
	 * We get every pawn belonging to the IA, we get every possible move from every pawn
	 * We choose one of this move and do it
	 */
	@Override
	public void exec(final Game game) {
		final Stream<Pawn> streamPawns = game.getBoard().getList().stream().filter(p -> p.getColor() == Color.Green);
		final ArrayList<Pawn> pawns = new ArrayList<Pawn>();
		streamPawns.forEach(p -> {
			pawns.add(p);
		});
		final ArrayList<Command> comList = new ArrayList<Command>();
		pawns.forEach(p -> {
			comList.addAll(p.movePlayable(game));
		});
		Collections.shuffle(comList);
		comList.get(0).commandDo(game);
		game.incrNbAction();
	}
}
