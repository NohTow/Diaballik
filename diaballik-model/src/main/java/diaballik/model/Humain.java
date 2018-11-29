package diaballik.model;

public class Humain extends Player {

	public Humain(final String name, final Color color) {
		super(name, color);
	}

	@Override
	public Color getColor() {
		return this.color;
	}

	@Override
	public void play(final Command command, final Game game) {

		command.commandDo(game);
		game.incrNbAction();
	}

}
