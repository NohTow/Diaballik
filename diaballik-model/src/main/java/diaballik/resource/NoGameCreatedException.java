package diaballik.resource;

public class NoGameCreatedException extends Exception{

	public NoGameCreatedException() {
		super("Erreur lors de la création de la partie");
	}
}
