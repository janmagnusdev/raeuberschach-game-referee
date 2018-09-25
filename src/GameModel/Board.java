package GameModel;

import assets.IO;
import GameModel.Pieces.*;

public class Board {
    private Field[][] fields;

    public Board() {
        fields = new Field[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                fields[i][j] = new Field(this, i, j, null);
            }
        }
        setPiecesInitial();
    }


    public void setPiecesInitial() {
        boolean isWhite = false;
        for (int i = 0; i < fields.length; i++) {
            if (i == 6) {
                isWhite = true;
            }
            for (int j = 0; j < fields[0].length; j++) {
                fields[i][j].setContentPiece(null);
                if (i == 0 || i == fields.length - 1) {
                    if (j == 0 || j == 7) {
                        fields[i][j].setContentPiece(new RookPiece(isWhite, fields[i][j]));
                    }
                    if (j == 1 || j == 6) {
                        fields[i][j].setContentPiece(new KnightPiece(isWhite, fields[i][j]));
                    }
                    if (j == 2 || j == 5) {
                        fields[i][j].setContentPiece(new BishopPiece(isWhite, fields[i][j]));
                    }
                    if (j == 3) {
                        fields[i][j].setContentPiece(new KingPiece(isWhite, fields[i][j]));
                    }
                    if (j == 4) {
                        fields[i][j].setContentPiece(new QueenPiece(isWhite, fields[i][j]));
                    }
                }
                if (i == 1 || i == fields.length - 2) {
                    fields[i][j].setContentPiece(new PawnPiece(isWhite, fields[i][j]));
                }
            }
        }
    }

    @Deprecated
    void printCurrentState() {
        IO.println("      Black");
        IO.println("  a b c d e f g h ");
        for (int i = 0; i < fields.length; i++) {
            IO.print(i + " ");
            for (int j = 0; j < fields[0].length; j++) {
                if (!fields[i][j].isEmpty()) {
                    IO.print(getFieldAtIndex(i, j).getContentPiece().getAsciiRepresentationChar() + " ");
                } else {
                    IO.print(". ");
                }
            }
            IO.println();
        }
        IO.println("      White");
    }


    //Getter and Setter
    public Field[][] getFields() {
        return fields;
    }

    public Field getFieldAtIndex(int rowIndex, int columnIndex) {
        if (rowIndex >= 0 && rowIndex <= fields.length && columnIndex >= 0 && columnIndex <= fields[0].length) {
            return fields[rowIndex][columnIndex];
        }
        return null;
    }

    public Field[] getFieldAtIndex (int rowIndex) {
        if (rowIndex >= 0 && rowIndex <= fields.length) {
            return fields[rowIndex];
        }
        return null;
    }
}
