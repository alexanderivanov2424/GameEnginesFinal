package projects.final_project.levels;

import engine.game.GameObject;
import engine.game.GameWorld;
import engine.game.tileSystem.TileMap;
import engine.game.components.LateRectComponent;
import engine.game.components.ProximityComponent;
import engine.support.Vec2d;
import javafx.scene.paint.Color;

public class CaveLevel {

    public static void setCaveLevel(TileMap tileMap, GameObject player, GameWorld gameWorld){
        int[][] tiles_int = new int[][]{
                {1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 1, 1},
                {1, 0, 0, 0, 0, 1, 1},
                {1, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 1},
                {1, 1, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1},
        };

        int[][] heights = new int[][]{
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
        };

        String[] index = new String[]{"cave", "wall"};
        String[][] tiles = new String[tiles_int.length][tiles_int[0].length];
        for(int i = 0; i < tiles_int.length; i++){
            for(int j = 0; j < tiles_int[0].length; j++){
                tiles[i][j] = index[tiles_int[i][j]];
                //addFogTile(gameWorld, new Vec2d(i,j).smult(2), player);
            }
        }

        tileMap.setTiles(tiles);
        tileMap.setHeights(heights);
        tileMap.setExteriorTile("wall",0);
    }

    public static void addGameObjects(GameWorld gameWorld){

    }

    private static void addFogTile(GameWorld gameWorld, Vec2d pos, GameObject player) {
        GameObject fog = new GameObject(gameWorld);
        fog.getTransform().position = pos;
        fog.getTransform().size = new Vec2d(2,2);

        LateRectComponent lateRectComponent = new LateRectComponent(Color.rgb(0,0,0,0.8));
        lateRectComponent.setGameObject(fog);
        fog.addComponent(lateRectComponent);

        ProximityComponent proximityComponent = new ProximityComponent(player, 5);
        proximityComponent.setGameObject(fog);
        proximityComponent.linkProximityCallback(CaveLevel::fogBreakCallback);
        fog.addComponent(proximityComponent);

        gameWorld.addGameObject(fog);

        //every tick, set the fog's color(transparency) based on proximity to objects with
    }

    //Distance is less than 5.
    private static void fogBreakCallback(GameObject fogTile, double distance) {

        LateRectComponent fog = (LateRectComponent)fogTile.getComponent("LateRectComponent");
        //The shorter the distance, the smaller the opacity.

        if(distance < 3) {
            fog.setColor(Color.rgb(0,0,0,0));
        }
        else {
            //0.39 = .8/(5-3)
            fog.setColor(Color.rgb(0,0,0,(distance-3)*0.39));
        }

    }
}
