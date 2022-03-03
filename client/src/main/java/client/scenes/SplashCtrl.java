package client.scenes;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;


public class SplashCtrl {

    private final MainCtrl mainCtrl;
    private static final String pathToUserData = "./src/main/Data/UserData.userdata";

    @Inject
    public SplashCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    public void quitAction(ActionEvent event){
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    public void settingsAction(ActionEvent event){
        mainCtrl.showSettingsScreen();
    }

    public void initTextField(Scene splash) {
        File userData = new File(pathToUserData);
        if(userData.exists()) {
            try {
                String username = "";

                Scanner userNameScanner = new Scanner(userData);

                if(userNameScanner.hasNextLine())
                    username = userNameScanner.nextLine();

                TextField usernameField = (TextField) splash.lookup("#nicknameField");
                usernameField.setText(username);

            } catch (FileNotFoundException e) {
                // TODO: Add error message
                e.printStackTrace();
            }

        }
        else {
            // TODO: Add error message
        }
    }
}
