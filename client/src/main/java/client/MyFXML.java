package client;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import client.scenes.MainCtrl;
import client.utils.SceneController;
import com.google.inject.Injector;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.util.Builder;
import javafx.util.BuilderFactory;
import javafx.util.Callback;

public class MyFXML {

    private final Injector injector;

    /**
     * Basic constructor
     * @param injector the injector that handles the controllers
     */
    public MyFXML(Injector injector) {
        this.injector = injector;
    }

    /**
     * Loads the javafx scene and assigns it to the proper controller instance.
     * @param filename Name of the .fxml file
     * @return Reference to the instance of a SceneController that holds a reference of the freshly loaded Scene
     */
    private <T extends SceneController> T loadScene(String filename) {
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

    /**
     * Get a reference of the specified scene controller
     * @param ctrl Type of the desired controller
     * @return Reference of the (singleton) instance of that specific controller type.
     */
    public <T extends SceneController> T get(Class<T> ctrl) {
        T c = injector.getInstance(ctrl);
        if(c.getScene() == null)
            // could also be implemented with a map<class, string> TODO: discuss with the team
            return loadScene(ctrl.getName().replace("Ctrl", ".fxml"));
        return c;
    }

    /**
     * Call SceneController.show() on a controller
     * @param ctrl Type of the controller
     */
    public <T extends SceneController> void showScene(Class<T> ctrl) {
        get(ctrl).show();
    }

    /**
     * Wraps the injector for ease of use
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