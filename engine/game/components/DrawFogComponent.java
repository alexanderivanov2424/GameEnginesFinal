package engine.game.components;

import engine.UIToolKit.UIViewport;
import engine.game.systems.SystemFlag;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class DrawFogComponent extends Component {

    private int viewport_id;
    private UIViewport viewport;


    private Vec2d offset; //center offset from gameObject
    private double resolution; //TODO need to move this over from the lighting system.

    public DrawFogComponent(int viewport_id, Vec2d offset, double resolution){
        this.viewport_id = 0;
        this.offset = offset;
        this.resolution = resolution;
    }

    //Draws fog around the object based on information from the lighting system.
    public void onLateDraw(GraphicsContext g){
        if(this.viewport == null){
            this.viewport = this.gameObject.gameWorld.getViewport(viewport_id);
        }
        if(this.viewport == null) return;

        Vec2d pos = this.gameObject.getTransform().position;

        //How to know viewport size? (for now do 19x12?)
        //Needed to add some new functions. There isn't really a good way to do this :(
        Vec2d corner = this.viewport.getGameWorldViewCorner();
        Vec2d size = this.viewport.getGameWorldViewSize();

        for(double i = corner.x; i < corner.x + size.x; i+=resolution){
            for(double j = corner.y; j < corner.y + size.y; j+=resolution){
                double brightness = this.gameObject.gameWorld.getLightingSystem().getBrightnessAt(
                        new Vec2d(i + resolution/2, j+ resolution/2));
                g.setFill(Color.rgb(0,0,0,1-brightness));
                g.fillRect(i,j, resolution, resolution);
            }
        }

//        double[][] lightMap = gameObject.gameWorld.getLightingSystem().getLightMap();
//        gameObject.getTransform().position.sdiv((float)size);
//
//
//        for(int i = 0; i < lightMap.length; i++) {
//            for(int j = 0; j < lightMap[0].length; j++) {
//                g.setFill(Color.rgb(0,0,0,1-lightMap[i][j]));
//                g.fillRect(size*i, size*j, size, size);
//            }
//        }
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
