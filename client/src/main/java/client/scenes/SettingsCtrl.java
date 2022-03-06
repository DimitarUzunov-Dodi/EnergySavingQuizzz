package client.scenes;

import com.google.inject.Inject;

public class SettingsCtrl {
    private final MainCtrl mainCtrl;

    @Inject
    public SettingsCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }
}
