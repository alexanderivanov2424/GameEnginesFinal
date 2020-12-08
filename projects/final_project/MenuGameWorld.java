package projects.final_project;

import engine.UIToolKit.UIViewport;
import engine.game.GameObject;
import engine.game.GameWorld;
import engine.game.components.CameraComponent;
import engine.game.systems.CollisionSystem;
import engine.game.tileSystem.TileMap;
import engine.support.Vec2d;
import projects.final_project.levels.tileMaps.WorldTileMap;

public class MenuGameWorld {

    public static final int LAYER = CollisionSystem.CollisionMask.layer0;

    private GameWorld gameWorld;
    private UIViewport viewport;
    private GameObject player;

    public MenuGameWorld(GameWorld gameWorld, UIViewport viewport) {
        this.gameWorld = gameWorld;
        this.viewport = viewport;

        gameWorld.linkViewport(0, this.viewport);
    }

    public void init() {
        this.player = new GameObject(this.gameWorld);
        player.addComponent(new CameraComponent(0, new Vec2d(0,0)));
        this.gameWorld.addGameObject(player);

        TileMap worldTileMap = WorldTileMap.createTileMap();


        setTerrain(worldTileMap);
        worldTileMap.addTilesToGameWorld(this.gameWorld, 0, 2, LAYER,LAYER);
        addGameObjects(gameWorld);

        this.player.getTransform().position = new Vec2d(10,10);
    }

    public void addGameObjects(GameWorld gameWorld){
        NaturalElements.placeTree(gameWorld, 1, new Vec2d(2,8));
        NaturalElements.placeChicken(gameWorld, 1, new Vec2d(1,9));
        NaturalElements.placeTree(gameWorld, 1, new Vec2d(18,16));

        NaturalElements.placeBush(gameWorld, 1, new Vec2d(16.5,8.5), 2);
        NaturalElements.placeRockFlowers(gameWorld, 1, new Vec2d(10,14), 2);
        NaturalElements.placeStump(gameWorld, 1, new Vec2d(2,15));
    }

    public void setTerrain(TileMap tileMap){
        int[][] tiles_int = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 1, 3, 0, 1},
                {0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                {1, 2, 2, 0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0},
        };

        int[][] heights = new int[][]{
                {1, 1, 2, 2, 2, 2, 2, 2, 2, 1, 0},
                {1, 1, 2, 2, 2, 2, 2, 2, 2, 1, 0},
                {1, 1, 2, 2, 2, 2, 2, 2, 2, 1, 1},
                {1, 1, 1, 1, 1, 2, 2, 1, 1, 1, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        };

        String[] index = new String[]{"grass", "wall", "stairsV", "caveDoor"};
        String[][] tiles = new String[tiles_int.length][tiles_int[0].length];
        for(int i = 0; i < tiles_int.length; i++){
            for(int j = 0; j < tiles_int[0].length; j++){
                tiles[i][j] = index[tiles_int[i][j]];
            }
        }

        tileMap.setTiles(tiles);
        tileMap.setHeights(heights);
        tileMap.setExteriorTile("grass",1);
    }
}
