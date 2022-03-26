package client;

//CHECKSTYLE:OFF
import client.scenes.*;
//CHECKSTYLE:ON
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;

public class MyModule implements Module {

    @Override
    public void configure(Binder binder) {
        // controllers
        binder.bind(AdminActivityCtrl.class).in(Scopes.SINGLETON);
        binder.bind(AdminActivityDetailsCtrl.class).in(Scopes.SINGLETON);
        binder.bind(AdminActivityDetailsCtrl.class).in(Scopes.SINGLETON);
        binder.bind(AdminCtrl.class).in(Scopes.SINGLETON);
        binder.bind(GameScreenCtrl.class).in(Scopes.SINGLETON);
        binder.bind(LoadingCtrl.class).in(Scopes.SINGLETON);
        binder.bind(MainCtrl.class).in(Scopes.SINGLETON);
        binder.bind(MatchLeaderboardCtrl.class).in(Scopes.SINGLETON);
        binder.bind(MultiplayerCtrl.class).in(Scopes.SINGLETON);
        binder.bind(ServerLeaderboardCtrl.class).in(Scopes.SINGLETON);
        binder.bind(SettingsCtrl.class).in(Scopes.SINGLETON);
        binder.bind(SplashCtrl.class).in(Scopes.SINGLETON);
        binder.bind(WaitingRoomCtrl.class).in(Scopes.SINGLETON);
        binder.bind(QuestionTypeAComponentCtrl.class).in(Scopes.SINGLETON);
        // other

    }
}
