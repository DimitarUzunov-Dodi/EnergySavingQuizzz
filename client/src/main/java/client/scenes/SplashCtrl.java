package client.scenes;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import com.google.inject.Inject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;


public class SplashCtrl {

    private final MainCtrl mainCtrl;
    private static final String pathToUserData = "./src/main/data/";
    private static final String fileName = "UserData.userdata";
    private String nickname;

    @Inject
    public SplashCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.nickname = "";
    }

    /**
     * Function called by quit button when clicked. Quits the application
     * @param event passed by JavaFX by default
     */
    public void quitAction(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    /**
     * Function called by settings button when clicked. Changes scene to Settings scene.
     * @param event passed by JavaFX by default
     */
    public void settingsAction(ActionEvent event) {
        mainCtrl.showSettingsScreen();
    }

    /**
     * Function initializes nickname TextField in SplashScreen scene
     * @param splash Scene of SplashScreen that contains a TextField for inputting username
     */
    public void initTextField(Scene splash) {
        File userData = new File(pathToUserData + fileName);
        if(userData.exists()) {
            try {
                String username = "";

                Scanner userNameScanner = new Scanner(userData);

                if(userNameScanner.hasNextLine())
                    username = userNameScanner.nextLine();

                TextField usernameField = (TextField) splash.lookup("#nicknameField");
                usernameField.setText(username);
                this.setNickname(username);

            } catch (FileNotFoundException e) {
                // TODO: Add error message
                e.printStackTrace();
            }
        }
        else {
            try {
                Files.createDirectories(Paths.get(pathToUserData));
                userData.createNewFile();
            } catch (IOException e) {
                // TODO: Add error message
                e.printStackTrace();
            }
        }
    }

    /**
     * Function saves nickname automatically when nickname TextField is changed
     * Nickname is stored according to pathToUserData
     */
    public void saveNickname() {
        File userData = new File(pathToUserData + fileName);
        TextField usernameField = (TextField) mainCtrl.getSplashScreenScene().lookup("#nicknameField");
        String nicknameString = usernameField.getText();
        if(userData.exists()) {
            try {
                PrintWriter fWriter = new PrintWriter(userData);
                fWriter.print(nicknameString);
                fWriter.close();
            } catch (IOException e) {
                // TODO: Add error message
                e.printStackTrace();
            }
        }
        else {
            try {
                Files.createDirectories(Paths.get(pathToUserData));
                userData.createNewFile();
                PrintWriter fWriter = new PrintWriter(userData);
                fWriter.print(nicknameString);
                fWriter.close();
            } catch (IOException e) {
                // TODO: Add error message
                e.printStackTrace();
            }
        }
    }

    /**
     * Get method for nickname attribute, so nickname can be accessed everywhere by every client function
     * Subject to change, because to the best thing to import SplashCtrl everytime. Maybe we should add function somewhere in common class
     * @return
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Set method for nickname attribute
     * @param nickname String value which is going to be assigned to nickname class attribute but will not be saved in file
     */
    private void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
