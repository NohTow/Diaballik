package diaballik.model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Stream;
import java.util.stream.IntStream;

//import com.fasterxml.jackson.annotation.JsonCreator;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import java.util.Objects;

public class Advanced implements Strategy {
	
	public Advanced() {
		super();
	}

	public ArrayList<Command> bloquerDevant(final Pawn balleAdv, final ArrayList<Command> comList) {
		final ArrayList<Command> actionsPossible = new ArrayList<Command>();
		IntStream.iterate(balleAdv.getY() + 1, i -> i + 1).limit(6).forEach(positionY -> {
			comList.stream().filter(c -> c.getNewX() == balleAdv.getX() && c.getNewY() == positionY).forEach(c -> {
				actionsPossible.add(c);
			});
		});
		return actionsPossible;
	}

	public ArrayList<Command> bloquerDiagGauche(final Pawn balleAdv, final ArrayList<Command> comList) {
		final ArrayList<Command> actionsPossible = new ArrayList<Command>();
		IntStream.iterate(balleAdv.getX() - 1, i -> i - 1).limit(0).forEach(positionX -> {
			IntStream.iterate(balleAdv.getY() - 1, i -> i - 1).limit(0).forEach(positionY -> {
				comList.stream().filter(c -> c.getNewX() == positionX && c.getNewY() == positionY).forEach(c -> {
					actionsPossible.add(c);
				});
			});
		});
		return actionsPossible;
	}

	public ArrayList<Command> bloquerDiagDroite(final Pawn balleAdv, final ArrayList<Command> comList) {
		final ArrayList<Command> actionsPossible = new ArrayList<Command>();
		IntStream.iterate(balleAdv.getX() + 1, i -> i + 1).limit(6).forEach(positionX -> {
			IntStream.iterate(balleAdv.getY() - 1, i -> i - 1).limit(0).forEach(positionY -> {
				comList.stream().filter(c -> c.getNewX() == positionX && c.getNewY() == positionY).forEach(c -> {
					actionsPossible.add(c);
				});
			});
		});
		return actionsPossible;
	}

	public ArrayList<Command> bloquerBalleAdverse(final Pawn balleAdv, final ArrayList<Command> comList) {
		final ArrayList<Command> actionsPossible = new ArrayList<Command>();
		actionsPossible.addAll(bloquerDevant(balleAdv, comList));
		actionsPossible.addAll(bloquerDiagDroite(balleAdv, comList));
		actionsPossible.addAll(bloquerDiagGauche(balleAdv, comList));

		return actionsPossible;
	}


	@Override
	public void exec(final Game game, final ArrayList<Pawn> pawns) {
		//ToDo

		final Board gameBoard = game.getBoard();
		final Pawn balleAdv = null;
		ArrayList<Command> actionsPossible = new ArrayList<Command>();

		//----------------Liste de tous les MovePion possibles----------------------------------------
		final ArrayList<Command> comList = new ArrayList<Command>();
		pawns.stream().filter(p -> p.hasBall() == false).forEach(p -> {
			comList.addAll(p.getMovePiece(game));
		});

		//---------------Recherche de la balle adverse--------------------------------------------------

		final Stream<Pawn> pionsAdv = gameBoard.getList().stream().filter(p -> p.getColor() == Color.Green);
		pionsAdv.forEach(p -> {
			if (p.hasBall()) {
				balleAdv.setPos(p.getX(), p.getY());
				balleAdv.setColor(p.getColor());
				balleAdv.setHasBall(true);
			}
		});
		//----------------------------------------------------------------------------------------------

		actionsPossible = bloquerBalleAdverse(balleAdv, comList);
		Collections.shuffle(actionsPossible);
		actionsPossible.get(0).commandDo(game);

	}





	//------------------Recherche si on peut bouger un pion sur la colonne derriere balleAdv-------
		/*IntStream.iterate(balleAdv.getY() - 1, i -> i - 1).limit(0).forEach(positionY -> {
			comList.stream().filter(c -> c.getNewX() == balleAdv.getX() && c.getNewY() == positionY).forEach(c -> {
				actionsPossible.add(c);
			});
		});*/

	//------------------Recherche si on peut bouger un pion sur la colonne devant balleAdv-------
		/*IntStream.iterate(balleAdv.getY() + 1, i -> i + 1).limit(6).forEach(positionY -> {
		comList.stream().filter(c -> c.getNewX() == balleAdv.getX() && c.getNewY() == positionY).forEach(c -> {
			actionsPossible.add(c);
		});
	});*/

	//------------------Recherche si on peut bouger un pion sur la ligne à gauche de balleAdv-------
		/*IntStream.iterate(balleAdv.getX() - 1, i -> i - 1).limit(0).forEach(positionX -> {
			comList.stream().filter(c -> c.getNewX() == positionX && c.getNewY() == balleAdv.getY()).forEach(c -> {
				actionsPossible.add(c);
			});
		});*/

	//------------------Recherche si on peut bouger un pion sur la ligne à droite de balleAdv-------
		/*IntStream.iterate(balleAdv.getX() + 1, i -> i + 1).limit(6).forEach(positionX -> {
			comList.stream().filter(c -> c.getNewX() == positionX && c.getNewY() == balleAdv.getY()).forEach(c -> {
				actionsPossible.add(c);
			});
		});*/

	//------------------Recherche si on peut bouger un pion sur la diagonale droite de ballAdv----------
		/*IntStream.iterate(balleAdv.getX() + 1, i -> i + 1).limit(6).forEach(positionX -> {
		IntStream.iterate(balleAdv.getY() - 1, i -> i - 1).limit(0).forEach(positionY -> {
			comList.stream().filter(c -> c.getNewX() == positionX && c.getNewY() == positionY).forEach(c -> {
				actionsPossible.add(c);
			});
		});
	});*/

	//------------------Recherche si on peut bouger un pion sur la diagonale gauche de ballAdv----------
		/*IntStream.iterate(balleAdv.getX() - 1, i -> i - 1).limit(0).forEach(positionX -> {
		IntStream.iterate(balleAdv.getY() - 1, i -> i - 1).limit(0).forEach(positionY -> {
			comList.stream().filter(c -> c.getNewX() == positionX && c.getNewY() == positionY).forEach(c -> {
				actionsPossible.add(c);
			});
		});
	});*/
}

