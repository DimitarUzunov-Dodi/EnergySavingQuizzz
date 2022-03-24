package client.utils;

import javafx.scene.control.Alert;

public class UserAlert {
    /**
     * This function is used for creating Error, Info and Warning alerts.
     * They have text and only one button that closes appeared window
     * @param type - Alert type INFO - information alert, ERROR - error alert, WARN - warning alert
     * @param header - String that will appear as the header text of the alert
     * @param message - String that will appear in context field of the alert
     */
    public static void userAlert(String type, String header, String message) {
        Alert a = new Alert(Alert.AlertType.NONE);
        if (type.equals("INFO")) {
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.setTitle("Information");
        } else if (type.equals("ERROR")) {
            a.setAlertType(Alert.AlertType.ERROR);
            a.setTitle("Error occurred");
        } else if (type.equals("WARN")) {
            a.setAlertType(Alert.AlertType.WARNING);
            a.setTitle("Warning");
        }
        a.setHeaderText(header);
        a.setContentText(message);
        a.show();
    }
}
