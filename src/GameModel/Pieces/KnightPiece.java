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

        //Im Uhrzeigersinn, beginnend bei 12 Uhr, mit dem Moveset des Knights
        this.addPossibleFieldsToArrayList(possibleFields, selfRow - 2, selfColumn + 1);
        this.addPossibleFieldsToArrayList(possibleFields, selfRow - 1, selfColumn + 2);
        this.addPossibleFieldsToArrayList(possibleFields, selfRow + 1, selfColumn + 2);
        this.addPossibleFieldsToArrayList(possibleFields, selfRow + 2, selfColumn + 1);
        this.addPossibleFieldsToArrayList(possibleFields, selfRow + 2, selfColumn - 1);
        this.addPossibleFieldsToArrayList(possibleFields, selfRow + 1, selfColumn - 2);
        this.addPossibleFieldsToArrayList(possibleFields, selfRow - 1, selfColumn - 2);
        this.addPossibleFieldsToArrayList(possibleFields, selfRow - 2, selfColumn - 1);

        return possibleFields;
    }

    private void addPossibleFieldsToArrayList(ArrayList<Field> possibleFields, int i, int j) {
        if (i >= 0 && i <= 7 && j >= 0 && j <= 7) {
            if (!this.canStrikeEnemy()) {
                if (this.getBelongingField().getBelongingBoard().getFieldAtIndex(i, j).isEmpty()) {
                    possibleFields.add(this.getBelongingField().getBelongingBoard().getFieldAtIndex(i, j));
                }
            } else {
                if (this.checkFieldForEnemy(i, j, !this.getIsWhite())) {
                    possibleFields.add(this.getBelongingField().getBelongingBoard().getFieldAtIndex(i, j));
                }
            }
        }
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
