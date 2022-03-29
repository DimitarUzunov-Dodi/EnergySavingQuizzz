package client.utils;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

public class ColorUtils {
    public static final Background defaultButton = new Background(
            new BackgroundFill(Color.rgb(40,78,97), null, null)
    );

    public static final Background redButton = new Background(
            new BackgroundFill(Color.rgb(255,0,0), null, null)
    );

    public static final Background greenButton = new Background(
            new BackgroundFill(Color.rgb(0,255,0), null, null)
    );
}
