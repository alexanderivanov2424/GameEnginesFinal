package projects.final_project.levels.tileMaps;

import engine.game.TileSystem.SpriteTileVariant;
import engine.game.TileSystem.Tile;
import engine.game.TileSystem.TileMap;
import engine.support.Vec2d;
import projects.final_project.FinalGame;

import java.util.ArrayList;

public class CaveTileMap {

    public static TileMap createTileMap(){
        ArrayList<Tile> tileTypes = new ArrayList<>();

        Vec2d TS = new Vec2d(24,24);

        //cave tile
        Tile caveTile = new Tile("cave", FinalGame.getSpritePath("cave_sprite_sheet"));
        caveTile.addVariant(new SpriteTileVariant(0,0,0,0,"edge_none", new Vec2d(4,14).smult(24), TS));

        caveTile.setAllVariantCollisionThickness(.1);
        tileTypes.add(caveTile);

        //wall tile
        Tile wallTile = new Tile("wall", FinalGame.getSpritePath("cave_sprite_sheet"));
        wallTile.addVariant(new SpriteTileVariant(1,1,1,1,"corner_br", new Vec2d(4,10).smult(24), TS));
        wallTile.addVariant(new SpriteTileVariant(1,1,1,1,"corner_bl", new Vec2d(6,10).smult(24), TS));
        wallTile.addVariant(new SpriteTileVariant(1,1,1,1,"corner_tr", new Vec2d(4,12).smult(24), TS));
        wallTile.addVariant(new SpriteTileVariant(1,1,1,1,"corner_tl", new Vec2d(6,12).smult(24), TS));

        wallTile.addVariant(new SpriteTileVariant(1,1,1,1,"wcorner_br", new Vec2d(7,10).smult(24), TS));
        wallTile.addVariant(new SpriteTileVariant(1,1,1,1,"wcorner_bl", new Vec2d(8,10).smult(24), TS));
        wallTile.addVariant(new SpriteTileVariant(1,1,1,1,"wcorner_tr", new Vec2d(7,11).smult(24), TS));
        wallTile.addVariant(new SpriteTileVariant(1,1,1,1,"wcorner_tl", new Vec2d(8,11).smult(24), TS));

        wallTile.addVariant(new SpriteTileVariant(1,1,1,1,"edge_l", new Vec2d(6,11).smult(24), TS));
        wallTile.addVariant(new SpriteTileVariant(1,1,1,1,"edge_r", new Vec2d(4,11).smult(24), TS));
        wallTile.addVariant(new SpriteTileVariant(1,1,1,1,"edge_t", new Vec2d(5,12).smult(24), TS));
        wallTile.addVariant(new SpriteTileVariant(1,1,1,1,"edge_b", new Vec2d(5,10).smult(24), TS));

        wallTile.addVariant(new SpriteTileVariant(1,1,1,1,"inner", new Vec2d(2,15).smult(24), TS));

        wallTile.set8DirectionRestrictions(
                (String type, int height)->{
                    if(type.equals("cave")) return new String[]{"edge_t", "wcorner_br", "wcorner_bl"};
                    return new String[]{"inner", "edge_b", "edge_r", "edge_l", "corner_br", "corner_bl", "corner_tr", "corner_tl", "wcorner_tr", "wcorner_tl"};
                },
                (String type, int height)->{
                    if(type.equals("cave")) return new String[]{"edge_r", "wcorner_bl", "wcorner_tl"};
                    return new String[]{"inner", "edge_b", "edge_t", "edge_l", "corner_br", "corner_bl", "corner_tr", "corner_tl", "wcorner_tr", "wcorner_br"};
                },
                (String type, int height)->{
                    if(type.equals("cave")) return new String[]{"edge_b", "wcorner_tr", "wcorner_tl"};
                    return new String[]{"inner", "edge_t", "edge_r", "edge_l", "corner_br", "corner_bl", "corner_tr", "corner_tl", "wcorner_br", "wcorner_bl"};
                },
                (String type, int height)->{
                    if(type.equals("cave")) return new String[]{"edge_l", "wcorner_br", "wcorner_tr"};
                    return new String[]{"inner", "edge_b", "edge_r", "edge_t", "corner_br", "corner_bl", "corner_tr", "corner_tl", "wcorner_tl", "wcorner_bl"};
                },
                (String type, int height)->{
                    if(type.equals("cave")) return new String[]{"corner_tr", "wcorner_bl", "wcorner_br", "wcorner_tl"};
                    return new String[]{"inner", "edge_b", "edge_r", "edge_t", "edge_l", "corner_bl", "corner_br", "corner_tl", "wcorner_tr"};
                },
                (String type, int height)->{
                    if(type.equals("cave")) return new String[]{"corner_br", "wcorner_br", "wcorner_tr", "wcorner_tl"};
                    return new String[]{"inner", "edge_b", "edge_r", "edge_t", "edge_l", "corner_bl", "corner_tr", "corner_tl", "wcorner_br"};
                },
                (String type, int height)->{
                    if(type.equals("cave")) return new String[]{"corner_bl", "wcorner_br", "wcorner_tr", "wcorner_tl"};
                    return new String[]{"inner", "edge_b", "edge_r", "edge_t", "edge_l", "corner_tr", "corner_br", "corner_tl", "wcorner_bl"};
                },
                (String type, int height)->{
                    if(type.equals("cave")) return new String[]{"corner_tl", "wcorner_br", "wcorner_tr", "wcorner_bl"};
                    return new String[]{"inner", "edge_b", "edge_r", "edge_t", "edge_l", "corner_bl", "corner_br", "corner_tr", "wcorner_tl"};
                });

        wallTile.setAllVariantCollisionThickness(.1);
        tileTypes.add(wallTile);

        return new TileMap(tileTypes, true);
    }
}
