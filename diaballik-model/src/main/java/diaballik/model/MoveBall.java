package diaballik.model;

public class MoveBall implements Command {
    private int oldX;
    private int oldY;
    private int newX;
    private int newY;

    public MoveBall(int oldX, int oldY, int newX, int newY){
        this.oldX = oldX;
        this.oldY = oldY;
        this.newX = newX;
        this.newY = newY;
    }
}
