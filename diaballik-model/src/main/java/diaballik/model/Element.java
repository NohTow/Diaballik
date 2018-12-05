package diaballik.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


/**
 * Abstract class of an element (ball or pawn)
 * deprecated since we don't use the ball class
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")

@JsonSubTypes({
		@JsonSubTypes.Type(value = Pawn.class),
		@JsonSubTypes.Type(value = Ball.class)
})

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public abstract class Element {
	protected Color color;

	@JsonCreator
	public Element(@JsonProperty("color") final Color color) {
		this.color = color;
	}

	public Color getColor() {
		return this.color;
	}

}
