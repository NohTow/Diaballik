package diaballik.model;

public class MovePion implements Command {
	private int oldX;
	private int oldY;
	private int newX;
	private int newY;

	public MovePion(final int oldX, final int oldY, final int newX, final int newY) {
		this.oldX = oldX;
		this.oldY = oldY;
		this.newX = newX;
		this.newY = newY;
	}
}
