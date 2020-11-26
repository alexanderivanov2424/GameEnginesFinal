package engine.game.components;

import engine.game.systems.SystemFlag;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class DrawFogComponent extends Component {

    private int viewport_id;
    private Vec2d offset; //center offset from gameObject
    private double resolution; //TODO need to move this over from the lighting system.

    public DrawFogComponent(int viewport_id, Vec2d offset, double resolution){
        this.viewport_id = 0;
        this.offset = offset;
        this.resolution = resolution;
    }

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
        return "DrawFogComponent";
    }
}
