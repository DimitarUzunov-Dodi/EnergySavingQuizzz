package client.scenes;

import com.google.inject.Inject;

public class LoadingController {
    private final MainCtrl mainCtrl;

    @Inject
    public LoadingController(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }
}
