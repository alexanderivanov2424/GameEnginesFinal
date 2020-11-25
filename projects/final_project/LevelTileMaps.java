package projects.final_project;

import engine.game.GameObject;
import engine.game.GameWorld;
import engine.game.TileSystem.SpriteTileVariant;
import engine.game.TileSystem.Tile;
import engine.game.TileSystem.TileMap;
import engine.game.components.LateRectComponent;
import engine.game.components.ProximityComponent;
import engine.support.Vec2d;
import javafx.scene.paint.Color;
import projects.WizTesting.WizLevelGenerator;

import java.util.ArrayList;
import java.util.logging.Level;

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
        Tile grassTile = new Tile("grass", FinalGame.getSpritePath("tile_sprite_sheet"));
        grassTile.addVariant(new SpriteTileVariant(1,0,0,1,"edge_tl", new Vec2d(0,1).smult(32), TS));
        grassTile.addVariant(new SpriteTileVariant(1,0,0,0,"edge_t", new Vec2d(1,1).smult(32), TS));
        grassTile.addVariant(new SpriteTileVariant(1,1,0,0,"edge_tr", new Vec2d(2,1).smult(32), TS));
        grassTile.addVariant(new SpriteTileVariant(0,0,0,1,"edge_l", new Vec2d(0,2).smult(32), TS));
        grassTile.addVariant(new SpriteTileVariant(0,0,0,0,"edge_none", new Vec2d(1,2).smult(32), TS));
        grassTile.addVariant(new SpriteTileVariant(0,1,0,0,"edge_r", new Vec2d(2,2).smult(32), TS));
        grassTile.addVariant(new SpriteTileVariant(0,0,1,1,"edge_bl", new Vec2d(0,3).smult(32), TS));
        grassTile.addVariant(new SpriteTileVariant(0,0,1,0,"edge_b", new Vec2d(1,3).smult(32), TS));
        grassTile.addVariant(new SpriteTileVariant(0,1,1,0,"edge_br", new Vec2d(2,3).smult(32), TS));

        grassTile.addVariant(new SpriteTileVariant(0,1,0,1,"edge_lr", new Vec2d(0,4).smult(32), TS));
        grassTile.addVariant(new SpriteTileVariant(1,1,0,1,"edge_ltr", new Vec2d(1,4).smult(32), TS));
        grassTile.addVariant(new SpriteTileVariant(1,0,1,0,"edge_tb", new Vec2d(2,4).smult(32), TS));
        grassTile.addVariant(new SpriteTileVariant(1,0,1,1,"edge_ltb", new Vec2d(0,5).smult(32), TS));
        grassTile.addVariant(new SpriteTileVariant(1,1,1,1,"edge_ltrb", new Vec2d(1,5).smult(32), TS));
        grassTile.addVariant(new SpriteTileVariant(1,1,1,0,"edge_trb", new Vec2d(2,5).smult(32), TS));
        grassTile.addVariant(new SpriteTileVariant(0,1,1,1,"edge_lrb", new Vec2d(1,6).smult(32), TS));

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
        grassTile.setAllVariantCollisionThickness(.1);
        tileTypes.add(grassTile);

        //wall tile
        Tile wallTile = new Tile("wall", FinalGame.getSpritePath("tile_sprite_sheet"));
        wallTile.addVariant(new SpriteTileVariant(1,1,1,1,"up_lr", new Vec2d(3,1).smult(32), TS));
        wallTile.addVariant(new SpriteTileVariant(1,1,1,1,"down_lr", new Vec2d(3,2).smult(32), TS));

        wallTile.addVariant(new SpriteTileVariant(1,1,1,1,"up_l", new Vec2d(3,3).smult(32), TS));
        wallTile.addVariant(new SpriteTileVariant(1,1,1,1,"up", new Vec2d(4,3).smult(32), TS));
        wallTile.addVariant(new SpriteTileVariant(1,1,1,1,"up_r", new Vec2d(5,3).smult(32), TS));

        wallTile.addVariant(new SpriteTileVariant(1,1,1,1,"down_l", new Vec2d(3,4).smult(32), TS));
        wallTile.addVariant(new SpriteTileVariant(1,1,1,1,"down", new Vec2d(4,4).smult(32), TS));
        wallTile.addVariant(new SpriteTileVariant(1,1,1,1,"down_r", new Vec2d(5,4).smult(32), TS));

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
        wallTile.setAllVariantCollisionThickness(.1);
        tileTypes.add(wallTile);


        TileMap tileMap = new TileMap(tileTypes, true);
        return tileMap;
    }

    public static void setCaveLevel(TileMap tileMap, GameObject player, GameWorld gameWorld){
        int[][] tiles_int = new int[][]{
                {1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 1, 1},
                {1, 0, 0, 0, 0, 1, 1},
                {1, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1},
        };

        int[][] heights = new int[][]{
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
        };

        String[] index = new String[]{"cave", "wall"};
        String[][] tiles = new String[tiles_int.length][tiles_int[0].length];
        for(int i = 0; i < tiles_int.length; i++){
            for(int j = 0; j < tiles_int[0].length; j++){
                tiles[i][j] = index[tiles_int[i][j]];
                //TODO
                //addFogTile(gameWorld, new Vec2d(i,j), player);
            }
        }

        tileMap.setTiles(tiles);
        tileMap.setHeights(heights);
    }

    private static void addFogTile(GameWorld gameWorld, Vec2d pos, GameObject player) {
        GameObject fog = new GameObject(gameWorld);
        fog.getTransform().position = pos;
        fog.getTransform().size = new Vec2d(2,2);

        LateRectComponent lateRectComponent = new LateRectComponent(Color.rgb(0,0,0,0.8));
        lateRectComponent.setGameObject(fog);
        fog.addComponent(lateRectComponent);

        ProximityComponent proximityComponent = new ProximityComponent(player, 10);
        proximityComponent.setGameObject(fog);
        proximityComponent.linkProximityCallback(LevelTileMaps::fogBreakCallback);
        fog.addComponent(proximityComponent);

        gameWorld.addGameObject(fog);

        //every tick, set the fog's color(transparency) based on proximity to objects with
    }

    //Distance is less than 10.
    private static void fogBreakCallback(GameObject fogTile, double distance) {

        LateRectComponent fog = (LateRectComponent)fogTile.getComponent("LateRectComponent");
        //The shorter the distance, the smaller the opacity.

        if(distance < 6) {
            fog.setColor(Color.rgb(0,0,0,0));
        }
        else {
            //0.19 = .8/(10-6)
            fog.setColor(Color.rgb(0,0,0,(distance-6)*0.19));
        }

    }

    public static TileMap createCaveTileMap(){
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
