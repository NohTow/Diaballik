package diaballik.model;

public class IA extends Player{
    private Strategy level;

    public IA(Color color, Strategy level){
        super("Computer",color);
        this.level = level;
    }

    public void changeLevel(Strategy newLevel){
        this.level = newLevel;
    }

    public void play(Command command){
        test
    }

}
