package engine.game.systems;

import engine.game.components.LightComponent;
import engine.support.Vec2d;

import java.util.Arrays;

//Keeps track of all light sources and calculates the brightness at different points.
public class LightingSystem extends GeneralSystem {

    public int getSystemFlag(){
        return SystemFlag.LightingSystem;
    }


    //TODO there may be a way to optimize this. Probably having static lights and storing light levels at certain locations
    // would help
    public double getBrightnessAt(Vec2d location){
        //TODO when lights have colors need to do proper color mixing here instead of just adding the colors together
        double totalBrightness = 0;
        double r = 0, g = 0, b = 0, a = 0;
        double count = 0;
        for(int i =0; i < this.components.size(); i++) {
            if (this.components.get(i).isDisabled()) continue;
            LightComponent lightComponent = (LightComponent)this.components.get(i);
            totalBrightness += lightComponent.getBrightnessAtLocation(location);
        }
        return Math.min(totalBrightness,1);
    }

}
