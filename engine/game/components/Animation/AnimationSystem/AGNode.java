package engine.game.components.Animation.AnimationSystem;

import engine.game.GameObject;
import javafx.scene.canvas.GraphicsContext;

public abstract class AGNode {
    public String name;

    public String onFinishTransitionTo = null;

    public AGNode(String name){
        this.name = name;
    }

    public AGNode(String name, String onFinishTransitionTo){
        this.name = name;
        this.onFinishTransitionTo = onFinishTransitionTo;
    }

    public abstract boolean justFinished();

    public abstract void restart();

    public abstract void setGameObject(GameObject g);

    public abstract void onTick(long nanosSincePreviousTick);

    public abstract void onLateTick();

    public abstract void onDraw(GraphicsContext g);

}