/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client;

<<<<<<< client/src/main/java/client/MyModule.java
import client.scenes.*;
=======


>>>>>>> client/src/main/java/client/MyModule.java
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;

<<<<<<< client/src/main/java/client/MyModule.java
=======
import client.scenes.MainCtrl;

>>>>>>> client/src/main/java/client/MyModule.java
public class MyModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(MainCtrl.class).in(Scopes.SINGLETON);
<<<<<<< client/src/main/java/client/MyModule.java
        binder.bind(GamePageController.class).in(Scopes.SINGLETON);
        binder.bind(DummyController.class).in(Scopes.SINGLETON);
=======
        binder.bind(SplashCtrl.class).in(Scopes.SINGLETON);
        binder.bind(SettingsCtrl.class).in(Scopes.SINGLETON);
        binder.bind(ServerLeaderboardCtrl.class).in(Scopes.SINGLETON);
>>>>>>> client/src/main/java/client/MyModule.java
    }
}