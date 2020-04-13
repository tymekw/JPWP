package view;

import controler.GameWindow;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class Rules {
    GameWindow gameWindow;
    StackPane root;

    public Rules(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        root = new StackPane();
    }

    public void build(){
        VBox vbox = new VBox();
        Label rule1 = new Label("1. Red player starts the game");
        Label rule2 = new Label("2. Uncrowned pieces can move diagonally forward only");
        Label rule3 = new Label("3. Kings can move in any diagonal direction");
        Label rule4 = new Label("4. Player must make all available jumps in the sequence chosen");
        Button menu = new Button("menu");
        menu.setOnMouseClicked(e -> gameWindow.handleMenuButton());

        vbox.setSpacing(30);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(rule1,rule2,rule3,rule4,menu);
        root.getChildren().add(vbox);
    }

    public StackPane getRoot() {
        return root;
    }
}
