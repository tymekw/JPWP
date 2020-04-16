package model;


import java.util.List;

public class Board {
    private Field[][] fields;
    int ROWS = 8;
    int COLS = 8;
    //CheckersManager checkersManager;


    public Board() {
        fields = new Field[ROWS][COLS];
        ROWS = 8;
        COLS = 8;
//        this.checkersManager = checkersManager;
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
                    fields[i][j].setPawn1(PawnType.WHITE);
                }else {
                    fields[i][ROWS-j-1].setPawn1(PawnType.BLACK);
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

    void setField(int x, int y, Field field){
        fields[x][y] = field;
    }

    void move(Position src, Position dst){
        fields[dst.x][dst.y].setPawn1(fields[src.x][src.y].getPawnType());
        fields[src.x][src.y].removePawn();
    }

    public void performMove(Move move){
        Position src = move.getSource().position;
        Position dst = move.getDestination().position;
        //System.out.println("inside performMove before:, pawnType: "+fields[dst.x][dst.y].getPawnType()+" isPawn: "+fields[dst.x][dst.y].isPawn());
        move(src,dst);
        //System.out.println("inside performMove after: "+fields[dst.x][dst.y].getPawnType()+" isPawn: "+fields[dst.x][dst.y].isPawn());
        if(move.isCaptured()){
            Position capturePos = move.getCaptured().position;
            //System.out.println("capture: x"+capturePos.x+"  y: "+capturePos.y);
            fields[capturePos.x][capturePos.y].removePawn();
        }
    }

    public void performAIMove(List<Move> moves){
        moves.forEach(e -> performMove(e));
        Position position = moves.get(moves.size()-1).getDestination().position;
        checkPromotion(fields[position.x][position.y]);
    }

    void checkPromotion(Field field){
        if(!isPawnPromoted(field) && isValidPromotionCondition(field)) {
            promote(field);
        }
    }

    void promote(Field field){
        if(field.getPawnType() == PawnType.WHITE){
            //System.out.println("PROMOTE WHITE PROMOTE WHYITE 1");
            field.setPawn1(PawnType.WHITE_KING);
        }else{
            field.setPawn1(PawnType.BLACK_KING);
        }
        //changePlayer();
    }

    boolean isValidPromotionCondition(Field field){
        PawnType type = field.getPawnType();
        int row = field.position.y;
        return row == 0 && type==PawnType.BLACK || row==7 && type==PawnType.WHITE;
    }

    boolean isPawnPromoted(Field field){
        if(field.isPawn()){
            return field.getPawnType() == PawnType.WHITE_KING || field.getPawnType() == PawnType.BLACK_KING;
        }
        return false;
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

    public Board copyBoard(){
        Board board = new Board();
        Field[][] newFields = board.getFields();
        for(int i = 0; i< ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                Field field = new Field();
                field.setType(fields[i][j].getType());
                field.setPawn1(fields[i][j].getPawnType());
                //!!!!!!!!!!!!!!!!!!!!!!!!!
                field.setPawnBoolean(fields[i][j].isPawn());
                Position pos = new Position(fields[i][j].getPosition().x,fields[i][j].getPosition().y);
                field.setPosition(pos);
                board.setField(i,j,field);
                if(fields[i][j].position.x != newFields[i][j].position.x || fields[i][j].position.y != newFields[i][j].position.y || fields[i][j].type != newFields[i][j].type || newFields[i][j].isPawn() != fields[i][j].isPawn() || newFields[i][j].getPawnType() != fields[i][j].getPawnType()){
                    System.out.println(fields[i][j].position+" "+fields[i][j].type+"  "+fields[i][j].isPawn()+"  "+fields[i][j].getPawnType());
                    System.out.println(newFields[i][j].position+" "+newFields[i][j].type+"  "+newFields[i][j].isPawn()+"  "+newFields[i][j].getPawnType());

                }
            }
        }
        return board;
    }

    boolean hasAnyPawn(Player player){
        for(int i = 0; i< ROWS; i++) {
            for (int j = 0; j < ROWS; j++) {
                if(fields[i][j].isPawn()  && player.isMyPawn(fields[i][j].getPawnType())) return true;
            }
        }
        return false;
    }

}
