package GameModel;

import GameModel.Players.AsciiPlayer;
import GameModel.Players.HumanPlayer;
import GameModel.Players.Player;
import assets.IO;

import java.util.Observable;

public class Game extends Observable {
    //TODO das spiel soll neu gestartet werden, wenn es beendet ist
    private Board board;
    private Referee referee;
    private int turnNumber;
    private Player white = new HumanPlayer(true);
    private Player black = new HumanPlayer(false);
    private Player currentPlayer = white;
    private Thread gameThread;

    public Game(Board board) {
        this.board = board;
        this.referee = new Referee(this.board, this);
        this.turnNumber = 1;
    }

    private synchronized void runGame(Game game) {
        game.gameThread = new Thread() {
            @Override
            public void run() {
                synchronized (game) {
                    game.setCurrentPlayer(game.white);
                    game.setChanged();
                    waitOnHumanIfNeeded();
                    Move oldMove = game.getCurrentPlayer().getNextMove(null);
                    notifyObservers(oldMove);
                    while (!game.checkEndingByPieces(board.getFields())) {
                        if (this.isInterrupted()) {
                            break;
                        }
                        game.setChanged();
                        waitOnHumanIfNeeded();
                        oldMove = game.currentPlayer.getNextMove(oldMove);
                        notifyObservers(oldMove);
                    }
                    game.gameThread = null;
                }
            }

            private void waitOnHumanIfNeeded() {
                synchronized (this) {
                    if (numberOfHumanPlayers() > 0) {
                        if (currentPlayer instanceof HumanPlayer) {
                            try {
                                this.wait();
                            } catch (InterruptedException e) {
                                this.interrupt();
                            }
                        }
                    }
                }
            }
        };
        game.gameThread.start();
    }

    public void startSelectedGame(Player white, Player black) {
        this.white = white;
        this.black = black;
        runGame(this);
    }

    //region ASCII
    @Deprecated
    public void startGameAscii() {
        System.out.print("Weiß beginnt, Schwarz gewinnt! Let it rip!\n\n");
        white = new AsciiPlayer(true);
        black = new AsciiPlayer(false);
        currentPlayer = white;
        Move nextMove;
        for (; ; ) {
            board.printCurrentStateToConsole();
            IO.println("Spieler " + currentPlayer + " ist am Zug!");
            nextMove = currentPlayer.getNextMove(null);
            while (!nextMove.isInBoardRange()) {
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

    private void printGameEnding(Player player) {
        IO.println("Player " + player + " hat verloren!");
        if (player.isWhite()) {
            IO.print("Black hat gewonnen!");
        } else {
            IO.print("White hat gewonnen!");
        }
        IO.println("\nDas Spiel ging " + turnNumber + " Züge!");
    }
    //endregion

    /**
     * @param fields The Field Array that is checked.
     * @return If one Player has Pieces, and the other doesn't, return true. False if both Players still have pieces. Also true if
     * nobody has pieces, which can't happen under normal circumstances.
     */
    public boolean checkEndingByPieces(Field[][] fields) {
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

    public int numberOfHumanPlayers() {
        int x = 0;
        if (white.getClass().equals(HumanPlayer.class))
            x++;
        if (black.getClass().equals(HumanPlayer.class))
            x++;
        return x;
    }

    //Getter and Setter

    public Thread getGameThread() {
        return gameThread;
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

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}
