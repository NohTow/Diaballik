package diaballik.model;

public class Pawn extends Element {
	protected int x;
	protected int y;
	protected int id;
	protected boolean hasBall;


	public Pawn(final int x, final int y, final int id, final boolean hasBall, final Color color) {
		super(color);
		this.x = x;
		this.y = y;
		this.id = id;
		this.hasBall = hasBall;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getId() {
		return this.id;
	}

	public boolean getHasBall() {
		return this.hasBall;
	}

	public void setPos(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

}
