package GUIView;

import GUIView.ComponentCreation.BoardPanel;
import GameModel.Game;
import GameModel.Move;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;

public class AnimationThread extends Thread {

    BoardPanel boardPanel;
    Game game;
    Move move;
    int animations;

    public AnimationThread(BoardPanel boardPanel, Game game, Move move, int animations) {
        this.boardPanel = boardPanel;
        this.game = game;
        this.move = move;
        this.animations = animations;
    }

    /**
     * Berechnet die Koordinaten des AnimatedPiece, und führt die update Methode des Boardpanels aus, bis die
     * Koordinaten des AnimatedPiece denen des Ziels des Move entsprechen. In der update Methode wird dann das
     * AnimatedPiece immer dort gezeichnet, wo dessen Koordinaten sind.
     */
    @Override
    public void run() {
        if (move != null) {
            double y = (move.getSourceRow() + 1) * boardPanel.CELL_SIZE;
            double x = (move.getSourceColumn() + 1) * boardPanel.CELL_SIZE;
            double yDir = move.getSourceRow() < move.getDestRow() ? 1 : -1;
            double xDir = move.getSourceColumn() < move.getDestColumn() ? 1 : -1;
            double incrementFactorY = Math.abs(y - (((double) move.getDestRow() + 1) * boardPanel.CELL_SIZE)) / animations;
            double incrementFactorX =
                    Math.abs(x - (((double) move.getDestColumn() + 1) * boardPanel.CELL_SIZE)) / animations;
            ActivePiece animatedPiece = new ActivePiece((move.getSourceRow() + 1) * boardPanel.CELL_SIZE,
                    (move.getSourceColumn() + 1) * boardPanel.CELL_SIZE);
            animatedPiece.setSrcField(boardPanel.getGame().getBoard().getFieldAtIndex(move.getSourceRow(), move.getSourceColumn()));
            boardPanel.setAnimatedPiece(animatedPiece);


            for (int z = 0; z < animations; z++) {
                boardPanel.update(null);
                boardPanel.getAnimatedPiece().setY(boardPanel.getAnimatedPiece().getY() + incrementFactorY * yDir);
                boardPanel.getAnimatedPiece().setX(boardPanel.getAnimatedPiece().getX() + incrementFactorX * xDir);
            }
        }
    }
}
