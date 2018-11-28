package diaballik.model;

import java.util.ArrayDeque;
import java.util.List;
//import java.util.Map;

public class Game {
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
	public Game() {
		this.numTurn = 0;
		this.color = Color.Yellow;
		this.hasIA = false;
		this.idGame = 0;
		this.save = new ArrayDeque<Command>();
		this.undo = new ArrayDeque<Command>();
		this.gameBoard = new Board();
		this.joueur1 = new Humain("Antoine", Color.Yellow);
		this.joueur2 = new Humain("Adrien", Color.Green);


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
		undo.add(c);
	}

	public void redo() {
		final Command c = undo.pollLast();
		c.commandDo(this);
		save.add(c);
	}

	public List<Command> getMoovePlayable(final int x, final int y) {
		return this.gameBoard.getPiece(x, y).movePlayable(this.gameBoard);
		// est-ce qu'on ne devrait pas mettre la fonction moovePlayable dans le gameBoard ?
	}
}
