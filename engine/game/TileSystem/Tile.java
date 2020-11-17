package engine.game.TileSystem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Tile {

    //Restriction for 1 direction from given tile.
    public interface VariantRestriction{
        public Set<String> availableVariants(String adjacentType);
    }

    public String type;
    private Map<String, TileVariant> variants;

    public VariantRestriction up,right,down,left;
    public VariantRestriction up_right,down_right,down_left,up_left;

    public Tile(String type){
        this.type = type;
        this.variants = new HashMap<String, TileVariant>();
    }

    public Tile(String type, Map<String, TileVariant> variants){
        this.type = type;
        this.variants = variants;
    }

    public void addVariant(String name, TileVariant variant){
        this.variants.put(name,variant);
    }

    public void set4DirectionRestrictions(VariantRestriction up, VariantRestriction right,
                                          VariantRestriction down, VariantRestriction left){
        this.up = up;
        this.right = right;
        this.down = down;
        this.left = left;
    }

    public void set8DirectionRestrictions(VariantRestriction up, VariantRestriction right,
                                          VariantRestriction down, VariantRestriction left,
                                          VariantRestriction up_right, VariantRestriction down_right,
                                          VariantRestriction down_left, VariantRestriction up_left){
        this.up = up;
        this.right = right;
        this.down = down;
        this.left = left;
        this.up_right = up_right;
        this.down_right = down_right;
        this.down_left = down_left;
        this.up_left = up_left;
    }

    public Set<String> getVariants(){
        return this.variants.keySet();
    }

    public TileVariant getVariant(String variant){
        return this.variants.get(variant);
    }


}
