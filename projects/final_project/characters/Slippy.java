package projects.final_project.characters;

import engine.game.GameObject;
import engine.game.GameWorld;
import engine.game.components.Component;
import engine.game.components.animation.AnimationComponent;
import engine.game.components.animation.SpriteAnimationComponent;
import engine.game.components.animation.animationGraph.AGAnimation;
import engine.game.components.animation.animationGraph.AGAnimationGroup;
import engine.game.components.animation.animationGraph.AGNode;
import engine.game.components.animation.animationGraph.AnimationGraphComponent;
import engine.game.systems.SystemFlag;
import engine.support.Vec2d;
import projects.final_project.FinalGame;

import java.util.concurrent.ThreadLocalRandom;

public class Slippy {

    //Walking(done), projectile(done), stomp(), death(), flying sword, parry?

    private static final Vec2d SLIPPY_SIZE = new Vec2d(1.8,1.8);

    public static void placeSlippy(GameWorld gameWorld, Vec2d pos){
        GameObject slippy = new GameObject(gameWorld, 1);
        AnimationGraphComponent agc = getSlippyAnimationGraph();
        slippy.addComponent(agc);
        slippy.addComponent(new SlippyMovementComponent(0.1, agc));

        slippy.getTransform().position = pos;
        slippy.getTransform().size = SLIPPY_SIZE;
        gameWorld.addGameObject(slippy);
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
        Vec2d spittingSize = new Vec2d(2,2);

        AnimationComponent spit_up = new SpriteAnimationComponent(FinalGame.getSpritePath("slippy"),
                spriteOffset, spittingSize, 2, new Vec2d(7,4*28 + 24), cropSizeSpit, new Vec2d(24,0), .5);
        AnimationComponent spit_left = new SpriteAnimationComponent(FinalGame.getSpritePath("slippy"),
                spriteOffset, spittingSize, 2, new Vec2d(7 + 48,4*28), cropSizeSpit, new Vec2d(24,0), .5);
        AnimationComponent spit_down = new SpriteAnimationComponent(FinalGame.getSpritePath("slippy"),
                spriteOffset, spittingSize, 2, new Vec2d(7,4*28), cropSizeSpit, new Vec2d(24,0), .5);
        AnimationComponent spit_right = new SpriteAnimationComponent(FinalGame.getSpritePath("slippy"),
                spriteOffset, spittingSize, 2, new Vec2d(7 + 48,4*28 + 24), cropSizeSpit, new Vec2d(24,0), .5);
        AGNode N_spit_up = new AGAnimation("spit_up", spit_up);
        AGNode N_spit_left = new AGAnimation("spit_left", spit_left);
        AGNode N_spit_down = new AGAnimation("spit_down", spit_down);
        AGNode N_spit_right = new AGAnimation("spit_right", spit_right);

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

        AGNode[] animationNodes = new AGNode[]{idle, walk, spit};
        AnimationGraphComponent agc = new AnimationGraphComponent(animationNodes);

        return agc;
    }

    private static class SlippyMovementComponent extends Component {

        private String direction = "down";
        private double speed, time = 2;

        private AnimationGraphComponent animationGraphComponent;

        public SlippyMovementComponent(double speed, AnimationGraphComponent animationGraphComponent) {
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
                this.animationGraphComponent.queueAnimation("spit");

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
            return "SlippyMovementComponent";
        }
    }
}
