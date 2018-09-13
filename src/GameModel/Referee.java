package GameModel;

public class Referee {
    Board board;
    Game game;

    public Referee(Board board, Game game) { //Board übergeben ist obsolet, wenn der Referee das Game kennt; anpassen
        this.board = board;
        this.game = game;
    }


    /**
     * The referee checks if the chosen move of a player can be executed without validating piece movement rules.
     *
     * @param move          The move a player has chosen.
     * @param currentPlayer The player the move belongs to.
     * @return Returns true, if the movement for the piece is valid. Returns false otherwise.
     */
    public boolean checkMove(Move move, Player currentPlayer) {
        if (move.isInBoardRange() && game.getCurrentPlayer().isWhite() == game.getBoard().getFieldAtIndex(move.getSourceRow(),
                move.getSourceColumn()).getContentPiece().getIsWhite()) {
            if (!isTargetFromSameColor(move, currentPlayer) && !(board.getFieldAtIndex(move.getSourceRow(), move.getSourceColumn()).isEmpty())) {
                return board.getFieldAtIndex(move.getSourceRow(), move.getSourceColumn()).getContentPiece().checkMove(move, board);
            }
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

    public void doMove(Move move) {
        //if statement sorgt dafür, dass die geschlagene Figur vom Brett entfernt wird; falls dort eine Figur steht
        if (board.getFieldAtIndex(move.getDestRow(), move.getDestColumn()).getContentPiece() != null) {
            board.getFieldAtIndex(move.getDestRow(), move.getDestColumn()).getContentPiece().setBelongingField(null);
            board.getFieldAtIndex(move.getDestRow(), move.getDestColumn()).setContentPiece(null);
        }
        board.getFieldAtIndex(move.getDestRow(), move.getDestColumn()).setContentPiece(board.getFieldAtIndex(move.getSourceRow(), move.getSourceColumn()).getContentPiece());
        board.getFieldAtIndex(move.getDestRow(), move.getDestColumn()).getContentPiece().setBelongingField(board.getFieldAtIndex(move.getDestRow(), move.getDestColumn()));
        board.getFieldAtIndex(move.getSourceRow(), move.getSourceColumn()).setContentPiece(null);
        board.getFieldAtIndex(move.getSourceRow(), move.getSourceColumn()).setContentPiece(null);
    }

    //Getter and Setter
    public Board getBoard() {
        return board;
    }
}
