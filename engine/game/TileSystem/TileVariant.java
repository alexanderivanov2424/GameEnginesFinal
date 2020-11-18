package engine.game.TileSystem;

import engine.game.GameObject;
import engine.game.GameWorld;
import engine.game.components.SpriteComponent;
import engine.support.Vec2d;

public abstract class TileVariant {

    /*
    Keeps information for single tile variant object. A single tile may have many

    Once a tile is processed it is converted into actual game objects sent to the game.
    This class is not actually used in the game.
     */
    public String variantName;

    public TileVariant(String variantName){
        this.variantName = variantName;
    }

    public abstract GameObject constructGameObject(Vec2d tilesSize, String spriteSheetPath, GameWorld gameWorld, int layer);
}
