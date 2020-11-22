package engine.game.components.Animation;

import engine.game.GameObject;
import engine.game.components.Component;
import engine.game.systems.SystemFlag;

public abstract class AnimationComponent extends Component {

    public boolean justFinished;

    public abstract void restart();

    @Override
    public int getSystemFlags() {
        return SystemFlag.RenderSystem | SystemFlag.TickSystem;
    }

}
