package GameModel.Players;

import GameModel.Board;
import GameModel.Field;
import GameModel.Move;

import java.util.ArrayList;

public class DummyPlayer extends GameModel.Players.Player {

    private Board board;

    //final String FULL_CLASSNAME = "GameModel.Players.DummyPlayer";

    //TODO Board soll nicht übergeben werden, da der Spieler seine Datenstruktur evtl. ganz anders aufbaut für bspw.
    // bessere Spielbäume
    public DummyPlayer(boolean isWhite) {
        super(isWhite);
        this.isAI = true;
        this.board = new Board();
    }

    @Override
    public Move getNextMove(Move oldMove) {
        if (oldMove != null) doMove(oldMove);
        Move newMove = possibleRandomMove();
        if (newMove != null) doMove(newMove);
        return newMove;
    }

    @SuppressWarnings("Duplicates")
    private void doMove(Move oldMove) {
        if (board.getFieldAtIndex(oldMove.getDestRow(), oldMove.getDestColumn()).getContentPiece() != null) {
            board.getFieldAtIndex(oldMove.getDestRow(), oldMove.getDestColumn()).getContentPiece().setBelongingField(null);
            board.getFieldAtIndex(oldMove.getDestRow(), oldMove.getDestColumn()).setContentPiece(null);
        }
        board.getFieldAtIndex(oldMove.getDestRow(), oldMove.getDestColumn()).setContentPiece(board.getFieldAtIndex(oldMove.getSourceRow(), oldMove.getSourceColumn()).getContentPiece());
        board.getFieldAtIndex(oldMove.getDestRow(), oldMove.getDestColumn()).getContentPiece().setBelongingField(board.getFieldAtIndex(oldMove.getDestRow(), oldMove.getDestColumn()));
        board.getFieldAtIndex(oldMove.getSourceRow(), oldMove.getSourceColumn()).setContentPiece(null);
    }

    private Move possibleRandomMove() {
        ArrayList<Move> possibleMovesAllBelongingPieces = new ArrayList<>();
        if (this.canStrikeEnemy(board)) {
            for (int i = 0; i < board.getFields().length; i++) {
                for (int j = 0; j < board.getFieldAtIndex(0).length; j++) {
                    if(!board.getFieldAtIndex(i, j).isEmpty()) {
                        if (board.getFieldAtIndex(i, j).getContentPiece().getIsWhite() == this.isWhite() && board.getFieldAtIndex(i, j).getContentPiece().canStrikeEnemy()) {
                            for (Field field : board.getFieldAtIndex(i, j).getContentPiece().getPossibleFields()
                            ) {
                                possibleMovesAllBelongingPieces.add(new Move(j + 'a', i, field.getColumnDesignation() + 'a',
                                        field.getRowDesignation()));
                            }
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < board.getFields().length; i++) {
                for (int j = 0; j < board.getFieldAtIndex(0).length; j++) {
                    if (!board.getFieldAtIndex(i, j).isEmpty()) {
                        if (board.getFieldAtIndex(i, j).getContentPiece().getIsWhite() == this.isWhite()) {
                            for (Field field : board.getFieldAtIndex(i, j).getContentPiece().getPossibleFields()
                            ) {
                                possibleMovesAllBelongingPieces.add(new Move(j + 'a', i, field.getColumnDesignation() + 'a',
                                        field.getRowDesignation()));
                            }
                        }
                    }
                }
            }
        }
        return possibleMovesAllBelongingPieces.get((int) (Math.random() * possibleMovesAllBelongingPieces.size()));
    }
}