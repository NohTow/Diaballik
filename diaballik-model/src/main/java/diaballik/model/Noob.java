package diaballik.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.IntStream;

public class Noob implements Strategy {
	@Override
	public void exec(final Game game, final ArrayList<Pawn> pawns, final Ball ball) {
		final Board board = game.getBoard();

		//final ArrayList<Integer> list = new ArrayList<>(Arrays.asList(0, 1)); //Choix au hasard de la commande a executer
		//ArrayList<Command> comList = new ArrayList<Command>();
		Collections.shuffle(pawns);
		final Pawn p = pawns.get(0);
		ArrayList<Command> comList = p.movePlayable(board);
		Collections.shuffle(comList);
		//Peut beuger si comList vide peut être faire un if sur la taille, et appeller un exec avec la mm liste et le pion en moins
		if(comList.size() == 0){
			//exec différent
			return;
		}else{
			final Command c = comList.get(0);
			c.commandDo(game);
		}

		/*if (list.get(0) == 1) {    //faire un MovePion
			Collections.shuffle(pawns); //choix d'un pion a deplacer
			final Pawn p = pawns.get(0);
			final int x = p.getX();
			final int y = p.getY();
			final ArrayList<Command> list1 = new ArrayList<Command>();

			final MovePion moveDevant = new MovePion(x, y, x, y + 1);
			final MovePion moveDerriere = new MovePion(x, y, x, y - 1);
			final MovePion moveGauche = new MovePion(x, y, x - 1, y);
			final MovePion moveDroite = new MovePion(x, y, x + 1, y);
			Collections.shuffle(list1);
			    //0 pour devant, 1 pour derriere, 2 pour gauche, 3 pour droite
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
			final Pawn p = new Pawn(0, 0, false, Color.Yellow);
			IntStream.range(0, 6).forEach(i -> {
				if(pawns.get(i).hasBall) {		//Trouver le pion qui possede la balle
					p.setPos(pawns.get(i).getX(), pawns.get(i).getY());
					p.setHasBall(pawns.get(i).hasBall);
					p.setColor(pawns.get(i).getColor());
				}
			});
			Collections.shuffle(p.moveBallPossible(board));
			final Command com = p.moveBallPossible(board).get(0);
			com.commandDo(game);
		}*/

	}
}
