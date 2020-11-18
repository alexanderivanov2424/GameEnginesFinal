package projects.nin;

import engine.UIToolKit.UIViewport;
import engine.game.GameObject;
import engine.game.GameWorld;
import engine.game.SpriteLoader;
import engine.game.collisionShapes.AABShape;
import engine.game.collisionShapes.PolygonShape;
import engine.game.collisionShapes.Ray;
import engine.game.collisionShapes.Shape;
import engine.game.components.*;
import engine.game.systems.CollisionSystem;
import engine.support.Vec2d;

import java.io.File;

public class NinGame {

    /*
    Layer 1 | Platforms
    Layer 2 | Player
    Layer 3 | Enemies / Objects
    Layer 4 |
     */

    private static final int PLAYER_LAYER = CollisionSystem.CollisionMask.ALL;
    private static final int PLAYER_MASK = CollisionSystem.CollisionMask.ALL;

    private static final int PLATFORM_LAYER = CollisionSystem.CollisionMask.ALL;
    private static final int PLATFORM_MASK = CollisionSystem.CollisionMask.ALL;

    private static final int BLOCK_LAYER = CollisionSystem.CollisionMask.ALL;
    private static final int BLOCK_MASK = CollisionSystem.CollisionMask.ALL;

    public GameWorld gameWorld;
    private UIViewport viewport;
    private SpriteLoader spriteLoader;

    private boolean beamON = false;

    private int difficulty = 0;

    private long seed = 0;

    private static final Vec2d GRAVITY = new Vec2d(0,500);



    public NinGame(GameWorld gameWorld, UIViewport viewport){
        this.gameWorld = gameWorld;
        this.viewport = viewport;

        spriteLoader = new SpriteLoader();
        gameWorld.setSpriteLoader(spriteLoader);

        gameWorld.linkViewport(0, viewport);
    }

    public void init(){
        this.gameWorld.addGameObject(createPlayer(new Vec2d(2,-3)));

        this.gameWorld.addGameObject(createBumpPlatform(new Vec2d(-6, 4)));
        for(int i = 0; i < 1000; i+=9){
            this.gameWorld.addGameObject(createPlatform(new Vec2d(i,4 + 5 * Math.sin(i/20.0))));
        }
        for(int i = 0; i < 1000; i+=9){
            if(Math.random() < .5){
                this.gameWorld.addGameObject(createBox(Math.random(), new Vec2d(i+5,-2)));
            }
        }
        this.gameWorld.addGameObject(createBox(Math.random(), new Vec2d(5,-2)));
        this.gameWorld.addGameObject(createBox(.5, new Vec2d(15,-2)));
        this.gameWorld.addGameObject(createBox(.9, new Vec2d(25,-2)));
    }

    public void restart(){
        this.gameWorld.clearAllGameObjects();
        this.init();
    }

    public void saveToXML(String saveName){
        File folder = new File(".\\projects\\nin\\saves\\");
        File xml = new File(folder, saveName.concat(".xml"));
        this.gameWorld.saveToXMLFile(xml.toString());
    }

    public void loadFromXML(String saveName){
        File folder = new File(".\\projects\\nin\\saves\\");
        File xml = new File(folder, saveName.concat(".xml"));
        this.gameWorld.loadFromXML(xml.toString(), NinGame::reloadCallBack);
    }

    /*
     * converts a string into the path to the corresponding png file.
     */
    public static String getSpritePath(String name){
        File folder = new File("file:.\\projects\\nin\\assets\\");
        File sprite = new File(folder, name.concat(".png"));
        return sprite.toString();
    }


    public GameObject createPlayer(Vec2d pos){
        GameObject player = new GameObject(this.gameWorld, 1);
        PhysicsComponent physicsComponent = new PhysicsComponent(player,
                new AABShape(new Vec2d(.5,0), new Vec2d(1,2)), 10, 0, false,
                PLAYER_LAYER, PLAYER_MASK);
        player.addComponent(physicsComponent);
        player.addComponent(new ConstantForceComponent(player, GRAVITY, physicsComponent));
        player.addComponent(new PlayerMovementComponent(player));
        player.addComponent(new IDComponent(player, "player"));
        player.addComponent(new SpriteAnimationComponent(player, getSpritePath("player"),
                new Vec2d(0,0), new Vec2d(2,2),
                8, new Vec2d(0,257), new Vec2d(64,64), .3));

        RayComponent rayComponent = new RayComponent(player, new Ray(pos, new Vec2d(1,0)), true);
        player.addComponent(rayComponent);

        RaySpriteAnimationComponent animation = new RaySpriteAnimationComponent(player, rayComponent, getSpritePath("beam"),
                new Vec2d(0,0), new Vec2d(2,2), new Vec2d(0,0), new Vec2d(64,64),
                new Vec2d(0,64), new Vec2d(64,64),  new Vec2d(0,128), new Vec2d(64,64), 3, .1);
        player.addComponent(animation);

        player.addComponent(new CameraComponent(player, 0));
        player.getTransform().position = pos;
        player.getTransform().size = new Vec2d(2,2);
        return player;
    }



    public GameObject createPlatform(Vec2d pos){
        GameObject platform = new GameObject(this.gameWorld,0);
        PhysicsComponent physicsComponent = new PhysicsComponent(platform,
                new AABShape(new Vec2d(.5,.7), new Vec2d(9,1)), 10, .9,true,
                PLATFORM_LAYER, PLATFORM_MASK);
        physicsComponent.linkCollisionCallback(NinGame::platformCollisionCallback);
        platform.addComponent(physicsComponent);

        platform.addComponent(new SpriteComponent(platform, getSpritePath("platform"),
                new Vec2d(0,0), new Vec2d(10,2)));
        platform.addComponent(new IDComponent(platform, "platform"));
        platform.getTransform().position = pos;
        return platform;
    }

    private GameObject createBox(double r, Vec2d pos){
        GameObject box = new GameObject(this.gameWorld, 1);
        box.addComponent(new SpriteComponent(box, this.getSpritePath("rocks"),
                new Vec2d(0,0), new Vec2d(2,2)));

        Shape s = new PolygonShape(new Vec2d(1,.1), new Vec2d(0,2), new Vec2d(2,2));
        //new AABShape(new Vec2d(0,0), new Vec2d(2,2))
        PhysicsComponent physicsComponent = new PhysicsComponent(box,
                s, 10, r, false,
                BLOCK_LAYER, BLOCK_MASK);
        box.addComponent(physicsComponent);
        box.addComponent(new ConstantForceComponent(box, GRAVITY, physicsComponent));

        HealthComponent healthComponent = new HealthComponent(box,10);
        healthComponent.linkDeathCallback(NinGame::breakRocksCallback);
        box.addComponent(healthComponent);

        box.addComponent(new IDComponent(box, "box"));
        box.getTransform().position = pos;
        box.getTransform().size = new Vec2d(2,2);
        return box;
    }

    public GameObject createBumpPlatform(Vec2d pos){
        GameObject platform = new GameObject(this.gameWorld,0);
        PhysicsComponent physicsComponent = new PhysicsComponent(platform,
                new PolygonShape(new Vec2d(0,1), new Vec2d(0,2), new Vec2d(8,2),
                        new Vec2d(8,1), new Vec2d(4,0)),
                10, .001,true, PLATFORM_LAYER, PLATFORM_MASK);
        physicsComponent.linkCollisionCallback(NinGame::platformCollisionCallback);
        platform.addComponent(physicsComponent);

        platform.addComponent(new SpriteComponent(platform, getSpritePath("bump_platform"),
                new Vec2d(0,0), new Vec2d(10,2)));
        platform.addComponent(new IDComponent(platform, "platform"));
        platform.getTransform().position = pos;
        return platform;
    }


    public static void platformCollisionCallback(CollisionSystem.CollisionInfo collisionInfo){
        PlayerMovementComponent movementComponent = (PlayerMovementComponent) collisionInfo.gameObjectOther.getComponent("PlayerMovementComponent");
        if(movementComponent != null){
            movementComponent.onGround = true;
        }
    }

    public static void breakRocksCallback(GameObject g){
        g.gameWorld.removeGameObject(g);
    }


    public static void reloadCallBack(Component c){
        IDComponent id = (IDComponent) c.getGameObject().getComponent("IDComponent");
        if(id != null){
            if(id.getId().equals("platform")){
                PhysicsComponent p = (PhysicsComponent)c;
                p.linkCollisionCallback(NinGame::platformCollisionCallback);
            }
            if(id.getId().equals("player")){
                if(c instanceof  RaySpriteAnimationComponent) {
                    RaySpriteAnimationComponent animation = (RaySpriteAnimationComponent) c;
                    animation.setRayComponent((RayComponent) c.getGameObject().getComponent("RayComponent"));
                }
            }
            if(id.getId().equals("box")){
                if(c instanceof  HealthComponent) {
                    HealthComponent health = (HealthComponent) c;
                    health.linkDeathCallback(NinGame::breakRocksCallback);
                }
            }
        }
    }
}
