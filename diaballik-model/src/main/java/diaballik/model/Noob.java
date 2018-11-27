package diaballik.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Noob implements Strategy {
	@Override
	public void exec(final Game game, final ArrayList<Pawn> pawns, final Ball ball) {
		final Board board = game.getBoard();

		final ArrayList<Integer> list = new ArrayList<>(Arrays.asList(0, 1)); //Choix au hasard de la commande a executer
		Collections.shuffle(list);

		if (list.get(0) == 1) {    //faire un MovePion
			Collections.shuffle(pawns); //choix d'un pion a deplacer
			final Pawn p = pawns.get(0);
			final int x = p.getX();
			final int y = p.getY();

			final MovePion moveDevant = new MovePion(x, y, x, y + 1);
			final MovePion moveDerriere = new MovePion(x, y, x, y - 1);
			final MovePion moveGauche = new MovePion(x, y, x - 1, y);
			final MovePion moveDroite = new MovePion(x, y, x + 1, y);

			final ArrayList<Integer> list1 = new ArrayList<>(Arrays.asList(0, 1, 2, 3));
			Collections.shuffle(list1);    //0 pour devant, 1 pour derriere, 2 pour gauche, 3 pour droite
			final int dest = list1.get(0);
			switch (dest) {
				case (0):    //Si on a choisi de bouger devant
					if (p.movePlayable(board).contains(moveDevant)) { //on verifie qu'on peut bouger devant
						moveDevant.commandDo(game);
						break;
					}
				case (1):    //Si on a choisi de bouger derriere
					if (p.movePlayable(board).contains(moveDerriere)) { //on verifie qu'on peut bouger derriere
						moveDerriere.commandDo(game);
						break;
					}
				case (2):    //Si on a choisi de bouger a gauche
					if (p.movePlayable(board).contains(moveGauche)) { //on verifie qu'on peut bouger a gauche
						moveGauche.commandDo(game);
						break;
					}
				case (3):    //Si on a choisi de bouger a droite
					if (p.movePlayable(board).contains(moveDroite)) { //on verifie qu'on peut bouger a droite
						moveDroite.commandDo(game);
						break;
					}
				default:
					//Changer de pion
					//ToDo
			}
		} else {    //faire un MoveBall
			//ToDo
		}

	}
}
