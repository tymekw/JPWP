package view;

import controler.GameWindow;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import model.FieldType;
import model.PawnType;
import model.Player;
import model.Position;


public class Board {
    Tile[][] tiles;
    public static final int ROWS = 8;
    public static final int COLS = 8;
    boolean isPawnFocused;
    Tile focusedPawn;
    Player player;//so far unnessesary
    GameWindow gameWindow;

    public Board(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        tiles = new Tile[ROWS][COLS];
        isPawnFocused = false;
    }

    void addTile(int x, int y){
        Tile tile = createTile(x,y);
        EventHandler<MouseEvent> clickHandler = gameWindow.getMouseClickHandler(this);
        tile.addEventHandler(MouseEvent.MOUSE_CLICKED ,clickHandler);
        tiles[x][y] = tile;
    }

    private Tile createTile(int x,int y){
        Tile tile;
        if((x+y) % 2 != 0){
            tile = new Tile(FieldType.DARK);
        }else{
            tile = new Tile(FieldType.BRIGHT);
        }
        tile.setPosition(x,y);
        return tile;
    }

    public Tile getFocusedPawn() {
        return focusedPawn;
    }

    public boolean isPawnFocused(){
       return isPawnFocused;
    }

    void fill(){
        for(int i = 0; i< Board.ROWS; i++) {
            for (int j = 0; j < Board.COLS; j++) {
                addTile(i, j);
            }
        }
        arrangePawns();
    }

    void arrangePawns(){
        for(int i=0; i<COLS; i++){
            for(int j=0; j<3;j++){
                if(tiles[i][j].getType() == FieldType.DARK){
                    tiles[i][j].setPawn(PawnType.WHITE);
                }else {
                    tiles[i][ROWS-j-1].setPawn(PawnType.BLACK);
                }
            }
        }
    }

    public void showMove(Tile src, Tile dst) {
        src.removeFocus(src.pawnType);
        src.removePawn();
        dst.setPawn(src.pawnType);
    }

    public void showMove(Position src, Position dst) {
        System.out.println("doing shppw move/perform");
        Tile source = tiles[src.x][src.y];
        Tile destination = tiles[dst.x][dst.y];
        source.removePawn();
        destination.setPawn(source.pawnType);
        //showMove(source,destination);
    }


    public void removePawn(int x, int y){
        tiles[x][y].removePawn();
    }

    public void setCurrentPlayer(Player player) {
        this.player = player;
    }

    public Player getCurrentPlayer() {
        return player;
    }

    public void setFocusedPawn(Tile focusedPawn) {
        isPawnFocused = true;
        this.focusedPawn = focusedPawn;
    }

    public void removeFocusedPawn(){
        isPawnFocused = false;
        focusedPawn = null;
    }
    public void promotePawn(Position position){
        tiles[position.x][position.y].promote();
    }
}
