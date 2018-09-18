package GUIView.ComponentCreation;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class DisableButtonProperty  { // not used
    private BooleanProperty value = new SimpleBooleanProperty(this, "value", false);

    public void setValue(boolean value) {
        this.value.setValue(value);
    }

    public boolean getValue() {
        return this.value.getValue();
    }

    public BooleanProperty valueProperty() {
        return value;
    }
}
