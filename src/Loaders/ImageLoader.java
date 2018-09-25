package Loaders;

import GameModel.Pieces.*;
import javafx.scene.image.Image;

public final class ImageLoader { //Mit freundlicher Unterstützung von Pascal Winkler -> Erklärung des Konzepts eines ImageLoaders und Singleton
    private static ImageLoader instance;

    private Image rookWhiteImage = new Image("piece-icons/rook-white.png");
    private Image queenWhiteImage = new Image("piece-icons/queen-white.png");
    private Image pawnWhiteImage = new Image("piece-icons/pawn-white.png");
    private Image knightWhiteImage = new Image("piece-icons/knight-white.png");
    private Image kingWhiteImage = new Image("piece-icons/king-white.png");
    private Image bishopWhiteImage = new Image("piece-icons/bishop-white.png");

    private Image rookBlackImage = new Image("piece-icons/rook-black.png");
    private Image queenBlackImage = new Image("piece-icons/queen-black.png");
    private Image pawnBlackImage = new Image("piece-icons/pawn-black.png");
    private Image knightBlackImage = new Image("piece-icons/knight-black.png");
    private Image kingBlackImage = new Image("piece-icons/king-black.png");
    private Image bishopBlackImage = new Image("piece-icons/bishop-black.png");

    private ImageLoader() {
    }

    //Singleton
    public static ImageLoader getInstance() { //Realisierung des Singleton-Prinzips
        return instance == null ? instance = new ImageLoader() : instance;
    }

    public Image loadPieceImage(Piece piece) {
        if (piece instanceof RookPiece) {
            return piece.getIsWhite() ? rookWhiteImage : rookBlackImage;
        } else if (piece instanceof QueenPiece) {
            return piece.getIsWhite() ? queenWhiteImage : queenBlackImage;
        } else if (piece instanceof PawnPiece) {
            return piece.getIsWhite() ? pawnWhiteImage : pawnBlackImage;
        } else if (piece instanceof KnightPiece) {
            return piece.getIsWhite() ? knightWhiteImage : knightBlackImage;
        } else if (piece instanceof KingPiece) {
            return piece.getIsWhite() ? kingWhiteImage : kingBlackImage;
        } else if (piece instanceof BishopPiece) {
            return piece.getIsWhite() ? bishopWhiteImage : bishopBlackImage;
        } else {
            return null;
        }
    }
}
