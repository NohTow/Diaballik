package diaballik.model;

import java.util.ArrayList;
//import java.util.Map;

public class Game {
	private int numTurn;
	private Color color;
	private boolean hasIA;
	private int idGame;
	private ArrayList<Command> save;
	private ArrayList<Command> undo;

	private Board gameBoard;

	private Player joueur1;
	private Player joueur2;

}
