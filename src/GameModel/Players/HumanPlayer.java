package GameModel.Players;

import GameModel.Board;
import GameModel.Move;

public class HumanPlayer extends Player {

    private Move mNextMove;

    public HumanPlayer(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public Move getNextMove(Move move) {
        return mNextMove;
    }

    public void setNextMove(Move nextMove) {
        mNextMove = nextMove;
    }
}
