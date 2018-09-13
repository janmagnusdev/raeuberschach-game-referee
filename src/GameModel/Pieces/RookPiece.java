package GameModel.Pieces;

import GameModel.Board;
import GameModel.Field;
import GameModel.Move;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RookPiece extends Piece {
    public RookPiece(boolean isWhite, Field belongingField) {
        super(isWhite, belongingField);
        if (isWhite) {
            asciiRepresentationChar = 't';
        } else {
            asciiRepresentationChar = 'T';
        }
    }

    @Override
    public boolean checkMove(@NotNull Move move, @NotNull Board board) {
        boolean pieceIsInTheWay = false;
        boolean moveAllowed = false;
        Field[][] fields = board.getFields();
                if (move.isInBoardRange()) {
                    if (move.getSourceColumn() == move.getDestColumn() ^ move.getSourceRow() == move.getDestRow()) {//checkt, ob der Move nur horizontal oder nur vertikal ist. ^ sorgt dafür, dass Diagonalität ausgeschlossen ist.
                        moveAllowed = true;
                        if (move.getSourceColumn() == move.getDestColumn()) {
                            pieceIsInTheWay = isPieceInWayVertical(move);
                        } else {
                            pieceIsInTheWay = isPieceInWayHorizontal(move);
                        }
                    }
                } else {
                    return false;
                }
        return !pieceIsInTheWay && moveAllowed;
    }

    @Override
    public ArrayList<Field> getPossibleFields() {
        boolean enemyIsWhite = !this.getIsWhite();
        int selfColumn = this.getBelongingField().getColumnDesignation();
        int selfRow = this.getBelongingField().getRowDesignation();
        Board board = this.getBelongingField().getBelongingBoard();

        ArrayList<Field> possibleFields = new ArrayList<>();

        //down
        this.addPossibleFieldsDirectionToArray(possibleFields, 1, 0);
        //up
        this.addPossibleFieldsDirectionToArray(possibleFields,  -1, 0);
        //right
        this.addPossibleFieldsDirectionToArray(possibleFields, 0, 1);
        //left
        this.addPossibleFieldsDirectionToArray(possibleFields, 0, -1);

        return possibleFields;
    }

    @Override
    public boolean canStrikeEnemy() {
        boolean enemyIsWhite = !this.getIsWhite();
        int selfColumn = this.getBelongingField().getColumnDesignation();
        int selfRow = this.getBelongingField().getRowDesignation();
        Board board = this.getBelongingField().getBelongingBoard();

        //down
        for (int i = selfRow + 1; i < 8; i++) {
            if (!this.getBelongingField().getBelongingBoard().getFieldAtIndex(i, selfColumn).isEmpty()) {
                if (checkFieldForEnemy(i, selfColumn, enemyIsWhite)) {
                    return true;
                } else {
                    break;
                }
            }
        }

        //up
        for (int i = selfRow - 1; i >= 0; i--) {
            if (!this.getBelongingField().getBelongingBoard().getFieldAtIndex(i, selfColumn).isEmpty()) {
                if (checkFieldForEnemy(i, selfColumn, enemyIsWhite)) {
                    return true;
                } else {
                    break;
                }
            }
        }

        //right
        for (int i = selfColumn + 1; i < 8; i++) {
            if (!this.getBelongingField().getBelongingBoard().getFieldAtIndex(selfRow, i).isEmpty()) {
                if (checkFieldForEnemy(selfRow, i, enemyIsWhite)) {
                    return true;
                } else {
                    break;
                }
            }
        }

        //left
        for (int i = selfColumn - 1; i >= 0; i--) {
            if (!this.getBelongingField().getBelongingBoard().getFieldAtIndex(selfRow, i).isEmpty()) {
                if (checkFieldForEnemy(selfRow, i, enemyIsWhite)) {
                    return true;
                } else {
                    break;
                }
            }
        }
        return false;
    }
}
