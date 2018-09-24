package GameModel.Players;

import GameModel.Board;
import GameModel.Move;
import assets.IO;

public class AsciiPlayer extends Player {

    public AsciiPlayer(boolean playerIsWhite) {
        super(playerIsWhite);
    }

    @Override
    public Move getNextMove (Move oldMove) {
        char moveFromColumn = IO.readChar("from column: ");
        int moveFromRow = IO.readInt("from row: ");
        IO.println("-------------");
        char moveToColumn = IO.readChar("to column: ");
        int moveToRow = IO.readInt("to row: ");
        IO.println();
        return new Move(moveFromColumn, moveFromRow, moveToColumn, moveToRow);
    }

    @Override
    public String toString() {
        if (isWhite()) {
            return "White";
        } else {
            return "Black";
        }
    }
}
