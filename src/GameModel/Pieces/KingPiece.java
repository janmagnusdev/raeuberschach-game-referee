package GameModel.Pieces;

import GameModel.Board;
import GameModel.Field;
import GameModel.Move;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class KingPiece extends Piece {
    public KingPiece(boolean isWhite, Field belongingField) {
        super(isWhite, belongingField);
        if (isWhite) {
            asciiRepresentationChar = 'k';
        } else {
            asciiRepresentationChar = 'K';
        }
    }

    @Override
    public boolean checkMove(@NotNull Move move, @NotNull Board board) {
        //King can only move one field in each direction
        if (move.getDestColumn() == move.getSourceColumn() && Math.abs(move.getDestRow() - move.getSourceRow()) == 1) {
            //vertical movement
            return !isPieceInWayVertical(move);
        } else if (move.getDestRow() == move.getSourceRow() && Math.abs(move.getDestColumn() - move.getSourceColumn()) == 1) {
            //horizontal movement
            return !isPieceInWayHorizontal(move);
        } else if (Math.abs(move.getSourceRow() - move.getDestRow()) == Math.abs(move.getSourceColumn() - move.getDestColumn()) && Math.abs(move.getDestColumn() - move.getSourceColumn()) == 1) {
            //diagonal movement
            return !isPieceInWayDiagonal(move);
        } else {
            return false;
        }
    }

    @Override
    public ArrayList<Field> getPossibleFields() { //TODO
        int selfRow = this.getBelongingField().getRowDesignation();
        int selfColumn = this.getBelongingField().getColumnDesignation();
        ArrayList<Field> possibleFields = new ArrayList<>();

        //Up
        this.addPossibleFieldToArrayList(possibleFields, selfRow - 1, selfColumn);
        //Up Right
        this.addPossibleFieldToArrayList(possibleFields, selfRow - 1, selfColumn + 1);
        //Right
        this.addPossibleFieldToArrayList(possibleFields, selfRow, selfColumn + 1);
        //Right Down
        this.addPossibleFieldToArrayList(possibleFields, selfRow + 1, selfColumn + 1);
        //Down
        this.addPossibleFieldToArrayList(possibleFields, selfRow + 1, selfColumn);
        //Left Down
        this.addPossibleFieldToArrayList(possibleFields, selfRow + 1, selfColumn - 1);
        //Left
        this.addPossibleFieldToArrayList(possibleFields, selfRow, selfColumn - 1);
        //Left Up
        this.addPossibleFieldToArrayList(possibleFields, selfRow - 1, selfColumn - 1);

        return possibleFields;
    }

    @Override
    public boolean canStrikeEnemy() { //evtl case switch
        boolean enemyIsWhite = !this.getIsWhite();
        int selfRow = this.getBelongingField().getRowDesignation();
        int selfColumn = this.getBelongingField().getColumnDesignation();
        Board board = this.getBelongingField().getBelongingBoard();

        //Down
        if (checkFieldForEnemy(selfRow + 1, selfColumn, enemyIsWhite)) {
            return true;
        }
        //Up
        else if (checkFieldForEnemy(selfRow - 1, selfColumn, enemyIsWhite)) {
            return true;
        }
        //Right
        else if (checkFieldForEnemy(selfRow, selfColumn + 1, enemyIsWhite)) {
            return true;
        }
        //Left
        else if (checkFieldForEnemy(selfRow, selfColumn - 1, enemyIsWhite)) {
            return true;
        }
        //Right Up
        else if (checkFieldForEnemy(selfRow - 1, selfColumn + 1, enemyIsWhite)) {
            return true;
        }
        //Right Down
        else if (checkFieldForEnemy(selfRow + 1, selfColumn + 1, enemyIsWhite)) {
            return true;
        }
        //Left Down
        else if (checkFieldForEnemy(selfRow + 1, selfColumn - 1, enemyIsWhite)) {
            return true;
        }
        //Left Up
        else if (checkFieldForEnemy(selfRow - 1, selfRow - 1, enemyIsWhite)) {
            return true;
        }
        else {
            return false;
        }
    }
}
