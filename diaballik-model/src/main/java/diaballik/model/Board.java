package diaballik.model;

//import java.util.stream.IntStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

import com.fasterxml.jackson.annotation.*;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.annotation.JsonSubTypes;

//import java.util.Objects;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Board {
	protected Pawn[][] plateau;

	@JsonCreator
	public Board() {
		this.plateau = new Pawn[7][7];
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Board board = (Board) o;
		return board.getList().equals(this.getList());
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(getPlateau());
	}

	public Pawn[][] getPlateau() {
		return plateau;
	}

	public Pawn getPiece(final int x, final int y) {
		if (x >= 0 && x < 7 && y >= 0 && y < 7) {
			return this.plateau[x][y];
		} else {
			return null;
		}
	}

	public void setPiece(final int x, final int y, final Pawn p) {
		this.plateau[x][y] = p;
	}

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

}

