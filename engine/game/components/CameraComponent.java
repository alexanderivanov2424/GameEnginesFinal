package engine.game.components;

import engine.UIToolKit.UIViewport;
import engine.game.GameObject;
import engine.game.GameWorld;
import engine.game.systems.SystemFlag;
import engine.support.Vec2d;
import javafx.scene.input.KeyCode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import java.util.Set;

public class CameraComponent extends Component{

    private int viewport_id;
    private UIViewport viewport;

    private Vec2d horizontalRange;
    private Vec2d verticalRange;

    //TODO add smoothness
    //TODO add option for level bounds (camera cant go past certain point but player can).

    public CameraComponent(int viewport_id) {
        super();
        this.viewport_id = viewport_id;
    }

    public CameraComponent(int viewport_id, Vec2d horizontalRange, Vec2d verticalRange) {
        super();
        this.viewport_id = viewport_id;
        this.horizontalRange = horizontalRange;
        this.verticalRange = verticalRange;
    }

    public void setHorizontalRange(Vec2d horizontalRange){
        this.horizontalRange = horizontalRange;
    }

    public void setVerticalRange(Vec2d verticalRange){
        this.verticalRange = verticalRange;
    }

    @Override
    public void onTick(long nanosSincePreviousTick){
        if(this.viewport == null){
            this.viewport = this.gameObject.gameWorld.getViewport(viewport_id);
        }
    }

    @Override
    public void onLateTick(){
        if(this.viewport == null){
            System.err.println("Viewport not found. Maybe you forgot to link the viewport with the gameworld.");
            return;
        }
        Vec2d pos = this.gameObject.getTransform().position;
        Vec2d size = this.gameObject.getTransform().size;
        double camera_x = pos.x + size.x/2;
        double camera_y = pos.y+ size.y/2;
        if(horizontalRange != null)
            camera_x = Math.max(this.horizontalRange.x, Math.min(this.horizontalRange.y,camera_x));
        if(verticalRange != null)
            camera_y = Math.max(this.verticalRange.x, Math.min(this.verticalRange.y,camera_y));
        this.viewport.setGamePosition(new Vec2d(camera_x, camera_y));
    };

    @Override
    public int getSystemFlags() {
        return SystemFlag.TickSystem;
    }

    @Override
    public String getTag() {
        return "CameraComponent";
    }

    public Element getXML(Document doc){
        Element component = doc.createElement(this.getClass().getName());
        component.setAttribute("viewport_id", Integer.toString(viewport_id));
        return component;
    }

    public static Component loadFromXML(Element n) {
        NamedNodeMap attr = n.getAttributes();
        int viewport_id = Integer.parseInt(attr.getNamedItem("viewport_id").getNodeValue());
        CameraComponent c = new CameraComponent(viewport_id);
        return c;
    }
}
