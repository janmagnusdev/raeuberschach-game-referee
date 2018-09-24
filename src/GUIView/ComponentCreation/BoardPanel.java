package GUIView.ComponentCreation;

import GameModel.Move;
import GUIView.ActivePiece;
import Loaders.ImageLoader;
import GameModel.Board;
import GameModel.Field;
import GameModel.Game;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public class BoardPanel extends Region {
    ActivePiece activePiece;
    private Game game;
    private Canvas canvas;
    ActivePiece animatedPiece;

    public final int BOARD_SIZE;
    public final int CELL_SIZE;
    public final int CELL_MIDDLE;
    private final String COLOR_WHITEFIELD = "#f5f5f5";
    private final String COLOR_BLACKFIELD = "#455a64";
    private int offset;

    private Board board;
    private Field[][] fields;

    public BoardPanel(Game game, int cellSize, int offset) {
        board = game.getBoard();
        fields = board.getFields();
        BOARD_SIZE = fields.length * cellSize;
        CELL_SIZE = BOARD_SIZE / fields.length;
        CELL_MIDDLE = cellSize / 2 - 1;
        this.game = game;
        this.canvas = new Canvas(cellSize * 9, cellSize * 9);
        this.setPrefSize(cellSize * 9, cellSize * 9);
        this.getChildren().add(canvas);
        this.offset = offset;
    }

    public void paintState(Move move) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);

        for (int i = 0; i < fields.length; i++) {
            gc.strokeText(((char) (i + 'a')) + "", i * CELL_SIZE + CELL_SIZE + CELL_MIDDLE + offset - 5, CELL_MIDDLE);
            gc.strokeText(i + "", CELL_MIDDLE, i * CELL_SIZE + CELL_SIZE + CELL_MIDDLE + offset + 5);
        }
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields[0].length; j++) {
                if (Math.abs(j - i) % 2 == 0) {
                    gc.setFill(Color.web(COLOR_WHITEFIELD));
                } else {
                    gc.setFill(Color.web(COLOR_BLACKFIELD));
                }
                gc.fillRect(j * CELL_SIZE + CELL_SIZE + offset, i * CELL_SIZE + CELL_SIZE + offset, CELL_SIZE, CELL_SIZE);
                gc.strokeRect(j * CELL_SIZE + CELL_SIZE + offset, i * CELL_SIZE + CELL_SIZE + offset, CELL_SIZE, CELL_SIZE);

                /*int val = 0; //Stackoverflow Solution: https://stackoverflow.com/questions/8850497/switch-case-request-with-boolean
                if (activePiece != null) val |= 0x1;
                if (animatedPiece != null) val |= 0x2;

                switch (val) {
                    case 0: // Both null
                        if (!fields[i][j].isEmpty()) {
                            gc.drawImage(ImageLoader.getInstance().loadPieceImage(fields[i][j].getContentPiece()), ((j + 1) * CELL_SIZE + 4), ((i + 1) * CELL_SIZE + 3), CELL_SIZE * 0.85, CELL_SIZE * 0.85);
                        }
                        break;
                    case 1: // activePiece not null
                        if (!fields[i][j].isEmpty() && !(i == activePiece.getSrcField().getRowDesignation() && j == activePiece.getSrcField().getColumnDesignation())) {
                            gc.drawImage(ImageLoader.getInstance().loadPieceImage(fields[i][j].getContentPiece()), ((j + 1) * CELL_SIZE + 4), ((i + 1) * CELL_SIZE + 3), CELL_SIZE * 0.85, CELL_SIZE * 0.85);
                        }
                        break;
                    case 2: // animatedPiece not null
                        if (!fields[i][j].isEmpty() && !(i == animatedPiece.getSrcField().getRowDesignation() && j == animatedPiece.getSrcField().getColumnDesignation())) {
                            gc.drawImage(ImageLoader.getInstance().loadPieceImage(fields[i][j].getContentPiece()), ((j + 1) * CELL_SIZE + 4), ((i + 1) * CELL_SIZE + 3), CELL_SIZE * 0.85, CELL_SIZE * 0.85);
                        }
                        break;
                    case 3: // both not null
                        if (!fields[i][j].isEmpty() && !(i == animatedPiece.getSrcField().getRowDesignation() && j == animatedPiece.getSrcField().getColumnDesignation()) && !(i == activePiece.getSrcField().getRowDesignation() && j == activePiece.getSrcField().getColumnDesignation())) {
                            gc.drawImage(ImageLoader.getInstance().loadPieceImage(fields[i][j].getContentPiece()), ((j + 1) * CELL_SIZE + 4), ((i + 1) * CELL_SIZE + 3), CELL_SIZE * 0.85, CELL_SIZE * 0.85);
                        }
                        break;
                }*/

                if (activePiece == null && animatedPiece == null) {
                    if (!fields[i][j].isEmpty()) {
                        gc.drawImage(ImageLoader.getInstance().loadPieceImage(fields[i][j].getContentPiece()), ((j + 1) * CELL_SIZE + 4), ((i + 1) * CELL_SIZE + 3), CELL_SIZE * 0.85, CELL_SIZE * 0.85);
                    }
                } else if (activePiece != null && animatedPiece == null){
                    if (!fields[i][j].isEmpty() && !(i == activePiece.getSrcField().getRowDesignation() && j == activePiece.getSrcField().getColumnDesignation())) {
                        gc.drawImage(ImageLoader.getInstance().loadPieceImage(fields[i][j].getContentPiece()), ((j + 1) * CELL_SIZE + 4), ((i + 1) * CELL_SIZE + 3), CELL_SIZE * 0.85, CELL_SIZE * 0.85);
                    }
                } else if (activePiece == null && animatedPiece != null) {
                    if (!fields[i][j].isEmpty() && !(i == animatedPiece.getSrcField().getRowDesignation() && j == animatedPiece.getSrcField().getColumnDesignation())) {
                        gc.drawImage(ImageLoader.getInstance().loadPieceImage(fields[i][j].getContentPiece()), ((j + 1) * CELL_SIZE + 4), ((i + 1) * CELL_SIZE + 3), CELL_SIZE * 0.85, CELL_SIZE * 0.85);
                    }
                } else {
                    if (!fields[i][j].isEmpty() && !(i == animatedPiece.getSrcField().getRowDesignation() && j == animatedPiece.getSrcField().getColumnDesignation()) && !(i == activePiece.getSrcField().getRowDesignation() && j == activePiece.getSrcField().getColumnDesignation())) {
                        gc.drawImage(ImageLoader.getInstance().loadPieceImage(fields[i][j].getContentPiece()), ((j + 1) * CELL_SIZE + 4), ((i + 1) * CELL_SIZE + 3), CELL_SIZE * 0.85, CELL_SIZE * 0.85);
                    }

                }
            }
        }
        if (activePiece != null) {
            gc.drawImage(ImageLoader.getInstance().loadPieceImage(fields[activePiece.getSrcField().getRowDesignation()][activePiece.getSrcField().getColumnDesignation()].getContentPiece()), activePiece.getX() - CELL_SIZE * 0.42, activePiece.getY() - CELL_SIZE * 0.42, CELL_SIZE * 0.85, CELL_SIZE * 0.85); //draws the pieceImage of the active piece where the mouse is
            gc.setStroke(Color.web("#00897b"));
            gc.setLineWidth(2.50);
            gc.strokeRect((activePiece.getSrcField().getColumnDesignation() + 1) * CELL_SIZE + 3,
                    (activePiece.getSrcField().getRowDesignation() + 1) * CELL_SIZE + 3, CELL_SIZE - 6,
                    CELL_SIZE - 6); //highlighting of source field of active piece, if there is one
            gc.setStroke(Color.web("#43a047"));
            for (Field field : activePiece.getSrcField().getContentPiece().getPossibleFields()) {
                gc.strokeRect((field.getColumnDesignation() + 1) * CELL_SIZE + 3,
                        (field.getRowDesignation() + 1) * CELL_SIZE + 3, CELL_SIZE - 6,
                        CELL_SIZE - 6);
            }
            gc.setStroke(Color.web("#ff3d00")); //Noch falsches Feld rot markieren, falls dem so ist TODO
            gc.setLineWidth(1.00);
        }
        if (animatedPiece != null) {
            gc.drawImage(ImageLoader.getInstance().loadPieceImage(board.getFieldAtIndex(animatedPiece.getSrcField().getRowDesignation(), animatedPiece.getSrcField().getColumnDesignation()).getContentPiece()), animatedPiece.getX() + 4, animatedPiece.getY() + 3, CELL_SIZE * 0.85, CELL_SIZE * 0.85);
        }
        /*if (move != null) {
            this.paintMoveAnimation(move.getSourceColumn(), move.getSourceRow(), move.getDestColumn(), move.getDestColumn());
        }*/
    }

    //Getter and Setter
    public Canvas getCanvas() {
        return canvas;
    }

    public ActivePiece getActivePiece() {
        return activePiece;
    }

    public void setActivePiece(ActivePiece activePiece) {
        this.activePiece = activePiece;
    }

    public void update(Move move) {
        canvas.getGraphicsContext2D().setStroke(Color.WHITE);
        canvas.getGraphicsContext2D().clearRect(0, 0, CELL_SIZE * (fields.length + 1),
                CELL_SIZE * (fields[0].length + 1));
        this.paintState(move);
    }

    public Field[][] getFields() {
        return fields;
    }

    public Game getGame() {
        return game;
    }

    public void setAnimatedPiece(ActivePiece animatedPiece) {
        this.animatedPiece = animatedPiece;
    }

    public ActivePiece getAnimatedPiece() {
        return animatedPiece;
    }
}