package pl.bliw;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pl.bliw.config.Chip8MainConfiguration;
import pl.bliw.util.Constants;

import java.io.IOException;

public class Application extends javafx.application.Application {

    private static Log log = LogFactory.getLog(Application.class);

    public static void main(String[] args) {
        log.info("JavaFX is starting");
        javafx.application.Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Chip8MainConfiguration.class);
        FXMLLoader loader = getFXMLLoaderConnectedWithSpring(context);
        loader.setLocation(getClass().getResource(Constants.MAIN_WINDOW_FXML_PATH));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root, Constants.WIDTH, Constants.HEIGHT);
            stage.setScene(scene);
            stage.setTitle(Constants.WINDOW_TITLE);
            stage.show();
        } catch (IOException e) {
            log.fatal(String.format("FXML cannot be loaded from specified path: %s", Constants.MAIN_WINDOW_FXML_PATH));
            log.fatal("IOException", e);
            System.exit(0);
        }
    }

    private FXMLLoader getFXMLLoaderConnectedWithSpring(ApplicationContext context) {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(context::getBean);
        return loader;
    }

}