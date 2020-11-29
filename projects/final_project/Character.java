package projects.final_project;

import engine.game.GameObject;
import engine.game.GameWorld;
import engine.game.components.animation.AnimationComponent;
import engine.game.components.animation.SpriteAnimationComponent;
import engine.game.components.animation.animationGraph.AGAnimationGroup;
import engine.game.components.animation.animationGraph.AGNode;
import engine.game.components.animation.animationGraph.AnimationGraphComponent;
import engine.support.Vec2d;

public class Character {

    public static GameObject createOldMan(GameWorld gameWorld){
        GameObject oldMan = new GameObject(gameWorld, 0);

        return oldMan;
    }

    private static AnimationGraphComponent getCharacterAnimationGraph(Vec2d CharacterSize, String spriteSheet){
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
