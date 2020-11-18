package engine.game.TileSystem;

import engine.game.GameObject;
import engine.game.GameWorld;
import engine.game.components.SpriteComponent;
import engine.support.Vec2d;

public class SpriteTileVariant extends TileVariant{

    protected Vec2d cropStart; //from where the crop starts on the sprite sheet
    protected Vec2d cropSize; //size of region from which to draw

    public SpriteTileVariant(String variantName, Vec2d cropStart, Vec2d cropSize){
        super(variantName);
        this.variantName = variantName;
        this.cropStart = cropStart;
        this.cropSize = cropSize;
    }

    public GameObject constructGameObject(Vec2d tilesSize, String spriteSheetPath, GameWorld gameWorld, int layer){
        GameObject tile = new GameObject(gameWorld, layer);
        SpriteComponent sprite = new SpriteComponent(tile, spriteSheetPath, new Vec2d(0,0), tilesSize, cropStart, cropSize);
        tile.addComponent(sprite);
        return tile;
    }
}