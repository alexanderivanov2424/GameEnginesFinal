package engine.game.TileSystem;

import engine.game.GameObject;
import engine.game.GameWorld;
import engine.support.Vec2d;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class TileMap {

    private String[][] tiles;

    private HashMap<String, Tile> tileTypes;

    private boolean fourDirectional;

    public TileMap(String[][] tiles, List<Tile> tileTypes, boolean fourDirectional){
        this.tiles = tiles;
        this.tileTypes = new HashMap<String, Tile>();
        this.fourDirectional = fourDirectional;

        for(Tile t: tileTypes){
            this.tileTypes.put(t.type,t);
        }
    }



    public void addTilesToGameWorld(GameWorld gameWorld, int layer, Vec2d tileSize){
        if(this.fourDirectional) {
            for (int i = 0; i < tiles.length; i++) {
                for (int j = 0; j < tiles[i].length; j++) {
                    gameWorld.addGameObject(this.get4DirectionalVariantAt(i, j).constructGameObject(tileSize, gameWorld, layer));
                }
            }
        } else {
            for (int i = 0; i < tiles.length; i++) {
                for (int j = 0; j < tiles[i].length; j++) {
                    gameWorld.addGameObject(this.get8DirectionalVariantAt(i, j).constructGameObject(tileSize, gameWorld, layer));
                }
            }
        }
    }

    private TileVariant get4DirectionalVariantAt(int i, int j){
        Tile t = this.tileTypes.get(tiles[i][j]);
        Set<String> variants = t.getVariants();
        if(j > 0){
            variants.retainAll(t.up.availableVariants(this.tileTypes.get(tiles[i][j-1]).type));
        }
        if(i < tiles.length - 1){
            variants.retainAll(t.right.availableVariants(this.tileTypes.get(tiles[i+1][j]).type));
        }
        if(j < tiles[0].length - 1){
            variants.retainAll(t.down.availableVariants(this.tileTypes.get(tiles[i][j+1]).type));
        }
        if(i > 0){
            variants.retainAll(t.left.availableVariants(this.tileTypes.get(tiles[i-1][j]).type));
        }
        if(variants.size() == 0){
            System.err.println("No Tile Options Left for tile: " + t.type + " at " + i + "," + j);
            return null;
        }
        return t.getVariant(variants.iterator().next());
    }

    private TileVariant get8DirectionalVariantAt(int i, int j){
        Tile t = this.tileTypes.get(tiles[i][j]);
        Set<String> variants = t.getVariants();
        if(j > 0){
            variants.retainAll(t.up.availableVariants(this.tileTypes.get(tiles[i][j-1]).type));
        }
        if(i < tiles.length - 1){
            variants.retainAll(t.right.availableVariants(this.tileTypes.get(tiles[i+1][j]).type));
        }
        if(j < tiles[0].length - 1){
            variants.retainAll(t.down.availableVariants(this.tileTypes.get(tiles[i][j+1]).type));
        }
        if(i > 0){
            variants.retainAll(t.left.availableVariants(this.tileTypes.get(tiles[i-1][j]).type));
        }

        if(j > 0 && i < tiles.length - 1){
            variants.retainAll(t.up_right.availableVariants(this.tileTypes.get(tiles[i+1][j-1]).type));
        }
        if(j < tiles[0].length - 1 && i < tiles.length - 1){
            variants.retainAll(t.down_right.availableVariants(this.tileTypes.get(tiles[i+1][j+1]).type));
        }
        if(j < tiles[0].length - 1 && i > 0){
            variants.retainAll(t.down_left.availableVariants(this.tileTypes.get(tiles[i-1][j+1]).type));
        }
        if(i > 0 && j > 0){
            variants.retainAll(t.up_left.availableVariants(this.tileTypes.get(tiles[i-1][j]).type));
        }
        if(variants.size() == 0){
            System.err.println("No Tile Options Left for tile: " + t.type + " at " + i + "," + j);
            return null;
        }
        return t.getVariant(variants.iterator().next());
    }



}
