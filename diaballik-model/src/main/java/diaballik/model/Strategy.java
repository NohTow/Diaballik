package diaballik.model;

import java.util.ArrayList;

//import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
//import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
//import java.util.Objects;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")

@JsonSubTypes({
		@JsonSubTypes.Type(value = Noob.class),
		@JsonSubTypes.Type(value = Advanced.class),
		@JsonSubTypes.Type(value = Progressive.class)
})

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public interface Strategy {
	public void exec(final Game game, ArrayList<Pawn> pions);
}
