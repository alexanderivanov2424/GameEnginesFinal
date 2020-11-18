package projects.nin;

import engine.Application;
import engine.Screen;
import engine.UIToolKit.UIButton;
import engine.UIToolKit.UIRect;
import engine.UIToolKit.UIText;
import engine.UIToolKit.UIViewport;
import engine.game.GameWorld;
import engine.game.systems.SystemFlag;
import engine.support.Vec2d;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class App extends Application {

  private final Color colorMain = Color.color(.75,.75,.75);
  private final Color colorBorder = Color.color(.25,.25,.25);
  private final Color colorBackground = Color.color(.5, .5, .5);

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
    mainMenu.addUIElement(new UIText(new Vec2d(100,80), new Vec2d(400, 50),"NIN II",
            colorBorder, fontLarge));

    UIButton startButton = new UIButton(new Vec2d(100,100), new Vec2d(400,50), colorMain, colorBorder);
    startButton.setOnMouseClicked(() -> {
      this.setCurrentScreen("gameScreen");
    });
    startButton.addChild(new UIText(new Vec2d(10,30), new Vec2d(400, 50),"START", colorBorder, fontNormal));

    mainMenu.addUIElement(startButton);
//
//    mainMenu.addUIElement(new UIText(new Vec2d(600,100), new Vec2d(400, 50),
//            "WASD to move\nClick and SPACE to shoot\nMouse to aim\nScroll to zoom" +
//                    "\n\nFind the door at the end \nof each level to proceed",
//            colorBorder, fontNormal));

    this.addScreen(mainMenu, "mainMenu");
  }


  private NinGame createGameScreen(Screen gameScreen){
    GameWorld gameWorld = new GameWorld();

    gameScreen.addUIElement(new UIRect(new Vec2d(0,0),
            new Vec2d(this.currentStageSize.x,this.currentStageSize.y), colorBackground));


    UIViewport viewport = new UIViewport(new Vec2d(0,0),
            new Vec2d(this.originalStageSize.x, this.originalStageSize.y),
            gameWorld, new Vec2d(0,0),false, 50);
    gameScreen.addUIElement(viewport);

    NinGame ninGame = new NinGame(gameWorld, viewport);
    ninGame.init();

    UIButton restartButton = new UIButton(new Vec2d(10,10), new Vec2d(150,50), colorMain, colorBorder);
    restartButton.setOnMouseClicked(() -> {
      ninGame.restart();
    });
    restartButton.addChild(new UIText(new Vec2d(10,35), new Vec2d(150, 50),"RESTART",
            colorBorder, fontNormal));
    gameScreen.addUIElement(restartButton);

    UIButton returnButton = new UIButton(new Vec2d(10,70), new Vec2d(150,50), colorMain, colorBorder);
    returnButton.setOnMouseClicked(() -> {
      this.setCurrentScreen("mainMenu");
    });
    returnButton.addChild(new UIText(new Vec2d(10,35), new Vec2d(150, 50),"MENU",
            colorBorder, fontNormal));
    gameScreen.addUIElement(returnButton);

    UIButton saveButton = new UIButton(new Vec2d(10,130), new Vec2d(150,50), colorMain, colorBorder);
    saveButton.setOnMouseClicked(() -> {
      ninGame.saveToXML("save");
    });
    saveButton.addChild(new UIText(new Vec2d(10,35), new Vec2d(150, 50),"SAVE",
            colorBorder, fontNormal));
    gameScreen.addUIElement(saveButton);

    UIButton loadButton = new UIButton(new Vec2d(10,190), new Vec2d(150,50), colorMain, colorBorder);
    loadButton.setOnMouseClicked(() -> {
      ninGame.loadFromXML("save");
    });
    loadButton.addChild(new UIText(new Vec2d(10,35), new Vec2d(150, 50),"LOAD",
            colorBorder, fontNormal));
    gameScreen.addUIElement(loadButton);

    this.addScreen(gameScreen, "gameScreen");

    return ninGame;
  }

}
