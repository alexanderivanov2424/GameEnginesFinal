package projects.final_project;

import engine.game.GameObject;
import engine.game.GameWorld;
import engine.game.collisionShapes.AABShape;
import engine.game.components.Animation.AnimationComponent;
import engine.game.components.Animation.AnimationSystem.AGAnimationGroup;
import engine.game.components.Animation.AnimationSystem.AGNode;
import engine.game.components.Animation.AnimationSystem.AnimationGraphComponent;
import engine.game.components.Animation.SpriteAnimationComponent;
import engine.game.components.CameraComponent;
import engine.game.components.CollisionComponent;
import engine.game.components.Component;
import engine.game.components.WASDMovementComponent;
import engine.game.systems.SystemFlag;
import engine.support.Vec2d;
import javafx.scene.input.KeyCode;

import java.util.Set;

public class Player {

    public static final Vec2d PLAYER_SIZE = new Vec2d(2,2);


    //creates player
    public static GameObject createPlayer(GameWorld gameWorld, Vec2d pos){
        GameObject player = new GameObject(gameWorld, 3);

        player.addComponent(new CameraComponent(0));
        AnimationGraphComponent agc = createPlayerAnimationGraph();
        player.addComponent(agc);

        player.addComponent(new PlayerMovementComponent(5,agc));

        player.addComponent(new CollisionComponent(new AABShape(new Vec2d(.3,1.5),new Vec2d(1.4,.5)),
                false, true, FinalGame.PLAYER_LAYER, FinalGame.PLAYER_MASK));

        player.getTransform().position = pos;
        player.getTransform().size = new Vec2d(1.4,1.75);
        return player;
    }

    private static AnimationGraphComponent createPlayerAnimationGraph(){
        AnimationComponent idle_up = new SpriteAnimationComponent(FinalGame.getSpritePath("player"),
                new Vec2d(0,0), PLAYER_SIZE, 1, new Vec2d(0,8*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent idle_left = new SpriteAnimationComponent(FinalGame.getSpritePath("player"),
                new Vec2d(0,0), PLAYER_SIZE, 1, new Vec2d(0,9*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent idle_down = new SpriteAnimationComponent(FinalGame.getSpritePath("player"),
                new Vec2d(0,0), PLAYER_SIZE, 1, new Vec2d(0,10*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent idle_right = new SpriteAnimationComponent(FinalGame.getSpritePath("player"),
                new Vec2d(0,0), PLAYER_SIZE, 1, new Vec2d(0,11*64), new Vec2d(64,64), new Vec2d(64,0), .05);

        AnimationComponent walk_up = new SpriteAnimationComponent(FinalGame.getSpritePath("player"),
                new Vec2d(0,0), PLAYER_SIZE, 9, new Vec2d(0,8*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent walk_left = new SpriteAnimationComponent(FinalGame.getSpritePath("player"),
                new Vec2d(0,0), PLAYER_SIZE, 9, new Vec2d(0,9*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent walk_down = new SpriteAnimationComponent(FinalGame.getSpritePath("player"),
                new Vec2d(0,0), PLAYER_SIZE, 9, new Vec2d(0,10*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent walk_right = new SpriteAnimationComponent(FinalGame.getSpritePath("player"),
                new Vec2d(0,0), PLAYER_SIZE, 9, new Vec2d(0,11*64), new Vec2d(64,64), new Vec2d(64,0), .05);

        AnimationComponent attack_up = new SpriteAnimationComponent(FinalGame.getSpritePath("player"),
                new Vec2d(0,0), PLAYER_SIZE, 6, new Vec2d(0,12*64), new Vec2d(64,64), new Vec2d(64,0), .1);
        AnimationComponent attack_left = new SpriteAnimationComponent(FinalGame.getSpritePath("player"),
                new Vec2d(0,0), PLAYER_SIZE, 6, new Vec2d(0,13*64), new Vec2d(64,64), new Vec2d(64,0), .1);
        AnimationComponent attack_down = new SpriteAnimationComponent(FinalGame.getSpritePath("player"),
                new Vec2d(0,0), PLAYER_SIZE, 6, new Vec2d(0,14*64), new Vec2d(64,64), new Vec2d(64,0), .1);
        AnimationComponent attack_right = new SpriteAnimationComponent(FinalGame.getSpritePath("player"),
                new Vec2d(0,0), PLAYER_SIZE, 6, new Vec2d(0,15*64), new Vec2d(64,64), new Vec2d(64,0), .1);

        AGAnimationGroup idle = new AGAnimationGroup("idle",
                new AnimationComponent[]{idle_up, idle_left, idle_down, idle_right},
                new Vec2d[]{new Vec2d(0,-1), new Vec2d(-1,0), new Vec2d(0,1), new Vec2d(1,0)});
        idle.setInterruptible(true);

        AGAnimationGroup walk = new AGAnimationGroup("walk",
                new AnimationComponent[]{walk_up, walk_left, walk_down, walk_right},
                new Vec2d[]{new Vec2d(0,-1), new Vec2d(-1,0), new Vec2d(0,1), new Vec2d(1,0)});
        walk.setInterruptible(true);

        AGAnimationGroup attack = new AGAnimationGroup("attack", "idle",
                new AnimationComponent[]{attack_up, attack_left, attack_down, attack_right},
                new Vec2d[]{new Vec2d(0,-1), new Vec2d(-1,0), new Vec2d(0,1), new Vec2d(1,0)});
        attack.setInterruptible(false);

        AGNode[] animationNodes = new AGNode[]{idle, walk, attack};
        return new AnimationGraphComponent(animationNodes);
    }

    private static class PlayerMovementComponent extends Component{

        private Vec2d direction = new Vec2d(0,1);
        private double speed;

        private AnimationGraphComponent animationGraphComponent;

        public PlayerMovementComponent(double speed, AnimationGraphComponent animationGraphComponent) {
            super();
            this.speed = speed;
            this.animationGraphComponent = animationGraphComponent;
        }

        @Override
        public void onTick(long nanosSincePreviousTick){
            double dx = 0;
            double dy = 0;
            double dt = nanosSincePreviousTick/1000000000.0; //seconds since last tick

            Set<KeyCode> keyState = this.gameObject.gameWorld.getKeyState();

            boolean W = keyState.contains(KeyCode.W);
            boolean A = keyState.contains(KeyCode.A);
            boolean S = keyState.contains(KeyCode.S);
            boolean D = keyState.contains(KeyCode.D);
            boolean ATTACK = keyState.contains(KeyCode.SPACE);
            if(W) dy += speed * dt;
            if(A) dx += speed * dt;
            if(S) dy -= speed * dt;
            if(D) dx -= speed * dt;

            if(!(W || A || S || D || ATTACK)){
                this.animationGraphComponent.queueAnimation("idle", true);
            } else if(!ATTACK) {
                this.direction = new Vec2d(-dx, -dy);
                this.animationGraphComponent.queueAnimation("walk");
            } else {
                this.animationGraphComponent.queueAnimation("attack", true);
            }
            this.animationGraphComponent.updateState(this.direction);

            Vec2d pos = this.gameObject.getTransform().position;
            this.gameObject.getTransform().position = new Vec2d(pos.x - dx, pos.y - dy);
        }

        @Override
        public int getSystemFlags() {
            return SystemFlag.TickSystem;
        }

        @Override
        public String getTag() {
            return "PlayerMovementComponent";
        }
    }

}
