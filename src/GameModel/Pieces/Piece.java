package GameModel.Pieces;

import GameModel.Board;
import GameModel.Field;
import GameModel.Move;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public abstract class Piece {
    private boolean isWhite;
    private Field belongingField;
    char asciiRepresentationChar;

    Piece(boolean isWhite, Field belongingField) {
        this.isWhite = isWhite;
        this.belongingField = belongingField;
    }

    /**
     * Checks, if the piece can perform a given move.
     * @param move The move that needs to be checked.
     * @param board The board the piece belongs to. Could be substituted by this.getBelongingField.getBelongingBoard.
     * @return True if the piece can perform the move. False otherwise.
     */
    @Deprecated
    public abstract boolean checkMove(@NotNull Move move, @NotNull Board board);

    /**
     * @return All possible fields the piece can perform moves to, as an ArrayList.
     **/
    public abstract ArrayList<Field> getPossibleFields();

    //FIXME Pieces können noch über andere eigene Pieces rüberfliegen, wenn sie auch ein anderes schlagen können
    void addPossibleFieldsDirectionToArray(ArrayList<Field> possibleFields, int dirRow, int dirColumn) {
        {
            int j = this.getBelongingField().getColumnDesignation() + dirColumn;
            for (int i = this.getBelongingField().getRowDesignation() + dirRow; (dirRow == 1 ? i < 8 : i >= 0) && (dirColumn == 1 ? j < 8 : j >= 0); i = i + dirRow,
                j = j + dirColumn) {
                if (!this.canStrikeEnemy()) {
                    if (this.getBelongingField().getBelongingBoard().getFieldAtIndex(i, j).isEmpty()) {
                        possibleFields.add(this.getBelongingField().getBelongingBoard().getFieldAtIndex(i, j));
                    } else {
                        break;
                    }
                } else if (checkFieldForEnemy(i, j, !this.getIsWhite())) {
                    possibleFields.add(this.getBelongingField().getBelongingBoard().getFieldAtIndex(i, j));
                    break;
                }
            }
        }
    }

    /**
     * General method for checking if the piece has another piece in its movement way - no matter which color the
     * suspected piece is.
     * @param move The move the piece would perform.
     * @return True if there is a piece in the way horizontally. False otherwise.
     */
    boolean isPieceInWayHorizontal(Move move) {
        int dir = move.getSourceColumn() > move.getDestColumn() ? -1 : 1;
        for (int i = 1; i < Math.abs(move.getSourceColumn() - move.getDestColumn()); ++i) {
            if (!this.getBelongingField().getBelongingBoard().getFieldAtIndex(move.getSourceRow(), move.getSourceColumn() + i * dir).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * General method for checking if the piece has another piece in its movement way - no matter which color the
     * suspected piece is.
     * @param move The move the piece would perform.
     * @return True if there is a piece in the way vertically. False otherwise.
     */
    boolean isPieceInWayVertical(Move move) {
        int dir = move.getSourceRow() > move.getDestRow() ? -1 : 1;
        for (int i = 1; i < Math.abs(move.getSourceRow() - move.getDestRow()); ++i) {
            if (!this.getBelongingField().getBelongingBoard().getFieldAtIndex((move.getSourceRow() + i * dir), move.getSourceColumn()).isEmpty()) { //Punkt vor Strich!
                return true;
            }
        }
        return false;
    }

    /**
     * General method for checking if the piece has another piece in its movement way - no matter which color the
     * suspected piece is.
     * @param move The move the piece would perform.
     * @return True if there is a piece in the way diagonally. False otherwise.
     */
    boolean isPieceInWayDiagonal(Move move) { //has to be i = 1, not i = 0; otherwise the own field would be checked, and of course return true
        int dirRow = move.getSourceRow() > move.getDestRow() ? -1 : 1;
        int dirColumn = move.getSourceColumn() > move.getDestColumn() ? -1 : 1;
        for (int i = 1; i < Math.abs(move.getSourceColumn() - move.getDestColumn()); i++) {
            if (!this.getBelongingField().getBelongingBoard().getFieldAtIndex((move.getSourceRow() + i * dirRow), (move.getSourceColumn() + i * dirColumn)).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method for checking if the piece could possibly strike an enemy piece. Is needed for the implementation of
     * force-move rule of the game. Is abstract, because each piece has its individual ruleset.
     * @return True if this piece can strike an enemy piece. False if otherwise.
     */
    public abstract boolean canStrikeEnemy();

    /**
     * General method for checking a field if it has an enemy assigned to it. Is implemented in this abstract class,
     * because it's the same for every possible piece.
     * @param rowIndex The row index of the field that is checked.
     * @param columnIndex The column index of the field that is checked.
     * @param enemyIsWhite Information, whether the enemy player is black or white. Needed, because this piece
     *                     doesn't know it's enemy. Actually, it does; this.getIsWhite().
     * @return True if there is an enemy assigned to the field. False if otherwise.
     */
    protected boolean checkFieldForEnemy(int rowIndex, int columnIndex, boolean enemyIsWhite) {
        if ( rowIndex >= 0 && rowIndex < 8 && columnIndex >= 0 && columnIndex < 8 && !this.getBelongingField().getBelongingBoard().getFieldAtIndex(rowIndex, columnIndex).isEmpty()) {
            if (this.getBelongingField().getBelongingBoard().getFieldAtIndex(rowIndex, columnIndex).getContentPiece().getIsWhite() == enemyIsWhite) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    void addPossibleFieldToArrayList(ArrayList<Field> possibleFields, int i, int j) {
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

    //Getter and Setter
    public Field getBelongingField() {
        return belongingField;
    }

    public void setBelongingField(Field belongingField) {
        this.belongingField = belongingField;
    }

    public char getAsciiRepresentationChar() {
        return asciiRepresentationChar;
    }

    public boolean getIsWhite() {
        return isWhite;
    }
}
