package diaballik.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameTest {
	public GameTest(){

	}

	@Test
	public void testCreationGamePVP(){
		Standard builder = new Standard();
		Board b = builder.placerPieces();
		Game g = new Game(false,8,"Antoine","Adrien",b);
		assertEquals("Antoine",g.getJoueur1().getName());
		assertEquals(Color.Yellow,g.getJoueur1().getColor());
		assertEquals(Color.Green, g.getJoueur2().getColor());
		assertEquals("Adrien",g.getJoueur2().getName());
		//Vérification que la partie commence sur le joueur 1
		assertEquals(Color.Yellow, g.getColor());
	}

	@Test
	public void testCreationGamePVIA(){
		Standard builder = new Standard();
		Board b = builder.placerPieces();
		Game g = new Game(true,8,"Antoine","",b);
		assertEquals("Antoine",g.getJoueur1().getName());
		assertEquals(Color.Yellow,g.getJoueur1().getColor());
		assertEquals(Color.Green, g.getJoueur2().getColor());
		assertEquals("Computer",g.getJoueur2().getName());
	}

	@Test
	public void testAction(){
		Standard builder = new Standard();
		Board b = builder.placerPieces();
		Game g = new Game(false,8,"Antoine","Adrien",b);
		g.play(new MovePion(0,0,1,0));
		assertEquals(Color.Yellow, g.getColor());
		assertEquals(1,g.getNumTurn());
		assertEquals(null,g.getBoard().getPiece(0,0));
		assertEquals(Color.Yellow,g.getBoard().getPiece(1,0).getColor());
		assertEquals(1,g.getNbAction());
		g.play(new MovePion(0,1,1,1));
		assertEquals(null,g.getBoard().getPiece(0,1));
		assertEquals(Color.Yellow,g.getBoard().getPiece(1,1).getColor());
		assertEquals(2,g.getNbAction());
		assertEquals(Color.Yellow, g.getColor());
		assertEquals(1,g.getNumTurn());
		assertFalse(g.getBoard().getPiece(0,2).hasBall());
		assertTrue(g.getBoard().getPiece(0,3).hasBall());
		g.play(new MoveBall(0,3,0,2));
		assertFalse(g.getBoard().getPiece(0,3).hasBall());
		assertTrue(g.getBoard().getPiece(0,2).hasBall());
		assertEquals(0,g.getNbAction());
		assertEquals(Color.Green, g.getColor());
		assertEquals(2,g.getNumTurn());
		g.undo();
		assertEquals(1,g.getNumTurn());
		assertEquals(2,g.getNbAction());
		g.redo();
		assertEquals(2,g.getNumTurn());
		assertEquals(0,g.getNbAction());
	}

	@Test
	public void testUndo(){
		Standard builder = new Standard();
		Board b = builder.placerPieces();
		Game g = new Game(false,8,"Antoine","Adrien",b);
		g.play(new MovePion(0,0,1,0));
		g.undo();
		assertEquals(null,g.getBoard().getPiece(1,0));
		assertEquals(Color.Yellow, g.getBoard().getPiece(0,0).getColor());
		assertEquals(0,g.getNbAction());
	}

	@Test
	public void testRedo(){
		Standard builder = new Standard();
		Board b = builder.placerPieces();
		Game g = new Game(false,8,"Antoine","Adrien",b);
		g.play(new MovePion(0,0,1,0));
		g.undo();
		g.redo();
		assertEquals(null,g.getBoard().getPiece(0,0));
		assertEquals(Color.Yellow, g.getBoard().getPiece(1,0).getColor());
		assertEquals(1,g.getNbAction());
	}


}
