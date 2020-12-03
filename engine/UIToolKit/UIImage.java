package engine.UIToolKit;

import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class UIImage extends UIElement{

    private Image image;

    public UIImage(Vec2d position, Vec2d size, Image image) {
        super(position, size);
        this.image = image;
    }

    @Override
    public void onDraw(GraphicsContext g) {
        Vec2d pos = this.getOffset();
        g.drawImage(this.image, pos.x, pos.y);
        super.onDraw(g);
    }
}
