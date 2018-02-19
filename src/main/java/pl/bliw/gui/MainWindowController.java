package pl.bliw.gui;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import org.apache.log4j.Logger;
import pl.bliw.emulator.Chip8;
import pl.bliw.emulator.io.Screen;
import pl.bliw.util.Constants;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainWindowController {

    private static Logger log = Logger.getLogger(MainWindowController.class.getName());
    private Chip8 chip;
    private GraphicsContext gc;
    private ScheduledExecutorService threadExecutor;

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
        canvas.requestFocus();
        String romPath = getFilePathFromFileChooser();
        if (!romPath.isEmpty()) {
            try {
                chip.initialize(romPath);
                runChipInAnotherThread();
            } catch (IOException e) {
                String message = String.format("Error occurred during opening rom file with specified path: %s%n%s", romPath, e.getMessage());
                log.error(message, e);
                ErrorDialog.show(message);
                Platform.exit();
            } catch (IllegalArgumentException e) {
                log.error(e.getMessage(), e);
                ErrorDialog.show(e.getMessage());
                Platform.exit();
            }
        } else {
            ErrorDialog.show("You have to select correct rom file");
            Platform.exit();
        }
    }

    private void runChipInAnotherThread() {
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() {
                        try {
                            threadExecutor = Executors.newScheduledThreadPool(1);
                            threadExecutor.scheduleAtFixedRate(() -> {
                                chip.run();
                                if (chip.getScreen().isCanvasUpdated()) {
                                    drawCanvas();
                                    chip.getScreen().setCanvasUpdated(false);
                                }
                            }, 0, Constants.EXPECTED_DELAY_IN_NANO_SECONDS, TimeUnit.NANOSECONDS).get();
                        } catch (Exception e) { // TOP EXCEPTION HANDLER, WHICH WILL SHUTDOWN EMULATOR AND SHOW CRASH LOG
                            String message = String.format("Critical error, shutting down the emulator%n%s", e.getMessage());
                            ErrorDialog.show(message);
                            log.fatal(message, e);
                            chip.shutDown();
                            Platform.exit();
                        }
                        return null;
                    }
                };
            }
        };
        service.start();
    }

    @FXML
    private void keyPressedListener(KeyEvent event) {
        chip.getKeyboard().keyPressed(event.getCode().getName());
    }

    @FXML
    private void keyReleasedListener(KeyEvent event) {
        chip.getKeyboard().keyReleased(event.getCode().getName());
    }

    private String getFilePathFromFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("SELECT ROM FILE");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            return file.getAbsolutePath();
        } else {
            return "";
        }
    }

}
