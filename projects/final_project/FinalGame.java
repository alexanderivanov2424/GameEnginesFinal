package projects.final_project;

import engine.UIToolKit.UIViewport;
import engine.game.GameObject;
import engine.game.GameWorld;
import engine.game.TileSystem.TileMap;
import engine.game.systems.CollisionSystem;
import engine.support.Vec2d;
import projects.final_project.levels.*;
import projects.final_project.levels.TileMaps.*;

import java.io.File;

public class FinalGame {

    public static final int TILE_LAYER = CollisionSystem.CollisionMask.layer0;
    public static final int TILE_MASK = CollisionSystem.CollisionMask.NONE;

    public static final int PLAYER_LAYER = CollisionSystem.CollisionMask.layer3;
    public static final int PLAYER_MASK = CollisionSystem.CollisionMask.layer0 | CollisionSystem.CollisionMask.layer1;

    private GameWorld gameWorld;
    private UIViewport viewport;

    public FinalGame(GameWorld gameWorld, UIViewport viewport) {
        this.gameWorld = gameWorld;
        this.viewport = viewport;


        gameWorld.linkViewport(0, this.viewport);
    }

    public void init() {
        GameObject player = Player.createPlayer(this.gameWorld,new Vec2d(3,3));
        this.gameWorld.addGameObject(player);


        /*TileMap tileMap = LevelTileMaps.createTileMap();
        LevelTileMaps.setTestingLevel(tileMap);*/
        TileMap tileMap = CaveTileMap.createCaveTileMap();
        LevelTileMaps.setCaveLevel(tileMap, player, gameWorld);
        tileMap.addTilesToGameWorld(this.gameWorld, 0, 2, TILE_LAYER, TILE_MASK);
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
