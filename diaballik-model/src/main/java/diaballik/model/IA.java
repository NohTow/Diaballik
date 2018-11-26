package diaballik.model;

public class IA extends Player {
	private Strategy level;

	public IA(final Color color, final Strategy level) {
		super("Computer", color);
		this.level = level;
	}

	public void changeLevel(final Strategy newLevel) {
		this.level = newLevel;
	}
	@Override
	public void play(final Command command, final Game game) {
		command.commandDo(game);
	}

}
