package GameModel;

public abstract class Player {
    protected boolean isAI;
    private boolean isWhite;

    Player (boolean isWhite) {
        this.isWhite = isWhite;
    }

    public abstract Move getNextMove(Move oldMove);

    public boolean isWhite() {
        return isWhite;
    }

    public boolean canStrikeEnemy(Board board) {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
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
