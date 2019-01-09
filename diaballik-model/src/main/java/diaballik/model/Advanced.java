package diaballik.model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * The advanced level of an AI
 */
public class Advanced implements Strategy {

	public Advanced() {
		super();
	}

	/**
	 * @param balleAdv the pawn of the adversary which has the ball
	 * @param comList the possible moves of the pawns of the AI
	 * @return the possible moves which set a pawn on the column of the adversary ball
	 */
	public ArrayList<Command> bloquerDevant(final Pawn balleAdv, final ArrayList<Command> comList) {
		final ArrayList<Command> actionsPossible = new ArrayList<Command>();
		IntStream.iterate(balleAdv.getY() + 1, i -> i + 1).limit(6).forEach(positionY -> {
			comList.stream().filter(c -> c.getNewX() == balleAdv.getX() && c.getNewY() == positionY).forEach(c -> {
				actionsPossible.add(c);
			});
		});
		return actionsPossible;
	}

	/**
	 * @param balleAdv the pawn of the adversary which has the ball
	 * @param comList the possible moves of the pawns of the AI
	 * @return the possible moves which set a pawn on the left diagonal of the adversary ball
	 */
	public ArrayList<Command> bloquerDiagGauche(final Pawn balleAdv, final ArrayList<Command> comList) {
		final ArrayList<Command> actionsPossible = new ArrayList<Command>();
		IntStream.iterate(balleAdv.getX() - 1, i -> i - 1).limit(0).forEach(positionX -> {
			IntStream.iterate(balleAdv.getY() + 1, i -> i + 1).limit(0).forEach(positionY -> {
				comList.stream().filter(c -> c.getNewX() == positionX && c.getNewY() == positionY).forEach(c -> {
					actionsPossible.add(c);
				});
			});
		});
		return actionsPossible;
	}

	/**
	 * @param balleAdv the pawn of the adversary which has the ball
	 * @param comList the possible moves of the pawns of the AI
	 * @return the possible moves which set a pawn on the right diagonal of the adversary ball
	 */
	public ArrayList<Command> bloquerDiagDroite(final Pawn balleAdv, final ArrayList<Command> comList) {
		final ArrayList<Command> actionsPossible = new ArrayList<Command>();
		IntStream.iterate(balleAdv.getX() + 1, i -> i + 1).limit(6).forEach(positionX -> {
			IntStream.iterate(balleAdv.getY() + 1, i -> i + 1).limit(0).forEach(positionY -> {
				comList.stream().filter(c -> c.getNewX() == positionX && c.getNewY() == positionY).forEach(c -> {
					actionsPossible.add(c);
				});
			});
		});
		return actionsPossible;
	}

	/**
	 * @param balleAdv the pawn of the adversary which has the ball
	 * @param comList the possible moves of the pawns of the AI
	 * @return all the possible moves which set a pawn on the path of the adversary ball
	 */
	public ArrayList<Command> bloquerBalleAdverse(final Pawn balleAdv, final ArrayList<Command> comList) {
		final ArrayList<Command> actionsPossible = new ArrayList<Command>();
		actionsPossible.addAll(bloquerDevant(balleAdv, comList));
		actionsPossible.addAll(bloquerDiagDroite(balleAdv, comList));
		actionsPossible.addAll(bloquerDiagGauche(balleAdv, comList));

		return actionsPossible;
	}


	/**
	 * If the AI wants to move a pawn, it will try to put a pawn so that it can prevent the adversary to move his ball
	 * If it choose to move its ball, it will move it randomly
	 */
	@Override
	public void exec(final Game game) {
		final Stream<Pawn> streamPawns = game.getBoard().getList().stream().filter(p -> p.getColor() == Color.Green);
		final ArrayList<Pawn> pawns = new ArrayList<Pawn>();
		streamPawns.forEach(p -> {
			pawns.add(p);
		});
		final Board gameBoard = game.getBoard();


		//----------------Liste de tous les MovePion possibles----------------------------------------
		final ArrayList<Command> comList = new ArrayList<Command>();
		pawns.stream().filter(p -> !(p.hasBall())).forEach(p -> {
			comList.addAll(p.movePlayable(game));
		});

		final ArrayList<Integer> choix = new ArrayList<Integer>();
		choix.add(1);
		choix.add(2);
		choix.add(3);
		choix.add(4);
		choix.add(5);
		choix.add(6);
		choix.add(7);


		Collections.shuffle(choix);

		if (choix.get(0) == 1) {                //Choisit de faire un MoveBall (une chance sur 7)
			final Optional<Pawn> maBalle = game.getBoard().getList().stream().filter(p -> p.getColor() == Color.Green && p.hasBall()).findFirst();
			Pawn p = new Pawn(0, 0, false, Color.Green);
			if (maBalle.isPresent()) {
				p = maBalle.get();
			}
			final ArrayList<Command> list = p.movePlayable(game);
			Collections.shuffle(list);
			list.get(0).commandDo(game);

		} else {                                //Choisit de faire un MovePion qui va gener l'adversaire

			//---------------Recherche de la balle adverse--------------------------------------------------

			final Optional<Pawn> poBallAdv = gameBoard.getList().stream().filter(p -> p.getColor() == Color.Yellow && p.hasBall()).findFirst();
			Pawn balleAdv = new Pawn(0, 0, false, Color.Yellow);
			if (poBallAdv.isPresent()) {
				balleAdv = poBallAdv.get();
			}

			//---------------Tente de bloquer la trajectoire de la balle adverse----------------------------

			final ArrayList<Command> actionsPossible = bloquerBalleAdverse(balleAdv, comList);
			if (actionsPossible.size() > 0) {
				Collections.shuffle(actionsPossible);
				actionsPossible.get(0).commandDo(game);
			} else {
				Collections.shuffle(comList);
				comList.get(0).commandDo(game);
			}
		}
		game.incrNbAction();
	}
}

