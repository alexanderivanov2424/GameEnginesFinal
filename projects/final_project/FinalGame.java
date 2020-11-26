package projects.final_project;

import engine.UIToolKit.UIViewport;
import engine.game.GameObject;
import engine.game.GameWorld;
import engine.game.Region;
import engine.game.TileSystem.TileMap;
import engine.game.systems.CollisionSystem;
import engine.support.Vec2d;
import projects.final_project.levels.CaveLevel;
import projects.final_project.levels.tileMaps.*;
import projects.final_project.levels.TutorialLevel;

import java.io.File;

public class FinalGame {

    public static final int TILE_LAYER = CollisionSystem.CollisionMask.layer0;
    public static final int TILE_MASK = CollisionSystem.CollisionMask.NONE;

    public static final int PLAYER_LAYER = CollisionSystem.CollisionMask.layer3;
    public static final int PLAYER_MASK = CollisionSystem.CollisionMask.layer0 | CollisionSystem.CollisionMask.layer1;

    public static final int OBJECT_LAYER = CollisionSystem.CollisionMask.layer1;
    public static final int OBJECT_MASK = CollisionSystem.CollisionMask.layer0 | CollisionSystem.CollisionMask.layer1 | CollisionSystem.CollisionMask.layer3;

    private GameWorld gameWorld;
    private UIViewport viewport;

    public FinalGame(GameWorld gameWorld, UIViewport viewport) {
        this.gameWorld = gameWorld;
        this.viewport = viewport;


        gameWorld.linkViewport(0, this.viewport);
    }

    public void init() {
        GameObject player = Player.createPlayer(this.gameWorld,new Vec2d(5,30));
        this.gameWorld.addGameObject(player);


        /*TileMap tileMap = LevelTileMaps.createTileMap();
        LevelTileMaps.setTestingLevel(tileMap);*/
        TileMap caveTileMap = CaveTileMap.createTileMap();
        TileMap worldTileMap = WorldTileMap.createTileMap();


        //create cave Region
        Region cave = new Region();
        gameWorld.loadRegion(cave);
        CaveLevel.setCaveLevel(caveTileMap, player, gameWorld);
        caveTileMap.addTilesToGameWorld(this.gameWorld, 0, 2, TILE_LAYER, TILE_MASK);
        gameWorld.unloadRegion();

        //create tutorial Region
        Region tutorial1 = new Region();
        gameWorld.loadRegion(tutorial1);
        TutorialLevel.setTutorialLevel1(worldTileMap);
        worldTileMap.addTilesToGameWorld(this.gameWorld, 0, 2, TILE_LAYER, TILE_MASK);
        TutorialLevel.addGameObjects(gameWorld);
        gameWorld.unloadRegion();

        //TODO give the viewport proper limiting instead of the camera component.
        // Makes more sense given that we are loading and unloading regions.
        gameWorld.loadRegion(tutorial1);
    }

    /*
     * converts a string into the path to the corresponding png file.
     */
    public static String getSpritePath(String name){
        File folder = new File("file:.\\projects\\final_project\\assets\\");
        File sprite = new File(folder, name.concat(".png"));
        return sprite.toString();
    }

}
