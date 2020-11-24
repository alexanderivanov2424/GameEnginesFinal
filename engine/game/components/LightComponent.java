package engine.game.components;

import engine.game.GameObject;
import engine.game.systems.SystemFlag;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.ColorAdjust;

public class LightComponent extends Component {

    protected double distance;

    public LightComponent(GameObject gameObject, double distance) {
        super(gameObject);

        this.distance = distance;
    }

    @Override
    public int getSystemFlags() {
        return SystemFlag.TickSystem;
    }

    @Override
    public String getTag() {
        return "DefogComponent";
    }

    public double getDistance() {
        return distance;
    }
}
