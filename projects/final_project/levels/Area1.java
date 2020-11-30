package projects.final_project.levels;

import engine.game.GameObject;
import engine.game.GameWorld;
import engine.game.tileSystem.TileMap;
import engine.game.collisionShapes.AABShape;
import engine.game.components.CollisionComponent;
import engine.game.systems.CollisionSystem;
import engine.support.Vec2d;
import projects.final_project.BackgroundMusic;
import projects.final_project.NaturalElements;
import projects.final_project.FinalGame;

public class Area1 {

    public static void setTiles(TileMap tileMap){
        int[][] tiles_int = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1},
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 2, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 1, 2, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        int[][] heights = new int[][]{
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1},
                {1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1},
                {1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1},
                {1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1},
                {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1},
                {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 1, 0, 0, 0, 0, 0},
                {1, 0, 0, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1},
                {1, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 0, 0, 0, 0, 1, 1},
                {1, 0, 0, 1, 1, 1, 1, 2, 2, 2, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1},
                {1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1},
                {1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                {1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                {1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        };

        String[] index = new String[]{"grass", "wall", "stairsV"};
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

    public static void addGameObjects(GameWorld gameWorld){
        //TODO need to add rendering order first
        NaturalElements.placeTree(gameWorld, 1, new Vec2d(11,31));
        NaturalElements.placeTree(gameWorld, 1, new Vec2d(10,38));
        NaturalElements.placeTree(gameWorld, 1, new Vec2d(22,33));
        NaturalElements.placeTree(gameWorld, 1, new Vec2d(5,9));
        NaturalElements.placeTree(gameWorld, 1, new Vec2d(8,10));
        NaturalElements.placeTree(gameWorld, 1, new Vec2d(27,23));
        NaturalElements.placeTree(gameWorld, 1, new Vec2d(11,21));
        NaturalElements.placeTree(gameWorld, 1, new Vec2d(27,11));
        NaturalElements.placeTree(gameWorld, 1, new Vec2d(30,13));
        NaturalElements.placeTree(gameWorld, 1, new Vec2d(34,9));

        NaturalElements.placeRocks(gameWorld, 1, new Vec2d(14,32), 2);
        NaturalElements.placeRocks(gameWorld, 1, new Vec2d(5,23), 4);
        NaturalElements.placeRocks(gameWorld, 1, new Vec2d(29,35), 4);
        NaturalElements.placeRocks(gameWorld, 1, new Vec2d(30,33), 3);
        NaturalElements.placeRocks(gameWorld, 1, new Vec2d(32,33), 1);
        NaturalElements.placeRocks(gameWorld, 1, new Vec2d(34,30), 4);
        NaturalElements.placeRocks(gameWorld, 1, new Vec2d(34,34), 2);
        NaturalElements.placeRocks(gameWorld, 1, new Vec2d(35,32), 1);
        NaturalElements.placeRocks(gameWorld, 1, new Vec2d(13,15), 4);
        NaturalElements.placeRocks(gameWorld, 1, new Vec2d(23,25), 2);
        NaturalElements.placeRocks(gameWorld, 1, new Vec2d(7,21), 4);
        NaturalElements.placeRocks(gameWorld, 1, new Vec2d(10,23), 3);

        NaturalElements.placeYellowFlowers(gameWorld, 0, new Vec2d(6.3,15.6), 1);
        NaturalElements.placeWhiteFlowers(gameWorld, 1, new Vec2d(23,21), 2);
        NaturalElements.placeWhiteFlowers(gameWorld, 0, new Vec2d(15,23), 1);
        NaturalElements.placeWhiteFlowers(gameWorld, 0, new Vec2d(13,23), 0);
        NaturalElements.placeRedFlowers(gameWorld, 0, new Vec2d(33,11), 0);
        NaturalElements.placeRedFlowers(gameWorld, 1, new Vec2d(19,9), 2);

        BackgroundMusic.playBGM1(gameWorld);


        placeWarpToArea2(gameWorld);
    }

    public static void placeWarpToArea2(GameWorld gameWorld){
        GameObject warp = new GameObject(gameWorld, 0);
        CollisionComponent collisionComponent = new CollisionComponent(new AABShape(new Vec2d(0,0), new Vec2d(1,2)),
                true, false, CollisionSystem.CollisionMask.NONE, FinalGame.PLAYER_LAYER);// only cares about player collision
        collisionComponent.linkCollisionCallback(Area1::WarpToArea2);
        warp.addComponent(collisionComponent);
        warp.getTransform().position = new Vec2d(39, 18);

        gameWorld.addGameObject(warp);
    }

    public static void WarpToArea2(CollisionSystem.CollisionInfo collisionInfo){
        //TODO start fadeout animation
        collisionInfo.gameObjectOther.gameWorld.unloadRegion();
        collisionInfo.gameObjectOther.gameWorld.loadRegion(Levels.area2);
        collisionInfo.gameObjectOther.getTransform().position = new Vec2d(1,18);
        //TODO start fadein animation
    }
}
