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

import static client.scenes.UserAlert.userAlert;


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
     */
    public void initTextField() {
        File userData = new File(pathToUserData + fileName);
        TextField usernameField = (TextField) mainCtrl.getSplashScreenScene().lookup("#nicknameField");

        String username = readNickname(userData);
        usernameField.setText(username);
        this.setNickname(username);
    }

    /**
     * Function saves nickname automatically when nickname TextField is changed
     * Nickname is stored according to pathToUserData
     */
    public void saveNickname() {
        File userData = new File(pathToUserData + fileName);
        TextField usernameField = (TextField) mainCtrl.getSplashScreenScene().lookup("#nicknameField");
        String nicknameString = usernameField.getText();
        writeNickname(userData, nicknameString);
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

    private void writeNickname(File userData, String nicknameString) {
        if(userData.exists()) {
            try {
                PrintWriter fWriter = new PrintWriter(userData);
                fWriter.print(nicknameString);
                fWriter.close();
            } catch (IOException e) {
                userAlert("ERROR", "Error while saving username", "Error occurred while trying to save username to file.");
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
                userAlert("ERROR", "Error while saving username", "Error occurred while trying to create a file for storing username.");
                e.printStackTrace();
            }
        }
    }

    private String readNickname(File userData) {
        String username = "";
        if(userData.exists()) {
            try {
                Scanner userNameScanner = new Scanner(userData);

                if(userNameScanner.hasNextLine())
                    username = userNameScanner.nextLine();

            } catch (FileNotFoundException e) {
                userAlert("ERROR", "Error while getting username", "Error occurred while trying to get a username from a file.");
                e.printStackTrace();
            }
        }
        else {
            try {
                Files.createDirectories(Paths.get(pathToUserData));
                userData.createNewFile();
            } catch (IOException e) {
                userAlert("ERROR", "Error while getting username", "Error occurred while trying to create a file for storing username.");
                e.printStackTrace();
            }
        }

        return username;
    }
}
