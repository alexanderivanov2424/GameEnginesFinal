package projects.final_project.characters;

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
import projects.WizTesting.WizGame;
import projects.WizTesting.WizPlayer;
import projects.final_project.FinalGame;
import projects.final_project.MiscElements;
import projects.final_project.levels.Enemies;

import java.util.concurrent.ThreadLocalRandom;

public class Slippy {

    //Walking(done), projectile(done), stomp(done), death(), flying sword, parry?

    private static final Vec2d SLIPPY_SIZE = new Vec2d(1.6,1.8);
    private static AnimationGraphComponent animationGraphComponent;

    public static void placeSlippy(GameWorld gameWorld, Vec2d pos){
        GameObject slippy = new GameObject(gameWorld, 1);
        animationGraphComponent = getSlippyAnimationGraph();
        slippy.addComponent(animationGraphComponent);
        slippy.addComponent(new SlippyMovementComponent(3, animationGraphComponent, gameWorld));

        slippy.addComponent(new CollisionComponent(new AABShape(new Vec2d(0.4,0.7),new Vec2d(0.7,0.7)),
                false, true, FinalGame.OBJECT_LAYER, FinalGame.OBJECT_MASK));

        CollisionComponent hitCollisionComponent = new CollisionComponent(new AABShape(new Vec2d(0.4,0.7),new Vec2d(0.7,0.7)),
                false, false, CollisionSystem.CollisionMask.NONE, FinalGame.ATTACK_MASK);
        hitCollisionComponent.linkCollisionCallback(Slippy::onHitCallback);
        slippy.addComponent(hitCollisionComponent);

        CollisionComponent nearPlayer = new CollisionComponent(new CircleShape(new Vec2d(0,0),10),
                false, false, FinalGame.OBJECT_LAYER, FinalGame.OBJECT_MASK);
        nearPlayer.linkCollisionCallback(Slippy::slippynearPlayer);
        slippy.addComponent(nearPlayer);

        HealthComponent healthComponent = new HealthComponent(7);
        healthComponent.linkDeathCallback(Slippy::onDeathCallback);
        slippy.addComponent(healthComponent);

        slippy.addComponent(new IDComponent("slippy"));

        slippy.getTransform().position = pos;
        slippy.getTransform().size = SLIPPY_SIZE;
        gameWorld.addGameObject(slippy);
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

    private static void onDeathCallback(GameObject enemy){
        CollisionComponent collision = (CollisionComponent)enemy.getComponent("CollisionComponent");
        collision.disable();

        animationGraphComponent.queueAnimation("death");
        //TODO freeze on dead frame?

        //Vec2d pos = enemy.getTransform().position;
        /*for(int i = 0; i < 5; i++) {
            MiscElements.placeCoin(enemy.gameWorld, 2, new Vec2d(pos.x, pos.y),
                    new Vec2d(Math.random() * 2 - 1, Math.random() * 2 - 1).normalize().smult(2));
        }*/
    }


    public static AnimationGraphComponent getSlippyAnimationGraph() {
        Vec2d spriteOffset = new Vec2d(0,0);
        Vec2d cropSizeWalk = new Vec2d(21,28);

        AnimationComponent idle_up = new SpriteAnimationComponent(FinalGame.getSpritePath("slippy"),
                spriteOffset, SLIPPY_SIZE, 1, new Vec2d(5,1*28), cropSizeWalk, new Vec2d(21,0), .05);
        AnimationComponent idle_left = new SpriteAnimationComponent(FinalGame.getSpritePath("slippy"),
                spriteOffset, SLIPPY_SIZE, 1, new Vec2d(5,2*28), cropSizeWalk, new Vec2d(21,0), .05);
        AnimationComponent idle_down = new SpriteAnimationComponent(FinalGame.getSpritePath("slippy"),
                spriteOffset, SLIPPY_SIZE, 1, new Vec2d(5,0*28), cropSizeWalk, new Vec2d(21,0), .05);
        AnimationComponent idle_right = new SpriteAnimationComponent(FinalGame.getSpritePath("slippy"),
                spriteOffset, SLIPPY_SIZE, 1, new Vec2d(5,3*28), cropSizeWalk, new Vec2d(21,0), .05);
        AGNode N_idle_up = new AGAnimation("idle_up", idle_up);
        AGNode N_idle_left = new AGAnimation("idle_left", idle_left);
        AGNode N_idle_down = new AGAnimation("idle_down", idle_down);
        AGNode N_idle_right = new AGAnimation("idle_right", idle_right);

        AnimationComponent walk_up = new SpriteAnimationComponent(FinalGame.getSpritePath("slippy"),
                spriteOffset, SLIPPY_SIZE, 7, new Vec2d(5,1*28), cropSizeWalk, new Vec2d(21,0), .1);
        AnimationComponent walk_left = new SpriteAnimationComponent(FinalGame.getSpritePath("slippy"),
                spriteOffset, SLIPPY_SIZE, 7, new Vec2d(5,2*28), cropSizeWalk, new Vec2d(21,0), .1);
        AnimationComponent walk_down = new SpriteAnimationComponent(FinalGame.getSpritePath("slippy"),
                spriteOffset, SLIPPY_SIZE, 7, new Vec2d(5,0*28), cropSizeWalk, new Vec2d(21,0), .1);
        AnimationComponent walk_right = new SpriteAnimationComponent(FinalGame.getSpritePath("slippy"),
                spriteOffset, SLIPPY_SIZE, 7, new Vec2d(5,3*28), cropSizeWalk, new Vec2d(21,0), .1);
        AGNode N_walk_up = new AGAnimation("walk_up", walk_up);
        AGNode N_walk_left = new AGAnimation("walk_left", walk_left);
        AGNode N_walk_down = new AGAnimation("walk_down", walk_down);
        AGNode N_walk_right = new AGAnimation("walk_right", walk_right);


        Vec2d cropSizeSpit = new Vec2d(24,24);
        Vec2d spittingSize = new Vec2d(1.8,1.8);

        AnimationComponent spit_up = new SpriteAnimationComponent(FinalGame.getSpritePath("slippy"),
                spriteOffset, spittingSize, 2, new Vec2d(7,4*28 + 24), cropSizeSpit, new Vec2d(24,0), .3);
        AnimationComponent spit_left = new SpriteAnimationComponent(FinalGame.getSpritePath("slippy"),
                spriteOffset, spittingSize, 2, new Vec2d(7 + 48,4*28), cropSizeSpit, new Vec2d(24,0), .3);
        AnimationComponent spit_down = new SpriteAnimationComponent(FinalGame.getSpritePath("slippy"),
                spriteOffset, spittingSize, 2, new Vec2d(7,4*28), cropSizeSpit, new Vec2d(24,0), .3);
        AnimationComponent spit_right = new SpriteAnimationComponent(FinalGame.getSpritePath("slippy"),
                spriteOffset, spittingSize, 2, new Vec2d(7 + 48,4*28 + 24), cropSizeSpit, new Vec2d(24,0), .3);
        AGNode N_spit_up = new AGAnimation("spit_up", spit_up);
        AGNode N_spit_left = new AGAnimation("spit_left", spit_left);
        AGNode N_spit_down = new AGAnimation("spit_down", spit_down);
        AGNode N_spit_right = new AGAnimation("spit_right", spit_right);

        AnimationComponent stomp = new SpriteAnimationComponent(FinalGame.getSpritePath("slippy"),
                new Vec2d(0,-0.2), new Vec2d(2,2.4), 4, new Vec2d(11,164), new Vec2d(28,38),
                new Vec2d(29,0), .3);

        AGNode N_stomp = new AGAnimation("stomp", stomp);

        AnimationComponent death = new SpriteAnimationComponent(FinalGame.getSpritePath("slippy"),
                spriteOffset, new Vec2d(1.45,1.6), 5, new Vec2d(0,217), new Vec2d(20,24),
                new Vec2d(20,0), .8);

        AGNode N_death = new AGAnimation("death", death);



        AGAnimationGroup idle = new AGAnimationGroup("idle",
                new AGNode[]{N_idle_up, N_idle_left, N_idle_down, N_idle_right},
                new Vec2d[]{new Vec2d(0,-1), new Vec2d(-1,0), new Vec2d(0,1), new Vec2d(1,0)});
        idle.setInterruptible(true);

        AGAnimationGroup walk = new AGAnimationGroup("walk",
                new AGNode[]{N_walk_up, N_walk_left, N_walk_down, N_walk_right},
                new Vec2d[]{new Vec2d(0,-1), new Vec2d(-1,0), new Vec2d(0,1), new Vec2d(1,0)});
        walk.setInterruptible(true);

        AGAnimationGroup spit = new AGAnimationGroup("spit",
                new AGNode[]{N_spit_up, N_spit_left, N_spit_down, N_spit_right},
                new Vec2d[]{new Vec2d(0,-1), new Vec2d(-1,0), new Vec2d(0,1), new Vec2d(1,0)});
        walk.setInterruptible(false);

        AGAnimationGroup stompAG = new AGAnimationGroup("stomp",
                new AGNode[]{N_stomp},
                new Vec2d[]{new Vec2d(0,1)});
        walk.setInterruptible(false);

        AGAnimationGroup deathAG = new AGAnimationGroup("death",
                new AGNode[]{N_death},
                new Vec2d[]{new Vec2d(0,1)});
        walk.setInterruptible(false);


        AGNode[] animationNodes = new AGNode[]{idle, walk, spit, stompAG, deathAG};
        AnimationGraphComponent agc = new AnimationGraphComponent(animationNodes);

        return agc;
    }

    public static void createProjectile(GameWorld gameWorld, GameObject target, Vec2d position) {
        GameObject projectile = new GameObject(gameWorld, 2);

        projectile.addComponent(new SpriteComponent(FinalGame.getSpritePath("projectile"),
                new Vec2d(0,0), new Vec2d(1,1)));

        CollisionComponent collisionComponent = new CollisionComponent(new CircleShape(new Vec2d(.5,.5),.3),
                false, true, FinalGame.OBJECT_LAYER, FinalGame.OBJECT_MASK);
        collisionComponent.linkCollisionCallback(Slippy::projectileCollisionCallback);
        projectile.addComponent(collisionComponent);

        projectile.addComponent(new VelocityComponent(new Vec2d(target.getTransform().position.minus(position)
        .smult(1.5))));

        projectile.getTransform().position = position;
        projectile.getTransform().size = new Vec2d(1.5,1.5);
        gameWorld.addGameObject(projectile);
    }

    private static void projectileCollisionCallback(CollisionSystem.CollisionInfo collisionInfo){
        IDComponent id = (IDComponent)collisionInfo.gameObjectOther.getComponent("IDComponent");
        if(id == null){
            collisionInfo.gameObjectSelf.gameWorld.removeGameObject(collisionInfo.gameObjectSelf);
        }
        else if(id.getId().equals("slippy")) {
            return;
        }
        HealthComponent health = (HealthComponent)collisionInfo.gameObjectOther.getComponent("HealthComponent");
        if(health != null){
            health.hit(1);
        }
        collisionInfo.gameObjectSelf.gameWorld.removeGameObject(collisionInfo.gameObjectSelf);

    }

    private static class SlippyMovementComponent extends Component {

        private Vec2d direction = new Vec2d(0,0);
        private double speed, time = 2, spitCooldown = 0.6;

        private String state = "idle"; // idle, follow, prep, spit, stomp

        public GameObject player;

        private AnimationGraphComponent animationGraphComponent;
        private GameWorld gameWorld;

        public SlippyMovementComponent(double speed, AnimationGraphComponent animationGraphComponent, GameWorld gameWorld) {
            super();
            this.speed = speed;
            this.animationGraphComponent = animationGraphComponent;
            this.gameWorld = gameWorld;
        }


        //Every couple seconds, choose a new direction to go and walk in that direction
        @Override
        public void onTick(long nanosSincePreviousTick){
            double dx = 0;
            double dy = 0;
            double dt = nanosSincePreviousTick/1000000000.0; //seconds since last tick

            time -= dt;
            spitCooldown -= dt;

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

            }

            else if(this.state.equals("follow")){
                Vec2d delta = this.player.getTransform().position.minus(this.gameObject.getTransform().position);
                this.direction = delta.normalize();
                if(time < 0){
                    this.state = "prep";
                    time = 0.5;
                }
                this.animationGraphComponent.queueAnimation("walk");
                dx -= direction.x * dt * speed;
                dy -= direction.y * dt * speed;
            }

            else if(this.state.equals("prep")){
                Vec2d delta = this.player.getTransform().position.minus(this.gameObject.getTransform().position);
                this.direction = delta.normalize();
                if(time < 0){
                    int pickAttack = ThreadLocalRandom.current().nextInt(0, 2);
                    if(pickAttack == 0) {
                        this.state = "spit";
                        time = 3;
                    }
                    else {
                        this.state = "stomp";
                        time = 0.9;
                    }
                }
                this.animationGraphComponent.queueAnimation("idle");
            }
            else if(this.state.equals("spit")){
                if(spitCooldown <= 0) {

                    createProjectile(gameWorld, player, this.gameObject.getTransform().position);
                    spitCooldown = 0.6;
                }
                if(time <= 0) {
                    this.state = "idle";
                    this.player = null;
                    //TODO issue where slippy will move during spit animation.
                }

                this.animationGraphComponent.queueAnimation("spit");
                dx = 0;
                dy = 0;
            }
            else if(this.state.equals("stomp")){
                if(time <= 0) {
                    this.state = "idle";
                    this.player = null;
                }
                this.animationGraphComponent.queueAnimation("stomp");

                if(time <= 0.3) {
                    for(GameObject object : gameWorld.getGameObjects()) {
                        if(!object.equals(this.gameObject)) {
                            if(object.getTransform().position.dist(this.gameObject.getTransform().position) < 5.5) {
                                if(object.getComponent("ShakeComponent") == null) {
                                    object.addComponent(new ShakeComponent(.1, .2));
                                }
                                if(object.getComponent("WASDMovementComponent") != null) {
                                    object.getComponent("WASDMovementComponent").disable();
                                }
                            }
                        }
                    }
                }

                dx = 0;
                dy = 0;
            }
            //TODO summon goomba.


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
            return "SlippyMovementComponent";
        }
    }

    public static void slippynearPlayer(CollisionSystem.CollisionInfo collisionInfo){
        SlippyMovementComponent smc = (SlippyMovementComponent)collisionInfo.gameObjectSelf.getComponent("SlippyMovementComponent");
        if(smc == null) return;
        IDComponent idComponent = (IDComponent)collisionInfo.gameObjectOther.getComponent("IDComponent");
        if(idComponent == null) return;
        if(!idComponent.getId().equals("player")) return;
        smc.followPlayer(collisionInfo.gameObjectOther);

    }


}
