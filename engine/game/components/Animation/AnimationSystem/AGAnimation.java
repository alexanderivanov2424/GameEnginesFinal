package engine.game.components.Animation.AnimationSystem;

import engine.game.GameObject;
import engine.game.components.Animation.AnimationComponent;
import javafx.scene.canvas.GraphicsContext;

public abstract class AGAnimation extends AGNode {
    private AnimationComponent animationComponent;

    public AGAnimation(String name, AnimationComponent animationComponent){
        super(name);
        this.animationComponent = animationComponent;
    }

    public AGAnimation(String name, String onFinishTransitionTo, AnimationComponent animationComponent){
        super(name, onFinishTransitionTo);
        this.animationComponent = animationComponent;
    }

    public boolean justFinished(){
        return animationComponent.justFinished;
    }

    public void restart(){
        this.animationComponent.restart();
    }

    public void setGameObject(GameObject g){
        this.animationComponent.setGameObject(g);
    }

    public void onTick(long nanosSincePreviousTick){
        animationComponent.onTick(nanosSincePreviousTick);
    }

    public void onLateTick(){
        animationComponent.onLateTick();
    }

    public void onDraw(GraphicsContext g){
        animationComponent.onDraw(g);
    }

}
