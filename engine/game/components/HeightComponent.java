package engine.game.components;

import engine.game.GameObject;
import engine.game.systems.SystemFlag;
import engine.support.Vec2d;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

public class HeightComponent extends Component{

    public double value;

    public HeightComponent(GameObject gameObject, double value) {
        super(gameObject);
        this.value = value;
    }

    @Override
    public int getSystemFlags() {
        return SystemFlag.None;
    }

    @Override
    public String getTag() {
        return "HeightComponent";
    }

    public Element getXML(Document doc){
        Element component = doc.createElement(this.getClass().getName());
        component.setAttribute("value", Double.toString(value));
        return component;
    }

    public static Component loadFromXML(Element n, GameObject g) {
        NamedNodeMap attr = n.getAttributes();
        Double value = Double.parseDouble(attr.getNamedItem("value").getNodeValue());
        HeightComponent c = new HeightComponent(g, value);
        return c;
    }
}
