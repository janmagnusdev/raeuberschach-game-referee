package GUIView;

import assets.IO;
import Controller.BoardEventHandler;
import GUIView.ComponentCreation.*;
import GameModel.Board;
import GameModel.Game;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GameGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private BoardPanel boardPanel;
    private Label messageLabel;
    private Game game;
    private MenuBar menuBar;
    private ToolBar toolBar;
    private Button playButton;
    private Button stopButton;
    private MenuItem startGame;
    private MenuItem stopGame;

    @Override
    public void start(Stage stage) throws Exception {

        BorderPane root = new BorderPane();
        Board board = new Board();
        game = new Game(board);
        boardPanel = new BoardPanel(game, 63, 0);
        menuBar = new MenuBarGameReferee(this);
        toolBar = createToolBar(this);

        ScrollPane gameScrollPane = new ScrollPane();
        gameScrollPane.setMaxSize(600, 600);
        gameScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        gameScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        gameScrollPane.setContent(boardPanel);

        root.getStylesheets().add("css/default.css");
        messageLabel = new Label("Welcome to this Referee!");
        VBox toolAndMenuBox = new VBox();
        toolAndMenuBox.getChildren().addAll(menuBar, toolBar);
        toolAndMenuBox.setAlignment(Pos.CENTER);
        VBox.setVgrow(boardPanel, Priority.ALWAYS);
        boardPanel.paintState(null);
        root.setTop(toolAndMenuBox);
        root.setCenter(gameScrollPane);
        root.setBottom(messageLabel);
        //alle Komponenten des GUIs werden der Vbox in den oberen Zeile hinzugefügt. Die Methoden erstellen diese Komponenten zur Laufzeit.
        Scene primaryScene = new Scene(root);

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                Platform.exit();
                System.exit(0);
            }
        });
        stage.setTitle("Räuberschach V2.01-//-02");
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

    private ToolBar createToolBar(GameGUI parent) { //erstellt die ToolBar unter dem Menü und fügt entsprechende
        // Buttons hinzu.
        ToolBar toolBar = new ToolBar();

        Button newGuiButton = new Button();
        Image fileIcon = new Image("icons/file-icon.png");
        newGuiButton.setGraphic(new ImageView(fileIcon));
        newGuiButton.setOnAction(event -> parent.createCloneGui());
        Button printButton = new Button();
        Image printIcon = new Image("icons/print-icon.png");
        printButton.setGraphic(new ImageView(printIcon));
        playButton = new Button();
        Image playIcon = new Image("icons/play-icon.png");
        playButton.setGraphic(new ImageView(playIcon));
        stopButton = new Button();
        stopButton.setDisable(true);
        Image recordIcon = new Image("icons/record-icon.png");
        stopButton.setGraphic(new ImageView(recordIcon));
        stopButton.setOnAction(event -> parent.exitAllGUIs());

        playButton.setOnAction(startDummyDummyGameHandler());
        stopButton.setOnAction(stopDummyDummyGameHandler());

        toolBar.getItems().addAll(newGuiButton, printButton, new Separator(), playButton, stopButton);

        return toolBar;
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

    public Game getGame() {
        return game;
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    public ToolBar getToolBar() {
        return toolBar;
    }

    private class MenuBarGameReferee extends MenuBar {


        MenuBarGameReferee(GameGUI parent) { //erstellt den obersten Menü-Balken; fügt diesem außerdem chronologisch
            // alle
            // MenutItems hinzu

            Menu gameMenu = new Menu("_Game");
            gameMenu.setMnemonicParsing(true);

            MenuItem newGame = createMenuItem("_Neu", "SHORTCUT + N", "/icons/open-menu-icon.png", null);
            newGame.setOnAction(event -> parent.createCloneGui());
            MenuItem printGame = createMenuItem("_Print", "SHORTCUT + P", "/icons/print-menu-icon.png", null);
            startGame = createMenuItem("St_art", "SHORTCUT + A", null, null);
            stopGame = createMenuItem("_Stop", "SHORTCUT + S", null, null);
            stopGame.setDisable(true);

            startGame.setOnAction(startDummyDummyGameHandler());
            stopGame.setOnAction(stopDummyDummyGameHandler());

        /*DisableButtonProperty disableStartBean = new DisableButtonProperty();
        disableStartBean.valueProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                startGame.setDisable(newValue);
            }
        });

        DisableButtonProperty disableStopBean = new DisableButtonProperty();
        disableStopBean.valueProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                stopGame.setDisable(newValue);

            }
        });*/

            MenuItem exitGame = createMenuItem("_Exit", "SHORTCUT + Q", null, null);
            exitGame.setOnAction(event -> parent.exitAllGUIs());

            gameMenu.getItems().addAll(newGame, printGame, new SeparatorMenuItem(), startGame, stopGame, new SeparatorMenuItem(), exitGame);
            //Separator können nicht benutzt werden; addAll() erwartet MenuItems. Dazu gibt es die Klasse SeparatorMenuItem.

            this.getMenus().addAll(gameMenu, createPlayerAMenu(), createPlayerBMenu());

        }

        private MenuItem createMenuItem(String text, String shortcut, String graphicPath,
                                  EventHandler<ActionEvent> eventHandler) {
            MenuItem menuItem = new MenuItem();
            if (text != null) {
                menuItem.setText(text);
            }
            if (shortcut != null) {
                menuItem.setAccelerator(KeyCombination.keyCombination(shortcut));
            }
            if (graphicPath != null) {
                Image image = new Image(getClass().getResource(graphicPath).toString());
                menuItem.setGraphic(new ImageView(image));
            }
            if (eventHandler != null) {
                menuItem.setOnAction(eventHandler);
            }
            return menuItem;

        }

        private Menu createPlayerBMenu() {
            Menu playerBMenu = new Menu("Player _B");
            playerBMenu.setMnemonicParsing(true);

            RadioMenuItem playerBButton = new RadioMenuItem("Player");
            playerBButton.setAccelerator(KeyCombination.valueOf("SHORTCUT + S"));
            playerBButton.setSelected(true);
            playerBMenu.getItems().add(playerBButton);

            return playerBMenu;
        }

        private Menu createPlayerAMenu() {
            Menu playerAMenu = new Menu("Player _A");
            playerAMenu.setMnemonicParsing(true);

            RadioMenuItem playerAButton = new RadioMenuItem("Player");
            playerAButton.setSelected(true);
            playerAButton.setAccelerator(KeyCombination.valueOf("SHORTCUT + S"));
            playerAMenu.getItems().add(playerAButton);

            return playerAMenu;
        }
    }

    private EventHandler<ActionEvent> startDummyDummyGameHandler() {
        return event -> {
            if (this.getGame().getDummyDummyGame() == null) {
                this.getGame().startDummyDummyGame(this.getGame());
                startGame.setDisable(true);
                stopGame.setDisable(false);
                playButton.setDisable(true);
                stopButton.setDisable(false);
            }
        };
    }

    private EventHandler<ActionEvent> stopDummyDummyGameHandler() {
        return event -> {
            try {
                this.getGame().getDummyDummyGame().interrupt();
            } catch (Exception e) {
                IO.println(e.toString());
            }
            startGame.setDisable(false);
            stopGame.setDisable(true);
            playButton.setDisable(false);
            stopButton.setDisable(true);
        };
    }
}
