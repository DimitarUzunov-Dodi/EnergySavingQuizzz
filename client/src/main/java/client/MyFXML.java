package client;

import client.utils.SceneController;
import com.google.inject.Inject;
import com.google.inject.Injector;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.util.Builder;
import javafx.util.BuilderFactory;
import javafx.util.Callback;

/**
 * JavaFX helper class.
 */
@SuppressWarnings("checkstyle:abbreviationaswordinname")
public class MyFXML {

    private final Injector injector;

    /**
     * Basic constructor.
     * @param injector the injector that handles the controllers
     */
    @Inject
    public MyFXML(Injector injector) {
        this.injector = injector;
    }

    /**
     * Loads the javafx scene and assigns it to the proper controller instance.
     * @param url URL of the resource
     * @return Reference to the instance of a SceneController that holds a
     * reference of the freshly loaded Scene
     */
    private <T extends SceneController> T loadScene(URL url) {
        try {
            var loader = new FXMLLoader(url, null, null, new MyFactory(), StandardCharsets.UTF_8);
            Parent parent = loader.load();
            T ctrl = loader.getController();
            ctrl.setScene(new Scene(parent));
            return ctrl;
        } catch (IOException e) {
            System.out.println("IO error while loading fxml:\n" + e);
            throw new RuntimeException(e); // crash (unless handled by caller)
        }
    }

    /**
     * Get a reference of the specified scene controller.
     * @param ctrl Type of the desired controller
     * @return Reference of the (singleton) instance of that specific controller type.
     */
    public <T extends SceneController> T get(Class<T> ctrl) {
        T c = injector.getInstance(ctrl);
        if (c.getScene() == null) {
            String file = ctrl.getSimpleName().replace("Ctrl", ".fxml");
            String  path = Path.of("", "client", "scenes", file).toString();
            return loadScene(MyFXML.class.getClassLoader().getResource(path));
        }
        return c;
    }

    /**
     * Call SceneController.show() on a controller.
     * @param ctrl Type of the controller
     */
    public <T extends SceneController> void showScene(Class<T> ctrl) {
        get(ctrl).show();
    }

    /**
     * Wraps the injector for ease of use.
     */
    private class MyFactory implements BuilderFactory, Callback<Class<?>, Object> {
        @Override
        @SuppressWarnings("rawtypes")
        public Builder<?> getBuilder(Class<?> type) {
            return (Builder) () -> injector.getInstance(type);
        }

        @Override
        public Object call(Class<?> type) {
            return injector.getInstance(type);
        }
    }
}