package engine.game.TileSystem;

import engine.game.GameObject;
import engine.game.GameWorld;
import engine.game.components.Component;
import engine.support.Vec2d;

import java.util.*;

public class TileMap {

    private Vec2d offset = new Vec2d(0,0);

    private String[][] tiles;
    private int[][] heightmap;

    private HashMap<Integer, List<Component>> tileComponents;

    private HashMap<String, Tile> tileTypes;

    private boolean fourDirectional;

    public TileMap(List<Tile> tileTypes, boolean fourDirectional){
        this.tileTypes = new HashMap<String, Tile>();
        this.tileComponents = new HashMap<Integer, List<Component>>();
        this.fourDirectional = fourDirectional;

        for(Tile t: tileTypes){
            this.tileTypes.put(t.type,t);
        }
    }

    public void setTiles(String[][] tiles){
        this.tiles = tiles;
    }

    public void setOffset(Vec2d offset){
        this.offset = offset;
    }

    public void addComponentToTile(int i, int j, Component c){
        if(this.tiles == null){
            System.err.println("Component not added at " + i + " " + j + ". Tiles need to be added first.");
            return;
        }
        if(this.tileComponents.get(i * this.tiles.length * j) == null){
            this.tileComponents.put(i * this.tiles.length * j, new ArrayList<Component>());
        }
        this.tileComponents.get(i * this.tiles.length * j).add(c);
    }

    public void setHeights(int[][] heightmap){
        this.heightmap = heightmap;
    }

    private int getHeight(int i, int j){
        if(this.heightmap == null) return 0;
        return this.heightmap[i][j];
    }

    public void addTilesToGameWorld(GameWorld gameWorld, int layer, double tileSize, int collisionLayer, int collisionMask){
        if(this.tiles == null){
            System.err.println("Tile Map Not Set");
            return;
        }
        if(this.fourDirectional) {
            for (int i = 0; i < tiles.length; i++) {
                for (int j = 0; j < tiles[i].length; j++) {
                    Tile t = this.tileTypes.get(tiles[i][j]);
                    TileVariant tv = this.get4DirectionalVariantAt(t, i, j);
                    GameObject tile = tv.constructGameObject(new Vec2d(j,i).smult(tileSize),
                            new Vec2d(tileSize,tileSize), t.getTileSheetPath(), gameWorld, layer);
                    for(Component c : tv.getCollisionComponents(tile, new Vec2d(tileSize,tileSize), collisionLayer, collisionMask)){
                        tile.addComponent(c);
                    }
                    List<Component> additionalComponents = this.tileComponents.get(i * this.tiles.length * j);
                    if(additionalComponents != null)
                    for(Component c : additionalComponents){
                        tile.addComponent(c);
                    }
                    gameWorld.addGameObject(tile);
                }
            }
        } else {
            for (int i = 0; i < tiles.length; i++) {
                for (int j = 0; j < tiles[i].length; j++) {
                    Tile t = this.tileTypes.get(tiles[i][j]);
                    TileVariant tv = this.get8DirectionalVariantAt(t, i, j);
                    GameObject tile = tv.constructGameObject(new Vec2d(j,i).smult(tileSize),
                            new Vec2d(tileSize,tileSize), t.getTileSheetPath(), gameWorld, layer);
                    for(Component c : tv.getCollisionComponents(tile, new Vec2d(tileSize,tileSize), collisionLayer, collisionMask)){
                        tile.addComponent(c);
                    }
                    List<Component> additionalComponents = this.tileComponents.get(i * this.tiles.length * j);
                    if(additionalComponents != null)
                        for(Component c : additionalComponents){
                            tile.addComponent(c);
                        }
                    gameWorld.addGameObject(tile);
                }
            }
        }
    }

    private TileVariant get4DirectionalVariantAt(Tile t, int i, int j){
        Set<String> variants = new HashSet<String>(Arrays.asList(t.getVariants()));
        if(j > 0){
            if(t.left != null) {
                String[] res = t.left.getVariantRestriction(this.tileTypes.get(tiles[i][j - 1]).type,
                        this.getHeight(i, j) - this.getHeight(i, j - 1));
                if(res != null)
                    variants.retainAll(Arrays.asList(res));
            }
        }
        if(i < tiles.length - 1){
            if(t.down != null) {
                String[] res = t.down.getVariantRestriction(this.tileTypes.get(tiles[i+1][j]).type,
                        this.getHeight(i, j) - this.getHeight(i+1, j));
                if(res != null)
                    variants.retainAll(Arrays.asList(res));
            }
        }
        if(j < tiles[0].length - 1){
            if(t.right != null) {
                String[] res = t.right.getVariantRestriction(this.tileTypes.get(tiles[i][j+1]).type,
                        this.getHeight(i, j) - this.getHeight(i, j+1));
                if(res != null)
                    variants.retainAll(Arrays.asList(res));
            }
        }
        if(i > 0){
            if(t.up != null) {
                String[] res = t.up.getVariantRestriction(this.tileTypes.get(tiles[i-1][j]).type,
                        this.getHeight(i, j) - this.getHeight(i-1, j));
                if(res != null)
                    variants.retainAll(Arrays.asList(res));
            }
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
            if(t.left != null) {
                String[] res = t.left.getVariantRestriction(this.tileTypes.get(tiles[i][j - 1]).type,
                        this.getHeight(i, j) - this.getHeight(i, j - 1));
                if(res != null)
                    variants.retainAll(Arrays.asList(res));
            }
        }
        if(i < tiles.length - 1){
            if(t.down != null) {
                String[] res = t.down.getVariantRestriction(this.tileTypes.get(tiles[i+1][j]).type,
                        this.getHeight(i, j) - this.getHeight(i+1, j));
                if(res != null)
                    variants.retainAll(Arrays.asList(res));
            }
        }
        if(j < tiles[0].length - 1){
            if(t.right != null) {
                String[] res = t.right.getVariantRestriction(this.tileTypes.get(tiles[i][j+1]).type,
                        this.getHeight(i, j) - this.getHeight(i, j+1));
                if(res != null)
                    variants.retainAll(Arrays.asList(res));
            }
        }
        if(i > 0){
            if(t.up != null) {
                String[] res = t.up.getVariantRestriction(this.tileTypes.get(tiles[i-1][j]).type,
                        this.getHeight(i, j) - this.getHeight(i-1, j));
                if(res != null)
                    variants.retainAll(Arrays.asList(res));
            }
        }
        if(j > 0 && i < tiles.length - 1){
            if(t.down_left != null) {
                String[] res = t.down_left.getVariantRestriction(this.tileTypes.get(tiles[i+1][j-1]).type,
                        this.getHeight(i, j) - this.getHeight(i+1, j-1));
                if(res != null)
                    variants.retainAll(Arrays.asList(res));
            }
        }
        if(j < tiles[0].length - 1 && i < tiles.length - 1){
            if(t.down_right != null) {
                String[] res = t.down_right.getVariantRestriction(this.tileTypes.get(tiles[i+1][j+1]).type,
                        this.getHeight(i, j) - this.getHeight(i+1, j+1));
                if(res != null)
                    variants.retainAll(Arrays.asList(res));
            }
        }
        if(j < tiles[0].length - 1 && i > 0){
            if(t.up_right != null) {
                String[] res = t.up_right.getVariantRestriction(this.tileTypes.get(tiles[i-1][j+1]).type,
                        this.getHeight(i, j) - this.getHeight(i-1, j+1));
                if(res != null)
                    variants.retainAll(Arrays.asList(res));
            }
        }
        if(i > 0 && j > 0){
            if(t.up_left != null) {
                String[] res = t.up_left.getVariantRestriction(this.tileTypes.get(tiles[i-1][j-1]).type,
                        this.getHeight(i, j) - this.getHeight(i-1, j-1));
                if(res != null)
                    variants.retainAll(Arrays.asList(res));
            }
        }
        if(variants.size() == 0){
            System.err.println("No Tile Options Left for tile: " + t.type + " at " + i + "," + j);
            return null;
        }
        return t.getVariant(variants.iterator().next());
    }



}
