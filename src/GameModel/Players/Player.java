package GameModel.Players;

import GameModel.Board;
import GameModel.Move;

public abstract class Player {
    // Access has to be protected because of the .jar programs
    protected boolean isAI;
    protected boolean isWhite;

    public Player(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public abstract Move getNextMove(Move oldMove);

    public boolean isWhite() {
        return isWhite;
    }

    public boolean isAI() {
        return isAI;
    }

    public boolean canStrikeEnemy(Board board) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!board.getFieldAtIndex(i, j).isEmpty()) {
                    if (board.getFieldAtIndex(i, j).getContentPiece().getIsWhite() == this.isWhite()) {
                        if (board.getFieldAtIndex(i, j).getContentPiece().canStrikeEnemy()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
