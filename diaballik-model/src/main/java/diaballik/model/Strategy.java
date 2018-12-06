package diaballik.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


/**
 * the interface of the level of an IA
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")

@JsonSubTypes({
		@JsonSubTypes.Type(value = Noob.class),
		@JsonSubTypes.Type(value = Advanced.class),
		@JsonSubTypes.Type(value = Progressive.class)
})

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public interface Strategy {
	public void exec(final Game game);
}
