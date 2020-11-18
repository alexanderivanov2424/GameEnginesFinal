package projects.final_project;

import engine.UIToolKit.UIViewport;
import engine.game.GameObject;
import engine.game.GameWorld;
import engine.game.TileSystem.SpriteTileVariant;
import engine.game.TileSystem.Tile;
import engine.game.TileSystem.TileMap;
import engine.game.collisionShapes.AABShape;
import engine.game.components.*;
import engine.game.systems.CollisionSystem;
import engine.support.Vec2d;

import java.io.File;
import java.util.ArrayList;

public class Game {

    private static final int PLAYER_LAYER = CollisionSystem.CollisionMask.layer3;
    private static final int PLAYER_MASK = CollisionSystem.CollisionMask.layer0 | CollisionSystem.CollisionMask.layer1;

    private GameWorld gameWorld;
    private UIViewport viewport;

    public Game(GameWorld gameWorld, UIViewport viewport) {
        this.gameWorld = gameWorld;
        this.viewport = viewport;
    }

    public void init() {
        this.gameWorld.addGameObject(createPlayer(this.gameWorld,new Vec2d(0,0)));


    }

    /*
     * converts a string into the path to the corresponding png file.
     */
    public static String getSpritePath(String name){
        File folder = new File("file:.\\projects\\final_project\\assets\\");
        File sprite = new File(folder, name.concat(".png"));
        return sprite.toString();
    }


    //creates player
    public static GameObject createPlayer(GameWorld gameWorld, Vec2d pos){
        GameObject player = new GameObject(gameWorld, 3);
        player.addComponent(new WASDMovementComponent(player,10));
        player.addComponent(new CameraComponent(player, 0));

        player.addComponent(new SpriteAnimationComponent(player, Game.getSpritePath("player"),
                new Vec2d(0,0), new Vec2d(2,2), 5, new Vec2d(0,0), new Vec2d(32,32), .1));//normal animation

        player.addComponent(new CollisionComponent(player, new AABShape(new Vec2d(.3,.25),new Vec2d(1.4,1.75)),
                false, true, PLAYER_LAYER, PLAYER_MASK));

        player.getTransform().position = pos;
        player.getTransform().size = new Vec2d(1.4,1.75);
        return player;
    }
}
