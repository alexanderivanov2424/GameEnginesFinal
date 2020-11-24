package engine.game.systems;

import engine.game.components.Component;
import javafx.scene.canvas.GraphicsContext;

import java.util.*;


//TODO sort by layer / other criteria
//TODO store objects intelligently

public class RenderSystem extends GeneralSystem {

    private Map<Integer,List<Component>> layers = new HashMap<Integer,List<Component>>();

    public int getSystemFlag(){
        return SystemFlag.RenderSystem;
    }

    public void onDraw(GraphicsContext g){
        List<Integer> orderedLayers = new ArrayList<Integer>(layers.keySet());
        Collections.sort(orderedLayers);
        for(int layer : orderedLayers){
            for(Component o : layers.get(layer)){
                if(o.isDisabled()) continue;
                o.onDraw(g);
            }
        }
    }

    public void onLateDraw(GraphicsContext g) {
        List<Integer> orderedLayers = new ArrayList<Integer>(layers.keySet());
        Collections.sort(orderedLayers);
        for(int layer : orderedLayers){
            for(Component o : layers.get(layer)){
                if(o.isDisabled()) continue;
                o.onLateDraw(g);
            }
        }
    }

    @Override
    public void addComponent(Component o){
        super.addComponent(o);
        if(layers.containsKey(o.getGameObject().getLayer())) {
            layers.get(o.getGameObject().getLayer()).add(o);
        } else {
            List<Component> layerList = new ArrayList<Component>();
            layerList.add(o);
            layers.put(o.getGameObject().getLayer(),layerList);
        }
    }
    @Override
    public void removeComponent(Component o) {
        super.removeComponent(o);
        layers.get(o.getGameObject().getLayer()).remove(o);

        //TODO maybe handle empty layers every once in a while?
    }

}
