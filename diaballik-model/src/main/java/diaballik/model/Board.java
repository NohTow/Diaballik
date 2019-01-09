package diaballik.model;



import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;




/**
 * The class representing the gaming board
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Board {
	protected Pawn[][] plateau;

	/**
	 * Initialization of the Board
	 */
	@JsonCreator
	public Board() {
		this.plateau = new Pawn[7][7];
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final Board board = (Board) o;
		return board.getList().equals(this.getList());
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(this.plateau);
	}


	/**
	 * @param x coordinate of the pawn we want to get
	 * @param y coordinate
	 * @return the Pawn wich has the (x,y) coordinate on the board or null if there isn't one
	 */
	public Pawn getPiece(final int x, final int y) {
		if (x >= 0 && x < 7 && y >= 0 && y < 7) {
			return this.plateau[x][y];
		} else {
			return null;
		}
	}

	/**
	 * @param x coordinate
	 * @param y coordinate
	 * @param p value of the Pawn to be set at these coordinates
	 */
	public void setPiece(final int x, final int y, final Pawn p) {
		this.plateau[x][y] = p;
	}

	/**
	 * @return an ArrayList of every Pawn in the Board
	 */
	public ArrayList<Pawn> getList() {
		final ArrayList<Pawn> res = new ArrayList<>();
		IntStream.rangeClosed(0, 6).forEach(i -> {
			IntStream.rangeClosed(0, 6).forEach(j -> {
				if (this.getPiece(i, j) != null) {
					res.add(this.plateau[i][j]);
				}
			});
		});
		return res;
	}

	/**
	 * @param x
	 * @param y
	 * @return true if there's no Pawn on these coordinates, false otherwise
	 */
	public boolean isEmpty(final int x, final int y) {
		return plateau[x][y] == null;
	}
}

