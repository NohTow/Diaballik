package diaballik.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * the class representing an human player
 */
public class Humain extends Player {

	/**
	 * @param name the name of the player
	 * @param color his color
	 */
	@JsonCreator
	public Humain(@JsonProperty("name") final String name, @JsonProperty("color") final Color color) {
		super(name, color);
	}

	@Override
	public Color getColor() {
		return this.color;
	}


	//Deprecated : use the Game's play method instead
	@Override
	public void play(final Command command, final Game game) {
		command.commandDo(game);
		game.incrNbAction();
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		return super.equals(obj);
	}
}
