package projects.final_project.characters;

import engine.game.GameObject;
import engine.game.GameWorld;
import engine.game.components.TextBoxComponent;
import engine.game.components.animation.AnimationComponent;
import engine.game.components.animation.SpriteAnimationComponent;
import engine.game.components.animation.animationGraph.AGAnimationGroup;
import engine.game.components.animation.animationGraph.AGNode;
import engine.game.components.animation.animationGraph.AnimationGraphComponent;
import engine.support.Vec2d;
import javafx.scene.text.Font;
import projects.final_project.FinalGame;

public class Characters {

    // Class to store components that are shared across multiple game characters.
    // For example, AI, animations, health components and callbacks, etc

    public static TextBoxComponent createTextBox(){
        TextBoxComponent textBoxComponent = new TextBoxComponent(0, true, new Vec2d(1.6,7.1), new Vec2d(15.5,3),
                "", .1, null, Font.font("Comic Sans MS", 20), TextBoxComponent.TextAlignment.TOP_LEFT, .5, 1, 1);
        textBoxComponent.setCenterImage(FinalGame.getSpritePath("textbox"),
                new Vec2d(270,375), new Vec2d(25,25), new Vec2d(0,0));
        textBoxComponent.setCornerImage(FinalGame.getSpritePath("textbox"),
                new Vec2d(254,359), new Vec2d(13,13), new Vec2d(.25,.25));
        textBoxComponent.setHorizontalEdgeImage(FinalGame.getSpritePath("textbox"),
                new Vec2d(271,359), new Vec2d(25,13), new Vec2d(.25,.25));
        textBoxComponent.setVerticalEdgeImage(FinalGame.getSpritePath("textbox"),
                new Vec2d(254,375), new Vec2d(13,25), new Vec2d(.25,.25));
        return textBoxComponent;
    }

    public static TextBoxComponent createOptionBox(){
        TextBoxComponent textBoxComponent = new TextBoxComponent(0, true, new Vec2d(12.1,7.5), new Vec2d(4.6,2.2),
                "", .1, null, Font.font("Comic Sans MS", 20), TextBoxComponent.TextAlignment.TOP_LEFT,.5, 1, 1);
        textBoxComponent.setCenterImage(FinalGame.getSpritePath("textbox"),
                new Vec2d(270,375), new Vec2d(25,25), new Vec2d(0,0));
        textBoxComponent.setCornerImage(FinalGame.getSpritePath("textbox"),
                new Vec2d(254,359), new Vec2d(13,13), new Vec2d(.25,.25));
        textBoxComponent.setHorizontalEdgeImage(FinalGame.getSpritePath("textbox"),
                new Vec2d(271,359), new Vec2d(25,13), new Vec2d(.25,.25));
        textBoxComponent.setVerticalEdgeImage(FinalGame.getSpritePath("textbox"),
                new Vec2d(254,375), new Vec2d(13,25), new Vec2d(.25,.25));
        return textBoxComponent;
    }

    public static AnimationGraphComponent getCharacterAnimationGraph(Vec2d CharacterSize, String spriteSheet){
        Vec2d spriteOffset = new Vec2d(-1,-2);
        AnimationComponent idle_up = new SpriteAnimationComponent(FinalGame.getSpritePath(spriteSheet),
                spriteOffset, CharacterSize, 1, new Vec2d(0,8*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent idle_left = new SpriteAnimationComponent(FinalGame.getSpritePath(spriteSheet),
                spriteOffset, CharacterSize, 1, new Vec2d(0,9*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent idle_down = new SpriteAnimationComponent(FinalGame.getSpritePath(spriteSheet),
                spriteOffset, CharacterSize, 1, new Vec2d(0,10*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent idle_right = new SpriteAnimationComponent(FinalGame.getSpritePath(spriteSheet),
                spriteOffset, CharacterSize, 1, new Vec2d(0,11*64), new Vec2d(64,64), new Vec2d(64,0), .05);

        AnimationComponent walk_up = new SpriteAnimationComponent(FinalGame.getSpritePath(spriteSheet),
                spriteOffset, CharacterSize, 9, new Vec2d(0,8*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent walk_left = new SpriteAnimationComponent(FinalGame.getSpritePath(spriteSheet),
                spriteOffset, CharacterSize, 9, new Vec2d(0,9*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent walk_down = new SpriteAnimationComponent(FinalGame.getSpritePath(spriteSheet),
                spriteOffset, CharacterSize, 9, new Vec2d(0,10*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent walk_right = new SpriteAnimationComponent(FinalGame.getSpritePath(spriteSheet),
                spriteOffset, CharacterSize, 9, new Vec2d(0,11*64), new Vec2d(64,64), new Vec2d(64,0), .05);

        AnimationComponent attack_up = new SpriteAnimationComponent(FinalGame.getSpritePath(spriteSheet),
                spriteOffset, CharacterSize, 6, new Vec2d(0,12*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent attack_left = new SpriteAnimationComponent(FinalGame.getSpritePath(spriteSheet),
                spriteOffset, CharacterSize, 6, new Vec2d(0,13*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent attack_down = new SpriteAnimationComponent(FinalGame.getSpritePath(spriteSheet),
                spriteOffset, CharacterSize, 6, new Vec2d(0,14*64), new Vec2d(64,64), new Vec2d(64,0), .05);
        AnimationComponent attack_right = new SpriteAnimationComponent(FinalGame.getSpritePath(spriteSheet),
                spriteOffset, CharacterSize, 6, new Vec2d(0,15*64), new Vec2d(64,64), new Vec2d(64,0), .05);

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
