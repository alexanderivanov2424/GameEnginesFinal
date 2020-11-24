package engine.game;

import java.util.ArrayList;
import java.util.List;

public class Region {

    private List<GameObject> gameObjects;

    public Region(){
        this.gameObjects = new ArrayList<GameObject>();
    }

    public void addGameObject(GameObject g){
        this.gameObjects.add(g);
    }

    public void removeGameObject(GameObject g){
        this.gameObjects.remove(g);
    }
}
