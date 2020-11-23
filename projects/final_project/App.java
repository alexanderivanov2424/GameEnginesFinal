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
    Screen gameScreen = new Screen();

    createMainMenu(mainMenu);
    createGameScreen(gameScreen);

    this.setCurrentScreen("mainMenu");
  }

  private void createMainMenu(Screen mainMenu){
    mainMenu.addUIElement(new UIRect(new Vec2d(0,0), this.originalStageSize, colorBackground));
    mainMenu.addUIElement(new UIText(new Vec2d(100,80), new Vec2d(400, 50),"SLIPPY TIME",
            colorBorder, fontLarge));

    UIButton startButton = new UIButton(new Vec2d(100,100), new Vec2d(400,50), colorMain, colorBorder);
    startButton.setOnMouseClicked(() -> {
      this.setCurrentScreen("gameScreen");
    });
    startButton.addChild(new UIText(new Vec2d(10,30), new Vec2d(400, 50),"START", colorBorder, fontNormal));

    mainMenu.addUIElement(startButton);

    this.addScreen(mainMenu, "mainMenu");
  }


  private void createGameScreen(Screen gameScreen){
    GameWorld gameWorld = new GameWorld();

    gameScreen.addUIElement(new UIRect(new Vec2d(0,0),
            new Vec2d(this.currentStageSize.x,this.currentStageSize.y), colorBackground));


    UIViewport viewport = new UIViewport(new Vec2d(0,0),
            new Vec2d(this.originalStageSize.x, this.originalStageSize.y),
            gameWorld, new Vec2d(0,0),false, 50);
    gameScreen.addUIElement(viewport);

    FinalGame finalGame = new FinalGame(gameWorld, viewport);
    finalGame.init();

    /**
     * Other Game Screen UI below
     */

    this.addScreen(gameScreen, "gameScreen");

  }

}
