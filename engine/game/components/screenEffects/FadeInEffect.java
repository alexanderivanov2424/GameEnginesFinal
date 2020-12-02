package engine.game.components.screenEffects;

import engine.UIToolKit.UIViewport;
import engine.game.GameObject;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class FadeInEffect extends ScreenEffectComponent{

    public interface DelayedEvent{
        void execute(GameObject gameObject);
    }

    private DelayedEvent delayedEvent;

    WritableImage snapshot;

    private int viewport_id;
    private UIViewport viewport;
    private double orig_duration;
    private double duration;

    private boolean firstFrame = true;
    private int skipFrame;

    public FadeInEffect(int viewport_id, double duration, int skipFrame){
        this.viewport_id = viewport_id;
        this.orig_duration = duration;
        this.duration = duration;
        this.skipFrame = skipFrame;
    }

    public void linkEventCallback(DelayedEvent delayedEvent){
        this.delayedEvent = delayedEvent;
    }

    @Override
    public void preEffect(GraphicsContext g) {

    }


    @Override
    public void postEffect(GraphicsContext g) {
        if(skipFrame > 0){
            skipFrame--;
            return;
        }
        if(firstFrame){
            snapshot = new WritableImage((int)g.getCanvas().getWidth(),(int)g.getCanvas().getHeight());
            g.getCanvas().snapshot(null, snapshot);
            firstFrame = false;
        } else if(this.viewport != null){
            Vec2d corner = this.viewport.getGameWorldViewCorner();
            Vec2d size = this.viewport.getGameWorldViewSize();


            g.drawImage(snapshot,corner.x,corner.y,size.x,size.y);

            double brightness = this.duration/this.orig_duration;
            brightness = Math.max(0, Math.min(1, brightness));
            //System.out.println(brightness);
            g.setFill(Color.rgb(0,0,0,brightness));
            g.fillRect(corner.x,corner.y, size.x, size.y);
        }
    }

    @Override
    public void onTick(long nanosSincePreviousTick) {
        if(this.viewport == null){
            this.viewport = this.gameObject.gameWorld.getViewport(this.viewport_id);
        }
        this.duration -= nanosSincePreviousTick/1000000000.0;
        if(duration <= 0){
            this.gameObject.removeComponent(this);
            if(this.delayedEvent != null)
                this.delayedEvent.execute(this.gameObject);
        }
    }

    @Override
    public String getTag() {
        return "FadeOutEffect";
    }
}
