package GameModel.Players;

import GameModel.Board;
import GameModel.Move;

public class HumanPlayer extends Player {
    public HumanPlayer(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public Move getNextMove(Move move) {
        return move;
    }
}