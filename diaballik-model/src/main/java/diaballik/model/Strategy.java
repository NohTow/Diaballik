package diaballik.model;

import java.util.ArrayList;

public interface Strategy {
	public void exec(final Game game, ArrayList<Pawn> pions, final Ball balle);
}
