package view;

import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.FieldType;
import model.PawnType;
import model.Position;


public class Tile extends StackPane {
    private Rectangle background;
    private FieldType type;
    private Rectangle pawn;
    Position position;
    boolean isPawn;
    public PawnType pawnType;


    public Tile(FieldType type) {
        this.type = type;
        background = new Rectangle(StyleManager.TILE_SIZE, StyleManager.TILE_SIZE);
        pawn = new Rectangle(StyleManager.TILE_SIZE, StyleManager.TILE_SIZE);
        pawn.setVisible(false);
        if(type == FieldType.DARK){
            background.setFill(StyleManager.disk);
        }else{
            background.setFill(Color.TRANSPARENT);
        }
        getChildren().addAll(background,pawn);
    }

    void setPawn(PawnType type){
        isPawn = true;
        pawn.setVisible(true);
        pawnType = type;
        pawn.setFill(null);

        switch(type) {
            case BLACK:
                pawn.setFill(StyleManager.blackPawn);
                break;
            case WHITE:
                pawn.setFill(StyleManager.whitePawn);
                break;
            case BLACK_KING:
                pawn.setFill(StyleManager.blackKing);
                break;
            case WHITE_KING:
                pawn.setFill(StyleManager.whiteKing);
                break;
        }
        pawn.setVisible(true);
    }

    public void focus(PawnType type){
        switch(type) {
            case BLACK:
                pawn.setFill(StyleManager.blackPawnHighlighted);
                break;
            case WHITE:
                pawn.setFill(StyleManager.whitePawnHighlighted);
                break;
            case BLACK_KING:
                pawn.setFill(StyleManager.blackKingHighlighted);
                break;
            case WHITE_KING:
                pawn.setFill(StyleManager.whiteKingHighlighted);
                break;
        }
        pawn.setVisible(true);
    }

    public void removeFocus(PawnType type){
        switch(type) {
            case BLACK:
                pawn.setFill(StyleManager.blackPawn);
                break;
            case WHITE:
                pawn.setFill(StyleManager.whitePawn);
                break;
            case BLACK_KING:
                pawn.setFill(StyleManager.blackKing);
                break;
            case WHITE_KING:
                pawn.setFill(StyleManager.whiteKing);
                break;
        }
        pawn.setVisible(true);
        if(!isPawn) {
            pawn.setVisible(false);
        }
    }

    public FieldType getType() {
        return type;
    }

    void removePawn(){
        isPawn = false;
        pawn.setVisible(false);
    }

    public void setPosition(int x, int y){
        position = new Position(x,y);
    }

    public Position getPosition(){
        return position;
    }
   public void printInfo(){
       System.out.println("=============");
        System.out.println("position x:" + position.x+ " \nposition y:"+position.y);
        System.out.println("is pawn visible:"+pawn.isVisible());

    }

    public void promote(){
        if(pawnType == PawnType.WHITE){
            setPawn(PawnType.WHITE_KING);
        }else{
            setPawn(PawnType.BLACK_KING);
        }
    }

    public boolean isPawn() {
        return isPawn;
    }
}
