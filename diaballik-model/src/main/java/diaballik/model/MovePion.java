package diaballik.model;

public class MovePion implements Command {
    private int oldX;
    private int oldY;
    private int newX;
    private int newY;

    public MovePion(int oldX, int oldY, int newX, int newY){
        this.oldX = oldX;
        this.oldY = oldY;
        this.newX = newX;
        this.newY = newY;
    }
}
