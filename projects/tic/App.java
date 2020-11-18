package projects.tic;

import engine.Application;
import engine.support.Vec2d;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


/**
 * This is your Tic-Tac-Toe top-level, App class.
 * This class will contain every other object in your game.
 */
public class App extends Application {

  public App(String title) {
    super(title);
  }

  public App(String title, Vec2d windowSize, boolean debugMode, boolean fullscreen) {
    super(title, windowSize, debugMode, fullscreen);
  }

  /**
   * Called when the app is starting up.
   */
  @Override
  protected void onStartup() {
    super.onStartup();
    TicGame.addGameToApplication(this);
  }

  @Override
  protected void onKeyPressed(KeyEvent e) {
    super.onKeyPressed(e);
    if(e.getCode() == KeyCode.ESCAPE){
      this.shutdown();
    }
  }
}
