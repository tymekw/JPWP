package view;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

public class GridMaker {


    GridPane buildGrid(Board board){
        GridPane grid  = new GridPane();
        for(int i = 0; i< Board.ROWS; i++){
            for (int j = 0; j< Board.ROWS; j++){
                grid.add(board.tiles[i][j],i,j);
            }
        }
        grid.setMaxSize(Board.ROWS*StyleManager.TILE_SIZE,Board.COLS*StyleManager.TILE_SIZE);
        grid.setStyle("-fx-background-color:TRANSPARENT");
        grid.setAlignment(Pos.CENTER);
        return grid;
    }
}
