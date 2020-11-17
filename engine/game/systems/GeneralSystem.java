package engine.game.systems;

import engine.game.components.Component;

import java.util.ArrayList;
import java.util.List;

public class GeneralSystem {
    protected List<Component> components;

    public GeneralSystem(){
        components = new ArrayList<Component>();
    }

    public void addComponent(Component c){
        this.components.add(c);
    }

    public void removeComponent(Component c) {
        this.components.remove(c);
    }
}
