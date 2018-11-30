package diaballik.model;

//import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
//import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
//import java.util.Objects;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")

@JsonSubTypes({
		@JsonSubTypes.Type(value = MoveBall.class),
		@JsonSubTypes.Type(value = MovePion.class)
})

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public interface Command {
	/*private int oldX;
	int oldY;
	int newX;
	int newY;*/

	public void commandDo(Game game);
	public void commandUndo(Game game);


	public int getOldX();
	public int getOldY();
	public int getNewX();
	public int getNewY();
}
