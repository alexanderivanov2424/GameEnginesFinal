package engine.game.components;

import engine.UIToolKit.UIViewport;
import engine.game.GameObject;
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

    //TODO add smoothness
    //TODO add option for level bounds (camera cant go past certain point but player can.

    public CameraComponent(GameObject gameObject, int viewport_id) {
        super(gameObject);
        this.viewport_id = viewport_id;
        this.viewport = gameObject.gameWorld.getViewport(viewport_id);
    }

    @Override
    public void onTick(long nanosSincePreviousTick){
    }

    @Override
    public void onLateTick(){
        if(this.viewport == null){
            System.err.println("Viewport not found. Maybe you forgot to link the viewport with the gameworld.");
            return;
        }
        Vec2d pos = this.gameObject.getTransform().position;
        Vec2d size = this.gameObject.getTransform().size;
        this.viewport.setGamePosition(new Vec2d(pos.x + size.x/2, pos.y+ size.y/2));
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

    public static Component loadFromXML(Element n, GameObject g) {
        NamedNodeMap attr = n.getAttributes();
        int viewport_id = Integer.parseInt(attr.getNamedItem("viewport_id").getNodeValue());
        CameraComponent c = new CameraComponent(g, viewport_id);
        return c;
    }
}
