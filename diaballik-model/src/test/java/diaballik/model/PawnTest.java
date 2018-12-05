package diaballik.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class PawnTest {
	@Test
	public void testMoveDeBase() {
		Standard builder = new Standard();
		Board b = builder.placerPieces();
		Game g = new Game(false, 1, "Antoine", "Adrien", b);
		assertEquals(2, b.getPiece(0, 3).movePlayable(g).size());
		assertTrue(b.getPiece(0, 3).movePlayable(g).contains(new MoveBall(0, 3, 0, 4)));
		assertTrue(b.getPiece(0, 3).movePlayable(g).contains(new MoveBall(0, 3, 0, 2)));

		assertEquals(1, b.getPiece(0, 4).movePlayable(g).size());
		assertTrue(b.getPiece(0, 4).movePlayable(g).contains(new MovePion(0, 4, 1, 4)));
	}

	@Test
	public void testMoveBall() {
		Standard builder = new Standard();
		Board b = builder.placerPieces();
		Game g = new Game(false, 1, "Antoine", "Adrien", b);

		g.play(new MovePion(0,4,4,3));
		assertTrue(b.getPiece(0, 3).movePlayable(g).contains(new MoveBall(0, 3, 4, 3)));

		g.play(new MovePion(6,4,3,3));
		assertFalse(b.getPiece(0, 3).movePlayable(g).contains(new MoveBall(0, 3, 4, 3)));
	}

	@Test
	public void testMoveBallDiag() {
		Standard builder = new Standard();
		Board b = builder.placerPieces();
		Game g = new Game(false, 1, "Antoine", "Adrien", b);

		g.play(new MovePion(0,5,2,5));
		assertTrue(b.getPiece(0, 3).movePlayable(g).contains(new MoveBall(0, 3, 2, 5)));

		g.play(new MovePion(6,4,1,4));
		assertFalse(b.getPiece(0, 3).movePlayable(g).contains(new MoveBall(0, 3, 2, 5)));
	}

	@Test
	public void testCannotPlay() {
		Standard builder = new Standard();
		Board b = builder.placerPieces();
		Game g = new Game(false, 1, "Antoine", "Adrien", b);

		assertEquals(0, b.getPiece(6, 3).movePlayable(g).size());

		g.play(new MovePion(0,4,1,4));
		g.play(new MovePion(0,5,1,5));
		g.play(new MoveBall(0,3,0,2));

		assertEquals(0, b.getPiece(1, 4).movePlayable(g).size());
	}
}