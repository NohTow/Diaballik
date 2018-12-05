package diaballik.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
//import java.util.Objects;

/**
 * The class representing a computer player
 */
public class IA extends Player {
	private Strategy level;

	/**
	 * @param color the color of the IA (Green anyways)
	 * @param level the level of the IA (noob, advanced, progressive)
	 */
	@JsonCreator
	public IA(@JsonProperty("color") final Color color, @JsonProperty("level") final Strategy level) {
		super("Computer", color);
		this.level = level;
	}

	public void changeLevel(final Strategy newLevel) {
		this.level = newLevel;
	}

	//Deprecated : use Game's play method instead
	@Override
	public void play(final Command command, final Game game) {
		command.commandDo(game);
	}

	public Strategy getLevel() {
		return this.level;
	}

}
