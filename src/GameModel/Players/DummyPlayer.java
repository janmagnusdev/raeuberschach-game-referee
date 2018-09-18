package GameModel.Players;

import GameModel.Board;
import GameModel.Field;
import GameModel.Move;

import java.util.ArrayList;

public class DummyPlayer extends Player {
    private Board board;

    public DummyPlayer(boolean isWhite, Board board) {
        super(isWhite);
        this.isAI = true;
        this.board = board;
    }

    @Override
    public Move getNextMove(Move oldMove) {
        return possibleRandomMove(board);
    }

    private Move possibleRandomMove(Board board) { //TODO
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