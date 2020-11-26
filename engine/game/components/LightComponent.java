package engine.game.components;

import engine.game.GameObject;
import engine.game.systems.SystemFlag;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;

public class LightComponent extends Component {

    //TODO There are a lot of parameters that can be added to specify how the light diffuses
    // For now just a range and linear fall in brightness.

    protected double brightness;
    protected double range;

    protected Color color = Color.rgb(255,255,255,0.0);

    private Vec2d offset;

    //brightness should be from 0 to 1. 0 being full dark and 1 being full light
    public LightComponent(Color color, double brightness, double range, Vec2d offset) {
        this.brightness = brightness;
        this.range = range;
        this.color = color;
        this.offset = offset;
    }

    public LightComponent(Color color, double range, Vec2d offset) {
        this.brightness = 1;
        this.range = range;
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

    //All light components should have this.
    //This is what the lighting system uses to figure out the light in any given location
    //later this probably will return the color (with the brightness already incorporated)
    public double getBrightnessAtLocation(Vec2d loc){
        Vec2d pos = this.gameObject.getTransform().position;
        double dist = pos.plus(this.offset).dist(loc);
        if(dist > this.range) return 0;
        return 1 - dist/range;
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
