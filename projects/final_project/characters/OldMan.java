package projects.final_project.characters;

import engine.game.GameObject;
import engine.game.GameWorld;
import engine.game.collisionShapes.CircleShape;
import engine.game.components.CollisionComponent;
import engine.game.components.animation.AnimationComponent;
import engine.game.components.animation.SpriteAnimationComponent;
import engine.game.components.animation.animationGraph.AGAnimationGroup;
import engine.game.components.animation.animationGraph.AGNode;
import engine.game.components.animation.animationGraph.AnimationGraphComponent;
import engine.game.systems.CollisionSystem;
import engine.support.Vec2d;
import projects.final_project.FinalGame;

public class OldMan {

    public static void placeOldMan(GameWorld gameWorld, Vec2d position){
        GameObject oldMan = new GameObject(gameWorld, 0);
        AnimationGraphComponent agc = Characters.getCharacterAnimationGraph(new Vec2d(2,2), "old_man");
        agc.updateState(new Vec2d(0,1));
        oldMan.addComponent(agc);

        CollisionComponent talk = new CollisionComponent(new CircleShape(new Vec2d(0,0),1),
                false, false, FinalGame.TALK_LAYER, FinalGame.TALK_MASK);
        talk.linkCollisionCallback(OldMan::speakToOldMan);
        oldMan.addComponent(talk);

        DialogComponent dc = new DialogComponent(getDialog(), Characters.createTextBox(), Characters.createOptionBox());
        oldMan.addComponent(dc);

        oldMan.getTransform().position = position;
        gameWorld.addGameObject(oldMan);
    }

    public static void speakToOldMan(CollisionSystem.CollisionInfo collisionInfo){
        DialogComponent dc = (DialogComponent)collisionInfo.gameObjectSelf.getComponent("DialogComponent");
        if(dc == null) return;
        dc.startDialog();
    }

    private static DialogNode getDialog(){
        DialogNode dialog = new DialogNode("Hey you! You're finally asleep!");
        DialogNode quest = new DialogNode("Quickly now! You must save the town...");
        DialogNode question = new DialogNode("Do you even know what is happening?");

        DialogNode A = new DialogNode("The evil villain, Slippy the Toad, has invaded our lands. \n" +
                "You are the only person in these parts that has the power to take him on in battle.\n" +
                "Be warned! He has many followers that have been corrupted to his evil ways");
        DialogNode B = new DialogNode("Good, good! Jabralter must have already told you everything.");

        DialogNode sendoff = new DialogNode("Now go, defeat Slippy and his henchmen!\n" +
                "For the TOWN!!");

        dialog.setNextNode(quest);
        quest.setNextNode(question);
        question.setOptions(new String[]{"No", "Yes"}, new DialogNode[]{A,B});
        A.setNextNode(sendoff);
        B.setNextNode(sendoff);
        return dialog;
    }

    private static AnimationGraphComponent getAnimationGraph(Vec2d CharacterSize, String spriteSheet){
        AnimationComponent idle_up = new SpriteAnimationComponent(FinalGame.getSpritePath(spriteSheet),
                new Vec2d(0,0), CharacterSize, 1, new Vec2d(0,8*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent idle_left = new SpriteAnimationComponent(FinalGame.getSpritePath(spriteSheet),
                new Vec2d(0,0), CharacterSize, 1, new Vec2d(0,9*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent idle_down = new SpriteAnimationComponent(FinalGame.getSpritePath(spriteSheet),
                new Vec2d(0,0), CharacterSize, 1, new Vec2d(0,10*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent idle_right = new SpriteAnimationComponent(FinalGame.getSpritePath(spriteSheet),
                new Vec2d(0,0), CharacterSize, 1, new Vec2d(0,11*64), new Vec2d(64,64), new Vec2d(64,0), .05);

        AnimationComponent walk_up = new SpriteAnimationComponent(FinalGame.getSpritePath(spriteSheet),
                new Vec2d(0,0), CharacterSize, 9, new Vec2d(0,8*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent walk_left = new SpriteAnimationComponent(FinalGame.getSpritePath(spriteSheet),
                new Vec2d(0,0), CharacterSize, 9, new Vec2d(0,9*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent walk_down = new SpriteAnimationComponent(FinalGame.getSpritePath(spriteSheet),
                new Vec2d(0,0), CharacterSize, 9, new Vec2d(0,10*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent walk_right = new SpriteAnimationComponent(FinalGame.getSpritePath(spriteSheet),
                new Vec2d(0,0), CharacterSize, 9, new Vec2d(0,11*64), new Vec2d(64,64), new Vec2d(64,0), .05);

        AnimationComponent attack_up = new SpriteAnimationComponent(FinalGame.getSpritePath(spriteSheet),
                new Vec2d(0,0), CharacterSize, 6, new Vec2d(0,12*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent attack_left = new SpriteAnimationComponent(FinalGame.getSpritePath(spriteSheet),
                new Vec2d(0,0), CharacterSize, 6, new Vec2d(0,13*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent attack_down = new SpriteAnimationComponent(FinalGame.getSpritePath(spriteSheet),
                new Vec2d(0,0), CharacterSize, 6, new Vec2d(0,14*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent attack_right = new SpriteAnimationComponent(FinalGame.getSpritePath(spriteSheet),
                new Vec2d(0,0), CharacterSize, 6, new Vec2d(0,15*64), new Vec2d(64,64), new Vec2d(64,0), .05);

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
}
