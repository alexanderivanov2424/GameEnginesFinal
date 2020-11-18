package projects.final_project;

import engine.game.TileSystem.SpriteTileVariant;
import engine.game.TileSystem.Tile;
import engine.game.TileSystem.TileMap;
import engine.support.Vec2d;

import java.util.ArrayList;

public class LevelTileMaps {

    public static String[][] getTestingLevel(){



        return null;
    }


    public static TileMap createTileMap(){
        ArrayList<Tile> tileTypes = new ArrayList<Tile>();

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
        tileTypes.add(grassTile);

        //wall tile
        Tile wallTile = new Tile("grass", Game.getSpritePath("tile_sprite_sheet"));
        wallTile.addVariant(new SpriteTileVariant("up_lr", new Vec2d(3,1).smult(32), TS));
        wallTile.addVariant(new SpriteTileVariant("down_lr", new Vec2d(3,2).smult(32), TS));

        wallTile.addVariant(new SpriteTileVariant("up_l", new Vec2d(3,3).smult(32), TS));
        wallTile.addVariant(new SpriteTileVariant("up", new Vec2d(4,3).smult(32), TS));
        wallTile.addVariant(new SpriteTileVariant("up_r", new Vec2d(5,3).smult(32), TS));

        wallTile.addVariant(new SpriteTileVariant("down_l", new Vec2d(3,4).smult(32), TS));
        wallTile.addVariant(new SpriteTileVariant("down", new Vec2d(4,4).smult(32), TS));
        wallTile.addVariant(new SpriteTileVariant("down_r", new Vec2d(5,4).smult(32), TS));
        tileTypes.add(wallTile);


        TileMap tileMap = new TileMap(tileTypes, true);
        return tileMap;
    }
}
