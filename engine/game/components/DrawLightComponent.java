package engine.game.components;

import engine.game.systems.SystemFlag;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class DrawLightComponent extends Component {

    //Draws fog around the object based on information from the lighting system.
    public void onLateDraw(GraphicsContext g){
        double[][] lightMap = gameObject.gameWorld.getLightingSystem().getLightMap();
        double size = gameObject.gameWorld.getLightingSystem().getSquareSize();
        gameObject.getTransform().position.sdiv((float)size);

        //How to know viewport size? (for now do 19x12?)
        for(int i = 0; i < lightMap.length; i++) {
            for(int j = 0; j < lightMap[0].length; j++) {
                g.setFill(Color.rgb(0,0,0,1-lightMap[i][j]));
                g.fillRect(size*i, size*j, size, size);
            }
        }

    }

    @Override
    public int getSystemFlags() {
        return SystemFlag.RenderSystem;
    }

    @Override
    public String getTag() {
        return "FogComponent";
    }
}
