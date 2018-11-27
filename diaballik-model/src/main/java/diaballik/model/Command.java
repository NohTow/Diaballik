package diaballik.model;

public interface Command {
	/*private int oldX;
	int oldY;
	int newX;
	int newY;*/

	public void commandDo(Game game);
	public void commandUndo(Game game);


	/*public int getOldX();
	public int getOldY();
	public int getNewX();
	public int getNewY();*/
}
