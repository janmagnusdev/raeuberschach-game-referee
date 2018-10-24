package GUIView.ComponentCreation;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class BoardCreation {
    public BorderPane createBoardBorderPane() {
        BorderPane borderPane = new BorderPane();

        Label playerALabel = new Label("Player A");
        borderPane.setTop(playerALabel);
        GridPane boardGrid = new GridPane();
        boardGrid.setId("BoardGrid");
        borderPane.setCenter(boardGrid);
        boardGrid.setMinSize(200, 200);
        boardGrid.setMaxWidth(600);
        Label playerBLabel = new Label("Player B");
        borderPane.setBottom(playerBLabel);

        BorderPane.setAlignment(playerALabel, Pos.CENTER);
        BorderPane.setAlignment(borderPane, Pos.CENTER);
        BorderPane.setAlignment(playerBLabel, Pos.CENTER);
        borderPane.setPadding(new Insets(25.0));
        borderPane.setMaxHeight(500);

        return borderPane;
    }
}
