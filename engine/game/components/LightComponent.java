package engine.game.components;

import engine.game.GameObject;
import engine.game.systems.SystemFlag;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;

public class LightComponent extends Component {

    protected double brightness;

    protected Color color = Color.rgb(255,255,255,0.0);

    private Vec2d offset;


    public LightComponent(Color color, double brightness, Vec2d offset) {
        this.brightness = brightness;
        this.color = color;
        this.offset = offset;
    }

    @Override
    public int getSystemFlags() {
        return SystemFlag.LightingSystem;
    }

    @Override
    public String getTag() {
        return "LightComponent";
    }


    public double getBrightness() {
        return brightness;
    }

    public void setBrightness(double brightness) {
        this.brightness = brightness;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Vec2d getOffset(){
        return this.offset;
    }

}
