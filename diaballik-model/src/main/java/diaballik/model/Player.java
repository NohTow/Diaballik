package diaballik.model;

public abstract class Player {
	protected String name;
	protected Color color;

	public Player(final String name, final Color color) {
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

}
