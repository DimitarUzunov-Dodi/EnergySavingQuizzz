# Status of our program after refactoring
reported by Raul on 23/03/22

## Overview
* The way scenes and controllers are loaded, instantiated and kept in memory has changed quite a bit.
The code became hard to maintain and objectively badly designed so a change was needed.
* Instead of having a huge number of objects initialized in `Main` and then passed around to `MainCtrl` and accessed via `MainCtrl` method calls, the program now relies more on the dependency injector.
The injector is created in `Main` and along with the primary Scene of the application it is passed to `MainCtrl`, which is then given the flow of control.
* From now on, every time something needs access (call a method, etc.) to another screen (Scene or SceneController) it can simply call `myFXML.getScene(...)` no matter if that page already loaded or not. This method is defined in `MyFXML`, which is a helper class that all controllers have access to via `SceneController`, an abstract class that is now the backbone of every scene controller.

## What's done
* Up until now I've re-implemented `Main`, `MainCtrl`, `MyFXML` (fxml utils) and `MyModule` (injector config stuff). I've also converted `SplashCtrl`, `MultiplayerCtrl` and `AdminCtrl` to use the new system to serve as examples and reference implementation. They are all fully commented.
* I kept parts of the old code as comments for reference.
* Bear in mind that the application is not meant to be fully working (or building) at the time of this commit!

## What must be done
* The only thing that remains to be done is to transition every controller to the new system.
I encourage (and by that I mean I really insist you do that) everyone to take the controllers they worked on previously and modify them to take advantage of the new system.
* This should be fairly quick and easy if you follow the guidelines and look at the examples I've made so far. You will find that in fact not a lot of stuff has to be changed.

## Transition guidelines
* All controllers extend `SceneController`.
* All scene controllers should be named `<scene_name>Ctrl` for consistency.
* All fxml files should be named `<scene_name>.java`. (must be the same <scene_name> as the controller)
* Only make attributes and methods `public` when they are needed/called from another class.
* Fxml elements should be referenced by declaring an `@FXML private foo;` attribute in the controller and an `fx:id="foo"` in the fxml resource. (pls no weird stuff like scene.lookup("#button"))
* Methods for fxml button actions and other such elements should be `@FXML private`.
* Methods and attributes that are only used internally should be `private`.
* Functions that load/refresh page content should be called in the public `show()` method.
* If you want to display another scene and pass control to it do `myFXML.showScene(<T>)` so that everything refreshes normally. (that's the new mainCtrl.showXXX())
* Utility classes should be kept static as mush as possible, otherwise add them to the @Inject constructor of your class if you need them.

You can analyze the classes I've already worked on and also look at the implementation of `MyFXML` an `MainCtrl` for more details as they are fully commented and documented.

Feel free to ask me anything or raise any issue if you find one. I will answer on a best-effort basis.

Have a great day :)