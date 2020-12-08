package projects.final_project;

import engine.Application;
import engine.Screen;
import engine.UIToolKit.*;
import engine.game.GameObject;
import engine.game.GameWorld;
import engine.game.components.HealthComponent;
import engine.game.components.ValueComponent;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class App extends Application {

  private final Color colorMain = Color.color(.2, .7, .35);
  private final Color colorBorder = Color.color(.4, 0, .15);
  private final Color colorBackground = Color.color(.5, .9, .8);

  private final Font fontLarge = Font.font("Ariel", FontWeight.BOLD, 50);
  private final Font fontNormal = Font.font("Ariel", FontWeight.NORMAL, 30);
  private final Font fontSmall = Font.font("Ariel", FontWeight.NORMAL, 12);
  private final Font fontSCORE = Font.font("Ariel", FontWeight.BOLD, 30);


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

    mainMenu.addUIElement(new UIText(new Vec2d(100,80), new Vec2d(400, 50),"S L I P P Y ' S   D E M I S E",

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
            "I/J or Arrow Keys - to change dialog options\n" +
            "Enter - to select dialog option";
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
            " - Credit goes to authors listed in credits directory, CC-BY-SA 3.0 and GNU GPL 3.0 licences\n" +
            "Thanks to Ivan Voirol for creating the RPG tilemap, \n" +
            " - OpenGameArt Link: https://opengameart.org/comment/31378, CC-BY-SA 3.0, GNU GPL 3.0, and GNU GPL 2.0 licences\n" +
            "Thanks to Davias for the Simulation RPG Tsukuru spritesheet for cave levels\n" +
            " - Link: https://www.spriters-resource.com/playstation/simrpgtsu/sheet/38986/\n" +
            "Thanks to Butch for the HUD sprite: https://opengameart.org/users/buch\n" +
            "Thanks to Bonsaiheldin for the interior tile set and potion sprites: \n" +
            " - https://opengameart.org/content/interior-tileset-16x16\n" +
            " - https://opengameart.org/content/rpg-potions-16x16\n" +
            "Thanks to Joe Williamson roguelike item sprites: https://opengameart.org/content/roguelikerpg-items\n" +
            "\n" +
            "Special thanks to friends and family for play testing\n" +
            "Special thanks to the TA's of CSCI1950N for debugging help and general guidance\n" +
            "\n" +
            "Licence Links:\n" +
            " - CC-BY-SA 3.0 licence https://creativecommons.org/licenses/by/3.0/\n" +
            " - GNU GPL 3.0 licence http://www.gnu.org/licenses/gpl-3.0.html\n" +
            " - GNU GPL 2.0 licence http://www.gnu.org/licenses/old-licenses/gpl-2.0.html\n" +
            "\n";
    creditsScreen.addUIElement(new UIText(new Vec2d(100,120), new Vec2d(400, 500),text, Color.BLACK, fontSmall));


    UIButton returnButton = new UIButton(new Vec2d(100,460), new Vec2d(130,50), colorMain, colorBorder);
    returnButton.setOnMouseClicked(() -> {
      this.setCurrentScreen("mainMenu");
    });
    returnButton.addChild(new UIText(new Vec2d(10,30), new Vec2d(400, 50),"Back", colorBorder, fontNormal));

    creditsScreen.addUIElement(returnButton);
    this.addScreen(creditsScreen, "creditsScreen");
  }

  public void createEndScreen(Screen endScreen, GameObject player){
    ValueComponent score = (ValueComponent)player.getComponent("ValueComponent");

    endScreen.addUIElement(new UIRect(new Vec2d(0,0), this.originalStageSize, colorBackground));
    endScreen.addUIElement(new UIText(new Vec2d(100,80), new Vec2d(400, 50),"Thanks for Playing",
            colorBorder, fontLarge));
    String text = "After defeating Slippy, our hero brought peace to the town and was rewarded handsomely.";
    endScreen.addUIElement(new UIText(new Vec2d(100,120), new Vec2d(400, 500),text, Color.BLACK, fontSmall));

    endScreen.addUIElement(new UIText(new Vec2d(100,150), new Vec2d(400, 50),"" +
            "Final Score",
            colorBorder, fontLarge));
    endScreen.addUIElement(new UIText(new Vec2d(100,180), new Vec2d(400, 50),"" +
            (int)score.value,
            colorBorder, fontNormal));
    UIButton returnButton = new UIButton(new Vec2d(100,460), new Vec2d(130,50), colorMain, colorBorder);
    returnButton.setOnMouseClicked(() -> {
      this.setCurrentScreen("mainMenu");
    });
    returnButton.addChild(new UIText(new Vec2d(10,30), new Vec2d(400, 50),"Back", colorBorder, fontNormal));

    endScreen.addUIElement(returnButton);
    this.addScreen(endScreen, "endScreen");
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
    //HUD
    gameScreen.addUIElement(new HUD(new Vec2d(8,6), new Vec2d(250, 250 * 32.0/100.0), finalGame.getPlayer()));

    //SCORE
    UIRect scorebox = new UIRect(new Vec2d(830,6), new Vec2d(120, 40),
            Color.rgb(90,90,90,0.9));
    scorebox.addChild(new Score(new Vec2d(60,30), new Vec2d(120, 30),
            true, "0", Color.WHITESMOKE, fontSCORE, finalGame.getPlayer()));
    gameScreen.addUIElement(scorebox);

//

    this.addScreen(gameScreen, "gameScreen");

  }

  private static class HUD extends UIElement {

    private GameObject player;

    private Image HUD_image;
    private Image weapon_image;


    public HUD(Vec2d position, Vec2d size, GameObject player) {
      super(position, size);
      this.player = player;
      this.HUD_image = new Image(FinalGame.getSpritePath("HUD"));
      this.weapon_image = new Image(FinalGame.getSpritePath("roguelikeitems"));

    }

    @Override
    public void onDraw(GraphicsContext g) {
      Vec2d pos = this.getOffset();

      g.save();
      g.setImageSmoothing(false);

      HealthComponent healthComponent = (HealthComponent)player.getComponent("HealthComponent");
      Player.PlayerComponent playerComponent = (Player.PlayerComponent)player.getComponent("PlayerComponent");
      int weapon = playerComponent.getCurrentWeapon();

      double crop_width = 51.0 * healthComponent.getHealthRatio();
      double image_ratio = 32 / 100.0; // y over x
      double W = this.size.x;
      double H = image_ratio * W;

      g.drawImage(this.HUD_image, 100, 0, crop_width, 8,
              pos.x + 37 / 100.0 * W, pos.y, crop_width / 100.0 * W, 8 / 32.0 * H);

      g.drawImage(this.HUD_image, 0, 0, 100, 32,
              pos.x, pos.y, W, H);

      Vec2d crop_start = new Vec2d(0,0);
      if(weapon == 0){
        crop_start = new Vec2d(16,112);
      } else {
        crop_start = new Vec2d(64,112);
      }
      g.drawImage(this.weapon_image, crop_start.x, crop_start.y, 16, 16,
              pos.x + 9/ 100.0 * W, pos.y + 9/ 100.0 * W, 14/100.0*W, 14/100.0*W);


      g.restore();
      super.onDraw(g);
    }
  }

  private static class Score extends UIText {

    private GameObject player;

    public Score(Vec2d position, Vec2d size, boolean centered, String text, Color textColor, Font font, GameObject player) {
      super(position, size, centered, text, textColor, font);
      this.player = player;
    }

    @Override
    public void onDraw(GraphicsContext g) {
      ValueComponent score = (ValueComponent)player.getComponent("ValueComponent");
      this.text = "" + (int)score.value;
      super.onDraw(g);
    }
  }

}
