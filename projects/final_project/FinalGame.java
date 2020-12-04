package projects.final_project;

import engine.UIToolKit.UIViewport;
import engine.game.GameObject;
import engine.game.GameWorld;
import engine.game.Region;
import engine.game.tileSystem.TileMap;
import engine.game.systems.CollisionSystem;
import engine.support.Vec2d;
import projects.final_project.levels.*;
import projects.final_project.levels.tileMaps.*;

import java.io.File;

public class FinalGame {

    public static final int TILE_LAYER = CollisionSystem.CollisionMask.layer0;
    public static final int TILE_MASK = CollisionSystem.CollisionMask.NONE;

    public static final int PLAYER_LAYER = CollisionSystem.CollisionMask.layer3;
    public static final int PLAYER_MASK = CollisionSystem.CollisionMask.layer0 | CollisionSystem.CollisionMask.layer1;

    public static final int OBJECT_LAYER = CollisionSystem.CollisionMask.layer1;
    public static final int OBJECT_MASK = CollisionSystem.CollisionMask.layer0 | CollisionSystem.CollisionMask.layer1 | CollisionSystem.CollisionMask.layer3;

    public static final int ATTACK_LAYER = CollisionSystem.CollisionMask.layer4;
    public static final int ATTACK_MASK = CollisionSystem.CollisionMask.layer4;

    public static final int TALK_LAYER = CollisionSystem.CollisionMask.layer5;
    public static final int TALK_MASK = CollisionSystem.CollisionMask.layer5;

    private GameWorld gameWorld;
    private UIViewport viewport;
    private GameObject player;

    public FinalGame(GameWorld gameWorld, UIViewport viewport) {
        this.gameWorld = gameWorld;
        this.viewport = viewport;


        gameWorld.linkViewport(0, this.viewport);
    }

    public void init() {
        player = Player.createPlayer(this.gameWorld,new Vec2d(3,37));
        this.gameWorld.addGameObject(player);

        //this.gameWorld.getRoot().addComponent(new ShakeEffect(10,5));

        /*TileMap tileMap = LevelTileMaps.createTileMap();
        LevelTileMaps.setTestingLevel(tileMap);*/
        TileMap caveTileMap = CaveTileMap.createTileMap();
        TileMap worldTileMap = WorldTileMap.createTileMap();


        //create cave Region
        Levels.cave = new Region();
        gameWorld.loadRegion(Levels.cave);
        Cave1.setCaveLevel(caveTileMap);
        caveTileMap.addTilesToGameWorld(this.gameWorld, 0, 2, TILE_LAYER, TILE_MASK);
        Cave1.addGameObjects(gameWorld);
        gameWorld.unloadRegion();

        //create area1 Region
        Levels.area1 = new Region();
        gameWorld.loadRegion(Levels.area1);
        Area1.setTiles(worldTileMap);
        worldTileMap.addTilesToGameWorld(this.gameWorld, 0, 2, TILE_LAYER, TILE_MASK);
        Area1.addGameObjects(gameWorld);
        gameWorld.unloadRegion();

        //create area2 Region
        Levels.area2 = new Region();
        gameWorld.loadRegion(Levels.area2);
        Area2.setTiles(worldTileMap);
        worldTileMap.addTilesToGameWorld(this.gameWorld, 0, 2, TILE_LAYER, TILE_MASK);
        Area2.addGameObjects(gameWorld);
        gameWorld.unloadRegion();

        //create area2 Region
        Levels.house = new Region();
        gameWorld.loadRegion(Levels.house);
        House1.addGameObjects(gameWorld);
        gameWorld.unloadRegion();

        gameWorld.processQueues();

        player.getTransform().position = new Vec2d(35,10);
        gameWorld.loadRegion(Levels.area2);
    }

    /*
     * converts a string into the path to the corresponding png file.
     */
    public static String getSpritePath(String name){
        File folder = new File("file:.\\projects\\final_project\\assets\\");
        File sprite = new File(folder, name.concat(".png"));
        return sprite.toString();
    }

    public GameObject getPlayer() {
        return player;
    }
}
