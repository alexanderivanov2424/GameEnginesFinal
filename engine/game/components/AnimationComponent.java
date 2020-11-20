package engine.game.components;

import engine.game.GameObject;
import engine.game.systems.SystemFlag;

public abstract class AnimationComponent extends Component{

    @Override
    public int getSystemFlags() {
        return SystemFlag.RenderSystem | SystemFlag.TickSystem;
    }

}
