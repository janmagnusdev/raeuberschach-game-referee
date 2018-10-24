package GameModel;

import GUIView.GameGUI;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class HumanMove extends Thread implements EventHandler<MouseEvent> {

    GameGUI mGameGUI;

    public HumanMove(GameGUI gameGUI) {
        this.mGameGUI = gameGUI;
    }

    @Override
    public void handle(MouseEvent event) {

    }
}
