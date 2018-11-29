package diaballik.model;
import java.util.ArrayList;
import java.util.Collections;

public class Noob implements Strategy {
	@Override
	public void exec(final Game game, final ArrayList<Pawn> pawns) {
		//final Board board = game.getBoard();

		//final ArrayList<Integer> list = new ArrayList<>(Arrays.asList(0, 1)); //Choix au hasard de la commande a executer
		//ArrayList<Command> comList = new ArrayList<Command>();
		//Collections.shuffle(pawns);
		//final Pawn p = pawns.get(0);
		final ArrayList<Command> comList = new ArrayList<Command>();
		pawns.forEach(p->{
			comList.addAll(p.movePlayable(game));
		});
		Collections.shuffle(comList);
		comList.get(0).commandDo(game);
	}
}
