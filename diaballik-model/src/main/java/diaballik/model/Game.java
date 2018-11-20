package diaballik.model;

import java.util.ArrayList;
import java.util.Map;

public class Game {
    private int numTurn;
    private Color color;
    private boolean hasIA;
    private int idGame;
    private ArrayList<Command> save;
    private ArrayList<Command> undo;

    private Board gameBoard;

    private Player joueur1;
    private Player joueur2;

    public boolean isFinished(){
        boolean fin = false;
        //parcourir les 2 lignes du Board pour voir si il y a un pion de couleur adverse avec la balle
        for(int i =0 ; i<7 ; i++){
            if (gameBoard.plateau[0][i].color.equals("Green") && gameBoard.plateau[0][i].hasBall == true){
                fin = true;
                break;
            }
        }
        for(int i =0 ; i<7 ; i++){
            if (gameBoard.plateau[6][i].color.equals("Yellow") && gameBoard.plateau[6][i].hasBall == true){
                fin = true;
                break;
            }
        }
        return fin;
    }

    public void saveGame(){

    }
    public void quitGame(){

    }
    public void pushSave(Command command){
        this.save.add(command);
    }
    public void pushUndo(Command command){
        this.undo.add(command);
    }

    public Command undo(){

    }
    public Command redo(){

    }


}
