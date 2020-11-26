package projects.final_project.levels;

import engine.AILibrary.BehaviorTree.BTNode;
import engine.AILibrary.BehaviorTree.BTNodeStatus;
import engine.AILibrary.BehaviorTree.Nodes.BTActionNode;
import engine.AILibrary.BehaviorTree.Nodes.BTConditionNode;
import engine.AILibrary.BehaviorTree.Nodes.BTSelectorNode;
import engine.AILibrary.BehaviorTree.Nodes.BTSequenceNode;
import engine.AILibrary.PathFinding.AStarGrid;
import engine.game.GameObject;
import engine.game.GameWorld;
import engine.game.collisionShapes.AABShape;
import engine.game.components.*;
import engine.game.components.Animation.SpriteAnimationComponent;
import engine.game.systems.CollisionSystem;
import engine.support.Vec2d;
import engine.support.Vec2i;
import projects.final_project.FinalGame;

import java.util.List;

public class TutorialEnemy {

    private static final int ENEMY_LAYER = CollisionSystem.CollisionMask.layer1;
    private static final int ENEMY_MASK = CollisionSystem.CollisionMask.layer0 | CollisionSystem.CollisionMask.layer1;

    private static final double ENEMY_SPEED = 2;

    public static void placeEnemy(GameWorld gameWorld, Vec2d pos){
        GameObject enemy = new GameObject(gameWorld, 2);


        enemy.addComponent(new SpriteComponent(FinalGame.getSpritePath("enemy"), new Vec2d(0,0),
                new Vec2d(2,2), new Vec2d(0,128), new Vec2d(64,64)));


        enemy.addComponent(new CollisionComponent(new AABShape(new Vec2d(.3,.3),new Vec2d(1.4,1.4)),
                false, true, ENEMY_LAYER, ENEMY_MASK));

        HealthComponent healthComponent = new HealthComponent(5);
        healthComponent.linkDeathCallback(TutorialEnemy::enemyDeathCallback);
        enemy.addComponent(healthComponent);

        enemy.getTransform().position = pos;
        enemy.getTransform().size = new Vec2d(2,2);
        gameWorld.addGameObject(enemy);
    }

    private static void enemyDeathCallback(GameObject enemy){
        CollisionComponent collision = (CollisionComponent)enemy.getComponent("CollisionComponent");
        collision.setCollisionLayer(CollisionSystem.CollisionMask.NONE);

        DelayEventComponent delayEventComponent = new DelayEventComponent(.5);
        delayEventComponent.linkEventCallback(TutorialEnemy::enemyRemoveCallback);
        enemy.addComponent(delayEventComponent);
    }

    private static void enemyRemoveCallback(GameObject gameObject){
        gameObject.gameWorld.removeGameObject(gameObject);
    }

}
