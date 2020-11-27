package engine.game.components.ScreenEffects;

import javafx.scene.canvas.GraphicsContext;

public class ShakeEffect extends ScreenEffectComponent{

    private double magnitude;
    private double lifespan;

    public ShakeEffect(double magnitude, double lifespan){
        this.magnitude = magnitude;
        this.lifespan = lifespan;
    }

    @Override
    public void preEffect(GraphicsContext g) {
        double x = (Math.random()*2 - 1)*this.magnitude;
        double y = (Math.random()*2 - 1)*this.magnitude;
        g.translate(x,y);
    }

    @Override
    public void postEffect(GraphicsContext g) {

    }

    @Override
    public void onTick(long nanosSincePreviousTick) {
        this.lifespan -= nanosSincePreviousTick/1000000000.0;
        if(lifespan <= 0){
            this.gameObject.removeComponent(this);
        }
    }

    @Override
    public String getTag() {
        return "ShakeEffect";
    }
}
