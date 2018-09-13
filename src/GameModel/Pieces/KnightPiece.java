package GameModel.Pieces;

import GameModel.Board;
import GameModel.Field;
import GameModel.Move;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class KnightPiece extends Piece {
    public KnightPiece(boolean isWhite, Field belongingField) {
        super(isWhite, belongingField);
        if (isWhite) {
            asciiRepresentationChar = 'n';
        } else {
            asciiRepresentationChar = 'N';
        }
    }

    @Override
    public boolean checkMove(@NotNull Move move, @NotNull Board board) {
        return ((Math.abs(move.getSourceRow() - move.getDestRow()) == 2 && Math
                .abs(move.getSourceColumn() - move.getDestColumn()) == 1) || ((Math
                .abs(move.getSourceRow() - move.getDestRow()) == 1 && Math.abs(move
                .getSourceColumn() - move.getDestColumn()) == 2))); // Math.abs bei der letzten Berechnung vergessen
    }

    @Override
    public ArrayList<Field> getPossibleFields() {
        boolean enemyIsWhite = !this.getIsWhite();
        int selfColumn = this.getBelongingField().getColumnDesignation();
        int selfRow = this.getBelongingField().getRowDesignation();
        Board board = this.getBelongingField().getBelongingBoard();
        ArrayList<Field> possibleFields = new ArrayList<>();

        //checkt im Uhrzeigersinn des Springers nach möglichen Zügen, startend bei 12 Uhr
        if (this.checkFieldForEnemy(selfRow - 2, selfColumn + 1, enemyIsWhite) || board.getFieldAtIndex(selfRow - 2,
                selfColumn + 1).isEmpty()) {
            possibleFields.add(board.getFieldAtIndex(selfRow - 2, selfColumn + 1));
        }
        if (this.checkFieldForEnemy(selfRow - 1, selfColumn + 2, enemyIsWhite) || board.getFieldAtIndex(selfRow - 2,
                selfColumn + 1).isEmpty()) {
            possibleFields.add(board.getFieldAtIndex(selfRow - 1, selfColumn + 2));
        }
        if (this.checkFieldForEnemy(selfRow + 1, selfColumn + 2, enemyIsWhite) || board.getFieldAtIndex(selfRow - 2,
                selfColumn + 1).isEmpty()) {
            possibleFields.add(board.getFieldAtIndex(selfRow + 1, selfColumn + 2));
        }
        if (this.checkFieldForEnemy(selfRow + 2, selfColumn + 1, enemyIsWhite) || board.getFieldAtIndex(selfRow - 2,
                selfColumn + 1).isEmpty()) {
            possibleFields.add(board.getFieldAtIndex(selfRow + 2, selfColumn + 1));
        }
        if (this.checkFieldForEnemy(selfRow + 2, selfColumn - 1, enemyIsWhite) || board.getFieldAtIndex(selfRow - 2,
                selfColumn + 1).isEmpty()) {
            possibleFields.add(board.getFieldAtIndex(selfRow - 2, selfColumn - 1));
        }
        if (this.checkFieldForEnemy(selfRow + 1, selfColumn - 2, enemyIsWhite) || board.getFieldAtIndex(selfRow - 2,
                selfColumn + 1).isEmpty()) {
            possibleFields.add(board.getFieldAtIndex(selfRow + 1, selfColumn - 2));
        }
        if (this.checkFieldForEnemy(selfRow - 1, selfColumn - 2, enemyIsWhite) || board.getFieldAtIndex(selfRow - 2,
                selfColumn + 1).isEmpty()) {
            possibleFields.add(board.getFieldAtIndex(selfRow - 1, selfColumn - 2));
        }
        if (this.checkFieldForEnemy(selfRow - 2, selfColumn - 1, enemyIsWhite) || board.getFieldAtIndex(selfRow - 2,
                selfColumn + 1).isEmpty()) {
            possibleFields.add(board.getFieldAtIndex(selfRow - 2, selfColumn + 1));
        }
        return possibleFields;
    }

    @Override
    public boolean canStrikeEnemy() {
        boolean enemyIsWhite = !this.getIsWhite();
        int selfColumn = this.getBelongingField().getColumnDesignation();
        int selfRow = this.getBelongingField().getRowDesignation();
        Board board = this.getBelongingField().getBelongingBoard();

        //checkt im Uhrzeigersinn des Springers nach gegnern, startend bei 12 Uhr
        if (this.checkFieldForEnemy(selfRow - 2, selfColumn + 1, enemyIsWhite)) {
            return true;
        } else if (this.checkFieldForEnemy(selfRow - 1, selfColumn + 2, enemyIsWhite)) {
            return true;
        } else if (this.checkFieldForEnemy(selfRow + 1, selfColumn + 2, enemyIsWhite)) {
            return true;
        } else if (this.checkFieldForEnemy(selfRow + 2, selfColumn + 1, enemyIsWhite)) {
            return true;
        } else if (this.checkFieldForEnemy(selfRow + 2, selfColumn - 1, enemyIsWhite)) {
            return true;
        } else if (this.checkFieldForEnemy(selfRow + 1, selfColumn - 2, enemyIsWhite)) {
            return true;
        } else if (this.checkFieldForEnemy(selfRow - 1, selfColumn - 2, enemyIsWhite)) {
            return true;
        } else if (this.checkFieldForEnemy(selfRow - 2, selfColumn - 1, enemyIsWhite)) {
            return true;
        } else {
            return false;
        }
    }
}
