package engine.game.systems;


import engine.game.components.LightComponent;
import engine.support.Vec2d;

import java.util.Arrays;

//Keeps track of all light sources and calculates the brightness at different points.
public class LightingSystem extends GeneralSystem {

    private double[][] lightMap = new double[70][70];
    private double squareSize = 0.2;

    public int getSystemFlag(){
        return SystemFlag.LightingSystem;
    }

    //Every tick update brightness of each region based on the lightComponent's locations.
    //0 is black, 1.0 is transparent. (Opposite of Color opacity.)
    public void onTick(long nanosSincePreviousTick){
        //For each light component
        for(int i =0; i < this.components.size(); i++){
            if(this.components.get(i).isDisabled()) continue;

            //If within a certain radius, completely bright
            //If between a certain distance range, gradient
            //Completely dark otherwise.
            for(int n = 0; n < lightMap.length; n++) {
                for(int m = 0; m < lightMap[0].length; m++) {
                    double distance = this.components.get(i).getGameObject().getTransform().position.
                            dist((float)squareSize*n, (float)squareSize*m);

                    if(distance < 3) {
                        lightMap[n][m] = 1;
                    }
                    else if(distance >= 3 && distance < 7) {
                        lightMap[n][m] = 1-((distance-3)*.25);
                    }
                    else {
                        lightMap[n][m] = 0;
                    }
                }
            }
        }
        /*System.out.println("lightmap: ");
        for(int i = 0; i<16; i++)
        {
            for(int j = 0; j<16; j++)
            {
                System.out.print(lightMap[i][j]);
            }
            System.out.println();
        }*/
    }

    public double[][] getLightMap() {
        return lightMap;
    }

    public double getSquareSize() {
        return squareSize;
    }

    public void setLightMap(double[][] lightMap) {
        this.lightMap = lightMap;
    }

    public void setSquareSize(double squareSize) {
        this.squareSize = squareSize;
    }
}
