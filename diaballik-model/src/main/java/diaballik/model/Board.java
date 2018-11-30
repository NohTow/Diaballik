package diaballik.model;

//import java.util.stream.IntStream;

import java.util.ArrayList;
import java.util.stream.IntStream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
//import java.util.Objects;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Board {
	protected Pawn[][] plateau;

	@JsonCreator
	public Board() {
		this.plateau = new Pawn[7][7];
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

