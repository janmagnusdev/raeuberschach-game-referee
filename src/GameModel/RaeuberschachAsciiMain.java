package GameModel;

/**
 * A Räuberschach-Implementation with schlagzwang.
 * No conversion from pawns to other pieces in version 1.0.
 *
 * @author jakister
 * @version 1.0
 */
@Deprecated
public class RaeuberschachAsciiMain {
    public static void main(String[] args) {
        Game game = new Game(new Board());
        game.startGameAscii();
    }
}
