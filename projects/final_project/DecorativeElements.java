package projects.final_project;

import engine.game.GameObject;
import engine.game.GameWorld;
import engine.game.collisionShapes.AABShape;
import engine.game.collisionShapes.CircleShape;
import engine.game.components.CollisionComponent;
import engine.game.components.LightComponent;
import engine.game.components.SpriteComponent;
import engine.support.Vec2d;
import javafx.scene.paint.Color;
import projects.final_project.FinalGame;

public class DecorativeElements {

    public static void placeTree(GameWorld gameWorld, Vec2d pos){
        GameObject tree = new GameObject(gameWorld, 2);

        SpriteComponent left_trunk = new SpriteComponent(FinalGame.getSpritePath("tile_sprite_sheet"),
                new Vec2d(-2,0), new Vec2d(2,2), new Vec2d(0,13).smult(32), new Vec2d(32,32));
        SpriteComponent right_trunk = new SpriteComponent(FinalGame.getSpritePath("tile_sprite_sheet"),
                new Vec2d(0,0), new Vec2d(2,2), new Vec2d(1,13).smult(32), new Vec2d(32,32));
        SpriteComponent bottom_left_folliage = new SpriteComponent(FinalGame.getSpritePath("tile_sprite_sheet"),
                new Vec2d(-2,-2), new Vec2d(2,2), new Vec2d(0,12).smult(32), new Vec2d(32,32));
        SpriteComponent bottom_right_folliage = new SpriteComponent(FinalGame.getSpritePath("tile_sprite_sheet"),
                new Vec2d(0,-2), new Vec2d(2,2), new Vec2d(1,12).smult(32), new Vec2d(32,32));
        SpriteComponent top_left_folliage = new SpriteComponent(FinalGame.getSpritePath("tile_sprite_sheet"),
                new Vec2d(-2,-4), new Vec2d(2,2), new Vec2d(2,12).smult(32), new Vec2d(32,32));
        SpriteComponent top_right_folliage = new SpriteComponent(FinalGame.getSpritePath("tile_sprite_sheet"),
                new Vec2d(0,-4), new Vec2d(2,2), new Vec2d(3,12).smult(32), new Vec2d(32,32));

        tree.addComponent(left_trunk);
        tree.addComponent(right_trunk);
        tree.addComponent(bottom_left_folliage);
        tree.addComponent(bottom_right_folliage);
        tree.addComponent(top_left_folliage);
        tree.addComponent(top_right_folliage);

        tree.getTransform().position = pos;

        gameWorld.addGameObject(tree);
    }

    /**
     * Places rocks int the game world
     * @param gameWorld Gameworld to add to
     * @param pos location in game world
     * @param size integer in {0, 1, 2, 3, 4}
     */
    public static void placeRocks(GameWorld gameWorld, Vec2d pos, int size){
        GameObject rocks = new GameObject(gameWorld, 2);

        if(size > 4 || size < 0) size = 0;

        SpriteComponent sprite = new SpriteComponent(FinalGame.getSpritePath("tile_sprite_sheet"),
                new Vec2d(0,0), new Vec2d(2,2), new Vec2d(4-size,14).smult(32), new Vec2d(32,32));
        rocks.addComponent(sprite);

        LightComponent lightComponent = new LightComponent(Color.WHITE, 3, new Vec2d(1,1.5));
        rocks.addComponent(lightComponent);

        rocks.addComponent(new CollisionComponent(new CircleShape(new Vec2d(1,1), 2.0/((double)size+1.0)), true, true,
                FinalGame.OBJECT_LAYER, FinalGame.OBJECT_MASK));

        rocks.getTransform().position = pos;
        gameWorld.addGameObject(rocks);
    }
}
