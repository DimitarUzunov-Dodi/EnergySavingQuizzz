package client.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import static client.scenes.UserAlert.userAlert;

public class FileUtils {

    private static final String pathToUserData = "./src/main/data/";
    private static final String fileName = "UserData.userdata";

    private static final File defaultUserData = new File(pathToUserData + fileName);
    
    /**
     * Method for reading nickname from default file
     * @return nickname read from a default user file
     */
    public static String readNickname() {
        return readNickname(defaultUserData);
    }

    /**
     * Method for reading a nickname from custom file
     * @param userData - file for reading the nickname from
     * @return nickname read from a specified file
     */
    public static String readNickname(File userData) {
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
