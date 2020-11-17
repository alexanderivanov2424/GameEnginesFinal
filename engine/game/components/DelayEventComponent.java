package engine.game.components;

import engine.game.GameObject;
import engine.game.collisionShapes.Shape;
import engine.game.systems.SystemFlag;
import jdk.internal.org.objectweb.asm.commons.Method;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class DelayEventComponent extends Component{

    public interface DelayedEvent{
        void execute(GameObject gameObject);
    }

    private DelayedEvent delayedEvent;

    private double timer = 0; //variable used for clock

    public DelayEventComponent(GameObject gameObject, double seconds) {
        super(gameObject);
        this.timer = seconds;
    }

    public void linkEventCallback(DelayedEvent delayedEvent){
        this.delayedEvent = delayedEvent;
    }

    @Override
    public void onTick(long nanosSincePreviousTick){
        this.timer -= nanosSincePreviousTick/1000000000.0;
        System.out.println("TICK");
        if(this.timer <= 0){
            System.out.println("GOOD");
            if(this.delayedEvent != null) {
                this.delayedEvent.execute(this.gameObject);
            }
            this.disable();
            this.gameObject.removeComponent(this);
        }
    }

    @Override
    public void onLateTick(){};

    @Override
    public int getSystemFlags() {
        return SystemFlag.TickSystem;
    }

    @Override
    public String getTag() {
        return "DelayEventComponent";
    }

    public Element getXML(Document doc){
        Element component = doc.createElement(this.getClass().getName());
        component.setAttribute("time", Double.toString(timer));
        return component;
    }

    public static Component loadFromXML(Element n, GameObject g) {
        NamedNodeMap attr = n.getAttributes();
        double time = Double.parseDouble(attr.getNamedItem("time").getNodeValue());
        DelayEventComponent c = new DelayEventComponent(g, time);
        c.NOT_FULLY_LOADED();
        return c;
    }
}
