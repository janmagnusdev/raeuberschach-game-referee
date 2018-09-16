package GameModel.Pieces;

import GameModel.Board;
import GameModel.Field;
import GameModel.Move;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PawnPiece extends Piece {
    boolean wasMoved;
    Field firstField;

    public PawnPiece(boolean isWhite, Field belongingField) {
        super(isWhite, belongingField);
        if (isWhite) {
            asciiRepresentationChar = 'p';
        } else {
            asciiRepresentationChar = 'P';
        }
        firstField = belongingField;
    }

    @Override
    public boolean checkMove(@NotNull Move move, @NotNull Board board) {
        wasMoved = (firstField != this.getBelongingField().getBelongingBoard().getFieldAtIndex(move.getSourceRow(),
                move.getSourceColumn()));
        int allowedDir = this.getIsWhite() ? -1 : 1;
        if (this.wasMoved && !canStrikeEnemy()) {
            if (move.getDestRow() - move.getSourceRow() == allowedDir && move.getDestColumn() ==
                    move.getSourceColumn()) {
                return true;
            }
        } else if (!this.wasMoved && !canStrikeEnemy()) {
            if ((move.getDestRow() - move.getSourceRow() == 2 * allowedDir || move.getDestRow() - move.getSourceRow() == allowedDir) && move.getDestColumn() == move.getSourceColumn() && !checkFieldForEnemy(move.getDestRow(), move.getDestColumn(), !this.getIsWhite())) {
                return true;
            }
        } else {
            if (this.checkFieldForEnemy(move.getSourceRow() + allowedDir, move.getSourceColumn() - 1, !this.getIsWhite())) {
                if (move.getDestRow() == move.getSourceRow() + allowedDir && move.getDestColumn() == move.getSourceColumn() - 1) {
                    return true;
                }
            } else if (this.checkFieldForEnemy(move.getSourceRow() + allowedDir, move.getSourceColumn() + 1, !this.getIsWhite())) {
                if (move.getDestRow() == move.getSourceRow() + allowedDir && move.getDestColumn() == move.getSourceColumn() + 1) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public ArrayList<Field> getPossibleFields() {
        boolean enemyIsWhite = !this.getIsWhite();
        int selfColumn = this.getBelongingField().getColumnDesignation();
        int selfRow = this.getBelongingField().getRowDesignation();
        Board board = this.getBelongingField().getBelongingBoard();
        int dir = this.getIsWhite() ? -1 : 1;
        ArrayList<Field> possibleFields = new ArrayList<>();

        if (selfRow == 7 && dir == 1) {
            dir = 0;
        }
        if (selfRow == 0 && dir == -1) {
            dir = 0;
        }
        wasMoved = (firstField != this.getBelongingField());
        int allowedDir = this.getIsWhite() ? -1 : 1;
        if (!wasMoved && !canStrikeEnemy()) {
            if (board.getFieldAtIndex(this.getBelongingField().getRowDesignation() + dir * 2,
                    this.getBelongingField().getColumnDesignation()).isEmpty() && board.getFieldAtIndex(this.getBelongingField().getRowDesignation() + dir,
                    this.getBelongingField().getColumnDesignation()).isEmpty()) {
                possibleFields.add(board.getFieldAtIndex(this.getBelongingField().getRowDesignation() + dir * 2,
                        this.getBelongingField().getColumnDesignation()));
            }
            if (board.getFieldAtIndex(this.getBelongingField().getRowDesignation() + dir,
                    this.getBelongingField().getColumnDesignation()).isEmpty()) {
                possibleFields.add(board.getFieldAtIndex(this.getBelongingField().getRowDesignation() + dir,
                        this.getBelongingField().getColumnDesignation()));
            }
        } else if (wasMoved && !canStrikeEnemy()) {
            if (board.getFieldAtIndex(this.getBelongingField().getRowDesignation() + dir,
                    this.getBelongingField().getColumnDesignation()).isEmpty()) {
                possibleFields.add(board.getFieldAtIndex(this.getBelongingField().getRowDesignation() + dir,
                        this.getBelongingField().getColumnDesignation()));
            }
        } else if (canStrikeEnemy()) {
            if (this.checkFieldForEnemy(this.getBelongingField().getRowDesignation() + dir,
                    this.getBelongingField().getColumnDesignation() - 1, enemyIsWhite)) {
                possibleFields.add(board.getFieldAtIndex(this.getBelongingField().getRowDesignation() + dir,
                        this.getBelongingField().getColumnDesignation() - 1));
            }
            if (this.checkFieldForEnemy(this.getBelongingField().getRowDesignation() + dir,
                    this.getBelongingField().getColumnDesignation() + 1, enemyIsWhite)) {
                possibleFields.add(board.getFieldAtIndex(this.getBelongingField().getRowDesignation() + dir,
                        this.getBelongingField().getColumnDesignation() + 1));
            }
        }
        return possibleFields;
    }

    @Override
    public boolean canStrikeEnemy() {
        boolean enemyIsWhite = !this.getIsWhite();
        int selfColumn = this.getBelongingField().getColumnDesignation();
        int selfRow = this.getBelongingField().getRowDesignation();
        Board board = this.getBelongingField().getBelongingBoard();
        int dir = this.getIsWhite() ? -1 : 1;

        if (this.checkFieldForEnemy(selfRow + dir, selfColumn - 1, enemyIsWhite) || this.checkFieldForEnemy(selfRow + dir, selfColumn + 1, enemyIsWhite)) {
            return true;
        }
        return false;
    }
}
