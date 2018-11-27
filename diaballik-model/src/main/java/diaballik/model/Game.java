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
