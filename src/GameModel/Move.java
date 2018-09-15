package GameModel;

public class Move {
    private final int sourceColumn;
    private final int sourceRow;
    private final int destColumn;
    private final int destRow;

    public Move (int sourceColumn, int sourceRow, int destColumn, int destRow) { // j an erster Stelle, i an zweiter!
        this.sourceColumn = sourceColumn - 'a';
        this.sourceRow = sourceRow;
        this.destColumn = destColumn - 'a';
        this.destRow = destRow;
    }

    public boolean isInBoardRange() { //only checks for standard 8x8 board; method could be overwritten with board parameter
        return (destColumn < 8 && destColumn >= 0) && (destRow < 8 && destRow >= 0);
    }

    //Getter and Setter
    public int getSourceColumn() {
        return sourceColumn;
    }

    public int getSourceRow() {
        return sourceRow;
    }

    public int getDestColumn() {
        return destColumn;
    }

    public int getDestRow() {
        return destRow;
    }
}
