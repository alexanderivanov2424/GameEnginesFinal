package projects.final_project.levels;

import engine.game.GameObject;
import engine.game.GameWorld;
import engine.game.collisionShapes.AABShape;
import engine.game.components.*;
import engine.game.components.screenEffects.FadeInEffect;
import engine.game.components.screenEffects.FadeOutEffect;
import engine.game.systems.CollisionSystem;
import engine.game.tileSystem.TileMap;
import engine.support.Vec2d;
import javafx.scene.paint.Color;
import projects.final_project.BackgroundMusic;
import projects.final_project.FinalGame;
import projects.final_project.Player;

public class Cave1 {

    public static void setCaveLevel(TileMap tileMap){
        int[][] tiles_int = new int[][]{
                {1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        };

        String[] index = new String[]{"cave", "wall", "exit"};
        String[][] tiles = new String[tiles_int.length][tiles_int[0].length];
        for(int i = 0; i < tiles_int.length; i++){
            for(int j = 0; j < tiles_int[0].length; j++){
                tiles[i][j] = index[tiles_int[i][j]];
            }
        }

        tileMap.setTiles(tiles);
        tileMap.setExteriorTile("wall",0);
    }

    public static void addGameObjects(GameWorld gameWorld){


        placeWarpToArea2(gameWorld);
    }

    public static void placeWarpToArea2(GameWorld gameWorld){
        GameObject warp = new GameObject(gameWorld, 0);
        CollisionComponent collisionComponent = new CollisionComponent(new AABShape(new Vec2d(0,0), new Vec2d(2,.5)),
                true, false, CollisionSystem.CollisionMask.NONE, FinalGame.PLAYER_LAYER);// only cares about player collision
        collisionComponent.linkCollisionCallback(Cave1::FadeOutCave1_Area2);
        warp.addComponent(collisionComponent);
        warp.getTransform().position = new Vec2d(4,0);
        gameWorld.addGameObject(warp);
    }


    public static void LoadArea2(GameObject gameObject){
        gameObject.addComponent(new FadeInEffect(0, 1));
        gameObject.gameWorld.unloadRegion();
        gameObject.gameWorld.loadRegion(Levels.area2);
        gameObject.getTransform().position = new Vec2d(32,8.5);
        DrawFogComponent fog = (DrawFogComponent)gameObject.getComponent("DrawFogComponent");
        fog.disable();
        CameraComponent camera = (CameraComponent)gameObject.getComponent("CameraComponent");
        camera.setHorizontalRange(new Vec2d(0,40));
        camera.setVerticalRange(new Vec2d(0,40));
        BackgroundMusic.stopBGM(gameObject.gameWorld);
        BackgroundMusic.playBGM1(gameObject.gameWorld);
        Player.isBetweenAreas = false;
    }

    public static void FadeOutCave1_Area2(CollisionSystem.CollisionInfo collisionInfo){
        if(!Player.isBetweenAreas) {
            Player.isBetweenAreas = true;
            FadeOutEffect fadeout = new FadeOutEffect(0, .5);
            fadeout.linkEventCallback(Cave1::LoadArea2);
            collisionInfo.gameObjectOther.addComponent(fadeout);
        }
    }
}
