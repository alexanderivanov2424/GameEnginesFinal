package projects.final_project;

import engine.Application;
import engine.Screen;
import engine.UIToolKit.UIButton;
import engine.UIToolKit.UIRect;
import engine.UIToolKit.UIText;
import engine.UIToolKit.UIViewport;
import engine.game.GameWorld;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class App extends Application {

  private final Color colorMain = Color.color(.2, .7, .35);
  private final Color colorBorder = Color.color(.4, 0, .15);
  private final Color colorBackground = Color.color(.5, .9, .8);

  private final Font fontLarge = Font.font("Ariel", FontWeight.BOLD, 50);
  private final Font fontNormal = Font.font("Ariel", FontWeight.NORMAL, 30);
  private final Font fontSmall = Font.font("Ariel", FontWeight.NORMAL, 15);
  private final Font fontHP = Font.font("Ariel", FontWeight.BOLD, 30);


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
    Screen controlsScreen = new Screen();
    Screen creditsScreen = new Screen();
    Screen gameScreen = new Screen();

    createMainMenu(mainMenu);
    createControlsScreen(controlsScreen);
    createCreditsScreen(creditsScreen);
    createGameScreen(gameScreen);

    this.setCurrentScreen("mainMenu");
  }

  private void createMainMenu(Screen mainMenu){
    mainMenu.addUIElement(new UIRect(new Vec2d(0,0), this.originalStageSize, colorBackground));
    mainMenu.addUIElement(new UIText(new Vec2d(100,80), new Vec2d(400, 50),"S L I P P Y ' s   D E M I S E'",
            colorBorder, fontLarge));

    UIButton startButton = new UIButton(new Vec2d(100,120), new Vec2d(250,50), colorMain, colorBorder);
    startButton.setOnMouseClicked(() -> {
      this.setCurrentScreen("gameScreen");
    });
    startButton.addChild(new UIText(new Vec2d(10,30), new Vec2d(400, 50),"Start", colorBorder, fontNormal));
    mainMenu.addUIElement(startButton);

    UIButton controlsButton = new UIButton(new Vec2d(100,200), new Vec2d(250,50), colorMain, colorBorder);
    controlsButton.setOnMouseClicked(() -> {
      this.setCurrentScreen("controlsScreen");
    });
    controlsButton.addChild(new UIText(new Vec2d(10,30), new Vec2d(400, 50),"Controls", colorBorder, fontNormal));
    mainMenu.addUIElement(controlsButton);

    UIButton creditsButton = new UIButton(new Vec2d(100,280), new Vec2d(250,50), colorMain, colorBorder);
    creditsButton.setOnMouseClicked(() -> {
      this.setCurrentScreen("creditsScreen");
    });
    creditsButton.addChild(new UIText(new Vec2d(10,30), new Vec2d(400, 50),"Credits", colorBorder, fontNormal));
    mainMenu.addUIElement(creditsButton);

    this.addScreen(mainMenu, "mainMenu");
  }

  private void createControlsScreen(Screen controlsScreen){
    controlsScreen.addUIElement(new UIRect(new Vec2d(0,0), this.originalStageSize, colorBackground));
    controlsScreen.addUIElement(new UIText(new Vec2d(100,80), new Vec2d(400, 50),"Controls",
            colorBorder, fontLarge));
    String text =
            "WASD - to move Up, Left, Down, and Right\n" +
            "Space Bar - to attack\n" +
            "E - to talk\n" +
            "I/J or Arrow Keys - to select dialog options";
    controlsScreen.addUIElement(new UIText(new Vec2d(100,120), new Vec2d(400, 500),text, Color.BLACK, fontNormal));


    UIButton returnButton = new UIButton(new Vec2d(100,460), new Vec2d(130,50), colorMain, colorBorder);
    returnButton.setOnMouseClicked(() -> {
      this.setCurrentScreen("mainMenu");
    });
    returnButton.addChild(new UIText(new Vec2d(10,30), new Vec2d(400, 50),"Back", colorBorder, fontNormal));

    controlsScreen.addUIElement(returnButton);
    this.addScreen(controlsScreen, "controlsScreen");
  }

  private void createCreditsScreen(Screen creditsScreen){
    creditsScreen.addUIElement(new UIRect(new Vec2d(0,0), this.originalStageSize, colorBackground));
    creditsScreen.addUIElement(new UIText(new Vec2d(100,80), new Vec2d(400, 50),"Credits",
            colorBorder, fontLarge));
    String text = "Universal-LPC-spritesheet : https://github.com/jrconway3/Universal-LPC-spritesheet\n" +
            " - Credit goes to authors listed in credits directory under these licences\n" +
            " - CC-BY-SA 3.0 licence http://creativecommons.org/licenses/by-sa/3.0/ \n" +
            " - GNU GPL 3.0 licence http://www.gnu.org/licenses/gpl-3.0.html \n" +
            "\n" +
            "Thanks to Ivan Voirol for creating the RPG tilemap\n" +
            " - OpenGameArt Link: https://opengameart.org/comment/31378\n" +
            " - CC-BY-SA 3.0 licence https://creativecommons.org/licenses/by/3.0/\n" +
            " - GNU GPL 3.0 licence http://www.gnu.org/licenses/gpl-3.0.html\n" +
            " - GNU GPL 2.0 licence http://www.gnu.org/licenses/old-licenses/gpl-2.0.html\n" +
            "\n" +
            "Thanks to Davias for the Simulation RPG Tsukuru spritesheet for cave levels\n" +
            " - Link: https://www.spriters-resource.com/playstation/simrpgtsu/sheet/38986/\n" +
            "\n" +
            "Special thanks to friends and family for play testing\n" +
            "Special thanks to the TA's of CSCI1950N for debugging help and general guidance\n";
    creditsScreen.addUIElement(new UIText(new Vec2d(100,120), new Vec2d(400, 500),text, Color.BLACK, fontSmall));


    UIButton returnButton = new UIButton(new Vec2d(100,460), new Vec2d(130,50), colorMain, colorBorder);
    returnButton.setOnMouseClicked(() -> {
      this.setCurrentScreen("mainMenu");
    });
    returnButton.addChild(new UIText(new Vec2d(10,30), new Vec2d(400, 50),"Back", colorBorder, fontNormal));

    creditsScreen.addUIElement(returnButton);
    this.addScreen(creditsScreen, "creditsScreen");
  }

  private void createGameScreen(Screen gameScreen){
    GameWorld gameWorld = new GameWorld();

    gameScreen.addUIElement(new UIRect(new Vec2d(0,0),
            new Vec2d(this.currentStageSize.x,this.currentStageSize.y), colorBackground));


    UIViewport viewport = new UIViewport(new Vec2d(0,0),
            new Vec2d(this.originalStageSize.x, this.originalStageSize.y),
            gameWorld, new Vec2d(0,0), 50, true);
    gameScreen.addUIElement(viewport);

    FinalGame finalGame = new FinalGame(gameWorld, viewport);
    finalGame.init();

    /**
     * Other Game Screen UI below
     */

    //Health bar UI
    gameScreen.addUIElement(new UIText(new Vec2d(10,30), new Vec2d(400, 50),"HP",
            Color.BLACK, fontHP));



    this.addScreen(gameScreen, "gameScreen");

  }

  private static class HPRect extends UIRect {

    private double maxSize = size.x;

    public HPRect(Vec2d position, Vec2d size, Color color) {
      super(position, size, color);
    }

    @Override
    public void onDraw(GraphicsContext g) {
      Vec2d pos = this.getOffset();

      //this.size.x = maxSize * player.

      g.setFill(this.color);
      g.fillRect(pos.x,pos.y,this.size.x,this.size.y);
      super.onDraw(g);
    }
  }

}
