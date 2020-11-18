package projects.alc;

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

  private final Color colorMain = Color.color(.35, .15, 1);
  private final Color colorBorder = Color.color(.15, 0, .6);
  private final Color colorBackground = Color.color(.6, .5, 1);

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

    GameWorld gameWorld = new GameWorld();

    createMainMenu(mainMenu);
    createGameScreen(gameScreen, gameWorld);

    createGameWorld(gameWorld);
  }

  private void createMainMenu(Screen mainMenu){
    mainMenu.addUIElement(new UIRect(new Vec2d(0,0), this.originalStageSize, colorBackground));
    mainMenu.addUIElement(new UIText(new Vec2d(100,80), new Vec2d(400, 50),"ALCHEMY",
            colorBorder, fontLarge));

    UIButton startButton = new UIButton(new Vec2d(100,100), new Vec2d(400,50), colorMain, colorBorder);
    startButton.setOnMouseClicked(() -> {this.setCurrentScreen("gameScreen");});
    startButton.addChild(new UIText(new Vec2d(10,25), new Vec2d(400, 50),"START",
            colorBorder, fontNormal));
    mainMenu.addUIElement(startButton);

    this.addScreen(mainMenu, "mainMenu");
  }

  private void createGameScreen(Screen gameScreen, GameWorld gameWorld){
    gameScreen.addUIElement(new UIRect(new Vec2d(5,5),
            new Vec2d(this.currentStageSize.x - 10,this.currentStageSize.y - 10), colorBorder));

    UIViewport viewport = new UIViewport(new Vec2d(10,10),
            new Vec2d(this.currentStageSize.x - 20,this.currentStageSize.y - 20),
            gameWorld, new Vec2d(0,0),true, 50);
    gameScreen.addUIElement(viewport);

    this.addScreen(gameScreen, "gameScreen");
  }

  private void createGameWorld(GameWorld gameWorld){
    AlchemyGame game = new AlchemyGame(gameWorld);
    game.init();
  }
}
