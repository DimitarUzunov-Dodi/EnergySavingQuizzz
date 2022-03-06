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

import static com.google.inject.Guice.createInjector;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;


import client.scenes.*;
import com.google.inject.Injector;

import client.scenes.SettingsCtrl;
import client.scenes.SplashCtrl;
import client.scenes.ServerLeaderboardCtrl;
import com.google.inject.Injector;

import client.scenes.MainCtrl;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    public static void main(String[] args) throws URISyntaxException, IOException {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        var mainCtrl = INJECTOR.getInstance(MainCtrl.class);


       
        var gamePage = FXML.load(GamePageController.class, "client", "scenes", "GameScreen.fxml");
        var dummyPage = FXML.load(DummyController.class, "client", "scenes", "DummyScene.fxml");
      

        var splash = FXML.load(SplashCtrl.class, "client", "scenes", "SplashScreen.fxml");
        var settings = FXML.load(SettingsCtrl.class, "client", "scenes", "SettingsScreen.fxml");
        var serverLeaderboard = FXML.load(ServerLeaderboardCtrl.class, "client", "scenes", "ServerLeaderboard.fxml");

        mainCtrl.initialize(primaryStage, splash, settings, serverLeaderboard, gamePage, dummyPage);

    }
}