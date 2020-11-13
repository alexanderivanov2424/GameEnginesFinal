package engine.game.components;

import engine.game.GameObject;
import engine.game.systems.SystemFlag;
import engine.support.Vec2d;
import jdk.internal.org.objectweb.asm.commons.Method;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

public class TimerComponent extends Component{

    public interface OnTimerFunction{
        void onTimer(GameObject gameObject);
    }

    private OnTimerFunction onTimerFunction;
    private double cycleTime;

    private double time = 0; //variable used for clock

    public TimerComponent(GameObject gameObject, double seconds) {
        super(gameObject);
        this.cycleTime = seconds;
    }

    public void linkTimerCallback(OnTimerFunction onTimerFunction){
        this.onTimerFunction = onTimerFunction;
    }

    @Override
    public void onTick(long nanosSincePreviousTick){
        this.time -= nanosSincePreviousTick/1000000000.0;
        while(this.time < 0){
            this.time += this.cycleTime;
            if(this.onTimerFunction != null)
                this.onTimerFunction.onTimer(gameObject);
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
        return "TimerComponent";
    }

    public Element getXML(Document doc){
        Element component = doc.createElement(this.getClass().getName());
        component.setAttribute("cycleTime", Double.toString(cycleTime));
        component.setAttribute("time", Double.toString(time));
        return component;
    }

    public static Component loadFromXML(Element n, GameObject g) {
        NamedNodeMap attr = n.getAttributes();
        double cycleTime = Double.parseDouble(attr.getNamedItem("cycleTime").getNodeValue());
        double time = Double.parseDouble(attr.getNamedItem("time").getNodeValue());
        TimerComponent c = new TimerComponent(g, cycleTime);
        c.time = time;
        return c;
    }
}
