package engine.game;

import engine.game.components.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class GameObject {


    public List<Component> componentList;

    private TransformComponent transformComponent = new TransformComponent();

    private int layer = 0;

    public GameWorld gameWorld;

    public GameObject(GameWorld gameWorld){
        this.gameWorld = gameWorld;
        componentList = new ArrayList<Component>();

    }

    public GameObject(GameWorld gameWorld, int layer){
        this.gameWorld = gameWorld;
        componentList = new ArrayList<Component>();
        this.layer = layer;
    }

    public int getLayer(){return this.layer;};

    public void addComponent(Component c){
        this.componentList.add(c);
        c.setGameObject(this);
    }

    public void removeComponent(Component c){
        if(c == null){
            return;
        }
        this.componentList.remove(c);
    }

    public Component getComponent(String s){
        for(int i = 0; i < this.componentList.size(); i++){
            Component c = this.componentList.get(i);
            if(c.getTag().equals(s)){
                return c;
            }
        }
        return null;
    }

    public void setTransform(TransformComponent transformComponent){
        this.transformComponent = transformComponent;
    }

    public TransformComponent getTransform(){
        return this.transformComponent;
    }


    public Element getXML(Document doc) {
        Element gameObject = doc.createElement("GameObject");
        gameObject.setAttribute("layer", Integer.toString(this.layer));
        gameObject.appendChild(this.transformComponent.getXML(doc));
        for(Component c : this.componentList){
            gameObject.appendChild(c.getXML(doc));
        }
        return gameObject;
    }

}
