package pl.bliw;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pl.bliw.config.Chip8MainConfiguration;
import pl.bliw.util.Constants;

import java.io.IOException;
import java.net.URL;

/**
 * The Chip8 program implements an application that emulates CHIP-8 and allows to
 * run the original machine code of programs which has been written for this virtual machine.
 * <b>http://devernay.free.fr/hacks/chip8/C8TECH10.HTM</b>
 *
 * @author b-liw
 * @version 1.0
 */

public class Application extends javafx.application.Application {
    /**
     * The logger for this class
     */
    private static Logger log = Logger.getLogger(Application.class.getName());
    /**
     * The application context provided by Spring Framework
     */
    private ApplicationContext context;

    /**
     * The entry point of application initialize JavaFX
     * @param args an array of program arguments
     */
    public static void main(String[] args) {
        log.info("JavaFX is starting");
        javafx.application.Application.launch(args);
    }

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned, and after the system is ready for
     * the application to begin running.
     * The method creates application context, fxml loader and main window
     * @param primaryStage the primary stage for this application
     */
    @Override
    public void start(Stage primaryStage) {
        context = new AnnotationConfigApplicationContext(Chip8MainConfiguration.class);
        FXMLLoader loader = getFXMLLoaderConnectedWithSpring(context);
        createScene(primaryStage, loader, getClass().getResource(Constants.MAIN_WINDOW_FXML_PATH));
    }

    /**
     * Creates JavaFX scene from specified URL and attaches it to provided stage using loader
     *
     * @param primaryStage the stage which will store new scene
     * @param loader       the fxml loader
     * @param sceneURL     the location of the file with scene fxml
     */
    private void createScene(Stage primaryStage, FXMLLoader loader, URL sceneURL) {
        loader.setLocation(sceneURL);
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root, Constants.WIDTH, Constants.HEIGHT);
            scene.getRoot().requestFocus();
            primaryStage.setScene(scene);
            primaryStage.setTitle(Constants.WINDOW_TITLE);
            primaryStage.setResizable(false);
            primaryStage.setOnCloseRequest(windowEvent -> {
                ((ConfigurableApplicationContext) context).close();
                Platform.exit();
                System.exit(0);
            });
            primaryStage.show();
        } catch (IOException e) {
            log.fatal(String.format("FXML cannot be loaded from specified path: %s", Constants.MAIN_WINDOW_FXML_PATH));
            log.fatal("IOException", e);
            System.exit(0);
        }
    }

    /**
     * Returns a FXML loader object that can be used to load scenes for JavaFX applications.
     * The method creates new FXML loader and connects loader with spring factory bean.
     * @param context the Spring application context
     * @return FXML loader object that can be used to load scenes
     */
    private FXMLLoader getFXMLLoaderConnectedWithSpring(ApplicationContext context) {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(context::getBean);
        return loader;
    }
}