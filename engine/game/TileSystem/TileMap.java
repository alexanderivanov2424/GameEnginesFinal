package engine.game.TileSystem;

import engine.game.GameObject;
import engine.game.GameWorld;
import engine.support.Vec2d;

import java.util.*;

public class TileMap {

    private String[][] tiles;
    private int[][] heightmap;

    private HashMap<String, Tile> tileTypes;

    private boolean fourDirectional;

    public TileMap(List<Tile> tileTypes, boolean fourDirectional){
        this.tileTypes = new HashMap<String, Tile>();
        this.fourDirectional = fourDirectional;

        for(Tile t: tileTypes){
            this.tileTypes.put(t.type,t);
        }
    }

    public void setTiles(String[][] tiles){
        this.tiles = tiles;
    }

    public void setTiles(int[][] heightmap){
        this.heightmap = heightmap;
    }

    private int getHeight(int i, int j){
        if(this.heightmap == null) return 0;
        return this.heightmap[i][j];
    }

    public void addTilesToGameWorld(GameWorld gameWorld, int layer, Vec2d tileSize){
        if(this.tiles == null){
            System.err.println("Tile Map Not Set");
            return;
        }
        if(this.fourDirectional) {
            for (int i = 0; i < tiles.length; i++) {
                for (int j = 0; j < tiles[i].length; j++) {
                    Tile t = this.tileTypes.get(tiles[i][j]);
                    TileVariant tv = this.get4DirectionalVariantAt(t, i, j);
                    gameWorld.addGameObject(tv.constructGameObject(tileSize, t.getTileSheetPath(), gameWorld, layer));
                }
            }
        } else {
            for (int i = 0; i < tiles.length; i++) {
                for (int j = 0; j < tiles[i].length; j++) {
                    Tile t = this.tileTypes.get(tiles[i][j]);
                    TileVariant tv = this.get8DirectionalVariantAt(t, i, j);
                    gameWorld.addGameObject(tv.constructGameObject(tileSize, t.getTileSheetPath(), gameWorld, layer));
                }
            }
        }
    }

    private TileVariant get4DirectionalVariantAt(Tile t, int i, int j){
        Set<String> variants = new HashSet<String>(Arrays.asList(t.getVariants()));
        if(j > 0){
            variants.retainAll(Arrays.asList(
                    t.up.getVariantRestriction(this.tileTypes.get(tiles[i][j-1]).type, this.getHeight(i,j))));
        }
        if(i < tiles.length - 1){
            variants.retainAll(Arrays.asList(
                    t.right.getVariantRestriction(this.tileTypes.get(tiles[i+1][j]).type, this.getHeight(i,j))));
        }
        if(j < tiles[0].length - 1){
            variants.retainAll(Arrays.asList(
                    t.down.getVariantRestriction(this.tileTypes.get(tiles[i][j+1]).type, this.getHeight(i,j))));
        }
        if(i > 0){
            variants.retainAll(Arrays.asList(
                    t.left.getVariantRestriction(this.tileTypes.get(tiles[i-1][j]).type, this.getHeight(i,j))));
        }
        if(variants.size() == 0){
            System.err.println("No Tile Options Left for tile: " + t.type + " at " + i + "," + j);
            return null;
        }
        return t.getVariant(variants.iterator().next());
    }

    private TileVariant get8DirectionalVariantAt(Tile t, int i, int j){
        Set<String> variants = new HashSet<String>(Arrays.asList(t.getVariants()));
        if(j > 0){
            variants.retainAll(Arrays.asList(
                    t.up.getVariantRestriction(this.tileTypes.get(tiles[i][j-1]).type, this.getHeight(i,j))));
        }
        if(i < tiles.length - 1){
            variants.retainAll(Arrays.asList(
                    t.right.getVariantRestriction(this.tileTypes.get(tiles[i+1][j]).type, this.getHeight(i,j))));
        }
        if(j < tiles[0].length - 1){
            variants.retainAll(Arrays.asList(
                    t.down.getVariantRestriction(this.tileTypes.get(tiles[i][j+1]).type, this.getHeight(i,j))));
        }
        if(i > 0){
            variants.retainAll(Arrays.asList(
                    t.left.getVariantRestriction(this.tileTypes.get(tiles[i-1][j]).type, this.getHeight(i,j))));
        }

        if(j > 0 && i < tiles.length - 1){
            variants.retainAll(Arrays.asList(
                    t.up_right.getVariantRestriction(this.tileTypes.get(tiles[i+1][j-1]).type, this.getHeight(i,j))));
        }
        if(j < tiles[0].length - 1 && i < tiles.length - 1){
            variants.retainAll(Arrays.asList(
                    t.down_right.getVariantRestriction(this.tileTypes.get(tiles[i+1][j+1]).type, this.getHeight(i,j))));
        }
        if(j < tiles[0].length - 1 && i > 0){
            variants.retainAll(Arrays.asList(
                    t.down_left.getVariantRestriction(this.tileTypes.get(tiles[i-1][j+1]).type, this.getHeight(i,j))));
        }
        if(i > 0 && j > 0){
            variants.retainAll(Arrays.asList(
                    t.up_left.getVariantRestriction(this.tileTypes.get(tiles[i-1][j]).type, this.getHeight(i,j))));
        }
        if(variants.size() == 0){
            System.err.println("No Tile Options Left for tile: " + t.type + " at " + i + "," + j);
            return null;
        }
        return t.getVariant(variants.iterator().next());
    }



}
