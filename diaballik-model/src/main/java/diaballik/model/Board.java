package diaballik.model;

//import java.util.stream.IntStream;

public class Board {
	protected Pawn[][] plateau;

	public Board() {
		this.plateau = new Pawn[7][7];
		/*IntStream.range(0, 6).forEach(i -> {
			IntStream.range(0, 6).forEach(j -> {
				this.plateau[i][j] = new Pawn(i, j, false, Color.Yellow);
			});
		});*/
	}
	public Pawn getPiece(final int x, final int y) {
		return this.plateau[x][y];
	}
	public void setPiece(final int x, final int y, final Pawn p) {
		this.plateau[x][y] = p;
	}

}

