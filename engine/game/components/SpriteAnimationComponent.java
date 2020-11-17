package engine.game.components;

import engine.game.GameObject;
import engine.game.systems.SystemFlag;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

public class SpriteAnimationComponent extends Component{

    //TODO Frames should be fully custom (spacing and offset)
    //TODO handle multiple animation sequences

    //TODO handle animating other components (moving hitbox)

    protected Image spriteSheet;
    protected String spriteSheetPath;

    protected Vec2d position; //relative position to game object
    protected Vec2d size; //size of sprite

    protected int frames; //offset on sprite sheet
    protected Vec2d cropStart; //from where the crop starts on the sprite sheet
    protected Vec2d cropSize; //size of region from which to draw
    protected Vec2d cropShift; //from where the crop starts on the sprite sheet

    protected boolean horizontalFlip = false;

    protected double frameDuration;

    private long currentTime = 0;
    private int currentFrame = 0;

    /**
     * Creates an animation component using a sprite sheet.
     * Sprite sheet is assumed to contain frames of the animation side by side horizontally.
     * Frames are also assumed to start as high up as possible.
     * @param gameObject gameObject
     * @param spriteSheetPath path to spriteSheet
     * @param position position of sprite relative to gameobject
     * @param size size to render the sprite
     * @param frames number of frames in animation
     * @param cropSize width and height of each frame
     * @param frameDuration Durations of a frame in seconds
     */
    public SpriteAnimationComponent(GameObject gameObject, String spriteSheetPath,
                                    Vec2d position, Vec2d size, int frames, Vec2d cropSize, double frameDuration) {
        super(gameObject);
        this.spriteSheet = gameObject.gameWorld.getSpriteLoader().loadImage(spriteSheetPath);
        this.spriteSheetPath = spriteSheetPath;
        this.position = position;
        this.size = size;

        this.frames = frames;
        this.cropSize = cropSize;
        this.cropStart = new Vec2d(0,0);
        this.frameDuration = frameDuration * 1000000000;
    }

    public SpriteAnimationComponent(GameObject gameObject, String spriteSheetPath,
                                    Vec2d position, Vec2d size, int frames, Vec2d cropStart, Vec2d cropSize, double frameDuration) {
        super(gameObject);
        this.spriteSheet = gameObject.gameWorld.getSpriteLoader().loadImage(spriteSheetPath);
        this.spriteSheetPath = spriteSheetPath;
        this.position = position;
        this.size = size;

        this.frames = frames;
        this.cropSize = cropSize;
        this.cropStart = cropStart;
        this.frameDuration = frameDuration * 1000000000;
    }

    public SpriteAnimationComponent(GameObject gameObject, String spriteSheetPath,
                                    Vec2d position, Vec2d size, int frames,
                                    Vec2d cropStart, Vec2d cropSize, Vec2d cropShift, double frameDuration) {
        super(gameObject);
        this.spriteSheet = gameObject.gameWorld.getSpriteLoader().loadImage(spriteSheetPath);
        this.spriteSheetPath = spriteSheetPath;
        this.position = position;
        this.size = size;

        this.frames = frames;
        this.cropStart = cropStart;
        this.cropSize = cropSize;
        this.cropShift = cropShift;
        this.frameDuration = frameDuration * 1000000000;
    }

    public void resetAnimation(String spriteSheetPath, Vec2d position, Vec2d size, int frames,
                               Vec2d cropStart, Vec2d cropSize, Vec2d cropShift, double frameDuration){
        this.spriteSheet = gameObject.gameWorld.getSpriteLoader().loadImage(spriteSheetPath);
        this.spriteSheetPath = spriteSheetPath;
        this.position = position;
        this.size = size;

        this.frames = frames;
        this.cropStart = cropStart;
        this.cropSize = cropSize;
        this.cropShift = cropShift;
        this.frameDuration = frameDuration * 1000000000;

        currentFrame = 0;
        currentTime = 0;
    }

    public void setHorizontalFlip(boolean horizontalFlip){
        this.horizontalFlip = horizontalFlip;
    }

    @Override
    public void onTick(long nanosSincePreviousTick){
        this.currentTime -= nanosSincePreviousTick;
        while(this.currentTime < 0){
            this.currentTime += this.frameDuration;
            this.currentFrame = (this.currentFrame + 1)%this.frames;
        }
    }

    @Override
    public void onLateTick(){};

    @Override
    public void onDraw(GraphicsContext g){
        Vec2d pos = this.gameObject.getTransform().position;
        Vec2d size = this.gameObject.getTransform().size;

        if(this.horizontalFlip) {
            g.drawImage(this.spriteSheet,
                    this.cropStart.x + this.cropShift.x * this.currentFrame,
                    this.cropStart.y + this.cropShift.y * this.currentFrame,
                    this.cropSize.x, this.cropSize.y,
                    pos.x + this.position.x + this.size.x, pos.y + this.position.y,
                    -this.size.x, this.size.y);
        } else {
            g.drawImage(this.spriteSheet,
                    this.cropStart.x + this.cropShift.x * this.currentFrame,
                    this.cropStart.y + this.cropShift.y * this.currentFrame,
                    this.cropSize.x, this.cropSize.y,
                    pos.x + this.position.x, pos.y + this.position.y,
                    this.size.x, this.size.y);
        }
    }

    @Override
    public int getSystemFlags() {
        return SystemFlag.RenderSystem | SystemFlag.TickSystem;
    }

    @Override
    public String getTag() {
        return "SpriteAnimationComponent";
    }

    public Element getXML(Document doc){
        Element component = doc.createElement(this.getClass().getName());
        component.setAttribute("spriteSheetPath", spriteSheetPath);
        component.setAttribute("position", position.toString());
        component.setAttribute("size", size.toString());

        component.setAttribute("frames", Integer.toString(frames));
        component.setAttribute("cropStart", cropStart.toString());
        component.setAttribute("cropSize", cropSize.toString());
        component.setAttribute("cropShift", cropShift.toString());

        component.setAttribute("frameDuration", Double.toString(frameDuration / 1000000000));
        component.setAttribute("currentTime", Long.toString(currentTime));
        component.setAttribute("currentFrame", Integer.toString(currentFrame));
        return component;
    }

    public static Component loadFromXML(Element n, GameObject g) {
        NamedNodeMap attr = n.getAttributes();
        String path = attr.getNamedItem("spriteSheetPath").getNodeValue();
        Vec2d position = Vec2d.fromString(attr.getNamedItem("position").getNodeValue());
        Vec2d size = Vec2d.fromString(attr.getNamedItem("size").getNodeValue());

        int frames = Integer.parseInt(attr.getNamedItem("frames").getNodeValue());
        Vec2d cropSize = Vec2d.fromString(attr.getNamedItem("cropSize").getNodeValue());
        Vec2d cropStart = Vec2d.fromString(attr.getNamedItem("cropStart").getNodeValue());
        Vec2d cropShift = Vec2d.fromString(attr.getNamedItem("cropShift").getNodeValue());

        double frameDuration = Double.parseDouble(attr.getNamedItem("frameDuration").getNodeValue());
        long currentTime = Long.parseLong(attr.getNamedItem("currentTime").getNodeValue());
        int currentFrame = Integer.parseInt(attr.getNamedItem("currentFrame").getNodeValue());
        SpriteAnimationComponent c = new SpriteAnimationComponent(g, path, position, size, frames, cropStart, cropSize, cropShift, frameDuration);
        c.currentTime = currentTime;
        c.currentFrame = currentFrame;
        return c;
    }
}
