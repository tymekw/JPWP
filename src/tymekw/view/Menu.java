package view;

import controler.GameWindow;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class Menu {
    GameWindow gameWindow;
    private StackPane root;

    public Menu(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        root = new StackPane();
        Button newGame = new Button("New game");
        Button rules = new Button("Rules");
        Button quit = new Button("Leave");
        VBox buttons = new VBox();

        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(10);
        buttons.getChildren().addAll(newGame,rules,quit);
        root.getChildren().add(buttons);

        newGame.setOnAction(event -> gameWindow.handleNewGame());
        quit.setOnAction(event -> gameWindow.handleQuit());
        rules.setOnAction(event -> gameWindow.handleRules());
    }

    public StackPane getRoot() {
        return root;
    }
}
