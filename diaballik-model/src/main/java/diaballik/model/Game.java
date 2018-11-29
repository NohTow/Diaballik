package diaballik.model;

import java.util.ArrayDeque;
import java.util.List;
//import java.util.Map;

public class Game {
	private int nbAction;
	private int numTurn;
	private Color color;
	private boolean hasIA;
	private int idGame;
	private ArrayDeque<Command> save;
	private ArrayDeque<Command> undo;

	private Board gameBoard;

	private Player joueur1;
	private Player joueur2;

	//Constructeur factice pour virer les unused TODO
	//TODO faire les loads/save
	public Game(final boolean IA, final int idG, final String nameJ1, final String nameJ2) {
		this.nbAction = 0;
		this.numTurn = 0;
		this.color = Color.Yellow;
		this.hasIA = IA;
		this.idGame = idG;
		this.save = new ArrayDeque<Command>();
		this.undo = new ArrayDeque<Command>();
		this.gameBoard = new Board();
		this.joueur1 = new Humain(nameJ1, Color.Yellow);
		if (IA) {
			joueur2 = new IA(Color.Green, new Noob());
		} else {
			this.joueur2 = new Humain(nameJ2, Color.Green);
		}


	}

	public Board getBoard() {
		return this.gameBoard;
	}

	public void addSave(final Command command) {
		this.save.add(command);
	}

	public void addUndo(final Command command) {
		this.undo.add(command);
	}

	public void undo() {
		final Command c = save.pollLast();
		c.commandUndo(this);
		this.decrNbAction();
		undo.add(c);
	}

	public void redo() {
		final Command c = undo.pollLast();
		c.commandDo(this);
		incrNbAction();
		save.add(c);
	}

	public List<Command> getMoovePlayable(final int x, final int y) {
		return this.gameBoard.getPiece(x, y).movePlayable(this);
		// est-ce qu'on ne devrait pas mettre la fonction moovePlayable dans le gameBoard ?
	}

	public Color getColor() {
		return this.color;
	}

	public void incrNbAction() {
		this.nbAction++;
		if (this.nbAction == 3) {
			this.nbAction = 0;
			this.numTurn++;
			if (this.color == Color.Yellow) {
				this.color = Color.Green;
			} else {
				this.color = Color.Yellow;
			}
		}
	}

	public void decrNbAction() {
		this.nbAction--;
		if (this.nbAction == -1) {
			this.numTurn--;
			this.nbAction = 2;
			if (this.color == Color.Yellow) {
				this.color = Color.Green;
			} else {
				this.color = Color.Yellow;
			}
		}
	}
}
