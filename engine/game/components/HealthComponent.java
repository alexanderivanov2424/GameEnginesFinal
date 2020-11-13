package engine.game.components;

import engine.game.GameObject;
import engine.game.systems.SystemFlag;
import engine.support.Vec2d;
import jdk.internal.org.objectweb.asm.commons.Method;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

public class HealthComponent extends Component{

    public interface OnDeath{
        void onDeath(GameObject gameObject);
    }

    private OnDeath onDeath;
    private double health;

    private boolean done = false;

    public HealthComponent(GameObject gameObject, double health) {
        super(gameObject);
        this.health = health;
    }

    public void linkDeathCallback(OnDeath onDeath){
        this.onDeath = onDeath;
    }

    public void hit(double damage){
        this.health -= damage;
    }

    @Override
    public void onTick(long nanosSincePreviousTick){
        if(this.health <= 0 && !done){
            done = true;
            if(this.onDeath != null)
                this.onDeath.onDeath(this.gameObject);
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
        return "HealthComponent";
    }

    public Element getXML(Document doc){
        Element component = doc.createElement(this.getClass().getName());
        component.setAttribute("health", Double.toString(health));
        component.setAttribute("done", Boolean.toString(done));
        return component;
    }

    public static Component loadFromXML(Element n, GameObject g) {
        NamedNodeMap attr = n.getAttributes();
        Double health = Double.parseDouble(attr.getNamedItem("health").getNodeValue());
        boolean done = Boolean.parseBoolean(attr.getNamedItem("done").getNodeValue());
        HealthComponent c = new HealthComponent(g, health);
        c.NOT_FULLY_LOADED();
        c.done = done;
        return c;
    }
}
