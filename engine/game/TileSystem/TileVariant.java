package engine.game.TileSystem;

import engine.game.GameObject;
import engine.game.GameWorld;
import engine.game.components.SpriteComponent;
import engine.support.Vec2d;

public class TileVariant {

    /*
    Keeps information for single tile variant object. A single tile may have many

    Once a tile is processed it is converted into actual game objects sent to the game.
    This class is not actually used in the game.
     */
    protected String spriteSheetPath;

    protected Vec2d cropStart; //from where the crop starts on the sprite sheet
    protected Vec2d cropSize; //size of region from which to draw

    public TileVariant(String spriteSheetPath, Vec2d cropStart, Vec2d cropSize){
        this.spriteSheetPath = spriteSheetPath;
        this.cropStart = cropStart;
        this.cropSize = cropSize;
    }

    public GameObject constructGameObject(Vec2d tilesSize, GameWorld gameWorld, int layer){
        GameObject tile = new GameObject(gameWorld, layer);
        SpriteComponent sprite = new SpriteComponent(tile, spriteSheetPath, new Vec2d(0,0), tilesSize, cropStart, cropSize);
        tile.addComponent(sprite);
        return tile;
    }
}
