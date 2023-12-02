package mg.opendays2023;
import com.gluonhq.ignite.spring.SpringContext;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import java.io.IOException;

@SpringBootApplication
public class OpenDays2023App extends Application implements CommandLineRunner {
    @Getter
    private static ConfigurableApplicationContext ctx;
    @Override public void init() throws Exception {
        ctx = SpringApplication.run(OpenDays2023App.class);
    }
    @Override public void stop() throws Exception {
        ctx.close();
        Platform.exit();
    }
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(OpenDays2023App.class.getResource("football-club-view.fxml"));
        fxmlLoader.setControllerFactory(ctx::getBean);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello OpenDays 2023");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
    @Override
    public void run(String... args) throws Exception {}
}