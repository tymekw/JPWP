package view;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.io.InputStream;

public class StyleManager {
    static final int TILE_SIZE = 80;
    static ImagePattern whitePawn, whitePawnHighlighted, whiteKing, whiteKingHighlighted;
    static ImagePattern blackPawn, blackPawnHighlighted, blackKing, blackKingHighlighted;
    static ImagePattern disk;

    public StyleManager(){
        InputStream redPawn = getClass().getResourceAsStream("/redPawnNormal1.png");
        InputStream redPawnHighlighted = getClass().getResourceAsStream("/redPawnFocus1.png");
        InputStream redKing = getClass().getResourceAsStream("/redPawnKingNormal1.png");
        InputStream redKingHighlighted = getClass().getResourceAsStream("/redPawnKingFocus1.png");
        InputStream bluePawn = getClass().getResourceAsStream("/bluePawnNormal1.png");
        InputStream bluePawnHighlighted = getClass().getResourceAsStream("/bluePawnFocus1.png");
        InputStream blueKing = getClass().getResourceAsStream("/bluePawnKingNormal1.png");
        InputStream blueKingHighlighted = getClass().getResourceAsStream("/bluePawnKingFocus1.png");
        InputStream diskImg = getClass().getResourceAsStream("/disk1.png");

        blackPawn = new ImagePattern(new Image(redPawn));
        blackPawnHighlighted = new ImagePattern(new Image(redPawnHighlighted));
        blackKing = new ImagePattern(new Image(redKing));
        blackKingHighlighted = new ImagePattern(new Image(redKingHighlighted));
        whitePawn = new ImagePattern(new Image(bluePawn));
        whitePawnHighlighted = new ImagePattern(new Image(bluePawnHighlighted));
        whiteKing = new ImagePattern(new Image(blueKing));
        whiteKingHighlighted = new ImagePattern(new Image(blueKingHighlighted));
        disk = new ImagePattern(new Image(diskImg));
    }
}
