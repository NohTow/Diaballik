package diaballik.model;

public class Humain extends Player{

    public Humain(String name, Color color){
        super(name, color);
    }
    public Color getColor(){
        return this.color;
    }

    @Override
    public void play(Command command){

    }

}
