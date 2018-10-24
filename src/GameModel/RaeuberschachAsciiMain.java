package GameModel;

@Deprecated
public class RaeuberschachAsciiMain {
    public static void main(String[] args) {
        Game game = new Game(new Board());
        game.startGameAscii();
    }
}
