package diaballik.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
//import java.util.Objects;

public class IA extends Player {
	private Strategy level;

	@JsonCreator
	public IA(@JsonProperty("color") final Color color, @JsonProperty("level") final Strategy level) {
		super("Computer", color);
		this.level = level;
	}

	public void changeLevel(final Strategy newLevel) {
		this.level = newLevel;
	}
	@Override
	public void play(final Command command, final Game game) {
		command.commandDo(game);
	}

}
