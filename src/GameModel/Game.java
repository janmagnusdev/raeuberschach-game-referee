package GameModel;

import GUIView.ComponentCreation.DisableButtonProperty;
import assets.IO;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.MenuItem;
import org.jetbrains.annotations.NotNull;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

public class Game extends Observable { //Puts a whole game with all its components together
    private Board board;
    private Referee referee;
    private int turnNumber;
    private Player white = new HumanPlayer(true);
    private Player black = new HumanPlayer(false);
    private Player currentPlayer = white;
    private boolean isActive;
    private Thread dummyDummyGame;


    public Game(Board board) {
        this.board = board;
        this.referee = new Referee(this.board, this);
        this.turnNumber = 1;
        this.isActive = false;
    }

    public synchronized void startDummyDummyGame(Game game) {
        game.white = new DummyPlayer(true, game.getBoard());
        game.black = new DummyPlayer(false, game.getBoard());
        game.currentPlayer = game.white;

        dummyDummyGame = new Thread() {
            @Override
            public synchronized void run() {
                while (!this.isInterrupted()) {
                    game.isActive = true;
                    while (!checkEndingByPieces(game.getBoard().getFields())) {
                        Move nextMove = game.currentPlayer.getNextMove(null);
                        game.setChanged();
                        notifyObservers(nextMove);
                    }
                }
            }
        };
        dummyDummyGame.start();
    }

    public void setDummyDummyGameOnWait() {
        try {
            this.dummyDummyGame.wait();
        } catch (Exception e) {
            IO.println("DummyDummyGame couldn't wait");
        }
    }

    public void startGameAscii() {
        System.out.print("Weiß beginnt, Schwarz gewinnt! Let it rip!\n\n");
        white = new AsciiPlayer(true);
        black = new AsciiPlayer(false);
        currentPlayer = white;
        Move nextMove;
        for (; ; ) {
            board.printCurrentState();
            IO.println("Spieler " + currentPlayer + " ist am Zug!");
            nextMove = currentPlayer.getNextMove(null);
            while (!nextMove.isInBoardRange()) { //eventuell an referee outsourcen; bessere objektorientierung
                IO.println("Der Zug ist nicht im Rahmen des Spielbretts!");
                nextMove = currentPlayer.getNextMove(null);
            }
            while (board.getFieldAtIndex(nextMove.getDestRow(), nextMove.getDestColumn()).isEmpty() && currentPlayer.canStrikeEnemy(board)) {
                IO.println("Du kannst eine gegnerische Figur schlagen!");
                nextMove = currentPlayer.getNextMove(null);
            }
            if (!referee.isSourceFromSameColorAsPlayer(nextMove, currentPlayer)) {
                IO.println("Du darfst diese Figur nicht bewegen!");
                break;
            }
            if (!referee.checkMove(nextMove, currentPlayer)) {
                break;
            } else {
                referee.doMove(nextMove);
                if (checkEndingByPieces(board.getFields())) {
                    currentPlayer = currentPlayer.isWhite() ? black : white;
                    break;
                }
            }
            currentPlayer = currentPlayer.isWhite() ? black : white;
            turnNumber++;
        }
        printGameEnding(currentPlayer);
    }

    private void printGameEnding(@NotNull Player player) {
        IO.println("Player " + player + " hat verloren!");
        if (player.isWhite()) {
            IO.print("Black hat gewonnen!");
        } else {
            IO.print("White hat gewonnen!");
        }
        IO.println("\nDas Spiel ging " + turnNumber + " Züge!");
    }

    public boolean checkEndingByPieces(@NotNull Field[][] fields) {
        boolean whiteHasPieces = false;
        boolean blackHasPieces = false;
        for (Field[] fields1 : fields) {
            for (Field field : fields1) {
                if (!field.isEmpty()) {
                    if (field.getContentPiece().getIsWhite()) {
                        whiteHasPieces = true;
                    } else {
                        blackHasPieces = true;
                    }
                }
            }
        }
        return !(whiteHasPieces && blackHasPieces);
    }

    //Getter and Setter

    public Thread getDummyDummyGame() {
        return dummyDummyGame;
    }

    public Board getBoard() {
        return board;
    }

    public Referee getReferee() {
        return referee;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getWhite() {
        return white;
    }

    public Player getBlack() {
        return black;
    }

    public void setBlack(Player black) {
        this.black = black;
    }

    public void setWhite(Player white) {
        this.white = white;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}
