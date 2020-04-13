package model;


import com.sun.xml.internal.ws.addressing.WsaActionUtil;

public class Board {
    private Field[][] fields;
    final int ROWS = 8;
    final int COLS = 8;
    CheckersManager checkersManager;


    public Board(CheckersManager checkersManager) {
        fields = new Field[ROWS][COLS];
        this.checkersManager = checkersManager;
    }

    public void init(){
        for(int i = 0; i< ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if ((i + j) % 2 != 0) {
                    fields[i][j] = new Field(FieldType.DARK);
                } else {
                    fields[i][j] = new Field(FieldType.BRIGHT);
                }
                fields[i][j].setPosition(new Position(i,j));
            }
        }
        arrangePawns();
    }

    void arrangePawns(){
        for(int i=0; i<COLS; i++){
            for(int j=0; j<3;j++){
                if(fields[i][j].type == FieldType.DARK){
                    fields[i][j].setPawn(PawnType.WHITE);
                }else {
                    fields[i][ROWS-j-1].setPawn(PawnType.BLACK);
                }
            }
        }
    }

    public Field[][] getFields() {
        return fields;
    }

    Field getField(Position position){
        return fields[position.x][position.y];
    }

    Field getField(int x, int y){
        return fields[x][y];
    }

    void move(Position src, Position dst){
        fields[dst.x][dst.y].setPawn(fields[src.x][src.y].getPawnType());
        fields[src.x][src.y].removePawn();
    }

    void removePawn(Field field){
        field.removePawn();
    }

    void printBoard(){
        for(int i = 0;i<ROWS; i++){
            System.out.print("| ");
            for(int j = 0; j<COLS; j++){
                System.out.print("["+j+" "+i);
                printField(fields[j][i]);
                System.out.print("] ");
            }
            System.out.println();
        }
    }

    void printField(Field field){
        if(!field.isPawn()){
            System.out.print("_");
        }else{
            if(field.getPawnType() == PawnType.WHITE){
                System.out.print("W");
            }
            if(field.getPawnType() == PawnType.BLACK){
                System.out.print("B");
            }
        }
    }

    boolean hasAnyPawn(Player player){
        for(int i = 0; i< view.Board.ROWS; i++) {
            for (int j = 0; j < view.Board.ROWS; j++) {
                if(fields[i][j].isPawn()  && player.isMyPawn(fields[i][j].getPawnType())) return true;
            }
        }
        return false;
    }

}
