package projects.final_project;

import engine.game.GameObject;
import engine.game.GameWorld;
import engine.game.collisionShapes.CircleShape;
import engine.game.components.*;
import engine.game.components.animation.animationGraph.AGAnimationGroup;
import engine.game.components.animation.animationGraph.AGNode;
import engine.game.components.animation.animationGraph.AnimationGraphComponent;
import engine.game.components.animation.SpriteAnimationComponent;
import engine.game.components.animation.AnimationComponent;
import engine.game.systems.SystemFlag;
import engine.support.Vec2d;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.Set;

public class Player {

    public static final Vec2d PLAYER_SIZE = new Vec2d(2,2);
    protected static AudioComponent swing;

    //creates player
    public static GameObject createPlayer(GameWorld gameWorld, Vec2d pos){
        GameObject player = new GameObject(gameWorld, 1);

        player.addComponent(new CameraComponent(0, new Vec2d(0,40), new Vec2d(0, 40)));

        AnimationGraphComponent agc = getPlayerAnimationGraph();
        player.addComponent(agc);

        player.addComponent(new PlayerMovementComponent(5,agc));

        LightComponent lightComponent = new LightComponent(Color.WHITE, 5, new Vec2d(1,1.5));
        player.addComponent(lightComponent);

        DrawFogComponent drawFogComponent = new DrawFogComponent(0, new Vec2d(0,0), .05, 1);
        drawFogComponent.disable();
        player.addComponent(drawFogComponent);

        swing = new AudioComponent("swing.wav", true);
        player.addComponent(swing);

        player.addComponent(new CollisionComponent(new CircleShape(new Vec2d(0,0),.25),
                false, true, FinalGame.PLAYER_LAYER, FinalGame.PLAYER_MASK));

        TextBoxComponent textBoxComponent = new TextBoxComponent(0, new Vec2d(0,-1),
                "Hey, you...\nYou're finally awake", .1, .2, .2, 1);
        textBoxComponent.setCenterImage(FinalGame.getSpritePath("textbox"),
                new Vec2d(270,375), new Vec2d(25,25), new Vec2d(0,0));
        textBoxComponent.setCornerImage(FinalGame.getSpritePath("textbox"),
                new Vec2d(254,359), new Vec2d(13,13), new Vec2d(.25,.25));
        textBoxComponent.setHorizontalEdgeImage(FinalGame.getSpritePath("textbox"),
                new Vec2d(271,359), new Vec2d(25,13), new Vec2d(.25,.25));
        textBoxComponent.setVerticalEdgeImage(FinalGame.getSpritePath("textbox"),
                new Vec2d(254,375), new Vec2d(13,25), new Vec2d(.25,.25));
        player.addComponent(textBoxComponent);

        player.getTransform().position = pos;
        player.getTransform().size = new Vec2d(1.4,1.75);
        return player;
    }

    private static AnimationGraphComponent getPlayerAnimationGraph(){
        Vec2d spriteOffset = new Vec2d(-1,-2);
        AnimationComponent idle_up = new SpriteAnimationComponent(FinalGame.getSpritePath("player"),
                spriteOffset, PLAYER_SIZE, 1, new Vec2d(0,8*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent idle_left = new SpriteAnimationComponent(FinalGame.getSpritePath("player"),
                spriteOffset, PLAYER_SIZE, 1, new Vec2d(0,9*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent idle_down = new SpriteAnimationComponent(FinalGame.getSpritePath("player"),
                spriteOffset, PLAYER_SIZE, 1, new Vec2d(0,10*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent idle_right = new SpriteAnimationComponent(FinalGame.getSpritePath("player"),
                spriteOffset, PLAYER_SIZE, 1, new Vec2d(0,11*64), new Vec2d(64,64), new Vec2d(64,0), .05);

        AnimationComponent walk_up = new SpriteAnimationComponent(FinalGame.getSpritePath("player"),
                spriteOffset, PLAYER_SIZE, 9, new Vec2d(0,8*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent walk_left = new SpriteAnimationComponent(FinalGame.getSpritePath("player"),
                spriteOffset, PLAYER_SIZE, 9, new Vec2d(0,9*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent walk_down = new SpriteAnimationComponent(FinalGame.getSpritePath("player"),
                spriteOffset, PLAYER_SIZE, 9, new Vec2d(0,10*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent walk_right = new SpriteAnimationComponent(FinalGame.getSpritePath("player"),
                spriteOffset, PLAYER_SIZE, 9, new Vec2d(0,11*64), new Vec2d(64,64), new Vec2d(64,0), .05);

        AnimationComponent attack_up = new SpriteAnimationComponent(FinalGame.getSpritePath("player"),
                spriteOffset, PLAYER_SIZE, 6, new Vec2d(0,12*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent attack_left = new SpriteAnimationComponent(FinalGame.getSpritePath("player"),
                spriteOffset, PLAYER_SIZE, 6, new Vec2d(0,13*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent attack_down = new SpriteAnimationComponent(FinalGame.getSpritePath("player"),
                spriteOffset, PLAYER_SIZE, 6, new Vec2d(0,14*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent attack_right = new SpriteAnimationComponent(FinalGame.getSpritePath("player"),
                spriteOffset, PLAYER_SIZE, 6, new Vec2d(0,15*64), new Vec2d(64,64), new Vec2d(64,0), .05);

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
        AnimationGraphComponent agc = new AnimationGraphComponent(animationNodes);

        return agc;
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
                swing.stop();
                this.animationGraphComponent.queueAnimation("idle", true);
            } else if(!ATTACK) {
                swing.stop();
                this.direction = new Vec2d(-dx, -dy);
                this.animationGraphComponent.queueAnimation("walk");
            } else {
                this.animationGraphComponent.queueAnimation("attack", true);

                swing.start();

                for(GameObject object : gameObject.gameWorld.getGameObjects()) {

                    //TODO very scuffed way of doing damage
                    if(object.getTransform().position.dist(this.getGameObject().getTransform().position) < 4) {
                        if(! (object.getComponent("HealthComponent") == null)) {
                            HealthComponent healthComponent = (HealthComponent)object.getComponent("HealthComponent");
                            healthComponent.hit(.1);
                        }
                    }
                }


            }
            this.animationGraphComponent.updateState(this.direction);

            Vec2d pos = this.gameObject.getTransform().position;
            this.gameObject.getTransform().position = new Vec2d(pos.x - dx, pos.y - dy);
            //System.out.println(new Vec2d(pos.x - dx, pos.y - dy));

            TextBoxComponent tb = (TextBoxComponent)this.gameObject.getComponent("TextBoxComponent");
            if(tb != null){
                if(keyState.contains(KeyCode.I)){
                    tb.open();
                }
                if(keyState.contains(KeyCode.K)){
                    tb.close();
                }
            }
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
