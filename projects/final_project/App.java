package projects.final_project;

import engine.Application;
import engine.Screen;
import engine.UIToolKit.UIButton;
import engine.UIToolKit.UIRect;
import engine.UIToolKit.UIText;
import engine.UIToolKit.UIViewport;
import engine.game.GameWorld;
import engine.support.Vec2d;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class App extends Application {

  private final Color colorMain = Color.color(.8, .35, .35);
  private final Color colorBorder = Color.color(.4, 0, .15);
  private final Color colorBackground = Color.color(.6, .35, .35);

  private final Font fontLarge = Font.font("Ariel", FontWeight.BOLD, 50);
  private final Font fontNormal = Font.font("Ariel", FontWeight.NORMAL, 30);

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
    Screen mainMenu = new Screen();

    createMainMenu(mainMenu);


    this.setCurrentScreen("mainMenu");
  }

  private void createMainMenu(Screen mainMenu){

  }


  private void createGameScreen(Screen gameScreen, GameWorld gameWorld, long seed){

  }

}
