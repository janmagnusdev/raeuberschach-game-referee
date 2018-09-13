package Controller;

import assets.IO;
import GUIView.ActivePiece;
import GUIView.ComponentCreation.BoardPanel;
import GUIView.GameGUI;
import GameModel.Game;
import GameModel.Move;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class BoardEventHandler implements EventHandler<MouseEvent> {
    private Game game; //durch Observable ersetzbar, sodass weniger Speicher verbraucht wird
    private GameGUI gameGUI;

    private BoardPanel boardPanel;

    public BoardEventHandler(Game game, GameGUI gameGUI) {
        this.game = game;
        this.gameGUI = gameGUI;
        boardPanel = gameGUI.getBoardPanel();
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
            actionOnMousePressed(event);
        } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            actionOnMouseDragged(event);
        } else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
            actionOnMouseReleased(event);
        }
    }

    public void actionOnMousePressed(MouseEvent event) {
        IO.println(event.getX() + " getX");
        IO.println(event.getY() + " getY");
        IO.println(calcIndex(event.getX()));
        IO.println(calcIndex(event.getY()));
        if (mouseIsInBoardRange(event)) {
            if (game.getBoard().getFieldAtIndex(calcIndex(event.getY()), calcIndex(event.getX())).getContentPiece() != null) {
                ActivePiece activePiece = new ActivePiece(event.getX(), event.getY());
                activePiece.setSrcField(game.getBoard().getFieldAtIndex(calcIndex(event.getY()),
                        calcIndex(event.getX())));
                boardPanel.setActivePiece(activePiece);
            } else  {
                boardPanel.setActivePiece(null);
            }
        } else {
            boardPanel.setActivePiece(null);
        }
        boardPanel.update();
    }

    public void actionOnMouseDragged(MouseEvent event) {
        if (mouseIsInBoardRange(event) && boardPanel.getActivePiece() != null) {
            boardPanel.getActivePiece().setX(event.getX());
            boardPanel.getActivePiece().setY(event.getY());
            IO.println(boardPanel.getActivePiece().getX() + " Dragged Piece X value");
            IO.println(boardPanel.getActivePiece().getY() + " Dragged Piece Y value");
        }
        boardPanel.update();
    }

    public void actionOnMouseReleased(MouseEvent event) {
        if (mouseIsInBoardRange(event) && boardPanel.getActivePiece() != null) {
            Move move = new Move(boardPanel.getActivePiece().getSrcField().getColumnDesignation() + 'a',
                    boardPanel.getActivePiece().getSrcField().getRowDesignation(),
                    calcIndex(boardPanel.getActivePiece().getX()) + 'a',
                    calcIndex(boardPanel.getActivePiece().getY()));
            boardPanel.setActivePiece(null);
            IO.print(move.getSourceColumn() + "\n" + move.getSourceRow() + "\n" + move.getDestColumn() + "\n" + move.getDestRow() + "\n");
            IO.println("Move erstellt");
            if (game.getReferee().checkMove(move, game.getCurrentPlayer())) {
                IO.println("Move checked");
                if ((game.getCurrentPlayer().canStrikeEnemy(game.getBoard()) && game.getBoard().getFieldAtIndex(
                        move.getDestRow(), move.getDestColumn()).getContentPiece() == null)) {
                        gameGUI.setLabelText((game.getCurrentPlayer().isWhite() ? "Weiß ist am Zug!" :
                            "Schwarz ist am Zug!") + " Du kannst eine gegnerische Figur schlagen!");
                } else {
                    game.getReferee().doMove(move);
                    game.setCurrentPlayer(game.getCurrentPlayer().isWhite() ? game.getBlack() : game.getWhite());
                    gameGUI.setLabelText(game.getCurrentPlayer().isWhite() ? "Weiß ist am Zug!" : "Schwarz ist am Zug!");
                    IO.println("Du kannst eine gegnerische Figur schlagen!");
                }
            } else {
                IO.println("Log: Move ist nicht gültig");
                gameGUI.setLabelText((game.getCurrentPlayer().isWhite() ? "Weiß ist am Zug!" :
                                "Schwarz ist am Zug!") + " Der Zug ist so nicht gültig!");
            }
        }
        if (game.checkEndingByPieces(game.getBoard().getFields())) {
            game.getBoard().setPiecesInitial();
        }
        boardPanel.update();
    }

    private boolean mouseIsInBoardRange(MouseEvent event) {
        return (event.getX() <= boardPanel.CELL_SIZE * 9 && event.getX() >= boardPanel.CELL_SIZE && event.getY() <= boardPanel.CELL_SIZE * 9 && event.getY() >= boardPanel.CELL_SIZE);
    }

    /**
     * Berechnet aus einer übergebenen Koordinate einen gültigen Array-Index.
     * @param x Die übergebene Koordinate, ganz egal ob x oder y.
     * @return Der berechnete Index.
     */
    public int calcIndex(Double x) {
        return ((int) ((x / boardPanel.CELL_SIZE) - 1));
    }
}
