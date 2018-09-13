package GameModel;

import java.util.ArrayList;
import java.util.Collection;

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

    private Move possibleRandomMove(Board board) { //am besten Methode für jedes Piece, welche alle möglichen Felder
        // gibt, zu denen das Piece moven kann; bspw. auch in Bezug auf Spielbäume TODO
        ArrayList<Move> possibleMovesAllBelongingPieces = new ArrayList<>();
        for (int i = 0; i < board.getFields().length; i++) {
            for (int j = 0; j < board.getFieldAtIndex(0).length; j++) {
                if (board.getFieldAtIndex(i, j).getContentPiece().getIsWhite() == this.isWhite()) {
                    for (Field field : board.getFieldAtIndex(i, j).getContentPiece().getPossibleFields()
                    ) {
                        possibleMovesAllBelongingPieces.add(new Move(i + 'a', j, field.getColumnDesignation() + 'a',
                                field.getRowDesignation()));
                    }
                }
            }
        }
        return possibleMovesAllBelongingPieces.get((int) (Math.random() * possibleMovesAllBelongingPieces.size()));
    }
}