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
import engine.game.components.TextBoxComponent;
import engine.game.systems.CollisionSystem;
import engine.game.systems.SystemFlag;
import engine.support.Vec2d;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import projects.WizTesting.WizGame;
import projects.WizTesting.WizPlayer;

import java.util.Set;

public class Player {

    public static boolean isBetweenAreas = false;

    public static final Vec2d PLAYER_SIZE = new Vec2d(2,2);
    protected static AudioComponent swing;

    //creates player
    public static GameObject createPlayer(GameWorld gameWorld, Vec2d pos){
        GameObject player = new GameObject(gameWorld, 1);

        player.addComponent(new CameraComponent(0, new Vec2d(0,0), new Vec2d(0,40), new Vec2d(0, 40)));

        CollisionComponent attackHitBox = new CollisionComponent(new CircleShape(new Vec2d(0,0),1),
                false, false, FinalGame.ATTACK_LAYER, FinalGame.ATTACK_MASK){
                    @Override
                    public String getTag() {
                return "AttackCollisionComponent";
            }
                };
        player.addComponent(attackHitBox);

        AnimationGraphComponent agc = getPlayerAnimationGraph(attackHitBox);
        player.addComponent(agc);

        player.addComponent(new PlayerMovementComponent(5,agc));

        LightComponent lightComponent = new LightComponent(Color.WHITE, 5, new Vec2d(0,0));
        player.addComponent(lightComponent);

        DrawFogComponent drawFogComponent = new DrawFogComponent(0, new Vec2d(0,0), .05, 1);
        drawFogComponent.disable();
        player.addComponent(drawFogComponent);

        swing = new AudioComponent("swing.wav", true);
        player.addComponent(swing);

        CollisionComponent hitbox = new CollisionComponent(new CircleShape(new Vec2d(0,0),.25),
                false, true, FinalGame.PLAYER_LAYER, FinalGame.PLAYER_MASK);
        hitbox.linkCollisionCallback(Player::PlayerCollisionCallback);
        player.addComponent(hitbox);

        HealthComponent healthComponent = new HealthComponent(10);
        player.addComponent(healthComponent);

        //TALKING TRIGGER
        //TODO this is very bad design needs to be fixed at some point
        // need to somehow differentiate between multiple components of same type. Maybe give components names?
        CollisionComponent talkTrigger = new CollisionComponent(new CircleShape(new Vec2d(0,0),1),
                false, false, FinalGame.TALK_LAYER, FinalGame.TALK_MASK){
                    @Override
                    public String getTag() {
                        return "TalkCollisionComponent";
                    }
                };
        player.addComponent(talkTrigger);

        player.getTransform().position = pos;
        player.getTransform().size = new Vec2d(1.4,1.75);
        return player;
    }

    private static void PlayerCollisionCallback(CollisionSystem.CollisionInfo collisionInfo){
        GameObject player = collisionInfo.gameObjectSelf;
        IDComponent id = (IDComponent)collisionInfo.gameObjectOther.getComponent("IDComponent");

        if(id != null && id.getId().equals("goomba")){
            //TODO HURT ANIMATION

            ((HealthComponent)(player.getComponent("HealthComponent"))).hit(0.1);

            /*DelayEventComponent delayEventComponent = new DelayEventComponent(.5);
            delayEventComponent.linkEventCallback(WizPlayer::playerDeath);
            player.addComponent(delayEventComponent);*/
        }
    }


    private static AnimationGraphComponent getPlayerAnimationGraph(CollisionComponent playerAttackBox){
        Vec2d spriteOffset = new Vec2d(-1,-2);
        AnimationComponent idle_up = new SpriteAnimationComponent(FinalGame.getSpritePath("player"),
                spriteOffset, PLAYER_SIZE, 1, new Vec2d(0,8*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent idle_left = new SpriteAnimationComponent(FinalGame.getSpritePath("player"),
                spriteOffset, PLAYER_SIZE, 1, new Vec2d(0,9*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent idle_down = new SpriteAnimationComponent(FinalGame.getSpritePath("player"),
                spriteOffset, PLAYER_SIZE, 1, new Vec2d(0,10*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent idle_right = new SpriteAnimationComponent(FinalGame.getSpritePath("player"),
                spriteOffset, PLAYER_SIZE, 1, new Vec2d(0,11*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        idle_up.addAnimationSequence(playerAttackBox.disabled, new Boolean[]{true});
        idle_left.addAnimationSequence(playerAttackBox.disabled, new Boolean[]{true});
        idle_down.addAnimationSequence(playerAttackBox.disabled, new Boolean[]{true});
        idle_right.addAnimationSequence(playerAttackBox.disabled, new Boolean[]{true});

        AnimationComponent walk_up = new SpriteAnimationComponent(FinalGame.getSpritePath("player"),
                spriteOffset, PLAYER_SIZE, 9, new Vec2d(0,8*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent walk_left = new SpriteAnimationComponent(FinalGame.getSpritePath("player"),
                spriteOffset, PLAYER_SIZE, 9, new Vec2d(0,9*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent walk_down = new SpriteAnimationComponent(FinalGame.getSpritePath("player"),
                spriteOffset, PLAYER_SIZE, 9, new Vec2d(0,10*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent walk_right = new SpriteAnimationComponent(FinalGame.getSpritePath("player"),
                spriteOffset, PLAYER_SIZE, 9, new Vec2d(0,11*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        walk_up.addAnimationSequence(playerAttackBox.disabled, new Boolean[]{true,true,true,true,true,true,true,true,true});
        walk_left.addAnimationSequence(playerAttackBox.disabled, new Boolean[]{true,true,true,true,true,true,true,true,true});
        walk_down.addAnimationSequence(playerAttackBox.disabled, new Boolean[]{true,true,true,true,true,true,true,true,true});
        walk_right.addAnimationSequence(playerAttackBox.disabled, new Boolean[]{true,true,true,true,true,true,true,true,true});

        AnimationComponent attack_up = new SpriteAnimationComponent(FinalGame.getSpritePath("player"),
                spriteOffset, PLAYER_SIZE, 6, new Vec2d(0,12*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent attack_left = new SpriteAnimationComponent(FinalGame.getSpritePath("player"),
                spriteOffset, PLAYER_SIZE, 6, new Vec2d(0,13*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent attack_down = new SpriteAnimationComponent(FinalGame.getSpritePath("player"),
                spriteOffset, PLAYER_SIZE, 6, new Vec2d(0,14*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent attack_right = new SpriteAnimationComponent(FinalGame.getSpritePath("player"),
                spriteOffset, PLAYER_SIZE, 6, new Vec2d(0,15*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        attack_up.addAnimationSequence(playerAttackBox.disabled, new Boolean[]{true,false,false,false,false,true});
        attack_up.addAnimationSequence(playerAttackBox.position,
                new Vec2d[]{new Vec2d(0,-1),new Vec2d(0,-2),new Vec2d(0,-3),new Vec2d(0,-2),new Vec2d(0,-1),new Vec2d(0,0)});

        attack_left.addAnimationSequence(playerAttackBox.disabled, new Boolean[]{true,false,false,false,false,true});
        attack_left.addAnimationSequence(playerAttackBox.position,
                new Vec2d[]{new Vec2d(-1,0),new Vec2d(-2,0),new Vec2d(-3,0),new Vec2d(-2,0),new Vec2d(-1,0),new Vec2d(0,0)});

        attack_down.addAnimationSequence(playerAttackBox.disabled, new Boolean[]{true,false,false,false,false,true});
        attack_down.addAnimationSequence(playerAttackBox.position,
                new Vec2d[]{new Vec2d(0,1),new Vec2d(0,2),new Vec2d(0,3),new Vec2d(0,2),new Vec2d(0,1),new Vec2d(0,0)});

        attack_right.addAnimationSequence(playerAttackBox.disabled, new Boolean[]{true,false,false,false,false,true});
        attack_right.addAnimationSequence(playerAttackBox.position,
                new Vec2d[]{new Vec2d(1,0),new Vec2d(2,0),new Vec2d(3,0),new Vec2d(2,0),new Vec2d(1,0),new Vec2d(0,0)});

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
            boolean E = keyState.contains(KeyCode.E);
            if(W) dy += speed * dt;
            if(S) dy -= speed * dt;
            if(A) dx += speed * dt;
            if(D) dx -= speed * dt;

            if(!(W || A || S || D || ATTACK)){
                swing.stop();
                this.animationGraphComponent.queueAnimation("idle", true);
            } else if(!ATTACK) {
                swing.stop();
                this.direction = new Vec2d(-dx, -dy);
                this.animationGraphComponent.queueAnimation("walk");

                Vec2d pos = this.gameObject.getTransform().position;
                this.gameObject.getTransform().position = new Vec2d(pos.x - dx, pos.y - dy);
                //System.out.println(new Vec2d(pos.x - dx, pos.y - dy));
            } else {
                this.animationGraphComponent.queueAnimation("attack", true);

                swing.start();

                for(GameObject object : gameObject.gameWorld.getGameObjects()) {

                    //TODO very scuffed way of doing damage
                    if(!object.equals(this.getGameObject()) &&
                            object.getTransform().position.dist(this.getGameObject().getTransform().position) < 4) {
                        if(! (object.getComponent("HealthComponent") == null)) {
                            HealthComponent healthComponent = (HealthComponent)object.getComponent("HealthComponent");
                            healthComponent.hit(.1);
                        }
                    }
                }
            }

            CollisionComponent talk = (CollisionComponent)this.gameObject.getComponent("TalkCollisionComponent");
            if(talk != null){
                if(E){
                    talk.enable();
                } else {
                    talk.disable();
                }
            }
            this.animationGraphComponent.updateState(this.direction);



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
