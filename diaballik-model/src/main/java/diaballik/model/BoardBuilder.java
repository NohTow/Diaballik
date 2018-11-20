package diaballik.model;

public abstract class BoardBuilder {

    public Board monterPlateau(){
        Board board = new Board();
        return board;
    }

    public abstract void placerPieces(Board board);
}
