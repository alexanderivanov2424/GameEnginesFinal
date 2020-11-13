package engine.game.components;

import engine.game.GameObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

public class IDComponent extends Component{
    private String id;

    public IDComponent(GameObject gameObject, String id) {
        super(gameObject);
        this.id = id;
    }

    public String getId(){
        return this.id;
    }

    @Override
    public int getSystemFlags() {
        return 0;
    }

    @Override
    public String getTag() {
        return "IDComponent";
    }

    public Element getXML(Document doc){
        Element component = doc.createElement(this.getClass().getName());
        component.setAttribute("id", this.id);
        return component;
    }

    public static Component loadFromXML(Element n, GameObject g) {
        NamedNodeMap attr = n.getAttributes();
        IDComponent c = new IDComponent(g, attr.getNamedItem("id").getNodeValue());
        return c;
    }
}
