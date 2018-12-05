package diaballik.model;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")

@JsonSubTypes({
		@JsonSubTypes.Type(value = MoveBall.class, name = "MoveBall"),
		@JsonSubTypes.Type(value = MovePion.class, name = "MovePion")
})

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public abstract class Command {
	protected int oldX;
	protected int oldY;
	protected int newX;
	protected int newY;

	@JsonCreator
	public Command(@JsonProperty("oldX") final int oX, @JsonProperty("oldY") final int oY, @JsonProperty("newX") final int nX, @JsonProperty("newY") final int nY) {
		this.oldX = oX;
		this.oldY = oY;
		this.newX = nX;
		this.newY = nY;
	}

	public abstract void commandDo(Game game);

	public abstract void commandUndo(Game game);


	public int getOldX() {
		return this.oldX;
	}

	public int getOldY() {
		return this.oldY;
	}

	public int getNewX() {
		return this.newX;
	}

	public int getNewY() {
		return this.newY;
	}
}
