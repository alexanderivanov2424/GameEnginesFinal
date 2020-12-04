package projects.final_project.levels;

import engine.game.GameObject;
import engine.game.GameWorld;
import engine.game.collisionShapes.AABShape;
import engine.game.collisionShapes.CircleShape;
import engine.game.components.*;
import engine.game.components.animation.AnimationComponent;
import engine.game.components.animation.SpriteAnimationComponent;
import engine.game.components.animation.animationGraph.AGAnimation;
import engine.game.components.animation.animationGraph.AGAnimationGroup;
import engine.game.components.animation.animationGraph.AGNode;
import engine.game.components.animation.animationGraph.AnimationGraphComponent;
import engine.game.systems.CollisionSystem;
import engine.game.systems.SystemFlag;
import engine.support.Vec2d;
import javafx.scene.input.KeyCode;
import projects.WizTesting.WizGame;
import projects.final_project.FinalGame;
import projects.final_project.MiscElements;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Enemies {

    private static final int ENEMY_LAYER = CollisionSystem.CollisionMask.layer1;
    private static final int ENEMY_MASK = CollisionSystem.CollisionMask.layer0 | CollisionSystem.CollisionMask.layer1;

    private static final Vec2d GOOMBA_SIZE = new Vec2d(1.13,1);

    public static void placeGoomba(GameWorld gameWorld, Vec2d pos){
        GameObject enemy = new GameObject(gameWorld, 1);

        AnimationGraphComponent agc = getGoombaAnimationGraph();
        enemy.addComponent(agc);
        enemy.addComponent(new GoombaMovementComponent(2, agc));


        enemy.addComponent(new CollisionComponent(new AABShape(new Vec2d(0.1,0.2),new Vec2d(0.7,0.65)),
                false, true, FinalGame.OBJECT_LAYER, FinalGame.OBJECT_MASK));

        CollisionComponent hitCollisionComponent = new CollisionComponent(new AABShape(new Vec2d(0.1,0.2),new Vec2d(0.7,0.65)),
                false, false, CollisionSystem.CollisionMask.NONE, FinalGame.ATTACK_MASK);
        hitCollisionComponent.linkCollisionCallback(Enemies::onHitCallback);
        enemy.addComponent(hitCollisionComponent);


        HealthComponent healthComponent = new HealthComponent(5);
        healthComponent.linkDeathCallback(Enemies::enemyDeathCallback);
        enemy.addComponent(healthComponent);

        enemy.addComponent(new IDComponent("goomba"));

        enemy.getTransform().position = pos;
        enemy.getTransform().size = new Vec2d(2,2);
        gameWorld.addGameObject(enemy);
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

    private static void enemyDeathCallback(GameObject enemy){
        CollisionComponent collision = (CollisionComponent)enemy.getComponent("CollisionComponent");
        collision.disable();

        Vec2d pos = enemy.getTransform().position;
        for(int i = 0; i < 5; i++) {
            MiscElements.placeCoin(enemy.gameWorld, 2, new Vec2d(pos.x, pos.y),
                    new Vec2d(Math.random() * 2 - 1, Math.random() * 2 - 1).normalize().smult(2));
        }

        DelayEventComponent delayEventComponent = new DelayEventComponent(.1);
        delayEventComponent.linkEventCallback(Enemies::enemyRemoveCallback);
        enemy.addComponent(delayEventComponent);
    }

    private static void enemyRemoveCallback(GameObject gameObject){
        gameObject.gameWorld.removeGameObject(gameObject);
    }

    private static AnimationGraphComponent getGoombaAnimationGraph(){
        Vec2d spriteOffset = new Vec2d(0,0);
        Vec2d cropSize = new Vec2d(25,22);
        AnimationComponent idle_up = new SpriteAnimationComponent(FinalGame.getSpritePath("goomba"),
                spriteOffset, GOOMBA_SIZE, 1, new Vec2d(0,1*22), cropSize, new Vec2d(25,0), .05);
        AnimationComponent idle_left = new SpriteAnimationComponent(FinalGame.getSpritePath("goomba"),
                spriteOffset, GOOMBA_SIZE, 1, new Vec2d(0,2*22), cropSize, new Vec2d(25,0), .05);
        AnimationComponent idle_down = new SpriteAnimationComponent(FinalGame.getSpritePath("goomba"),
                spriteOffset, GOOMBA_SIZE, 1, new Vec2d(0,0*22), cropSize, new Vec2d(25,0), .05);
        AnimationComponent idle_right = new SpriteAnimationComponent(FinalGame.getSpritePath("goomba"),
                spriteOffset, GOOMBA_SIZE, 1, new Vec2d(0,3*22), cropSize, new Vec2d(25,0), .05);
        AGNode N_idle_up = new AGAnimation("idle_up", idle_up);
        AGNode N_idle_left = new AGAnimation("idle_left", idle_left);
        AGNode N_idle_down = new AGAnimation("idle_down", idle_down);
        AGNode N_idle_right = new AGAnimation("idle_right", idle_right);

        AnimationComponent walk_up = new SpriteAnimationComponent(FinalGame.getSpritePath("goomba"),
                spriteOffset, GOOMBA_SIZE, 7, new Vec2d(25,1*22), cropSize, new Vec2d(25,0), .1);
        AnimationComponent walk_left = new SpriteAnimationComponent(FinalGame.getSpritePath("goomba"),
                spriteOffset, GOOMBA_SIZE, 7, new Vec2d(25,2*22), cropSize, new Vec2d(25,0), .1);
        AnimationComponent walk_down = new SpriteAnimationComponent(FinalGame.getSpritePath("goomba"),
                spriteOffset, GOOMBA_SIZE, 7, new Vec2d(25,0*22), cropSize, new Vec2d(25,0), .1);
        AnimationComponent walk_right = new SpriteAnimationComponent(FinalGame.getSpritePath("goomba"),
                spriteOffset, GOOMBA_SIZE, 7, new Vec2d(25,3*22), cropSize, new Vec2d(25,0), .1);
        AGNode N_walk_up = new AGAnimation("walk_up", walk_up);
        AGNode N_walk_left = new AGAnimation("walk_left", walk_left);
        AGNode N_walk_down = new AGAnimation("walk_down", walk_down);
        AGNode N_walk_right = new AGAnimation("walk_right", walk_right);

        AGAnimationGroup idle = new AGAnimationGroup("idle",
                new AGNode[]{N_idle_up, N_idle_left, N_idle_down, N_idle_right},
                new Vec2d[]{new Vec2d(0,-1), new Vec2d(-1,0), new Vec2d(0,1), new Vec2d(1,0)});
        idle.setInterruptible(true);

        AGAnimationGroup walk = new AGAnimationGroup("walk",
                new AGNode[]{N_walk_up, N_walk_left, N_walk_down, N_walk_right},
                new Vec2d[]{new Vec2d(0,-1), new Vec2d(-1,0), new Vec2d(0,1), new Vec2d(1,0)});
        walk.setInterruptible(true);

        AGNode[] animationNodes = new AGNode[]{idle, walk};
        AnimationGraphComponent agc = new AnimationGraphComponent(animationNodes);

        return agc;
    }

    private static class GoombaMovementComponent extends Component{

        private String direction = "down";
        private double speed, time = 2;

        private AnimationGraphComponent animationGraphComponent;

        public GoombaMovementComponent(double speed, AnimationGraphComponent animationGraphComponent) {
            super();
            this.speed = speed;
            this.animationGraphComponent = animationGraphComponent;
        }


        //Every couple seconds, choose a new direction to go and walk in that direction
        @Override
        public void onTick(long nanosSincePreviousTick){
            double dx = 0;
            double dy = 0;
            double dt = nanosSincePreviousTick/1000000000.0; //seconds since last tick

            time = time - dt;

            //Randomly pick a new direction every 2 seconds.
            if(time <= 0) {
                int pickDirection = ThreadLocalRandom.current().nextInt(0, 3 + 1);

                if(pickDirection == 0) direction = "down";
                else if(pickDirection == 1) direction = "up";
                else if(pickDirection == 2) direction = "left";
                else if(pickDirection == 3) direction = "right";
                else direction = "none";

                time = 2;
            }

            if(direction.equals("none")) this.animationGraphComponent.queueAnimation("idle", true);

            else {
                this.animationGraphComponent.queueAnimation("walk");

                if(direction.equals("up")) {
                    dy += speed * dt;
                    this.animationGraphComponent.updateState(new Vec2d[]{new Vec2d(0,-1)});
                }
                if(direction.equals("left")) {
                    dx += speed * dt;
                    this.animationGraphComponent.updateState(new Vec2d[]{new Vec2d(-1,0)});
                }
                if(direction.equals("down")) {
                    dy -= speed * dt;
                    this.animationGraphComponent.updateState(new Vec2d[]{new Vec2d(0,1)});
                }
                if(direction.equals("right")) {
                    dx -= speed * dt;
                    this.animationGraphComponent.updateState(new Vec2d[]{new Vec2d(1,0)});
                }
            }

            Vec2d pos = this.gameObject.getTransform().position;
            this.gameObject.getTransform().position = new Vec2d(pos.x - dx, pos.y - dy);
        }

        @Override
        public int getSystemFlags() {
            return SystemFlag.TickSystem;
        }

        @Override
        public String getTag() {
            return "GoombaMovementComponent";
        }
    }
}
