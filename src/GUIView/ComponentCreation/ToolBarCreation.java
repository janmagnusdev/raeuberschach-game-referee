package GUIView.ComponentCreation;

import GUIView.GameGUI;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ToolBarCreation {
    public ToolBar createToolBar(GameGUI parent) { //erstellt die ToolBar unter dem Menü und fügt entsprechende
        // Buttons hinzu.
        ToolBar toolBar = new ToolBar();

        Button newGuiButton = new Button();
        Image fileIcon = new Image("icons/file-icon.png");
        newGuiButton.setGraphic(new ImageView(fileIcon));
        newGuiButton.setOnAction(event -> parent.createCloneGui());
        Button printButton = new Button();
        Image printIcon = new Image("icons/print-icon.png");
        printButton.setGraphic(new ImageView(printIcon));
        Button playButton = new Button();
        Image playIcon = new Image("icons/play-icon.png");
        playButton.setGraphic(new ImageView(playIcon));
        Button stopButton = new Button();
        Image recordIcon = new Image("icons/record-icon.png");
        stopButton.setGraphic(new ImageView(recordIcon));
        stopButton.setOnAction(event -> parent.exitAllGUIs());

        toolBar.getItems().addAll(newGuiButton, printButton, new Separator(), playButton, stopButton);

        return toolBar;
    }
}
