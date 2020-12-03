package projects.final_project;

import engine.game.GameObject;
import engine.game.GameWorld;
import engine.game.collisionShapes.CircleShape;
import engine.game.components.*;
import engine.game.components.animation.SpriteAnimationComponent;
import engine.game.systems.CollisionSystem;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;

public class MiscElements {

    /**
     * Places barrel into the game world
     * @param gameWorld Gameworld to add to
     * @param pos location in game world
     */
    public static void placeBarrel(GameWorld gameWorld, int layer, Vec2d pos){
        GameObject barrel = new GameObject(gameWorld, layer);

        SpriteComponent sprite = new SpriteComponent(FinalGame.getSpritePath("barrel"), new Vec2d(-.375,-1), new Vec2d(.75, 1.125));
        barrel.addComponent(sprite);
        barrel.addComponent(new CollisionComponent(new CircleShape(new Vec2d(0,-.25), .5), true, true,
                FinalGame.OBJECT_LAYER, FinalGame.OBJECT_MASK));

        CollisionComponent hitComponent = new CollisionComponent(new CircleShape(new Vec2d(0,-.25), .5), true, false,
                FinalGame.ATTACK_LAYER, FinalGame.ATTACK_MASK);
        hitComponent.linkCollisionCallback(MiscElements::onHitCallback);
        barrel.addComponent(hitComponent);

        HealthComponent healthComponent = new HealthComponent(3);
        healthComponent.linkDeathCallback(MiscElements::onBreakCallback);
        barrel.addComponent(healthComponent);

        barrel.getTransform().position = pos;
        gameWorld.addGameObject(barrel);
    }

    public static void onHitCallback(CollisionSystem.CollisionInfo collisionInfo){
        if(collisionInfo.gameObjectSelf.getComponent("ShakeComponent") == null) {
            collisionInfo.gameObjectSelf.addComponent(new ShakeComponent(.1, .1));
            HealthComponent health = (HealthComponent)collisionInfo.gameObjectSelf.getComponent("HealthComponent");
            if(health != null){
                health.hit(1);
            }
        }
    }

    public static void onBreakCallback(GameObject gameObject){
        double x = (Math.random()*2 - 1)*1;
        double y = (Math.random()*2 - 1)*1;
        Vec2d pos = gameObject.getTransform().position;
        placeCoin(gameObject.gameWorld, 1, new Vec2d(pos.x, pos.y), new Vec2d(x,y).normalize().smult(2));
        gameObject.gameWorld.removeGameObject(gameObject);
    }


    /**
     * Places barrel into the game world
     * @param gameWorld Gameworld to add to
     * @param pos location in game world
     */
    public static void placeCoin(GameWorld gameWorld, int layer, Vec2d pos, Vec2d vel){
        GameObject coin = new GameObject(gameWorld, layer);

        SpriteAnimationComponent animation = new SpriteAnimationComponent(FinalGame.getSpritePath("coin"),
                new Vec2d(-.25,-.25), new Vec2d(.5,.5), 8, new Vec2d(0,0), new Vec2d(16,16), new Vec2d(16,0), .05);
        coin.addComponent(animation);

        CollisionComponent coinPickup = new CollisionComponent(new CircleShape(new Vec2d(0,0), .5), true, false,
                FinalGame.OBJECT_LAYER, FinalGame.OBJECT_MASK);
        coinPickup.linkCollisionCallback(MiscElements::onPickUp);
        coin.addComponent(coinPickup);

        if(vel != null) {
            coin.addComponent(new VelocityComponent(vel, .9));
        }

        coin.getTransform().position = pos;
        gameWorld.addGameObject(coin);
    }

    public static void onPickUp(CollisionSystem.CollisionInfo collisionInfo){

        IDComponent id = (IDComponent)collisionInfo.gameObjectOther.getComponent("IDComponent");
        if(id != null && id.getId().equals("player")){

            //TODO give player points

            VelocityComponent vel = (VelocityComponent)collisionInfo.gameObjectSelf.getComponent("VelocityComponent");
            if(vel != null){
                vel.velocity = new Vec2d(0,-2);
            }

            DelayEventComponent delete = new DelayEventComponent(.2);
            delete.linkEventCallback(MiscElements::coinDeleteCallback);
            collisionInfo.gameObjectSelf.addComponent(delete);
        }
    }

    public static void coinDeleteCallback(GameObject coin){
        coin.gameWorld.removeGameObject(coin);
    }
}
