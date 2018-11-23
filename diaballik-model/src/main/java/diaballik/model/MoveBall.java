package diaballik.model;

public class MoveBall implements Command {
	private int oldX;
	private int oldY;
	private int newX;
	private int newY;

	public MoveBall(final int oldX, final int oldY, final int newX, final int newY) {
		this.oldX = oldX;
		this.oldY = oldY;
		this.newX = newX;
		this.newY = newY;
	}
}