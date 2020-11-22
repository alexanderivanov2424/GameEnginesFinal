package engine.game.components.Animation.AnimationSystem;

import engine.game.components.Animation.AnimationComponent;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;

public class AGAnimationGroup  extends AGNode {

    private AnimationComponent[] animationComponents;
    private Vec2d[] stateSpacePosition;

    private Vec2d state = new Vec2d(0,0);

    private int current_animation = 0;
    private boolean stateUpdated = true;

    public AGAnimationGroup(String name, AnimationComponent[] animationComponents, Vec2d[] stateSpacePosition) {
        super(name);
        this.animationComponents = animationComponents;
        this.stateSpacePosition = stateSpacePosition;
        assert(animationComponents.length == stateSpacePosition.length);
        assert(animationComponents.length>0);
    }

    public void updateState(Vec2d newState){
        this.state = newState;
        stateUpdated = true;
    }

    public boolean justFinished(){
        return animationComponents[this.current_animation].justFinished;
    }

    public void restart(){
        this.animationComponents[this.current_animation].restart();
    }

    @Override
    public void onTick(long nanosSincePreviousTick) {
        if(stateUpdated){
            int original_animation = this.current_animation;
            double mag = this.state.minus(stateSpacePosition[0]).mag();
            for(int i = 1; i < stateSpacePosition.length; i++){
                double m = this.state.minus(stateSpacePosition[i]).mag();
                if(m < mag){
                    mag = m;
                    this.current_animation = i;
                }
            }
            stateUpdated = false;
            if(original_animation != current_animation){
                animationComponents[original_animation].restart();
            }
        }
        animationComponents[this.current_animation].onTick(nanosSincePreviousTick);
    }

    @Override
    public void onLateTick() {
        animationComponents[this.current_animation].onLateTick();
    }

    @Override
    public void onDraw(GraphicsContext g) {
        animationComponents[this.current_animation].onDraw(g);
    }
}
