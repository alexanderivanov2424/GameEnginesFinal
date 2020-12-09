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
import engine.game.systems.CollisionSystem;
import engine.support.Vec2d;
import javafx.scene.paint.Color;
import projects.final_project.*;
import projects.final_project.assets.sounds.AnimationGraphComponent;
import projects.final_project.levels.Area3;

public class Frog {

    private static final Vec2d FROG_SIZE = new Vec2d(1,1);

    public static void placeFrog(GameWorld gameWorld, Vec2d pos){
        GameObject frog = new GameObject(gameWorld, 1);
        frog.addComponent(getFrogAnimationGraph());


        frog.addComponent(new CollisionComponent(new AABShape(new Vec2d(-.35,-0.7),new Vec2d(0.7,0.7)),
                false, true,  FinalGame.ENEMY_LAYER, FinalGame.ENEMY_MASK));

        CollisionComponent hitCollisionComponent = new CollisionComponent(new AABShape(new Vec2d(-.35,-0.7),new Vec2d(0.7,0.7)),
                false, false, CollisionSystem.CollisionMask.NONE, FinalGame.ATTACK_MASK);
        hitCollisionComponent.linkCollisionCallback(Frog::onHitCallback);
        frog.addComponent(hitCollisionComponent);

        HealthComponent healthComponent = new HealthComponent(20); //TODO Change hp so fair difficulty
        healthComponent.linkDeathCallback(Frog::onDeathCallback);
        frog.addComponent(healthComponent);

        frog.addComponent(new HealthBarComponent(Color.RED, new Vec2d(0,-2), new Vec2d(2,.2), healthComponent, true));

        CollisionComponent talk = new CollisionComponent(new CircleShape(new Vec2d(0,0),1),
                false, false, FinalGame.TALK_LAYER, FinalGame.TALK_MASK);
        talk.linkCollisionCallback(OldMan::speakToOldMan);
        frog.addComponent(talk);

        DialogComponent dc = new DialogComponent(getDialog(), Characters.createTextBox(), Characters.createOptionBox());
        frog.addComponent(dc);


        frog.addComponent(new IDComponent("slippy"));
        frog.addComponent(new AudioComponent("slippyDamage.wav"));

        frog.getTransform().position = pos;
        frog.getTransform().size = FROG_SIZE;
        gameWorld.addGameObject(frog);
    }

    private static DialogNode getDialog(){
        DialogNode A = new DialogNode("Oh, what a wonderful day it would have been!\n" +
                "I was going to chop down my tree and then rest my cold old\n" +
                "bones by the fireplace this winter.");
        DialogNode B = new DialogNode("But that DARN TOAD STOLE MY TREEE!");
        DialogNode C = new DialogNode("*cough* *cough*\n" +
                "Oh well, I guess I don't need my axe anymore.");
        DialogNode D = new DialogNode("Here, you can have it. Maybe you can make better use of it.\n" +
                "\n" +
                "Just press Q to switch to it and get swinging");

        DialogNode E = new DialogNode("Kids these days. Always swinging things all over the place.");

        A.setNextNode(B);
        B.setNextNode(C);
        C.setNextNode(D);
        D.setNextNode(E);
        return A;
    }

    public static void onHitCallback(CollisionSystem.CollisionInfo collisionInfo){
        if(collisionInfo.gameObjectSelf.getComponent("ShakeComponent") == null) {
            collisionInfo.gameObjectSelf.addComponent(new ShakeComponent(.1, .1));
            ((AudioComponent)(collisionInfo.gameObjectSelf.getComponent("AudioComponent"))).start();
            Player.PlayerComponent playerComponent = (Player.PlayerComponent)collisionInfo.gameObjectOther.getComponent("PlayerComponent");
            HealthComponent health = (HealthComponent)collisionInfo.gameObjectSelf.getComponent("HealthComponent");
            if(health != null && playerComponent != null){
                health.hit(playerComponent.getAttack());
            }
        }
    }

    private static void onDeathCallback(GameObject frog){
        DelayEventComponent delayEventComponent = new DelayEventComponent(.1);
        delayEventComponent.linkEventCallback(Frog::enemyRemoveCallback);
        frog.addComponent(delayEventComponent);

        //TODO particles

        BackgroundMusic.stopBGM(frog.gameWorld);
        BackgroundMusic.playBossBGM(frog.gameWorld);
    }

    private static void enemyRemoveCallback(GameObject gameObject){
        gameObject.gameWorld.removeGameObject(gameObject);
        Vec2d pos = gameObject.getTransform().position;

        Slippy.placeSlippy(gameObject.gameWorld, pos);
    }


    public static AnimationGraphComponent getFrogAnimationGraph() {
        Vec2d spriteOffset = new Vec2d(-FROG_SIZE.x/2,-FROG_SIZE.y);

        AnimationComponent idle = new SpriteAnimationComponent(FinalGame.getSpritePath("frog"),
                spriteOffset, FROG_SIZE, 2, new Vec2d(0,0), new Vec2d(57,54), new Vec2d(57,0), .5);
        AGNode N_idle_up = new AGAnimation("idle_up", idle);



        AGNode[] animationNodes = new AGNode[]{N_idle_up};
        AnimationGraphComponent agc = new AnimationGraphComponent(animationNodes);

        return agc;
    }
}
