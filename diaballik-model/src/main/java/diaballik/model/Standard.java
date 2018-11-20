package diaballik.model;

import static diaballik.model.Color.Green;
import static diaballik.model.Color.Yellow;

public class Standard extends BoardBuilder {

    @Override
    public void placerPieces(Board board){

        //Placer les pions sur le plateau dans le scénario standard
        for(int i=0 ; i<6 ; i++){
            board.plateau[0][i].color = Yellow;
            board.plateau[0][i].hasBall = false;
            board.plateau[0][i].x = 0;
            board.plateau[0][i].y = i;
            board.plateau[0][i].id = i;
        }
        for(int i=0 ; i<6 ; i++){
            board.plateau[6][i].color = Green;
            board.plateau[6][i].hasBall = false;
            board.plateau[6][i].x = 6;
            board.plateau[6][i].y = i;
            board.plateau[6][i].id = i+7;
        }

        //Placer la balle sur le pion du milieu de chaque ligne
        board.plateau[0][3].hasBall = true;
        board.plateau[6][3].hasBall = true;
    }
}
