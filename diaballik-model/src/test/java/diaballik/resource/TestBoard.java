package diaballik.model;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestBoard {
	@Test
	public void testMain() throws Exception{
		Board gameBoard = new Board();
		Pawn p1 = new Pawn(0, 0, true, Color.Yellow);
		Pawn p2 = new Pawn(0,1,false, Color.Yellow);

		gameBoard.setPiece(0,0,p1);

		gameBoard.setPiece(0,0,p2);
		assertEquals(p1.movePlayable(gameBoard).size(),3);
	}
}