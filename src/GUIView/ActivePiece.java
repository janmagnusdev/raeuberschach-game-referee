package GUIView;

import GameModel.Field;

public class ActivePiece {
    Field srcField;
    double x;
    double y;

    public ActivePiece (double x, double y) {
        this.x = x;
        this.y = y;
    }

    //Setter
    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setSrcField(Field srcField) {
        this.srcField = srcField;
    }

    //Getter
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Field getSrcField() {
        return srcField;
    }
}

