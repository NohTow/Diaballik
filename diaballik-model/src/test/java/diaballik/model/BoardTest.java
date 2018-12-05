package diaballik.model;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.util.stream.IntStream;

class BoardTest {

	@Test
	public void R22_1() {
		Standard builder = new Standard();
		Board b = builder.placerPieces();
		assertTrue(b.getPiece(0, 3).hasBall());
		assertTrue(b.getPiece(6, 3).hasBall());
		IntStream.rangeClosed(0, 6).forEach(i -> {
			assertEquals(Color.Yellow, b.getPiece(0, i).getColor());
			assertEquals(Color.Green, b.getPiece(6, i).getColor());
		});
		IntStream.range(0, 3).forEach(i -> {
			assertFalse(b.getPiece(0, i).hasBall());
		});
		IntStream.rangeClosed(4, 6).forEach(i -> {
			assertFalse(b.getPiece(0, i).hasBall());
		});
		IntStream.range(0, 3).forEach(i -> {
			assertFalse(b.getPiece(6, i).hasBall());
		});
		IntStream.rangeClosed(4, 6).forEach(i -> {
			assertFalse(b.getPiece(6, i).hasBall());
		});
		IntStream.rangeClosed(1, 5).forEach(i -> {
			IntStream.rangeClosed(1, 5).forEach(j -> {
				assertEquals(null, b.getPiece(i, j));
			});
		});
	}

	@Test
	public void R22_2() {
		Standard builder = new Standard();
		Board b = builder.placerPieces();
		IntStream.rangeClosed(0, 6).forEach(i -> {
			assertEquals(Color.Yellow, b.getPiece(0, i).getColor());
			assertEquals(Color.Green, b.getPiece(6, i).getColor());
		});
		IntStream.rangeClosed(1, 5).forEach(i -> {
			IntStream.rangeClosed(1, 5).forEach(j -> {
				assertEquals(null, b.getPiece(i, j));
			});
		});
	}

	@Test
	public void R22_3() {
		Standard builder = new Standard();
		Board b = builder.placerPieces();

		assertTrue(b.getPiece(0, 3).hasBall());
		assertTrue(b.getPiece(6, 3).hasBall());
		assertEquals(Color.Yellow, b.getPiece(0, 3).getColor());
		assertEquals(Color.Green, b.getPiece(6, 3).getColor());
		
		IntStream.rangeClosed(1, 5).forEach(i -> {
			IntStream.rangeClosed(1, 5).forEach(j -> {
				assertEquals(null, b.getPiece(i, j));
			});
		});
	}
}
