package projects.final_project.characters;

import engine.game.GameObject;
import engine.game.GameWorld;
import engine.game.collisionShapes.AABShape;
import engine.game.collisionShapes.CircleShape;
import engine.game.components.*;
import engine.game.components.animation.*;
import engine.game.components.animation.animationGraph.*;
import engine.game.systems.CollisionSystem;
import engine.game.systems.SystemFlag;
import engine.support.Vec2d;
import projects.final_project.FinalGame;
import projects.final_project.MiscElements;

import java.util.concurrent.ThreadLocalRandom;

public class Skeleton {

    private static final Vec2d SKELETON_SIZE = new Vec2d(2,2);

    public static void placeSkeleton(GameWorld gameWorld, Vec2d pos){
        GameObject enemy = new GameObject(gameWorld, 1);

        AnimationGraphComponent agc = getSkeletonAnimationGraph();
        enemy.addComponent(agc);
        enemy.addComponent(new SkeletonMovementComponent(2, agc));


        enemy.addComponent(new CollisionComponent(new AABShape(new Vec2d(-.46,-.3),new Vec2d(0.7,0.65)),
                false, true, FinalGame.OBJECT_LAYER, FinalGame.OBJECT_MASK));

        CollisionComponent hitCollisionComponent = new CollisionComponent(new AABShape(new Vec2d(-.46,-.3),new Vec2d(0.7,0.65)),
                false, false, CollisionSystem.CollisionMask.NONE, FinalGame.ATTACK_MASK);
        hitCollisionComponent.linkCollisionCallback(Skeleton::onHitCallback);
        enemy.addComponent(hitCollisionComponent);

        CollisionComponent nearPlayer = new CollisionComponent(new CircleShape(new Vec2d(0,0),5),
                false, false, FinalGame.OBJECT_LAYER, FinalGame.OBJECT_MASK);
        nearPlayer.linkCollisionCallback(Skeleton::skeletonNearPlayer);
        enemy.addComponent(nearPlayer);


        HealthComponent healthComponent = new HealthComponent(5);
        healthComponent.linkDeathCallback(Skeleton::enemyDeathCallback);
        enemy.addComponent(healthComponent);

        enemy.addComponent(new IDComponent("skeleton"));

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
        delayEventComponent.linkEventCallback(Skeleton::enemyRemoveCallback);
        enemy.addComponent(delayEventComponent);


    }

    private static void enemyRemoveCallback(GameObject gameObject){
        gameObject.gameWorld.removeGameObject(gameObject);
    }

    private static AnimationGraphComponent getSkeletonAnimationGraph(){
        Vec2d spriteOffset = new Vec2d(-1,-2);
        AnimationComponent idle_up = new SpriteAnimationComponent(FinalGame.getSpritePath("skeleton"),
                spriteOffset, SKELETON_SIZE, 1, new Vec2d(0,8*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent idle_left = new SpriteAnimationComponent(FinalGame.getSpritePath("skeleton"),
                spriteOffset, SKELETON_SIZE, 1, new Vec2d(0,9*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent idle_down = new SpriteAnimationComponent(FinalGame.getSpritePath("skeleton"),
                spriteOffset, SKELETON_SIZE, 1, new Vec2d(0,10*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent idle_right = new SpriteAnimationComponent(FinalGame.getSpritePath("skeleton"),
                spriteOffset, SKELETON_SIZE, 1, new Vec2d(0,11*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AGNode N_idle_up = new AGAnimation("idle_up", idle_up);
        AGNode N_idle_left = new AGAnimation("idle_left", idle_left);
        AGNode N_idle_down = new AGAnimation("idle_down", idle_down);
        AGNode N_idle_right = new AGAnimation("idle_right", idle_right);

        AnimationComponent walk_up = new SpriteAnimationComponent(FinalGame.getSpritePath("skeleton"),
                spriteOffset, SKELETON_SIZE, 9, new Vec2d(0,8*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent walk_left = new SpriteAnimationComponent(FinalGame.getSpritePath("skeleton"),
                spriteOffset, SKELETON_SIZE, 9, new Vec2d(0,9*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent walk_down = new SpriteAnimationComponent(FinalGame.getSpritePath("skeleton"),
                spriteOffset, SKELETON_SIZE, 9, new Vec2d(0,10*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent walk_right = new SpriteAnimationComponent(FinalGame.getSpritePath("skeleton"),
                spriteOffset, SKELETON_SIZE, 9, new Vec2d(0,11*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AGNode N_walk_up = new AGAnimation("walk_up", walk_up);
        AGNode N_walk_left = new AGAnimation("walk_left", walk_left);
        AGNode N_walk_down = new AGAnimation("walk_down", walk_down);
        AGNode N_walk_right = new AGAnimation("walk_right", walk_right);

        AnimationComponent attack_up = new SpriteAnimationComponent(FinalGame.getSpritePath("skeleton"),
                spriteOffset, SKELETON_SIZE, 6, new Vec2d(0,12*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent attack_left = new SpriteAnimationComponent(FinalGame.getSpritePath("skeleton"),
                spriteOffset, SKELETON_SIZE, 6, new Vec2d(0,13*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent attack_down = new SpriteAnimationComponent(FinalGame.getSpritePath("skeleton"),
                spriteOffset, SKELETON_SIZE, 6, new Vec2d(0,14*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent attack_right = new SpriteAnimationComponent(FinalGame.getSpritePath("skeleton"),
                spriteOffset, SKELETON_SIZE, 6, new Vec2d(0,15*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AGNode N_attack_up = new AGAnimation("attack_up", attack_up);
        AGNode N_attack_left = new AGAnimation("attack_left", attack_left);
        AGNode N_attack_down = new AGAnimation("attack_down", attack_down);
        AGNode N_attack_right = new AGAnimation("attack_right", attack_right);

        AGAnimationGroup idle = new AGAnimationGroup("idle",
                new AGNode[]{N_idle_up, N_idle_left, N_idle_down, N_idle_right},
                new Vec2d[]{new Vec2d(0,-1), new Vec2d(-1,0), new Vec2d(0,1), new Vec2d(1,0)});
        idle.setInterruptible(true);

        AGAnimationGroup walk = new AGAnimationGroup("walk",
                new AGNode[]{N_walk_up, N_walk_left, N_walk_down, N_walk_right},
                new Vec2d[]{new Vec2d(0,-1), new Vec2d(-1,0), new Vec2d(0,1), new Vec2d(1,0)});
        walk.setInterruptible(true);

        AGAnimationGroup attack = new AGAnimationGroup("attack", "idle",
                new AGNode[]{N_attack_up, N_attack_left, N_attack_down, N_attack_right},
                new Vec2d[]{new Vec2d(0,-1), new Vec2d(-1,0), new Vec2d(0,1), new Vec2d(1,0)});
        attack.setInterruptible(false);

        AGNode[] animationNodes = new AGNode[]{idle, walk, attack};
        AnimationGraphComponent agc = new AnimationGraphComponent(animationNodes);
        return agc;
    }

    private static class SkeletonMovementComponent extends Component{

        private Vec2d direction = new Vec2d(0,0);
        private double speed, time = 2;

        private double margin = .5;

        private String state = "idle"; // idle, follow, prep, charge

        public GameObject player;

        private AnimationGraphComponent animationGraphComponent;

        public SkeletonMovementComponent(double speed, AnimationGraphComponent animationGraphComponent) {
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

            time -= dt;

            if(this.state.equals("idle")){
                if(time <= 0) {//Randomly pick a new direction every 2 seconds.
                    int pickDirection = ThreadLocalRandom.current().nextInt(0, 4);
                    if(pickDirection == 0) direction = new Vec2d(0,1);
                    else if(pickDirection == 1) direction = new Vec2d(0,-1);
                    else if(pickDirection == 2) direction = new Vec2d(-1,0);
                    else if(pickDirection == 3) direction = new Vec2d(1,0);
                    else direction = new Vec2d(0,0);
                    time = ThreadLocalRandom.current().nextInt(1, 3);
                }

                if(direction.x == 0 && direction.y == 0) {
                    this.animationGraphComponent.queueAnimation("idle");
                } else {
                    this.animationGraphComponent.queueAnimation("walk");
                }
                dx -= direction.x * dt * speed;
                dy -= direction.y * dt * speed;

            } else if(this.state.equals("follow")){
                Vec2d delta = this.player.getTransform().position.minus(this.gameObject.getTransform().position);
                this.direction = delta.normalize();
                if(time < 0){
                    this.state = "prep";
                    time = 1;
                }
                this.animationGraphComponent.queueAnimation("walk");
                dx -= direction.x * dt * speed;
                dy -= direction.y * dt * speed;
            } else if(this.state.equals("prep")){
                Vec2d delta = this.player.getTransform().position.minus(this.gameObject.getTransform().position);
                this.direction = delta.normalize();
                if(time < 0){
                    this.state = "charge";
                    time = 3;
                }
                this.animationGraphComponent.queueAnimation("walk");
            }else if(this.state.equals("charge")){
                if(time <= 0) {
                    this.state = "idle";
                    this.player = null;
                }
                this.animationGraphComponent.queueAnimation("charge");
                dx -= direction.x * dt * speed * 2;
                dy -= direction.y * dt * speed * 2;
            }


            this.animationGraphComponent.updateState(new Vec2d[]{this.direction});
            Vec2d pos = this.gameObject.getTransform().position;
            this.gameObject.getTransform().position = new Vec2d(pos.x - dx, pos.y - dy);

        }

        public void followPlayer(GameObject player){
            if(this.player == null) {
                this.state = "follow";
                this.player = player;
                this.time = 2;
            }
        }

        @Override
        public int getSystemFlags() {
            return SystemFlag.TickSystem;
        }

        @Override
        public String getTag() {
            return "SkeletonMovementComponent";
        }
    }

    public static void skeletonNearPlayer(CollisionSystem.CollisionInfo collisionInfo){
        SkeletonMovementComponent gmc = (SkeletonMovementComponent)collisionInfo.gameObjectSelf.getComponent("SkeletonMovementComponent");
        if(gmc == null) return;
        IDComponent idComponent = (IDComponent)collisionInfo.gameObjectOther.getComponent("IDComponent");
        if(idComponent == null) return;
        if(!idComponent.getId().equals("player")) return;
        gmc.followPlayer(collisionInfo.gameObjectOther);

    }
}
