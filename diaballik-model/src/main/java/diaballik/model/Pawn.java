package diaballik.model;

public class Pawn extends Element {
    protected int x;
    protected int y;
    protected int id;
    protected boolean hasBall;


    public Pawn(int x, int y, int id, boolean hasBall, Color color) {
        super(color);
        this.x = x;
        this.y = y;
        this.id = id;
        this.hasBall = hasBall;
    }

    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public int getId(){
        return this.id;
    }
    public boolean getHasBall(){
        return this.hasBall;
    }
    public void setPos(int x, int y){
        this.x = x;
        this.y = y;
    }

}
