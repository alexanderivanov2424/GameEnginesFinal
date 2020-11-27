package engine.game.components.Animation.AnimationSystem;

import engine.game.components.Component;
import engine.game.systems.SystemFlag;

public class TestAnimationComponent extends Component {

    /*
    Test Animation Component. If this works well will replace the other Animation Component

    Idea here is to have a pointer to the fields that need to be animated.
    This way a single animation component does the clock timings and can adjust the positions, currentFrames, sizes etc
    of an arbitrary amount of other objects concurrently.

    This should allow:
    - animation of collision shapes (hit boxes)
    - animation of sprites (as before, but synced with other animations)
    - animation of general fields (boolean, Vec2d, int, String, etc)
    - syncing animation across many game objects (tiles) **this may be harder to make work game side

     */

    protected int frames;

    protected double frameDuration;

    private long currentTime = 0;
    private int currentFrame = 0;

    public TestAnimationComponent(int frames, double frameDuration){
        this.frames = frames;
        this.frameDuration = frameDuration;
    }

    @Override
    public int getSystemFlags() {
        return SystemFlag.TickSystem;
    }

    @Override
    public String getTag() {
        return "TestAnimationComponent";
    }
}
