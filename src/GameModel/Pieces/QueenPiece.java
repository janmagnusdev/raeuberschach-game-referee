package GameModel.Pieces;

import GameModel.Board;
import GameModel.Field;
import GameModel.Move;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class QueenPiece extends Piece {
    public QueenPiece(boolean isWhite, Field belongingField) {
        super(isWhite, belongingField);
        if (isWhite) {
            asciiRepresentationChar = 'q';
        } else {
            asciiRepresentationChar = 'Q';
        }
    }

    @Override
    public boolean checkMove(Move move, Board board) {
        //Queen can move in all directions, any number of fields

        if (move.getDestColumn() == move.getSourceColumn()) {
            //vertical movement
            return !isPieceInWayVertical(move);
        } else if (move.getDestRow() == move.getSourceRow()) {
            //horizontal movement
            return !isPieceInWayHorizontal(move);
        } else if (Math.abs(move.getSourceRow() - move.getDestRow()) == Math.abs(move.getSourceColumn() - move.getDestColumn())) {
            //diagonal movement
            return !isPieceInWayDiagonal(move);
        } else {
            return false;
        }
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
        //right up
        this.addPossibleFieldsDirectionToArray(possibleFields, -1, +1);
        //right down
        this.addPossibleFieldsDirectionToArray(possibleFields,  +1, +1);
        //left down
        this.addPossibleFieldsDirectionToArray(possibleFields, +1, -1);
        //left up
        this.addPossibleFieldsDirectionToArray(possibleFields, -1, -1);

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

        //right up
        {
            int j = selfColumn + 1;
            for (int i = selfRow - 1; i >= 0 && j < 8; i--, j++) {
                if (!this.getBelongingField().getBelongingBoard().getFieldAtIndex(i, j).isEmpty())
                    if (checkFieldForEnemy(i, j, enemyIsWhite)) {
                        return true;
                    } else {
                        break;
                    }
            }
        }

        //right down
        {
            int j = selfColumn + 1;
            for (int i = selfRow + 1; i < 8 && j < 8; i++, j++) {
                if (!this.getBelongingField().getBelongingBoard().getFieldAtIndex(i, j).isEmpty())
                    if (checkFieldForEnemy(i, j, enemyIsWhite)) {
                        return true;
                    } else {
                        break;
                    }
            }
        }

        //left down
        {
            int j = selfColumn - 1;
            for (int i = selfRow + 1; i < 8 && j >= 0; i++, j--) {
                if (!this.getBelongingField().getBelongingBoard().getFieldAtIndex(i, j).isEmpty())
                    if (checkFieldForEnemy(i, j, enemyIsWhite)) {
                        return true;
                    } else {
                        break;
                    }
            }
        }

        //left up
        {
            int j = selfColumn - 1;
            for (int i = selfRow - 1; i >= 0 && j >= 0; i--, j--) {
                if (!this.getBelongingField().getBelongingBoard().getFieldAtIndex(i, j).isEmpty())
                    if (checkFieldForEnemy(i, j, enemyIsWhite)) {
                        return true;
                    } else {
                        break;
                    }
            }
        }
        return false;
    }
}

