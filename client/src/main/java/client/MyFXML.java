package client;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import client.utils.SceneController;
import com.google.inject.Injector;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.util.Builder;
import javafx.util.BuilderFactory;
import javafx.util.Callback;

public class MyFXML {

    private Injector injector;

    public MyFXML(Injector injector) {
        this.injector = injector;
    }

    /**
     * Loads the javafx scene and returns an instance of its controller.
     * This function can be used to dynamically load pages individually .
     * @param filename Name of the .fxml file
     * @return An instance of a SceneController that holds a reference to the Scene
     */
    public <T extends SceneController> T loadScene(String filename) {
        try {
            var loader = new FXMLLoader(MyFXML.class.getClassLoader().getResource(filename), null, null, new MyFactory(), StandardCharsets.UTF_8);
            T ctrl = loader.getController();
            ctrl.setScene(new Scene(loader.load()));
            return ctrl;
        } catch (IOException e) {
            System.out.println("IO error while loading fxml:\n" + e);
            throw new RuntimeException(e); // crash (unless handled by caller)
        }
    }

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

    /*
    private Injector injector;

    public MyFXML(Injector injector) {
        this.injector = injector;
    }

    public <T> Pair<T, Parent> load(Class<T> c, String... parts) {
        try {
            var loader = new FXMLLoader(getLocation(parts), null, null, new MyFactory(), StandardCharsets.UTF_8);
            Parent parent = loader.load();
            T ctrl = loader.getController();
            return new Pair<>(ctrl, parent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private URL getLocation(String... parts) {
        var path = Path.of("", parts).toString();
        return MyFXML.class.getClassLoader().getResource(path);
    }

    private class MyFactory implements BuilderFactory, Callback<Class<?>, Object> {

        @Override
        @SuppressWarnings("rawtypes")
        public Builder<?> getBuilder(Class<?> type) {
            return new Builder() {
                @Override
                public Object build() {
                    return injector.getInstance(type);
                }
            };
        }

        @Override
        public Object call(Class<?> type) {
            return injector.getInstance(type);
        }
    }
    */
}