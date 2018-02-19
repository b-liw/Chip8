package pl.bliw.gui;

import javafx.scene.control.Alert;
import javafx.scene.layout.Region;
import pl.bliw.util.Constants;

public class ErrorDialog {

    public static void show(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.setTitle(Constants.WINDOW_TITLE);
        alert.setHeaderText(Constants.ERROR_INFO);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
