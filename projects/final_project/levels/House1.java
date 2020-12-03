package projects.final_project.levels;

import engine.game.GameObject;
import engine.game.GameWorld;
import engine.game.components.SpriteComponent;
import engine.support.Vec2d;
import projects.final_project.FinalGame;

public class House1 {



    public static void addGameObjects(GameWorld gameWorld){
        placeHouseInterior(gameWorld, 0);
    }

    public static void placeHouseInterior(GameWorld gameWorld, int layer){
        GameObject house = new GameObject(gameWorld, layer);

        SpriteComponent sprite = new SpriteComponent(FinalGame.getSpritePath("house"), new Vec2d(0,0), new Vec2d(16, 16));
        house.addComponent(sprite);

        gameWorld.addGameObject(house);
    }
}
