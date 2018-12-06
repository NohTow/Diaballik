package diaballik.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.Objects;

/**
 * Abstract representation of a player
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")

@JsonSubTypes({
		@JsonSubTypes.Type(value = Humain.class),
		@JsonSubTypes.Type(value = IA.class)
})

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public abstract class Player {
	protected String name;
	protected Color color;

	/**
	 * @param name the name of the player
	 * @param color the color of the player
	 */
	@JsonCreator
	public Player(@JsonProperty("name") final String name, @JsonProperty("color") final Color color) {
		this.name = name;
		this.color = color;
	}

	public Color getColor() {
		return this.color;
	}

	public String getName() {
		return this.name;
	}

	public abstract void play(final Command command, Game game);

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final Player player = (Player) o;
		return Objects.equals(getName(), player.getName()) &&
				getColor() == player.getColor();
	}

	@Override
	public int hashCode() {

		return Objects.hash(getName(), getColor());
	}
}
