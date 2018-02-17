package pl.bliw.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pl.bliw.emulator.Chip8;
import pl.bliw.emulator.io.Screen;
import pl.bliw.util.Constants;

import java.util.Timer;
import java.util.TimerTask;

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
        Screen screen = chip.getScreen();
        boolean[] screenState = screen.getScreenState();
        for (int i = 0; i < screenState.length; i++) {
            boolean b = screenState[i];
            gc.setFill(b ? Color.WHITE : Color.BLACK);
            int x = (i % 64);
            int y = i / Screen.getWidth();
            gc.fillRect(x * Constants.SCALE, y * Constants.SCALE, Constants.SCALE, Constants.SCALE);
        }
    }

    @FXML
    public void initialize() {
        gc = canvas.getGraphicsContext2D();
        chip.initialize(Constants.PONG_CHIP_8_DEFAULT_ROM_PATH);
        long delay = ((long) (Constants.EXPECTED_DELAY_IN_NANO_SECONDS / Constants.NANO_SECONDS_FACTOR * Constants.MILI_SECONDS_FACTOR));
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> drawCanvas());
            }
        }, 1, delay);
    }

}
