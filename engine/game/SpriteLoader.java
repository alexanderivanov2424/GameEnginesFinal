package engine.game;


import javafx.scene.image.Image;

import java.util.HashMap;

public class SpriteLoader {

    private HashMap<String,Image> loadedResources = new HashMap<String,Image>();

    /**
     * Loads image from given path. If image has already been loaded returns copy.
     * @return Image of the given name in the given path.
     */
    public Image loadImage(String path) {
        if(this.loadedResources.containsKey(path)){
            return this.loadedResources.get(path);
        }
        Image img = new Image(path);
        this.loadedResources.put(path, img);
        return img;
    }
}
