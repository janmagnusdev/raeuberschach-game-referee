package GUIView;

import assets.IO;
import Controller.BoardEventHandler;
import GUIView.ComponentCreation.*;
import GUIView.ComponentCreation.MenuBarGameReferee;
import GameModel.Board;
import GameModel.Game;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private BoardPanel boardPanel;
    private Label messageLabel;

    @Override
    public void start(Stage stage) throws Exception {

        BorderPane root = new BorderPane();
        Board board = new Board();
        Game game = new Game(board);
        boardPanel = new BoardPanel(game, 63, 0);

        ScrollPane gameScrollPane = new ScrollPane();
        gameScrollPane.setMaxSize(600, 600);
        gameScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        gameScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        gameScrollPane.setContent(boardPanel);

        root.getStylesheets().add("css/default.css");
        messageLabel = new Label("Welcome to this Referee!");
        VBox toolAndMenuBox = new VBox();
        toolAndMenuBox.getChildren().addAll(new MenuBarGameReferee(this), new ToolBarCreation().createToolBar(this));
        toolAndMenuBox.setAlignment(Pos.CENTER);
        VBox.setVgrow(boardPanel, Priority.ALWAYS);
        boardPanel.paintState();
        root.setTop(toolAndMenuBox);
        root.setCenter(gameScrollPane);
        root.setBottom(messageLabel);
        //alle Komponenten des GUIs werden der Vbox in den oberen Zeile hinzugefügt. Die Methoden erstellen diese Komponenten zur Laufzeit.
        Scene primaryScene = new Scene(root);

        stage.setTitle("GameGUI");
        stage.setMaxHeight(1000);
        stage.setMaxWidth(1000);
        stage.setScene(primaryScene);
        stage.show();

        //Eventhandler/ Controller
        BoardEventHandler bh = new BoardEventHandler(game, this);
        boardPanel.getCanvas().setOnMousePressed(bh);
        boardPanel.getCanvas().setOnMouseDragged(bh);
        boardPanel.getCanvas().setOnMouseReleased(bh);
    }

    public void exitAllGUIs() {
        Platform.exit();
    }

    public void createCloneGui() {
        try {
            new GameGUI().start(new Stage());
        } catch (Exception e) {
            IO.println("Couldn't create new GUI");
        }
    }

    //Getter
    public BoardPanel getBoardPanel() {
        return boardPanel;
    }

    public void setLabelText(String text) {
        messageLabel.setText(text);
    }

    public String getLabelText() {
        return messageLabel.getText();
    }
}
