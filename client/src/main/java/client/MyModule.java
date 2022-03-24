package client;

import client.scenes.*;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;

public class MyModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(ActivityImageCtrl.class).in(Scopes.SINGLETON);
        binder.bind(AdminActivityCtrl.class).in(Scopes.SINGLETON);
        binder.bind(AdminActivityDetailsCtrl.class).in(Scopes.SINGLETON);
        binder.bind(AdminActivityDetailsCtrl.class).in(Scopes.SINGLETON);
        binder.bind(AdminCtrl.class).in(Scopes.SINGLETON);
        binder.bind(GameScreenCtrl.class).in(Scopes.SINGLETON);
        binder.bind(LoadingController.class).in(Scopes.SINGLETON);
        binder.bind(MainCtrl.class).in(Scopes.SINGLETON);
        binder.bind(MatchLeaderboardCtrl.class).in(Scopes.SINGLETON);
        binder.bind(MultiplayerCtrl.class).in(Scopes.SINGLETON);
        binder.bind(ServerLeaderboardCtrl.class).in(Scopes.SINGLETON);
        binder.bind(SettingsCtrl.class).in(Scopes.SINGLETON);
        binder.bind(SplashCtrl.class).in(Scopes.SINGLETON);
        binder.bind(WaitingRoomCtrl.class).in(Scopes.SINGLETON);
        // TODO: add necessary utils in here
    }
}
