package GameModel;

import GameModel.Players.Player;

public class Referee {
    private Board board;
    private Game game;

    public Referee(Board board, Game game) {
        this.board = board;
        this.game = game;
    }

    /**
     * The referee checks if the chosen move of a player can be executed without validating piece movement rules/
     * possible fields of the chosen piece.
     *
     * @param move          The move a player has chosen.
     * @param currentPlayer The player the move belongs to.
     * @return Returns true, if the movement for the piece is valid, based on the ArrayList of possible moves for the
     * instance of the piece in the source field of the move. Returns false otherwise.
     */
    public boolean checkMove(Move move, Player currentPlayer) {
        if (move.isInBoardRange() && game.getCurrentPlayer().isWhite() == board.getFieldAtIndex(move.getSourceRow(),
                move.getSourceColumn()).getContentPiece().getIsWhite()) {
            return board.getFieldAtIndex(move.getSourceRow(), move.getSourceColumn()).getContentPiece().getPossibleFields().contains(board.getFieldAtIndex(move.getDestRow(), move.getDestColumn()));
        }
        return false;
    }

    /**
     * @param move   The move a player has chosen.
     * @param player The player the move belongs to.
     * @return True if the target of the move is from the same color as the player. False otherwise. Also false if the target field is empty.
     */
    private boolean isTargetFromSameColor(Move move, Player player) {
        if (!board.getFieldAtIndex(move.getDestRow(), move.getDestColumn()).isEmpty()) {
            return (board.getFieldAtIndex(move.getDestRow(), move.getDestColumn()).getContentPiece().getIsWhite() == player.isWhite());
        } else {
            return false;
        }
    }

    /**
     * @param move   The move a player has chosen.
     * @param player The player the move belongs to.
     * @return True if the source piece of the move is from the same color as the player. False otherwise. Also false if the source field is empty.
     */
    boolean isSourceFromSameColorAsPlayer(Move move, Player player) {
        if (!board.getFieldAtIndex(move.getSourceRow(), move.getSourceColumn()).isEmpty()) {
            return (board.getFieldAtIndex(move.getSourceRow(), move.getSourceColumn()).getContentPiece().getIsWhite() == player.isWhite());
        } else {
            return false;
        }
    }

    @SuppressWarnings("Duplicates")
    public void doMove(Move move) {
        //if statement sorgt dafür, dass die geschlagene Figur vom Brett entfernt wird; falls dort eine Figur steht
        if (board.getFieldAtIndex(move.getDestRow(), move.getDestColumn()).getContentPiece() != null) {
            board.getFieldAtIndex(move.getDestRow(), move.getDestColumn()).getContentPiece().setBelongingField(null);
            board.getFieldAtIndex(move.getDestRow(), move.getDestColumn()).setContentPiece(null);
        }
        board.getFieldAtIndex(move.getDestRow(), move.getDestColumn()).setContentPiece(board.getFieldAtIndex(move.getSourceRow(), move.getSourceColumn()).getContentPiece());
        board.getFieldAtIndex(move.getDestRow(), move.getDestColumn()).getContentPiece().setBelongingField(board.getFieldAtIndex(move.getDestRow(), move.getDestColumn()));
        board.getFieldAtIndex(move.getSourceRow(), move.getSourceColumn()).setContentPiece(null);
    }
}
