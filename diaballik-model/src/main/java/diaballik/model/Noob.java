package diaballik.model;
import java.util.ArrayList;
import java.util.Collections;

//import com.fasterxml.jackson.annotation.JsonCreator;
////import com.fasterxml.jackson.annotation.JsonProperty;
////import java.util.Objects;

public class Noob implements Strategy {
	public Noob() {
		super();
	}
	@Override
	public void exec(final Game game, final ArrayList<Pawn> pawns) {
		final ArrayList<Command> comList = new ArrayList<Command>();
		pawns.forEach(p -> {
			comList.addAll(p.movePlayable(game));
		});
		Collections.shuffle(comList);
		comList.get(0).commandDo(game);
	}
}
