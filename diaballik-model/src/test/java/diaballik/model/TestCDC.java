package diaballik.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
class TestCDC {


	@Test
	public void R21_1(){
		//There's 2 player in game object
	}
	@Test
	public void R21_2(){
		//The board is a Pawn[7][7] so it's a square of length 7
	}
	@Test
	public void R21_3(){
		//We set 7 pawn for each player in every builder
		//The identifier of a pawn is the color wich is the same as the player who's owning them
		Standard builder = new Standard();
		Board b = builder.placerPieces();
		Game g = new Game(false,7,"Antoine", "Adrien",b);
		assertEquals(7, g.getBoard().getList().stream().filter(p->p.getColor()==g.getJoueur1().getColor()).count());
		assertEquals(7, g.getBoard().getList().stream().filter(p->p.getColor()==g.getJoueur2().getColor()).count());
	}
	@Test
	public void R21_4(){
		//We only set one ball for each color of pawn, and a ball is carried by a piece
		//See pawn and board test
	}

	@Test
	public void R21_5(){
		Standard builder = new Standard();
		Board b = builder.placerPieces();
		Game g = new Game(false,7,"Antoine", "Adrien",b);
		assertEquals(Color.Yellow, g.getJoueur1().getColor());
		assertEquals(Color.Green, g.getJoueur2().getColor());
	}

	@Test
	public void R21_6_8(){
		//There's an attribute Color in game wich is the current player color
		Standard builder = new Standard();
		Board b = builder.placerPieces();
		Game g = new Game(false,8,"Antoine","Adrien",b);
		g.play(new MovePion(0,0,1,0));
		assertEquals(Color.Yellow, g.getColor());
		assertEquals(1,g.getNumTurn());
		assertEquals(1,g.getNbAction());
		g.play(new MovePion(0,1,1,1));
		assertEquals(2,g.getNbAction());
		assertEquals(Color.Yellow, g.getColor());
		assertEquals(1,g.getNumTurn());
		g.play(new MoveBall(0,3,0,2));
		assertEquals(0,g.getNbAction());
		assertEquals(Color.Green, g.getColor());
		assertEquals(2,g.getNumTurn());
		g.undo();
		assertEquals(1,g.getNumTurn());
		assertEquals(2,g.getNbAction());
		g.redo();
		assertEquals(0,g.getNbAction());
		assertEquals(2,g.getNumTurn());

	}

	@Test
	public void R21_9_10_11(){
		//This is represented by the movePlayable method
		//See PawnTest for advanced test
	}

	@Test
	public void R21_12(){
		Standard builder = new Standard();
		Board b = builder.placerPieces();
		Game g = new Game(false, 1, "Antoine", "Adrien", b);

		assertEquals(g.getColor(), g.getJoueur1().getColor());

		assertEquals(g.getJoueur1().getColor(), b.getPiece(0, 3).getColor());
		assertEquals(g.getJoueur2().getColor(), b.getPiece(6, 3).getColor());

		assertEquals(0, b.getPiece(6, 3).movePlayable(g).size());

		g.play(new MovePion(0,5,1,5));
		g.play(new MovePion(0,4,1,4));
		g.play(new MoveBall(0,3,0,2));

		assertEquals(0, b.getPiece(1, 4).movePlayable(g).size());
	}

	@Test
	public void R21_13_14() {
		Standard builder = new Standard();
		Board b = builder.placerPieces();
		Game g = new Game(false, 1, "Antoine", "Adrien", b);
		assertEquals(g.getColor(), g.getJoueur1().getColor());
		g.play(new MovePion(0,5,1,5));
		g.play(new MovePion(0,4,1,4));
		g.play(new MoveBall(0,3,0,2));
		assertEquals(g.getColor(), g.getJoueur2().getColor());
	}

	@Test
	public void R21_15(){
		//The game is only finished when a player get his ball to the enemie line
	}

	@Test
	public void R23_1(){
		//A game has 2 players, a players can be either Human, or IA
		//See the constructor of Game to be sure that the IA is always the second player

	}

	@Test
	public void R24_1(){
		Standard builder = new Standard();
		Board b = builder.placerPieces();
		Game g = new Game(false, 1, "Antoine", "Adrien", b);
		g.play(new MovePion(0,0,2,0));
		g.play(new MoveBall(6,3,6,0));
		g.play(new MovePion(6,0,0,0));
		assertEquals(g.getJoueur2(), g.isFinished().get());
	}

}
