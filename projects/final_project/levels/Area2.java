package projects.final_project.levels;

import engine.game.GameObject;
import engine.game.GameWorld;
import engine.game.components.SpriteComponent;
import engine.game.tileSystem.TileMap;
import engine.game.collisionShapes.AABShape;
import engine.game.components.CollisionComponent;
import engine.game.components.DrawFogComponent;
import engine.game.systems.CollisionSystem;
import engine.support.Vec2d;
import projects.final_project.NaturalElements;
import projects.final_project.FinalGame;

public class Area2 {

    public static void setTiles(TileMap tileMap){
        int[][] tiles_int = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 3, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        int[][] heights = new int[][]{
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1},
                {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1},
                {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1},
                {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1},
                {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1},
                {0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1},
                {0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1},
                {1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                {1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
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

    public static void addGameObjects(GameWorld gameWorld){
        //TODO need to add rendering order first
        //Decorative.placeTree(gameWorld, new Vec2d(12,32));

        placeHouse(gameWorld, new Vec2d(6,1), 1);

        NaturalElements.placeRocks(gameWorld, 1, new Vec2d(12,32), 2);
        NaturalElements.placeRocks(gameWorld, 1, new Vec2d(5,23), 4);
        NaturalElements.placeRocks(gameWorld, 1, new Vec2d(29,35), 4);
        NaturalElements.placeRocks(gameWorld, 1, new Vec2d(25,6), 3);
        NaturalElements.placeRocks(gameWorld, 1, new Vec2d(28,12), 1);
        NaturalElements.placeRocks(gameWorld, 1, new Vec2d(27,14), 4);
        NaturalElements.placeRocks(gameWorld, 1, new Vec2d(34,25), 2);
        NaturalElements.placeRocks(gameWorld, 1, new Vec2d(35,32), 1);
        NaturalElements.placeRocks(gameWorld, 1, new Vec2d(13,15), 4);
        NaturalElements.placeRocks(gameWorld, 1, new Vec2d(23,25), 2);
        NaturalElements.placeRocks(gameWorld, 1, new Vec2d(15,21), 4);
        NaturalElements.placeRocks(gameWorld, 1, new Vec2d(10,23), 3);

        NaturalElements.placeYellowFlowers(gameWorld, 0, new Vec2d(6.3,15.6), 1);
        NaturalElements.placeWhiteFlowers(gameWorld, 1, new Vec2d(23,21), 2);
        NaturalElements.placeWhiteFlowers(gameWorld, 0, new Vec2d(13,23), 0);
        NaturalElements.placeRedFlowers(gameWorld, 0, new Vec2d(33,11), 0);
        NaturalElements.placeRedFlowers(gameWorld, 1, new Vec2d(19,9), 2);


        NaturalElements.placeWhiteFlowers(gameWorld, 0, new Vec2d(13,27), 1);
        NaturalElements.placeWhiteFlowers(gameWorld, 0, new Vec2d(13,29), 1);
        NaturalElements.placeWhiteFlowers(gameWorld, 0, new Vec2d(15,27), 1);
        NaturalElements.placeWhiteFlowers(gameWorld, 0, new Vec2d(15,29), 1);
        NaturalElements.placeWhiteFlowers(gameWorld, 0, new Vec2d(17,27), 1);
        NaturalElements.placeWhiteFlowers(gameWorld, 0, new Vec2d(17,29), 1);

        NaturalElements.placeWhiteFlowers(gameWorld, 0, new Vec2d(19,27), 0);
        NaturalElements.placeWhiteFlowers(gameWorld, 0, new Vec2d(19,29), 0);
        NaturalElements.placeWhiteFlowers(gameWorld, 0, new Vec2d(11,31), 0);
        NaturalElements.placeWhiteFlowers(gameWorld, 0, new Vec2d(13,31), 0);
        NaturalElements.placeWhiteFlowers(gameWorld, 0, new Vec2d(11,29), 0);

        NaturalElements.placeTree(gameWorld, 1, new Vec2d(9,31));
        NaturalElements.placeTree(gameWorld, 1, new Vec2d(25,38));
        NaturalElements.placeTree(gameWorld, 1, new Vec2d(31,33));
        NaturalElements.placeTree(gameWorld, 1, new Vec2d(2,9));
        NaturalElements.placeTree(gameWorld, 1, new Vec2d(6,14));
        NaturalElements.placeTree(gameWorld, 1, new Vec2d(27,19));
        NaturalElements.placeTree(gameWorld, 1, new Vec2d(11,21));
        NaturalElements.placeTree(gameWorld, 1, new Vec2d(24,11));
        NaturalElements.placeTree(gameWorld, 1, new Vec2d(30,13));
        NaturalElements.placeTree(gameWorld, 1, new Vec2d(38,9));


        Enemies.placeGoomba(gameWorld, new Vec2d(20, 24));
        Enemies.placeGoomba(gameWorld, new Vec2d(22, 26));

        placeWarpToArea1(gameWorld);
        placeWarpToCave(gameWorld);
    }

    public static void placeWarpToArea1(GameWorld gameWorld){
        GameObject warp = new GameObject(gameWorld, 0);
        CollisionComponent collisionComponent = new CollisionComponent(new AABShape(new Vec2d(0,0), new Vec2d(1,2)),
                true, false, CollisionSystem.CollisionMask.NONE, FinalGame.PLAYER_LAYER);// only cares about player collision
        collisionComponent.linkCollisionCallback(Area2::WarpToArea1);
        warp.addComponent(collisionComponent);
        warp.getTransform().position = new Vec2d(0,18);

        gameWorld.addGameObject(warp);
    }

    public static void WarpToArea1(CollisionSystem.CollisionInfo collisionInfo){
        //TODO start fadeout animation
        collisionInfo.gameObjectOther.gameWorld.unloadRegion();
        collisionInfo.gameObjectOther.gameWorld.loadRegion(Levels.area1);
        collisionInfo.gameObjectOther.getTransform().position = new Vec2d(36,18);
        //TODO start fadein animation
    }

    public static void placeWarpToCave(GameWorld gameWorld){
        GameObject warp = new GameObject(gameWorld, 0);
        CollisionComponent collisionComponent = new CollisionComponent(new AABShape(new Vec2d(0,0), new Vec2d(2,1)),
                true, false, CollisionSystem.CollisionMask.NONE, FinalGame.PLAYER_LAYER);// only cares about player collision
        collisionComponent.linkCollisionCallback(Area2::WarpToCave);
        warp.addComponent(collisionComponent);
        warp.getTransform().position = new Vec2d(32,6);

        gameWorld.addGameObject(warp);
    }

    public static void WarpToCave(CollisionSystem.CollisionInfo collisionInfo){
        //TODO start fadeout animation
        collisionInfo.gameObjectOther.gameWorld.unloadRegion();
        collisionInfo.gameObjectOther.gameWorld.loadRegion(Levels.cave);
        collisionInfo.gameObjectOther.getTransform().position = new Vec2d(5,5);
        DrawFogComponent fog = (DrawFogComponent)collisionInfo.gameObjectOther.getComponent("DrawFogComponent");
        fog.enable();
        //TODO start fadein animation
    }


    /**
     * Places house into the game world
     * @param gameWorld Gameworld to add to
     * @param pos location in game world
     */
    public static void placeHouse(GameWorld gameWorld, Vec2d pos, int layer) {
        GameObject house = new GameObject(gameWorld, layer);

        SpriteComponent up_slant_back = new SpriteComponent(FinalGame.getSpritePath("tile_sprite_sheet"),
                new Vec2d(1,1), new Vec2d(2,2), new Vec2d(15,2).smult(32), new Vec2d(32,32));
        SpriteComponent roof = new SpriteComponent(FinalGame.getSpritePath("tile_sprite_sheet"),
                new Vec2d(3,1), new Vec2d(2,2), new Vec2d(17,3).smult(32), new Vec2d(32,32));
        SpriteComponent roof3 = new SpriteComponent(FinalGame.getSpritePath("tile_sprite_sheet"),
                new Vec2d(5,1), new Vec2d(2,2), new Vec2d(17,3).smult(32), new Vec2d(32,32));
        SpriteComponent chimney  = new SpriteComponent(FinalGame.getSpritePath("tile_sprite_sheet"),
                new Vec2d(3,1), new Vec2d(2,2), new Vec2d(16,2).smult(32), new Vec2d(32,32));
        SpriteComponent down_slant_back = new SpriteComponent(FinalGame.getSpritePath("tile_sprite_sheet"),
                new Vec2d(7,1), new Vec2d(2,2), new Vec2d(18,2).smult(32), new Vec2d(32,32));

        SpriteComponent up_slant_mid = new SpriteComponent(FinalGame.getSpritePath("tile_sprite_sheet"),
                new Vec2d(1,3), new Vec2d(2,2), new Vec2d(15,3).smult(32), new Vec2d(32,32));
        SpriteComponent down_slant_mid = new SpriteComponent(FinalGame.getSpritePath("tile_sprite_sheet"),
                new Vec2d(7,3), new Vec2d(2,2), new Vec2d(18,3).smult(32), new Vec2d(32,32));
        SpriteComponent roof4 = new SpriteComponent(FinalGame.getSpritePath("tile_sprite_sheet"),
                new Vec2d(3,3), new Vec2d(2,2), new Vec2d(17,3).smult(32), new Vec2d(32,32));
        SpriteComponent roof2 = new SpriteComponent(FinalGame.getSpritePath("tile_sprite_sheet"),
                new Vec2d(5,3), new Vec2d(2,2), new Vec2d(17,3).smult(32), new Vec2d(32,32));

        SpriteComponent up_slant_front = new SpriteComponent(FinalGame.getSpritePath("tile_sprite_sheet"),
                new Vec2d(1,5), new Vec2d(2,2), new Vec2d(15,4).smult(32), new Vec2d(32,32));
        SpriteComponent down_slant_front = new SpriteComponent(FinalGame.getSpritePath("tile_sprite_sheet"),
                new Vec2d(7,5), new Vec2d(2,2), new Vec2d(18,4).smult(32), new Vec2d(32,32));
        SpriteComponent front_top = new SpriteComponent(FinalGame.getSpritePath("tile_sprite_sheet"),
                new Vec2d(3,5), new Vec2d(2,2), new Vec2d(16,4).smult(32), new Vec2d(32,32));
        SpriteComponent front_top2 = new SpriteComponent(FinalGame.getSpritePath("tile_sprite_sheet"),
                new Vec2d(5,5), new Vec2d(2,2), new Vec2d(16,4).smult(32), new Vec2d(32,32));

        SpriteComponent front_topLeft = new SpriteComponent(FinalGame.getSpritePath("tile_sprite_sheet"),
                new Vec2d(1,7), new Vec2d(2,2), new Vec2d(17,6).smult(32), new Vec2d(32,32));
        SpriteComponent front_topRight = new SpriteComponent(FinalGame.getSpritePath("tile_sprite_sheet"),
                new Vec2d(7,7), new Vec2d(2,2), new Vec2d(18,6).smult(32), new Vec2d(32,32));
        SpriteComponent front_topV2 = new SpriteComponent(FinalGame.getSpritePath("tile_sprite_sheet"),
                new Vec2d(3,7), new Vec2d(2,2), new Vec2d(16,9).smult(32), new Vec2d(32,32));
        SpriteComponent door = new SpriteComponent(FinalGame.getSpritePath("tile_sprite_sheet"),
                new Vec2d(5,7), new Vec2d(2,2), new Vec2d(18,9).smult(32), new Vec2d(32,32));

        house.addComponent(up_slant_back);
        house.addComponent(roof);
        house.addComponent(roof3);
        house.addComponent(chimney);
        house.addComponent(down_slant_back);

        house.addComponent(up_slant_mid);
        house.addComponent(down_slant_mid);
        house.addComponent(roof2);
        house.addComponent(roof4);

        house.addComponent(up_slant_front);
        house.addComponent(down_slant_front);
        house.addComponent(front_top);
        house.addComponent(front_top2);

        house.addComponent(front_topLeft);
        house.addComponent(front_topV2);
        house.addComponent(front_topRight);
        house.addComponent(door);



        house.addComponent(new CollisionComponent(new AABShape(new Vec2d(1,5), new Vec2d(8,4)), true,
                true, FinalGame.OBJECT_LAYER, FinalGame.OBJECT_MASK));

        house.getTransform().position = pos;

        gameWorld.addGameObject(house);
    }


}
