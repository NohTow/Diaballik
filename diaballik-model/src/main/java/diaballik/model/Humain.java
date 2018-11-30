package diaballik.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
//import java.util.Objects;

public class Humain extends Player {

	@JsonCreator
	public Humain(@JsonProperty("name") final String name, @JsonProperty("color") final Color color) {
		super(name, color);
	}

	@Override
	public Color getColor() {
		return this.color;
	}

	@Override
	public void play(final Command command, final Game game) {
		command.commandDo(game);
		game.incrNbAction();
	}

}
