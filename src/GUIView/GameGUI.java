package GUIView;

import GameModel.Players.Player;
import Loaders.ProgramManager;
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

import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class GameGUI extends Application {

    public static void main(String[] args) { //TODO Thread Safety!
        launch(args);
    }

    private BoardPanel boardPanel;
    private Label messageLabel; //TODO Messages müssen noch angezeigt werden; bspw. "Weiß ist am Zug!".
    private Game game;
    private MenuBar menuBar;
    private ToggleGroup tgWhite;
    private ToggleGroup tgBlack;
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
                exitAllGUIs();
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

    private void exitAllGUIs() {
        Platform.exit();
        System.exit(0);
    }

    private void createCloneGui() {
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

        playButton.setOnAction(startAIAIGameHandler());
        stopButton.setOnAction(stopGameThreadHandler());

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

    //region MenuBarGameReferee
    private class MenuBarGameReferee extends MenuBar {


        MenuBarGameReferee(GameGUI parent) {

            Menu gameMenu = new Menu("_Game");
            gameMenu.setMnemonicParsing(true);

            MenuItem newGame = createMenuItem("_Neu", "SHORTCUT + N", "/icons/open-menu-icon.png", null);
            newGame.setOnAction(event -> parent.createCloneGui());
            MenuItem printGame = createMenuItem("_Print", "SHORTCUT + P", "/icons/print-menu-icon.png", null);
            startGame = createMenuItem("St_art", "SHORTCUT + A", null, null);
            stopGame = createMenuItem("_Stop", "SHORTCUT + S", null, null);
            stopGame.setDisable(true);

            startGame.setOnAction(startAIAIGameHandler());
            stopGame.setOnAction(stopGameThreadHandler());

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

            this.getMenus().addAll(gameMenu, createPlayerMenu(true), createPlayerMenu(false));

        }

        @SuppressWarnings("all")
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

        private Menu createPlayerMenu(boolean isWhite) {
            Menu playerMenu = new Menu(isWhite ? "White" : "Black");
            playerMenu.setMnemonicParsing(true);
            if (isWhite) {
                tgWhite = new ToggleGroup();
            } else {
                tgBlack = new ToggleGroup();
            }
            ToggleGroup tg = isWhite ? tgWhite : tgBlack;

            RadioMenuItem playerButton = new RadioMenuItem("Player");
            playerButton.setAccelerator(KeyCombination.valueOf("SHORTCUT + H"));
            playerButton.setSelected(true);
            playerButton.setToggleGroup(tg);
            playerMenu.getItems().add(playerButton);

            ArrayList<String> classnames = ProgramManager.getInstance().getPlayerNamesList();
            for (String classname : classnames) {
                RadioMenuItem AIButton = new RadioMenuItem(classname);
                AIButton.setToggleGroup(tg);
                playerMenu.getItems().add(AIButton);
            }

            return playerMenu;
        }
    }
    //endregion

    private EventHandler<ActionEvent> startDummyDummyGameHandler() {
        return event -> {
            if (this.getGame().getGameThread() == null) {
                this.getGame().startDummyDummyGame(this.getGame());
                startGame.setDisable(true);
                stopGame.setDisable(false);
                playButton.setDisable(true);
                stopButton.setDisable(false);
            }
        };
    }

    private EventHandler<ActionEvent> startAIAIGameHandler() {
        return event -> {
            if (this.getGame().getGameThread() == null) {
                    RadioMenuItem radioWhite = (RadioMenuItem) tgWhite.getSelectedToggle();
                    String whiteProgramClassname = radioWhite.getText();
                    RadioMenuItem radioBlack = (RadioMenuItem) tgBlack.getSelectedToggle();
                    String blackProgramClassname = radioBlack.getText();

                    if (!whiteProgramClassname.equals("Player") && !blackProgramClassname.equals("Player")) {
                        //NOTIDEAL Noch wird einfach überprüft, ob einer der Spieler Mensch ist, über Classname Player.
                        Class<?> whiteClass =
                                ProgramManager.getInstance().loadClassFromProgramsFolder(whiteProgramClassname);
                        Class<?> blackClass = ProgramManager.getInstance().loadClassFromProgramsFolder(blackProgramClassname);
                        try {
                            Constructor<?> csw = whiteClass.getDeclaredConstructor(boolean.class);
                            Constructor<?> csb = blackClass.getDeclaredConstructor(boolean.class);
                            this.game.startSelectedAIGame((Player) csw.newInstance(true),
                                                               (Player) csb.newInstance(false));
                            startGame.setDisable(true);
                            stopGame.setDisable(false);
                            playButton.setDisable(true);
                            stopButton.setDisable(false);
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                }
        };
    }

        private EventHandler<ActionEvent> stopGameThreadHandler() {
            return event -> {
                if (game.getGameThread().isAlive() || game.getGameThread() != null) {
                    this.getGame().getGameThread().interrupt();
                    startGame.setDisable(false);
                    stopGame.setDisable(true);
                    playButton.setDisable(false);
                    stopButton.setDisable(true);
                    game.getBoard().setPiecesInitial();
                    Platform.runLater(() -> boardPanel.update(null));
                    game.setCurrentPlayer(game.getWhite());
                }
            };
        }

        // UNUSED
        private EventHandler<ActionEvent> pauseDummyDummyGameHandler() {
            return event -> {
                this.getGame().getGameThread().interrupt();
                startGame.setDisable(false);
                stopGame.setDisable(true);
                playButton.setDisable(false);
                stopButton.setDisable(true);
            };
        }
    }
