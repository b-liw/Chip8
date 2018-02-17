package pl.bliw.gui;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pl.bliw.emulator.Chip8;

public class MainWindowController {

    private static Log log = LogFactory.getLog(MainWindowController.class);
    private Chip8 chip;
    private GraphicsContext gc;

    @FXML
    private Canvas canvas;

    public MainWindowController(Chip8 chip8) {
        this.chip = chip8;
    }

    public void drawCanvas() {

    }

    @FXML
    public void initialize() {
        gc = canvas.getGraphicsContext2D();
    }

}
