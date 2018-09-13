package GameModel;

import GameModel.Pieces.Piece;

public class Field {
    Piece contentPiece;
    Board belongingBoard;
    int columnDesignation;
    int rowDesignation;

    Field (Board belongingBoard,  int rowDesignation, int columnDesignation, Piece contentPiece) {
        this.columnDesignation = columnDesignation;
        this.rowDesignation = rowDesignation;
        this.belongingBoard = belongingBoard;
        this.contentPiece = contentPiece;
    }

    public boolean isEmpty() {
        return (contentPiece == null);
    }


    //Getter and Setter
    public void setContentPiece(Piece contentPiece) {
        this.contentPiece = contentPiece;
    }

    public int getColumnDesignation() {
        return columnDesignation;
    }

    public int getRowDesignation() {
        return rowDesignation;
    }

    public Piece getContentPiece() {
        return contentPiece;
    }

    public Board getBelongingBoard() {
        return belongingBoard;
    }
}
