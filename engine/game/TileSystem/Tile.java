package engine.game.TileSystem;

import engine.support.Vec2d;

import java.util.*;

public class Tile {


    public interface VariantRestriction{
        String[] getVariantRestriction(String type, int height);
    }


    private String tileSheetPath;


    public String type;
    private List<TileVariant> variants;

    public VariantRestriction up,right,down,left;
    public VariantRestriction up_right,down_right,down_left,up_left;

    public Tile(String type, String tileSheetPath){
        this.type = type;
        this.tileSheetPath = tileSheetPath;
        this.variants = new ArrayList<TileVariant>();
    }

    public Tile(String type, String tileSheetPath, List<TileVariant> variants){
        this.type = type;
        this.tileSheetPath = tileSheetPath;
        this.variants = variants;
    }

    public void addVariant(TileVariant variant){
        this.variants.add(variant);
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

    public String[] getVariants(){
        String[] variantNames = new String[this.variants.size()];
        for(int i =0; i < this.variants.size(); i++){
            variantNames[i] = this.variants.get(i).variantName;
        }
        return variantNames;
    }

    public TileVariant getVariant(String variant){
        for(TileVariant tv: this.variants){
            if(tv.variantName.equals(variant)){
                return tv;
            }
        }
        return null;
    }

    public String getTileSheetPath(){
        return tileSheetPath;
    }

}
