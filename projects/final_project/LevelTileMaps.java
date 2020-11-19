package projects.final_project;

import engine.game.TileSystem.SpriteTileVariant;
import engine.game.TileSystem.Tile;
import engine.game.TileSystem.TileMap;
import engine.support.Vec2d;

import java.util.ArrayList;

public class LevelTileMaps {

    public static void setTestingLevel(TileMap tileMap){
        int[][] tiles_int = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 1, 0, 1, 0},
                {0, 0, 0, 1, 1, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        };

        int[][] heights = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 2, 1, 1, 0, 2, 0},
                {0, 0, 0, 1, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 1, 1, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        };

        String[] index = new String[]{"grass", "wall"};
        String[][] tiles = new String[tiles_int.length][tiles_int[0].length];
        for(int i = 0; i < tiles_int.length; i++){
            for(int j = 0; j < tiles_int[0].length; j++){
                tiles[i][j] = index[tiles_int[i][j]];
            }
        }

        tileMap.setTiles(tiles);
        tileMap.setHeights(heights);

    }


    public static TileMap createTileMap(){
        ArrayList<Tile> tileTypes = new ArrayList<>();

        Vec2d TS = new Vec2d(32,32);

        //grass tile
        Tile grassTile = new Tile("grass", Game.getSpritePath("tile_sprite_sheet"));
        grassTile.addVariant(new SpriteTileVariant("edge_tl", new Vec2d(0,1).smult(32), TS));
        grassTile.addVariant(new SpriteTileVariant("edge_t", new Vec2d(1,1).smult(32), TS));
        grassTile.addVariant(new SpriteTileVariant("edge_tr", new Vec2d(2,1).smult(32), TS));
        grassTile.addVariant(new SpriteTileVariant("edge_l", new Vec2d(0,2).smult(32), TS));
        grassTile.addVariant(new SpriteTileVariant("edge_none", new Vec2d(1,2).smult(32), TS));
        grassTile.addVariant(new SpriteTileVariant("edge_r", new Vec2d(2,2).smult(32), TS));
        grassTile.addVariant(new SpriteTileVariant("edge_bl", new Vec2d(0,3).smult(32), TS));
        grassTile.addVariant(new SpriteTileVariant("edge_b", new Vec2d(1,3).smult(32), TS));
        grassTile.addVariant(new SpriteTileVariant("edge_br", new Vec2d(2,3).smult(32), TS));

        grassTile.addVariant(new SpriteTileVariant("edge_lr", new Vec2d(0,4).smult(32), TS));
        grassTile.addVariant(new SpriteTileVariant("edge_ltr", new Vec2d(1,4).smult(32), TS));
        grassTile.addVariant(new SpriteTileVariant("edge_tb", new Vec2d(2,4).smult(32), TS));
        grassTile.addVariant(new SpriteTileVariant("edge_ltb", new Vec2d(0,5).smult(32), TS));
        grassTile.addVariant(new SpriteTileVariant("edge_ltrb", new Vec2d(1,5).smult(32), TS));
        grassTile.addVariant(new SpriteTileVariant("edge_trb", new Vec2d(2,5).smult(32), TS));
        grassTile.addVariant(new SpriteTileVariant("edge_lrb", new Vec2d(1,6).smult(32), TS));

        grassTile.set4DirectionRestrictions(
                (String type, int height)->{
                    if(height > 0)
                        return new String[]{"edge_tl","edge_t","edge_tr","edge_ltr","edge_tb","edge_ltb","edge_ltrb","edge_trb"};
                    return new String[]{"edge_l","edge_none","edge_r","edge_bl","edge_b","edge_br","edge_lr","edge_lrb"};
                },
                (String type, int height)->{
                    if(height > 0)
                        return new String[]{"edge_tr","edge_r","edge_br","edge_lr","edge_ltr","edge_ltrb","edge_trb","edge_lrb"};
                    return new String[]{"edge_tl","edge_t","edge_l","edge_none","edge_bl","edge_b","edge_tb","edge_ltb"};
                },
                (String type, int height)->{
                    if(height > 0)
                        return new String[]{"edge_bl","edge_b","edge_br","edge_tb","edge_ltb","edge_ltrb","edge_trb","edge_lrb"};
                    return new String[]{"edge_tl","edge_t","edge_tr","edge_l","edge_none","edge_r","edge_lr","edge_ltr"};
                },
                (String type, int height)->{
                    if(height > 0)
                        return new String[]{"edge_tl","edge_l","edge_bl","edge_lr","edge_ltr","edge_ltb","edge_ltrb","edge_lrb"};
                    return new String[]{"edge_t","edge_tr","edge_none","edge_r","edge_b","edge_br","edge_tb","edge_trb"};
                });
        tileTypes.add(grassTile);

        //wall tile
        Tile wallTile = new Tile("wall", Game.getSpritePath("tile_sprite_sheet"));
        wallTile.addVariant(new SpriteTileVariant("up_lr", new Vec2d(3,1).smult(32), TS));
        wallTile.addVariant(new SpriteTileVariant("down_lr", new Vec2d(3,2).smult(32), TS));

        wallTile.addVariant(new SpriteTileVariant("up_l", new Vec2d(3,3).smult(32), TS));
        wallTile.addVariant(new SpriteTileVariant("up", new Vec2d(4,3).smult(32), TS));
        wallTile.addVariant(new SpriteTileVariant("up_r", new Vec2d(5,3).smult(32), TS));

        wallTile.addVariant(new SpriteTileVariant("down_l", new Vec2d(3,4).smult(32), TS));
        wallTile.addVariant(new SpriteTileVariant("down", new Vec2d(4,4).smult(32), TS));
        wallTile.addVariant(new SpriteTileVariant("down_r", new Vec2d(5,4).smult(32), TS));

        wallTile.set4DirectionRestrictions(
                (String type, int height)->{
                    return new String[]{"up_lr","down_lr","up_l","up","up_r","down_l","down","down_r"};
                },
                (String type, int height)->{
                    if(height >= 0 && type.equals("grass"))
                        return new String[]{"up_lr","down_lr","up_r","down_r"};
                    return new String[]{"up_l","up","down_l","down"};
                },
                (String type, int height)->{
                    if(type.equals("wall")) return new String[]{"up_lr","up_l","up","up_r"};
                    if(type.equals("grass")) return new String[]{"down_lr","down_l","down","down_r"};
                    return new String[]{"up_lr","down_lr","up_l","up","up_r","down_l","down","down_r"};
                },
                (String type, int height)->{
                    if(height >= 0 && type.equals("grass"))
                        return new String[]{"up_lr","down_lr","up_l","down_l"};
                    return new String[]{"up","up_r","down","down_r"};
                });

        tileTypes.add(wallTile);


        TileMap tileMap = new TileMap(tileTypes, true);
        return tileMap;
    }
}
